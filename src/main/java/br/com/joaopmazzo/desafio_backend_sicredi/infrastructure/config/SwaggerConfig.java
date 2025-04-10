package br.com.joaopmazzo.desafio_backend_sicredi.infrastructure.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("API de Votação - Sicredi")
                        .version("1.0.0")
                        .description("Documentação da API para o desafio backend do Sicredi"));
    }

}
