package com.rentalapp.services;

import com.rentalapp.models.Property;
import com.rentalapp.models.PropertyImage;
import com.rentalapp.repositories.PropertyImageRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class ImagePropertyService {

    private final PropertyImageRepository propertyImageRepository;

    public ImagePropertyService(PropertyImageRepository propertyImageRepository) {
        this.propertyImageRepository = propertyImageRepository;
    }

    // Настраиваем путь загрузки изображений через параметр приложения
    @Value("${spring.application.name:default}")
    private String applicationName;

    // Метод для сохранения нескольких изображений
    public void saveImage(List<MultipartFile> files, Property property) throws IOException {
        // Директория для загрузки изображений
        String uploadDir = "C:/uploads/property/";

        for (MultipartFile file : files) {
            // Генерируем уникальное имя файла
            String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();

            // Определяем путь для загрузки файла
            Path uploadPath = Paths.get(uploadDir);
            Path filePath = uploadPath.resolve(fileName);

            // Создаем директории, если их еще нет
            Files.createDirectories(uploadPath);

            // Копируем файл в указанное место
            try (InputStream inputStream = file.getInputStream()) {
                Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
            }

            // Создаем объект PropertyImage для сохранения в базу данных
            PropertyImage image = new PropertyImage();
            image.setFileName(fileName);
            // Устанавливаем URL для доступа к изображению
            image.setImageUrl("/images/property/" + fileName);
            image.setProperty(property);
            propertyImageRepository.save(image); // Сохраняем изображение в базе данных
        }
    }

    // Метод для удаления изображения
    public void deleteImage(Long imageId) throws IOException {
        // Находим изображение по его ID
        PropertyImage image = propertyImageRepository.findById(imageId)
                .orElseThrow(() -> new FileNotFoundException("Photo not found with id " + imageId));

        // Удаляем файл изображения из файловой системы
        Path filePath = Paths.get("/static/photo/property", image.getFileName());
        Files.deleteIfExists(filePath);

        // Удаляем запись изображения из базы данных
        propertyImageRepository.delete(image);
    }
}
