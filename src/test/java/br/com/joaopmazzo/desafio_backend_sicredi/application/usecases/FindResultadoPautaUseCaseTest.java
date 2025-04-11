package br.com.joaopmazzo.desafio_backend_sicredi.application.usecases;

import br.com.joaopmazzo.desafio_backend_sicredi.application.dtos.response.ResultadoResponseDTO;
import br.com.joaopmazzo.desafio_backend_sicredi.domain.entities.SessaoEntity;
import br.com.joaopmazzo.desafio_backend_sicredi.domain.services.SessaoService;
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
public class FindResultadoPautaUseCaseTest {

    @Mock
    private SessaoService sessaoService;

    @InjectMocks
    private FindResultadoPautaUseCase findResultadoPautaUseCase;

    @Test
    @DisplayName("Should return ResultadoResponseDTO when valid Pauta ID is provided")
    void shouldReturnResultadoResponseDTOForValidPautaId() {
        UUID pautaId = UUID.randomUUID();
        SessaoEntity sessaoEntity = new SessaoEntity();
        ResultadoResponseDTO resultadoResponseDTO = mock(ResultadoResponseDTO.class);

        when(sessaoService.findSessaoByPautaId(pautaId)).thenReturn(sessaoEntity);
        when(sessaoService.contabilizaResultado(sessaoEntity)).thenReturn(resultadoResponseDTO);

        ResultadoResponseDTO result = findResultadoPautaUseCase.execute(pautaId);

        assertEquals(resultadoResponseDTO, result);
        verify(sessaoService).findSessaoByPautaId(pautaId);
        verify(sessaoService).contabilizaResultado(sessaoEntity);
    }

    @Test
    @DisplayName("Should throw exception when Sessao is not found for the given Pauta ID")
    void shouldThrowExceptionWhenSessaoNotFoundForPautaId() {
        UUID pautaId = UUID.randomUUID();

        when(sessaoService.findSessaoByPautaId(pautaId)).thenThrow(new IllegalArgumentException("Sessao not found"));

        assertThrows(IllegalArgumentException.class, () -> findResultadoPautaUseCase.execute(pautaId));
        verify(sessaoService).findSessaoByPautaId(pautaId);
        verifyNoMoreInteractions(sessaoService);
    }

}
