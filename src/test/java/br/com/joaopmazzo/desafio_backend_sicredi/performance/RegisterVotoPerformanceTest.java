package br.com.joaopmazzo.desafio_backend_sicredi.performance;

import br.com.joaopmazzo.desafio_backend_sicredi.application.dtos.request.VotoRequestDTO;
import br.com.joaopmazzo.desafio_backend_sicredi.application.dtos.response.VotoResponseDTO;
import br.com.joaopmazzo.desafio_backend_sicredi.domain.entities.AssociadoEntity;
import br.com.joaopmazzo.desafio_backend_sicredi.domain.entities.PautaEntity;
import br.com.joaopmazzo.desafio_backend_sicredi.domain.entities.SessaoEntity;
import br.com.joaopmazzo.desafio_backend_sicredi.domain.enums.EscolhaVotoEnum;
import br.com.joaopmazzo.desafio_backend_sicredi.domain.repositories.AssociadoRepository;
import br.com.joaopmazzo.desafio_backend_sicredi.domain.repositories.PautaRepository;
import br.com.joaopmazzo.desafio_backend_sicredi.domain.repositories.SessaoRepository;
import br.com.joaopmazzo.desafio_backend_sicredi.infrastructure.config.RabbitMQTestConfig;
import br.com.joaopmazzo.desafio_backend_sicredi.utils.CpfUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Import(RabbitMQTestConfig.class)
@ActiveProfiles("test")
public class RegisterVotoPerformanceTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private PautaRepository pautaRepository;

    @Autowired
    private SessaoRepository sessaoRepository;

    @Autowired
    private AssociadoRepository associadoRepository;

    private static final int THREAD_COUNT = 1000; // Número de requisições simultâneas

    @Test
    @DisplayName("Should handle 1000 concurrent Voto requests")
    void shouldHandleConcurrentVotoRequests() throws InterruptedException {
        // cria registros necessários para testes no banco
        PautaEntity pauta = pautaRepository.save(PautaEntity.builder()
                .titulo("Pauta de Teste Performance")
                .descricao("Descrição da pauta")
                .build());
        sessaoRepository.save(SessaoEntity.builder()
                .pauta(pauta)
                .inicio(LocalDateTime.now())
                .termino(LocalDateTime.now().plusMinutes(1))
                .build());
        List<AssociadoEntity> associados = IntStream.range(0, THREAD_COUNT)
                .mapToObj(i -> associadoRepository.save(AssociadoEntity.builder()
                        .documento(CpfUtils.generateValidCpf())
                        .ableToVote(true)
                        .build()))
                .toList();

        // Cria um ExecutorService para gerenciar as threads
        ExecutorService executorService = Executors.newFixedThreadPool(THREAD_COUNT);
        try {
            List<ResponseEntity<?>> responses = Collections.synchronizedList(new ArrayList<>());

            // Envia requisições simultâneas
            associados.forEach(associado -> executorService.submit(() -> {
                VotoRequestDTO request = new VotoRequestDTO(associado.getDocumento(), EscolhaVotoEnum.SIM);
                HttpEntity<VotoRequestDTO> httpEntity = new HttpEntity<>(request);
                ResponseEntity<VotoResponseDTO> response = restTemplate.exchange(
                        "/api/v1/pautas/" + pauta.getId() + "/voto",
                        HttpMethod.POST,
                        httpEntity,
                        VotoResponseDTO.class
                );
                responses.add(response);
            }));

            executorService.shutdown();
            boolean terminated = executorService.awaitTermination(10, TimeUnit.SECONDS); // Aguardando até 10 segundos para todas as tarefas serem concluídas

            // Verifica se todas as tarefas foram concluídas dentro do tempo limite
            if (!terminated) {
                throw new IllegalStateException("O tempo limite foi excedido antes de todas as tarefas serem concluídas.");
            }

            // Verifica se todas as requisições foram processadas com sucesso
            assertThat(responses).hasSize(THREAD_COUNT);
            responses.forEach(response -> assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED));
        } finally {
            if (!executorService.isTerminated()) {
                executorService.shutdownNow();
            }
        }
    }

}

