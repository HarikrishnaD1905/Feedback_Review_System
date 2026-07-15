package com.examly.springapp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI openAPI(){
        final String securitySchemeName = "bearerAuth";

        SecurityScheme bearerScheme = new SecurityScheme()
            .name("Authorization")
            .type(SecurityScheme.Type.HTTP)
            .scheme("bearer")
            .bearerFormat("JWT");

        return new OpenAPI()
            .components(new Components().addSecuritySchemes(securitySchemeName, bearerScheme))
            .addSecurityItem(new SecurityRequirement().addList(securitySchemeName))
            .info(new Info()
                .title("Feedback Management System API")
                .version("v1")
                .description("RESTful API for the Feedback Management System (FMS). "
                        + "Provides endpoints for public feedback submission, "
                        + "admin authentication, feedback management, categorization, "
                        + "dashboard statistics, and audit trail.")
                .contact(new Contact()
                    .name("FMS Admin")
                    .email("admin@feedbacksystem.com"))
                .license(new License()
                    .name("Apache 2.0")
                    .url("https://www.apache.org/licenses/")));
    }
}
