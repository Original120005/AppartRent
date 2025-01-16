package com.rentalapp.security;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Set;

@Component
@Slf4j
public class SecurityHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException {

        // Получаем роли пользователя из Authentication
        Set<String> roles = AuthorityUtils.authorityListToSet(authentication.getAuthorities());
        // Получаем имя пользователя из Authentication
        String username = authentication.getName();
        // Устанавливаем URL перенаправления по умолчанию
        String redirectUrl = "/home";

        // Проверяем роли и настраиваем соответствующий URL перенаправления
        if (roles.contains("ROLE_ADMIN")) {
            redirectUrl = "/admin/dashboard";
            log.info("Admin {} successfully logged in", username);
        } else if (roles.contains("ROLE_LANDLORD")) {
            redirectUrl = "/landlord/dashboard";
            log.info("Landlord {} successfully logged in", username);
        } else if (roles.contains("ROLE_TENANT")) {
            redirectUrl = "/home";
            log.info("Tenant {} successfully logged in", username);
        }

        // Сохраняем роль пользователя в сессии
        HttpSession session = request.getSession();
        session.setAttribute("userRole", roles.iterator().next());

        // Перенаправляем пользователя на соответствующую страницу
        response.sendRedirect(redirectUrl);
    }
}

