package br.com.joaopmazzo.desafio_backend_sicredi.application.usecases;

import br.com.joaopmazzo.desafio_backend_sicredi.application.dtos.response.SessaoResponseDTO;
import br.com.joaopmazzo.desafio_backend_sicredi.application.exceptions.sessao.SessaoNotFoundException;
import br.com.joaopmazzo.desafio_backend_sicredi.domain.entities.SessaoEntity;
import br.com.joaopmazzo.desafio_backend_sicredi.domain.services.SessaoService;
import br.com.joaopmazzo.desafio_backend_sicredi.infrastructure.mappers.SessaoMapper;
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
public class FindSessaoUseCaseTest {

    @Mock
    private SessaoService sessaoService;

    @Mock
    private SessaoMapper sessaoMapper;

    @InjectMocks
    private FindSessaoUseCase findSessaoUseCase;

    @Test
    @DisplayName("Should return SessaoResponseDTO when valid Pauta ID is provided")
    void shouldReturnSessaoResponseDTOWhenValidPautaIdProvided() {
        UUID pautaId = UUID.randomUUID();
        SessaoEntity sessaoEntity = new SessaoEntity();
        SessaoResponseDTO responseDTO = new SessaoResponseDTO();

        when(sessaoService.findSessaoByPautaId(pautaId)).thenReturn(sessaoEntity);
        when(sessaoMapper.toResponseDTO(sessaoEntity)).thenReturn(responseDTO);

        SessaoResponseDTO result = findSessaoUseCase.execute(pautaId);

        assertEquals(responseDTO, result);
        verify(sessaoService).findSessaoByPautaId(pautaId);
        verify(sessaoMapper).toResponseDTO(sessaoEntity);
    }

    @Test
    @DisplayName("Should throw exception when no Sessao is found for the given Pauta ID")
    void shouldThrowExceptionWhenNoSessionFoundForPautaId() {
        UUID pautaId = UUID.randomUUID();

        when(sessaoService.findSessaoByPautaId(pautaId)).thenThrow(new SessaoNotFoundException());

        assertThrows(SessaoNotFoundException.class, () -> findSessaoUseCase.execute(pautaId));
        verify(sessaoService).findSessaoByPautaId(pautaId);
        verifyNoInteractions(sessaoMapper);
    }

}
