package com.example.main.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenApiConfig {
    
    @Bean
    public OpenAPI myOpenAPI() {
        Server devServer = new Server();
        devServer.setUrl("http://localhost:8080");
        devServer.setDescription("Development server");

        Contact contact = new Contact();
        contact.setName("API Support");
        contact.setEmail("support@example.com");

        Info info = new Info()
                .title("Room Reservation API")
                .version("1.0")
                .contact(contact)
                .description("API for managing room reservations");

        return new OpenAPI()
                .info(info)
                .servers(List.of(devServer));
    }
}