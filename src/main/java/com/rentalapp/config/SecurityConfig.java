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
        http.csrf((csrf) -> csrf
                        .csrfTokenRepository(new HttpSessionCsrfTokenRepository()))
                .cors(cors -> cors.configurationSource(request -> {
                    var corsConfiguration = new CorsConfiguration();
                    corsConfiguration.setAllowedOriginPatterns(List.of("*"));
                    corsConfiguration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
                    corsConfiguration.setAllowedHeaders(List.of("*"));
                    corsConfiguration.setAllowCredentials(true);
                    return corsConfiguration;
                }))
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
                        ).permitAll()
                        .requestMatchers("/auth/registration", "/auth/**", "/home/**").permitAll()
                        .requestMatchers(
                                "/landlord/**",
                                "/properties/add/**",
                                "/properties/edit/**",
                                "/properties/delete/**"
                        ).hasRole("LANDLORD")
                        .requestMatchers(
                                "/tenant/**",
                                "/bookings/**",
                                "/reviews/**"
                        ).hasRole("TENANT")
                        .requestMatchers(
                                "/admin/**",
                                "/management/**"
                        ).hasRole("ADMIN")
                        .requestMatchers(
                                "/profile/**",
                                "/messages/**",
                                "/notifications/**",
                                "/user/profile/**"
                        ).hasAnyRole("TENANT", "LANDLORD", "ADMIN")
                        .anyRequest().authenticated()
                )
                .formLogin((login) -> login
                        .loginPage("/auth/login")
                        .loginProcessingUrl("/process_login")
                        .usernameParameter("email")
                        .successHandler((request, response, authentication) -> {
                            if ("XMLHttpRequest".equals(request.getHeader("X-Requested-With"))) {
                                response.setStatus(HttpServletResponse.SC_OK);
                                response.getWriter().write("success");
                            } else {
                                response.sendRedirect("/home");
                            }
                        })
                        .failureHandler((request, response, exception) -> {
                            if ("XMLHttpRequest".equals(request.getHeader("X-Requested-With"))) {
                                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                                response.getWriter().write("error");
                            } else {
                                response.sendRedirect("/auth/login?error");
                            }
                        })
                )
                .exceptionHandling(exception -> exception
                        .authenticationEntryPoint((request, response, authException) -> {
                            if ("XMLHttpRequest".equals(request.getHeader("X-Requested-With"))) {
                                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
                            } else {
                                response.sendRedirect("/home?loginRequired");
                            }
                        })
                )
                .logout(logout -> logout
                        .logoutUrl("/auth/logout")
                        .logoutSuccessUrl("/home?logout")
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID")
                        .permitAll()
                )
                .rememberMe(remember -> remember
                        .tokenValiditySeconds(86400)
                        .key("uniqueAndSecret")
                        .userDetailsService(personDetailsService)
                )
                .authenticationProvider(authenticationProvider());

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(personDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public RoleHierarchy roleHierarchy() {
        return RoleHierarchyImpl.fromHierarchy("""
            ROLE_ADMIN > ROLE_LANDLORD
            ROLE_ADMIN > ROLE_TENANT
            """);
    }

    @Bean
    public SecurityExpressionHandler<FilterInvocation> customWebSecurityExpressionHandler() {
        DefaultWebSecurityExpressionHandler handler = new DefaultWebSecurityExpressionHandler();
        handler.setRoleHierarchy(roleHierarchy());
        return handler;
    }
}