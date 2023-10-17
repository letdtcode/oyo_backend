package com.mascara.oyo_booking_backend.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * Created by: IntelliJ IDEA
 * User      : boyng
 * Date      : 01/10/2023
 * Time      : 3:47 CH
 * Filename  : SwaggerConfig
 */
@Configuration
public class SwaggerConfig {

    @Value("${mascara.openapi.dev-url}")
    private String mainURL;

    @Bean
    public OpenAPI myOpenAPI() {
        Server devServer = new Server();
        devServer.setUrl(mainURL);
        devServer.setDescription("Server URL in Development environment");

        Contact contact = new Contact();
        contact.setEmail("ndtforwork@gmail.com");
        contact.setName("Duc Thanh");
        contact.setUrl("https://www.ducthanh.com");

        License mitLicense = new License().name("MIT License").url("https://choosealicense.com/licenses/mit/");

        Info info = new Info()
                .title("Tutorial Management API")
                .version("1.0")
                .contact(contact)
                .description("This API exposes endpoints to manage tutorials.").termsOfService("https://www.bezkoder.com/terms")
                .license(mitLicense);

        return new OpenAPI().info(info).servers(List.of(devServer));
    }
}

