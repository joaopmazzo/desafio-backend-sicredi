package br.com.joaopmazzo.desafio_backend_sicredi.infrastructure.messaging;

import br.com.joaopmazzo.desafio_backend_sicredi.application.dtos.response.ResultadoResponseDTO;
import br.com.joaopmazzo.desafio_backend_sicredi.infrastructure.config.RabbitMQConfig;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class ResultadoSessaoConsumer {

    @RabbitListener(queues = RabbitMQConfig.QUEUE_NAME)
    public void receiveSessaoResults(ResultadoResponseDTO message) {
        log.debug("Resultado da sessão de votação da pauta {} recebido: {}", message.tituloPauta(), message);
    }

}
