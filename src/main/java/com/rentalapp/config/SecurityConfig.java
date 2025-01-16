package com.rentalapp.config;


import com.rentalapp.security.SecurityHandler;
import com.rentalapp.services.PersonDetailsService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.expression.SecurityExpressionHandler;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.expression.DefaultWebSecurityExpressionHandler;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;
import org.springframework.web.cors.CorsConfiguration;

import java.util.List;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final PersonDetailsService personDetailsService;
    private final SecurityHandler securityHandler;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        // Настройка CSRF-защиты с использованием репозитория токенов сессии
        http.csrf((csrf) -> csrf
                        .csrfTokenRepository(new HttpSessionCsrfTokenRepository()))
                // Настройка CORS, разрешая доступ с любых источников
                .cors(cors -> cors.configurationSource(request -> {
                    var corsConfiguration = new CorsConfiguration();
                    corsConfiguration.setAllowedOriginPatterns(List.of("*")); // Разрешить запросы с любых доменов
                    corsConfiguration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS")); // Разрешённые HTTP-методы
                    corsConfiguration.setAllowedHeaders(List.of("*")); // Разрешённые заголовки
                    corsConfiguration.setAllowCredentials(true); // Разрешить передачу cookie
                    return corsConfiguration;
                }))
                // Настройка доступа к URL-адресам в зависимости от роли пользователя
                .authorizeHttpRequests(request -> request
                        .requestMatchers(
                                "/auth/registration",
                                "/auth/**",
                                "/search/**",
                                "/properties/view/**",
                                "/home/**",
                                "/css/**",
                                "/js/**",
                                "/photo/**",
                                "/*",
                                "/images/property/**"
                        ).permitAll() // Эти пути доступны без авторизации
                        .requestMatchers("/auth/registration", "/auth/**", "/home/**").permitAll()
                        .requestMatchers(
                                "/landlord/**",
                                "/properties/add/**",
                                "/properties/edit/**",
                                "/properties/delete/**"
                        ).hasRole("LANDLORD") // Эти пути доступны только арендодателям
                        .requestMatchers(
                                "/tenant/**",
                                "/bookings/**",
                                "/reviews/**"
                        ).hasRole("TENANT") // Эти пути доступны только арендаторам
                        .requestMatchers(
                                "/admin/**",
                                "/management/**"
                        ).hasRole("ADMIN") // Эти пути доступны только администраторам
                        .requestMatchers(
                                "/profile/**",
                                "/messages/**",
                                "/notifications/**",
                                "/user/profile/**"
                        ).hasAnyRole("TENANT", "LANDLORD", "ADMIN") // Эти пути доступны нескольким ролям
                        .anyRequest().authenticated() // Остальные запросы требуют авторизации
                )
                // Настройка формы авторизации
                .formLogin((login) -> login
                        .loginPage("/auth/login") // Указание страницы логина
                        .loginProcessingUrl("/process_login") // URL для обработки логина
                        .usernameParameter("email") // Поле, используемое для ввода имени пользователя
                        .successHandler((request, response, authentication) -> {
                            // Обработка успешного входа
                            if ("XMLHttpRequest".equals(request.getHeader("X-Requested-With"))) {
                                response.setStatus(HttpServletResponse.SC_OK);
                                response.getWriter().write("success");
                            } else {
                                response.sendRedirect("/home");
                            }
                        })
                        .failureHandler((request, response, exception) -> {
                            // Обработка ошибки входа
                            if ("XMLHttpRequest".equals(request.getHeader("X-Requested-With"))) {
                                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                                response.getWriter().write("error");
                            } else {
                                response.sendRedirect("/auth/login?error");
                            }
                        })
                )
                // Настройка обработки исключений
                .exceptionHandling(exception -> exception
                        .authenticationEntryPoint((request, response, authException) -> {
                            // Обработка неавторизованных запросов
                            if ("XMLHttpRequest".equals(request.getHeader("X-Requested-With"))) {
                                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
                            } else {
                                response.sendRedirect("/home?loginRequired");
                            }
                        })
                )
                // Настройка выхода из системы
                .logout(logout -> logout
                        .logoutUrl("/auth/logout") // URL для выхода
                        .logoutSuccessUrl("/home?logout") // Куда перенаправить после выхода
                        .invalidateHttpSession(true) // Инвалидация сессии
                        .deleteCookies("JSESSIONID") // Удаление cookie
                        .permitAll()
                )
                // Настройка "Запомнить меня"
                .rememberMe(remember -> remember
                        .tokenValiditySeconds(86400) // Срок действия токена
                        .key("uniqueAndSecret") // Секретный ключ
                        .userDetailsService(personDetailsService) // Сервис для загрузки пользователей
                )
                // Настройка провайдера аутентификации
                .authenticationProvider(authenticationProvider());

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        // Настройка кодировщика паролей (BCrypt)
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        // Настройка провайдера аутентификации с использованием personDetailsService
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(personDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public RoleHierarchy roleHierarchy() {
        // Настройка иерархии ролей (ADMIN > LANDLORD, ADMIN > TENANT)
        return RoleHierarchyImpl.fromHierarchy("""
            ROLE_ADMIN > ROLE_LANDLORD
            ROLE_ADMIN > ROLE_TENANT
            """);
    }

    @Bean
    public SecurityExpressionHandler<FilterInvocation> customWebSecurityExpressionHandler() {
        // Настройка обработчика выражений безопасности с учетом иерархии ролей
        DefaultWebSecurityExpressionHandler handler = new DefaultWebSecurityExpressionHandler();
        handler.setRoleHierarchy(roleHierarchy());
        return handler;
    }
}