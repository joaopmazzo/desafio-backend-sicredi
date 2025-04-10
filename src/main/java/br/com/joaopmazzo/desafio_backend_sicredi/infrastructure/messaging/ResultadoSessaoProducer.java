package br.com.joaopmazzo.desafio_backend_sicredi.infrastructure.messaging;

import br.com.joaopmazzo.desafio_backend_sicredi.infrastructure.config.RabbitMQConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ResultadoSessaoProducer {

    private final RabbitTemplate rabbitTemplate;

    public void sendMessage(Object message) {
            rabbitTemplate
                    .convertAndSend(
                            RabbitMQConfig.EXCHANGE_NAME,
                            RabbitMQConfig.ROUTING_KEY,
                            message
                    );
    }

}
