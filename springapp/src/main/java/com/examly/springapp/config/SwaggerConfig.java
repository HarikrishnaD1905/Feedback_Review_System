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
                .title("Feedback & Review System")
                .description("Hello there")
                .contact(new Contact()
                    .name("Igris")
                    .email("727724eucs079@skcet.ac.in")
                    .url("github_url"))
                .license(new License()
                    .name("Apache 2.0")
                    .url("https://www.apache.org/licenses/")));
    }
}
