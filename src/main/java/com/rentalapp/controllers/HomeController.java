package com.rentalapp.controllers;

import com.rentalapp.models.Property;
import com.rentalapp.services.PropertyService;
import com.rentalapp.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/home")
@RequiredArgsConstructor
public class HomeController {

    private final PropertyService propertyService;
    private final UserService userService;

    // Обработчик для главной страницы
    @GetMapping()
    public String homePage(Model model,
                           @RequestParam(required = false) List<String> location,
                           @RequestParam(required = false) Integer roomCount,
                           @RequestParam(required = false) String priceSort,
                           @RequestParam(required = false) String areaSize,
                           @RequestParam(defaultValue = "0") int page,
                           @RequestParam(name = "loginRequired", required = false, defaultValue = "false") boolean loginRequired) {
        // Получаем страницу с объектами Property
        Page<Property> propertiesPage = propertyService.getProperties(location, roomCount, priceSort, areaSize, page);

        // Добавляем информацию о свойствах и страницах в модель
        model.addAttribute("properties", propertiesPage.getContent()); // Список объектов недвижимости
        model.addAttribute("currentPage", page); // Текущая страница
        model.addAttribute("totalPages", propertiesPage.getTotalPages()); // Общее количество страниц
        model.addAttribute("totalProperties", propertiesPage.getTotalElements()); // Общее количество объектов недвижимости

        // Добавляем параметры фильтрации в модель
        model.addAttribute("location", location);
        model.addAttribute("roomCount", roomCount);
        model.addAttribute("priceSort", priceSort);
        model.addAttribute("areaSize", areaSize);

        // Добавляем информацию об авторизации пользователя
        model.addAttribute("isAuthenticated", userService.isUserAuthenticated());
        model.addAttribute("userId", userService.getAuthenticatedUserId());

        // Проверяем, требуется ли вход в систему для доступа к странице
        boolean isLoginRequired = "true".equalsIgnoreCase(String.valueOf(loginRequired));
        if (isLoginRequired) {
            model.addAttribute("loginMessage", "Вам потрібно увійти для доступу до цієї сторінки."); // Сообщение о необходимости входа
            System.out.println("Користувач має увійти!"); // Логирование
            model.addAttribute("loginRequired", loginRequired);
        }

        return "index"; // Возвращаем шаблон главной страницы
    }

    // Обработчик для страницы ошибок
    @GetMapping("/error")
    public String errorPage() {
        return "errors/error404"; // Возвращаем шаблон страницы ошибки
    }
}
