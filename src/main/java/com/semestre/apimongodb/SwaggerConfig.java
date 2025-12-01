package com.semestre.apimongodb;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
      return new OpenAPI()
        .info(new Info()
          .title("API Pastelería1000Sabores")
          .version("1.0.0")
          .description("Documentación de la API Spring Boot")
          .contact(new Contact()
            .name("Diego Herrera & Joaquin Araya")
            .email("diego.herrera@profesor.duoc.cl")
          )
        );
    }
}