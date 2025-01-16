package com.rentalapp.controllers;

import com.rentalapp.models.Property;
import com.rentalapp.models.Tag;
import com.rentalapp.services.PropertyService;
import com.rentalapp.services.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/properties")
@RequiredArgsConstructor
public class PropertyController {
    private final PropertyService propertyService;
    private final TagService tagService;

    // Обработчик для отображения страницы добавления нового объекта недвижимости
    @GetMapping("/add")
    public String getNewProperty(Model model) {
        model.addAttribute("property", new Property()); // Добавляем пустой объект Property для формы
        model.addAttribute("availableTags", tagService.getAllTags()); // Добавляем все доступные теги
        return "property/addProperty"; // Возвращаем шаблон страницы добавления объекта недвижимости
    }

    // Обработчик для обработки формы добавления нового объекта недвижимости
    @PostMapping("/add")
    public String addProperty(@ModelAttribute("property") Property property,
                              @RequestParam(name = "selectedTags", required = false) List<Long> selectedTagIds,
                              @RequestParam(name = "files", required = false) List<MultipartFile> files) throws IOException {

        // Передаем данные о недвижимости, выбранных тегах и загруженных файлах в сервис
        propertyService.addProperty(property, selectedTagIds, files);
        return "redirect:/home"; // Перенаправляем на главную страницу после успешного добавления
    }
}
