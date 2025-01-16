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
    // Метод для збереження кількох фото
    @Value("${spring.application.name:default}")
    private String applicationName;

    public void saveImage(List<MultipartFile> files, Property property) throws IOException {
        // Змінюємо шлях на C:/uploads/property/ або свій
        String uploadDir = "C:/uploads/property/";

        for (MultipartFile file : files) {
            String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();

            Path uploadPath = Paths.get(uploadDir);
            Path filePath = uploadPath.resolve(fileName);

            Files.createDirectories(uploadPath);

            try (InputStream inputStream = file.getInputStream()) {
                Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
            }

            PropertyImage image = new PropertyImage();
            image.setFileName(fileName);
            // Змінюємо URL щоб відповідав налаштуванням ResourceHandler
            image.setImageUrl("/images/property/" + fileName);
            image.setProperty(property);
            propertyImageRepository.save(image);
        }
    }
    public void deleteImage(Long imageId) throws IOException {
        // Знаходимо фото за його ID
        PropertyImage image = propertyImageRepository.findById(imageId)
                .orElseThrow(() -> new FileNotFoundException("Photo not found with id " + imageId));

        // Видаляємо файл з файлової системи
        Path filePath = Paths.get("/static/photo/property", image.getFileName());
        Files.deleteIfExists(filePath);

        // Видаляємо запис з бази даних
        propertyImageRepository.delete(image);
    }

}
