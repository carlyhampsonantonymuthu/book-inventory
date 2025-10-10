package com.example.bookinventory.config;


import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Swagger/OpenAPI Configuration
 *
 * Access Swagger UI at: http://localhost:8080/swagger-ui/index.html
 */
@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI bookInventoryOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("📚 Book Inventory Management API")
                        .description("REST API documentation for the Book Inventory Management System built with Spring Boot.")
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("Book Inventory Developer Team")
                                .email("support@bookinventory.com")
                                .url("https://github.com/your-username/book-inventory"))
                        .license(new License()
                                .name("Apache 2.0")
                                .url("http://springdoc.org"))
                );
    }
}
