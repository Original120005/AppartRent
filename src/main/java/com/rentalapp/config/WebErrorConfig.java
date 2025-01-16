package com.rentalapp.config;

import org.springframework.boot.web.server.ErrorPage;
import org.springframework.boot.web.server.ErrorPageRegistrar;
import org.springframework.boot.web.server.ErrorPageRegistry;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;

@Configuration
public class WebErrorConfig implements ErrorPageRegistrar {

    @Override
    public void registerErrorPages(ErrorPageRegistry registry) {
        // Настройка страницы ошибки для кода 404 (Страница не найдена)
        ErrorPage error404 = new ErrorPage(HttpStatus.NOT_FOUND, "/error");

        // Настройка страницы ошибки для кода 500 (Внутренняя ошибка сервера)
        ErrorPage error500 = new ErrorPage(HttpStatus.INTERNAL_SERVER_ERROR, "/error");

        // Настройка страницы ошибки для кода 403 (Доступ запрещен)
        ErrorPage error403 = new ErrorPage(HttpStatus.FORBIDDEN, "/error");

        // Регистрация страниц ошибок в реестре
        registry.addErrorPages(error404, error500, error403);
    }
}
