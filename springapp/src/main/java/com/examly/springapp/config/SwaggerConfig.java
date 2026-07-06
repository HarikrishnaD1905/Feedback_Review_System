package com.examly.springapp.config;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI openAPI(){
        return new OpenAPI()
            .info(new Info()
                .title("Feedback & Review System")
                .description("Hello there")
                .contact(new Contact()
                    .name("Igris")
                    .email("727724eucs079@skcet.ac.in")
                    .url("github_url"))
                .license(new License()
                    .name("Apache 2.0")
                    .url("https://www.apache.org/licenses/")))
            .servers(List.of(
                    new Server().url("http://localhost:8080")
            ));
    }
}
