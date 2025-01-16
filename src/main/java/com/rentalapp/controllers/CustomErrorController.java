package com.rentalapp.controllers;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CustomErrorController  implements ErrorController {

    // Обработчик для страницы ошибок
    @GetMapping("/error")
    public String errorPage(HttpServletRequest request) {
        // Получение статуса ошибки из запроса
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);

        if (status != null) {
            // Преобразование статуса ошибки в числовой код
            int statusCode = Integer.parseInt(status.toString());
            // Можно добавить логику обработки различных кодов ошибок здесь
        }

        return "errors/error404"; // Возвращает шаблон страницы ошибки (например, для 404)
    }

}
