package br.com.joaopmazzo.desafio_backend_sicredi.infrastructure.config;

import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

import static org.mockito.Mockito.mock;

@TestConfiguration
public class RabbitMQTestConfig {

    @Bean
    public RabbitTemplate rabbitTemplate() {
        return mock(RabbitTemplate.class);
    }

    @Bean
    public RabbitAdmin rabbitAdmin() {
        return mock(RabbitAdmin.class);
    }

}
