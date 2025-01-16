package com.rentalapp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.thymeleaf.spring6.templateresolver.SpringResourceTemplateResolver;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // Добавление обработчика для ресурсов с URL-адресом "/images/**"
        // Ресурсы будут загружаться из указанной директории на локальном диске
        registry.addResourceHandler("/images/**")
                .addResourceLocations("file:C:/uploads/");

        // Добавление обработчика для ресурсов с URL-адресом "/photo/**"
        // Ресурсы будут загружаться из директории в classpath
        registry.addResourceHandler("/photo/**")
                .addResourceLocations("classpath:/static/photo/");
    }

    @Bean
    public SpringResourceTemplateResolver templateResolver() {
        // Настройка Thymeleaf-шаблонизатора
        SpringResourceTemplateResolver resolver = new SpringResourceTemplateResolver();
        resolver.setPrefix("classpath:/templates/"); // Указание папки с HTML-шаблонами
        resolver.setSuffix(".html"); // Указание расширения шаблонов
        return resolver;
    }
}

