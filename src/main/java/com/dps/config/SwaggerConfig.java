package com.dps.config;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI dpsOpenAPI() {

        return new OpenAPI()
                .info(new Info()
                        .title("DPS - Data Processing System API")
                        .description("Spring Boot API for Data Processing System")
                        .version("1.0")
                        .contact(new Contact()
                                .name("DPS Team")
                                .email("support@dps.com"))
                        .license(new License()
                                .name("Apache 2.0")))
                .externalDocs(new ExternalDocumentation()
                        .description("DPS Documentation"));
    }
}
