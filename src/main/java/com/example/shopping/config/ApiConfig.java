package com.example.shopping.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;

import java.util.List;


public class ApiConfig {
    @Value("${server.host}")
    private String url;

    @Bean
    public OpenAPI myOpenAPI() {
        Server devServer = new Server();
        devServer.url(url);
        devServer.description("Server URL");

        Contact contact = new Contact();
        contact.email("kun.uz");
        contact.name("BezKoder");
        contact.url("https://www.bezkoder.com");


        Info info = new Info()
                .title("Kun uz Management API")
                .version("1.0")
                .contact(contact)
                .description("This API exposes endpoints to manage tutorials.")
                .termsOfService("https://www.bezkoder.com/terms")
                .license(null);

        return new OpenAPI().info(info).servers(List.of(devServer));
    }
}
