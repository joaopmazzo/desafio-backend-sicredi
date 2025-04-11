package br.com.joaopmazzo.desafio_backend_sicredi.infrastructure.messaging;

import br.com.joaopmazzo.desafio_backend_sicredi.application.dtos.enums.ResultadoVotacaoEnum;
import br.com.joaopmazzo.desafio_backend_sicredi.application.dtos.response.ResultadoResponseDTO;
import br.com.joaopmazzo.desafio_backend_sicredi.infrastructure.config.RabbitMQConfig;
import br.com.joaopmazzo.desafio_backend_sicredi.infrastructure.config.RabbitMQTestConfig;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.util.UUID;

import static org.mockito.Mockito.verify;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Import(RabbitMQTestConfig.class)
@ActiveProfiles("test")
class ResultadoSessaoProducerTest {

    @Autowired
    private ResultadoSessaoProducer producer;

    @MockitoBean
    private RabbitTemplate rabbitTemplate;

    @Test
    @DisplayName("Should send message to RabbitMQ with the correct exchange and routing key")
    void shouldSendAndConsumeMessage() throws InterruptedException {
        // Cria uma mensagem de teste
        ResultadoResponseDTO message = new ResultadoResponseDTO(
                UUID.randomUUID(),
                "pauta teste",
                10,
                5,
                15,
                ResultadoVotacaoEnum.DEFERIDA
        );

        // Envia a mensagem para a fila
        producer.sendMessage(message);

        verify(rabbitTemplate)
                .convertAndSend(
                        RabbitMQConfig.EXCHANGE_NAME,
                        RabbitMQConfig.ROUTING_KEY,
                        message
                );
    }
}