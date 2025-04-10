package br.com.joaopmazzo.desafio_backend_sicredi.application.usecases;

import br.com.joaopmazzo.desafio_backend_sicredi.application.dtos.request.SessaoRequestDTO;
import br.com.joaopmazzo.desafio_backend_sicredi.application.dtos.response.SessaoResponseDTO;
import br.com.joaopmazzo.desafio_backend_sicredi.application.exceptions.pauta.PautaNotFoundException;
import br.com.joaopmazzo.desafio_backend_sicredi.application.exceptions.sessao.SessaoJaAbertaException;
import br.com.joaopmazzo.desafio_backend_sicredi.domain.entities.PautaEntity;
import br.com.joaopmazzo.desafio_backend_sicredi.domain.entities.SessaoEntity;
import br.com.joaopmazzo.desafio_backend_sicredi.domain.services.PautaService;
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
public class AbrirSessaoUseCaseTest {

    @Mock
    private PautaService pautaService;

    @Mock
    private SessaoService sessaoService;

    @Mock
    private SessaoMapper sessaoMapper;

    @InjectMocks
    private AbrirSessaoUseCase abrirSessaoUseCase;

    @Test
    @DisplayName("Should open a Sessao successfully when valid Pauta ID and SessaoRequestDTO are provided")
    void shouldOpenSessionSuccessfully() {
        UUID pautaId = UUID.randomUUID();
        SessaoRequestDTO requestDTO = new SessaoRequestDTO(1);
        PautaEntity pautaEntity = new PautaEntity();
        SessaoEntity sessaoEntityMapper = new SessaoEntity();
        SessaoEntity savedSessaoEntity = new SessaoEntity();
        SessaoResponseDTO responseDTO = new SessaoResponseDTO();

        when(pautaService.findPautaById(pautaId)).thenReturn(pautaEntity);
        doNothing().when(sessaoService).existsSessaoByPautaIdAndStatusNotLikeCancelado(pautaId);
        when(sessaoMapper.toEntity(requestDTO)).thenReturn(sessaoEntityMapper);
        when(sessaoService.saveSessao(sessaoEntityMapper)).thenReturn(savedSessaoEntity);
        when(sessaoMapper.toResponseDTO(savedSessaoEntity)).thenReturn(responseDTO);

        SessaoResponseDTO result = abrirSessaoUseCase.execute(pautaId, requestDTO);

        assertEquals(responseDTO, result);
        verify(pautaService).findPautaById(pautaId);
        verify(sessaoService).existsSessaoByPautaIdAndStatusNotLikeCancelado(pautaId);
        verify(sessaoMapper).toEntity(requestDTO);
        verify(sessaoService).saveSessao(sessaoEntityMapper);
        verify(sessaoMapper).toResponseDTO(savedSessaoEntity);
    }

    @Test
    @DisplayName("Should throw exception when Pauta ID is not found")
    void shouldThrowExceptionWhenPautaIdNotFound() {
        UUID pautaId = UUID.randomUUID();
        SessaoRequestDTO requestDTO = new SessaoRequestDTO(1);

        when(pautaService.findPautaById(pautaId)).thenThrow(new PautaNotFoundException());

        assertThrows(PautaNotFoundException.class, () -> abrirSessaoUseCase.execute(pautaId, requestDTO));
        verify(pautaService).findPautaById(pautaId);
        verifyNoInteractions(sessaoService, sessaoMapper);
    }

    @Test
    @DisplayName("Should throw exception when Sessao already exists for the given Pauta ID")
    void shouldThrowExceptionWhenSessionAlreadyExists() {
        UUID pautaId = UUID.randomUUID();
        SessaoRequestDTO requestDTO = new SessaoRequestDTO(1);
        PautaEntity pautaEntity = new PautaEntity();

        when(pautaService.findPautaById(pautaId)).thenReturn(pautaEntity);
        doThrow(new SessaoJaAbertaException()).when(sessaoService).existsSessaoByPautaIdAndStatusNotLikeCancelado(pautaId);

        assertThrows(SessaoJaAbertaException.class, () -> abrirSessaoUseCase.execute(pautaId, requestDTO));
        verify(pautaService).findPautaById(pautaId);
        verify(sessaoService).existsSessaoByPautaIdAndStatusNotLikeCancelado(pautaId);
        verifyNoInteractions(sessaoMapper);
        verifyNoMoreInteractions(sessaoService);
    }

}
