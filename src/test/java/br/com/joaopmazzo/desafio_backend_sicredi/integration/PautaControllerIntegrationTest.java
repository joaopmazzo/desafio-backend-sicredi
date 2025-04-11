package br.com.joaopmazzo.desafio_backend_sicredi.integration;

import br.com.joaopmazzo.desafio_backend_sicredi.application.dtos.request.PautaRequestDTO;
import br.com.joaopmazzo.desafio_backend_sicredi.application.dtos.request.SessaoRequestDTO;
import br.com.joaopmazzo.desafio_backend_sicredi.application.dtos.request.VotoRequestDTO;
import br.com.joaopmazzo.desafio_backend_sicredi.application.dtos.response.PautaResponseDTO;
import br.com.joaopmazzo.desafio_backend_sicredi.application.dtos.response.ResultadoResponseDTO;
import br.com.joaopmazzo.desafio_backend_sicredi.application.dtos.response.SessaoResponseDTO;
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
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.within;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Import(RabbitMQTestConfig.class)
@ActiveProfiles("test")
public class PautaControllerIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private PautaRepository pautaRepository;

    @Autowired
    private SessaoRepository sessaoRepository;

    @Autowired
    private AssociadoRepository associadoRepository;

    PautaEntity pauta;
    PautaEntity pautaSaved;

    SessaoEntity sessaoSaved;

    AssociadoEntity associadoSaved;

    private static final Integer DURACAO_MINUTOS = 1;

    private static final String CPF = CpfUtils.generateValidCpf();

    @BeforeAll
    void setUp() {
        pauta = PautaEntity.builder()
                .titulo("Pauta de Teste 1")
                .descricao("Descrição da nova pauta 1")
                .build();

        pautaSaved = pautaRepository.save(PautaEntity.builder()
                .titulo("Pauta de Teste 2")
                .descricao("Descrição da nova pauta 2")
                .build());

        sessaoSaved = sessaoRepository.save(SessaoEntity.builder()
                .pauta(pautaSaved)
                .inicio(LocalDateTime.now())
                .termino(LocalDateTime.now().plusMinutes(DURACAO_MINUTOS))
                .build());

        associadoSaved = associadoRepository.save(AssociadoEntity.builder()
                .documento(CPF)
                .ableToVote(true)
                .build());
    }

    @Test
    void shouldCreatePautaWithSuccess() {
        PautaRequestDTO request = new PautaRequestDTO("Nova pauta", "Descrição da nova pauta");

        HttpEntity<PautaRequestDTO> httpEntity = new HttpEntity<>(request);

        ResponseEntity<PautaResponseDTO> response = restTemplate.exchange(
                "/api/v1/pautas",
                HttpMethod.POST,
                httpEntity,
                PautaResponseDTO.class
        );

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getId()).isNotNull();
        assertThat(response.getBody().getTitulo()).isEqualTo("Nova pauta");
        assertThat(response.getBody().getDescricao()).isEqualTo("Descrição da nova pauta");
        assertThat(response.getBody().getCriadoEm()).isNotNull();
    }

    @Test
    void shouldReturnAllPautasWithSuccess() {
        ResponseEntity<Page> response = restTemplate.exchange(
                "/api/v1/pautas?page=0&size=10",
                HttpMethod.GET,
                null,
                Page.class
        );

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    void shouldReturnPautaByIdWithSuccess() {
        ResponseEntity<PautaResponseDTO> response = restTemplate.exchange(
                "/api/v1/pautas/" + pautaSaved.getId(),
                HttpMethod.GET,
                null,
                PautaResponseDTO.class
        );

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getId()).isEqualTo(pautaSaved.getId());
    }

    @Test
    void shouldCreateSessaoWithSuccess() {
        PautaEntity newPautaSaved = pautaRepository.save(pauta);
        SessaoRequestDTO request = new SessaoRequestDTO(DURACAO_MINUTOS);

        HttpEntity<SessaoRequestDTO> httpEntity = new HttpEntity<>(request);

        ResponseEntity<SessaoResponseDTO> response = restTemplate.exchange(
                "/api/v1/pautas/" + newPautaSaved.getId() + "/sessao",
                HttpMethod.POST,
                httpEntity,
                SessaoResponseDTO.class
        );

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getId()).isNotNull();
        assertThat(response.getBody().getPauta().getId()).isEqualTo(newPautaSaved.getId());
        assertThat(response.getBody().getInicio()).isNotNull();
        assertThat(response.getBody().getTermino())
                .isCloseTo(response.getBody().getInicio().plusMinutes(DURACAO_MINUTOS), within(100, ChronoUnit.MILLIS));
    }

    @Test
    void shouldReturnSessaoByPautaIdWithSuccess() {
        ResponseEntity<SessaoResponseDTO> response = restTemplate.exchange(
                "/api/v1/pautas/" + pautaSaved.getId() + "/sessao",
                HttpMethod.GET,
                null,
                SessaoResponseDTO.class
        );

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getId()).isEqualTo(sessaoSaved.getId());
    }

    @Test
    void shouldRegisterVotoWithSuccess() {
        VotoRequestDTO request = new VotoRequestDTO(CPF, EscolhaVotoEnum.SIM);

        HttpEntity<VotoRequestDTO> httpEntity = new HttpEntity<>(request);

        ResponseEntity<VotoResponseDTO> response = restTemplate.exchange(
                "/api/v1/pautas/" + pautaSaved.getId() + "/voto",
                HttpMethod.POST,
                httpEntity,
                VotoResponseDTO.class
        );

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getId()).isNotNull();
        assertThat(response.getBody().getSessao().getId()).isEqualTo(sessaoSaved.getId());
        assertThat(response.getBody().getAssociado().getId()).isEqualTo(associadoSaved.getId());
        assertThat(response.getBody().getAssociado().isAbleToVote()).isEqualTo(true);
        assertThat(response.getBody().getEscolhaVoto()).isNotNull();
        assertThat(response.getBody().getRegistradoEm()).isNotNull();
    }

    @Test
    void shouldReturnResultadoByPautaIdWithSuccess() {
        ResponseEntity<ResultadoResponseDTO> response = restTemplate.exchange(
                "/api/v1/pautas/" + pautaSaved.getId() + "/resultado",
                HttpMethod.GET,
                null,
                ResultadoResponseDTO.class
        );

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
    }

}
