package com.rentalapp.services;

import com.rentalapp.models.*;
import com.rentalapp.repositories.PropertyRepository;
import com.rentalapp.repositories.TagRepository;
import com.rentalapp.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PropertyService {

    private final PropertyRepository propertyRepository;
    private final UserRepository userRepository;

    private final TagService tagService;
    private final UserService userService;
    private final ImagePropertyService imagePropertyService;

    // Метод для добавления нового объекта недвижимости
    public void addProperty(Property property, List<Long> selectedTagIds, List<MultipartFile> files) throws IOException {
        // Устанавливаем теги для объекта, если они были выбраны
        if (selectedTagIds != null) {
            List<Tag> selectedTags = tagService.getTagsByIds(selectedTagIds);
            property.setTags(selectedTags);
        }

        // Получаем текущего авторизованного пользователя
        User user = userRepository.findById(userService.getAuthenticatedUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Проверяем, является ли пользователь арендодателем
        if (user.getRole() != UserRole.ROLE_LANDLORD) {
            throw new RuntimeException("Only Landlords can add properties");
        }

        // Устанавливаем данные для объекта недвижимости
        property.setLandlord(user);
        property.setPublicationDate(LocalDate.now());
        property.setRating(0.0f);

        // Сохраняем объект в базе данных
        property = propertyRepository.save(property);

        // Сохраняем изображения, если они были загружены
        if (files != null && !files.isEmpty()) {
            imagePropertyService.saveImage(files, property);
        }
    }

    // Метод для получения объектов недвижимости с фильтрацией и пагинацией
    public Page<Property> getProperties(List<String> location, Integer roomCount, String priceSort, String areaSize, int page) {
        int pageSize = 5; // Количество элементов на странице
        Specification<Property> specification = Specification.where(null);

        // Фильтрация по локации
        if (location != null && !location.isEmpty()) {
            specification = specification.and((root, query, builder) -> root.get("location").in(location));
        }

        // Фильтрация по количеству комнат
        if (roomCount != null) {
            specification = specification.and((root, query, builder) -> builder.equal(root.get("roomCount"), roomCount));
        }

        // Фильтрация по площади
        if (areaSize != null) {
            switch (areaSize) {
                case "small":
                    specification = specification.and((root, query, builder) -> builder.lessThan(root.get("area"), 50));
                    break;
                case "medium":
                    specification = specification.and((root, query, builder) -> builder.between(root.get("area"), 50, 100));
                    break;
                case "large":
                    specification = specification.and((root, query, builder) -> builder.greaterThan(root.get("area"), 100));
                    break;
            }
        }

        // Сортировка по цене
        Pageable pageable;
        if ("high-to-low".equals(priceSort)) {
            pageable = PageRequest.of(page, pageSize, Sort.by(Sort.Order.desc("price")));
        } else {
            pageable = PageRequest.of(page, pageSize, Sort.by(Sort.Order.asc("price")));
        }

        // Возвращаем страницу объектов недвижимости с применением фильтров
        return propertyRepository.findAll(specification, pageable);
    }
}