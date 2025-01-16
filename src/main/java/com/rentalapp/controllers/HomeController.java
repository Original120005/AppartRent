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
    @GetMapping()
    public String homePage(Model model, @RequestParam(required = false) List<String> location,
                           @RequestParam(required = false) Integer roomCount,
                           @RequestParam(required = false) String priceSort,
                           @RequestParam(required = false) String areaSize,
                           @RequestParam(defaultValue = "0") int page,
                           @RequestParam(name = "loginRequired", required = false, defaultValue = "false") boolean loginRequired) {
        // Отримуємо Page<Property>
        Page<Property> propertiesPage = propertyService.getProperties(location, roomCount, priceSort, areaSize, page);

        // Додаємо базову інформацію про сторінки
        model.addAttribute("properties", propertiesPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", propertiesPage.getTotalPages());

        model.addAttribute("location", location);
        model.addAttribute("roomCount", roomCount);
        model.addAttribute("priceSort", priceSort);
        model.addAttribute("areaSize", areaSize);

        model.addAttribute("isAuthenticated", userService.isUserAuthenticated());
        model.addAttribute("userId", userService.getAuthenticatedUserId());

        boolean isLoginRequired = "true".equalsIgnoreCase(String.valueOf(loginRequired));
        if (isLoginRequired) {
            model.addAttribute("loginMessage", "Вам потрібно увійти для доступу до цієї сторінки.");
            System.out.println("Користувач має увійти!");
            model.addAttribute("loginRequired", loginRequired);
        }

        return "index";
    }
    @GetMapping("/error")
    public String errorPage(){
        return "errors/error404";
    }
}