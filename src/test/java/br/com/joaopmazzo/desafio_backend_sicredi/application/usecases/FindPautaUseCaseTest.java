package br.com.joaopmazzo.desafio_backend_sicredi.application.usecases;

import br.com.joaopmazzo.desafio_backend_sicredi.application.dtos.response.PautaResponseDTO;
import br.com.joaopmazzo.desafio_backend_sicredi.application.exceptions.pauta.PautaNotFoundException;
import br.com.joaopmazzo.desafio_backend_sicredi.domain.entities.PautaEntity;
import br.com.joaopmazzo.desafio_backend_sicredi.domain.services.PautaService;
import br.com.joaopmazzo.desafio_backend_sicredi.infrastructure.mappers.PautaMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class FindPautaUseCaseTest {

    @Mock
    private PautaService pautaService;

    @Mock
    private PautaMapper pautaMapper;

    @InjectMocks
    private FindPautaUseCase findPautaUseCase;

    @Test
    @DisplayName("Should return PautaResponseDTO when a valid ID is provided")
    void shouldReturnPautaResponseDTOWhenValidIdProvided() {
        UUID id = UUID.randomUUID();
        PautaEntity pautaEntity = new PautaEntity();
        PautaResponseDTO responseDTO = PautaResponseDTO.builder()
                .id(id)
                .titulo("Valid Pauta")
                .descricao("Valid Description")
                .build();

        when(pautaService.findPautaById(id)).thenReturn(pautaEntity);
        when(pautaMapper.toResponseDTO(pautaEntity)).thenReturn(responseDTO);

        PautaResponseDTO result = findPautaUseCase.execute(id);

        assertEquals(responseDTO, result);
        verify(pautaService).findPautaById(id);
        verify(pautaMapper).toResponseDTO(pautaEntity);
    }

    @Test
    @DisplayName("Should throw exception when Pauta with given ID is not found")
    void shouldThrowExceptionWhenPautaNotFound() {
        UUID id = UUID.randomUUID();

        when(pautaService.findPautaById(id)).thenThrow(new PautaNotFoundException());

        assertThrows(PautaNotFoundException.class, () -> findPautaUseCase.execute(id));

        verify(pautaService).findPautaById(id);
        verifyNoInteractions(pautaMapper);
    }

}
