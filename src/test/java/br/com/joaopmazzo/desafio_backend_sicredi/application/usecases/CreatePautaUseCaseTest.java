package br.com.joaopmazzo.desafio_backend_sicredi.application.usecases;

import br.com.joaopmazzo.desafio_backend_sicredi.application.dtos.request.PautaRequestDTO;
import br.com.joaopmazzo.desafio_backend_sicredi.application.dtos.response.PautaResponseDTO;
import br.com.joaopmazzo.desafio_backend_sicredi.application.exceptions.pauta.DuplicatePautaException;
import br.com.joaopmazzo.desafio_backend_sicredi.domain.entities.PautaEntity;
import br.com.joaopmazzo.desafio_backend_sicredi.domain.services.PautaService;
import br.com.joaopmazzo.desafio_backend_sicredi.infrastructure.mappers.PautaMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class CreatePautaUseCaseTest {

    @Mock
    private PautaService pautaService;

    @Mock
    private PautaMapper pautaMapper;

    @InjectMocks
    private CreatePautaUseCase createPautaUseCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Should create a Pauta successfully when valid data is provided")
    void shouldCreatePautaSuccessfully() {
        PautaRequestDTO requestDTO = new PautaRequestDTO("pauta teste 1", "descricao teste 1");
        PautaEntity entity = new PautaEntity();
        PautaEntity savedEntity = new PautaEntity();
        PautaResponseDTO responseDTO = PautaResponseDTO.builder()
                .id(UUID.randomUUID())
                .titulo("pauta teste 1")
                .descricao("descricao teste 1")
                .criadoEm(LocalDateTime.now())
                .build();

        when(pautaMapper.toEntity(requestDTO)).thenReturn(entity);
        when(pautaService.savePauta(entity)).thenReturn(savedEntity);
        when(pautaMapper.toResponseDTO(savedEntity)).thenReturn(responseDTO);

        PautaResponseDTO result = createPautaUseCase.execute(requestDTO);

        assertEquals(responseDTO, result);
        verify(pautaMapper).toEntity(requestDTO);
        verify(pautaService).savePauta(entity);
        verify(pautaMapper).toResponseDTO(savedEntity);
        assertNotNull(result.getId());
        assertNotNull(result.getCriadoEm());
    }

    @Test
    @DisplayName("Should throw DuplicatePautaException when a Pauta with the same title already exists")
    void shouldThrowDuplicatePautaExceptionWhenPautaWithSameTitleExists() {
        PautaRequestDTO requestDTO = new PautaRequestDTO("pauta duplicada", "descricao duplicada");

        when(pautaService.existsByTitulo(requestDTO.titulo())).thenThrow(new DuplicatePautaException());

        assertThrows(DuplicatePautaException.class, () -> createPautaUseCase.execute(requestDTO));

        verify(pautaService).existsByTitulo(requestDTO.titulo());
        verifyNoInteractions(pautaMapper);
        verifyNoMoreInteractions(pautaService);
    }

    @Test
    @DisplayName("Should throw exception when saving Pauta fails due to unexpected error")
    void shouldThrowExceptionWhenSavingPautaFails() {
        PautaRequestDTO requestDTO = new PautaRequestDTO("pauta erro", "descricao erro");
        PautaEntity entity = new PautaEntity();

        when(pautaService.existsByTitulo(requestDTO.titulo())).thenReturn(false);
        when(pautaMapper.toEntity(requestDTO)).thenReturn(entity);
        when(pautaService.savePauta(entity)).thenThrow(new RuntimeException("Unexpected error"));

        RuntimeException exception = assertThrows(RuntimeException.class, () -> createPautaUseCase.execute(requestDTO));

        verify(pautaService).existsByTitulo(requestDTO.titulo());
        verify(pautaMapper).toEntity(requestDTO);
        verify(pautaService).savePauta(entity);
        verifyNoMoreInteractions(pautaMapper);
    }

}
