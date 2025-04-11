package br.com.joaopmazzo.desafio_backend_sicredi.application.usecases;

import br.com.joaopmazzo.desafio_backend_sicredi.application.dtos.request.VotoRequestDTO;
import br.com.joaopmazzo.desafio_backend_sicredi.application.dtos.response.VotoResponseDTO;
import br.com.joaopmazzo.desafio_backend_sicredi.application.exceptions.associado.AssociadoNaoPodeVotarException;
import br.com.joaopmazzo.desafio_backend_sicredi.application.exceptions.associado.AssociadoNotFoundException;
import br.com.joaopmazzo.desafio_backend_sicredi.application.exceptions.sessao.SessaoEncerradaException;
import br.com.joaopmazzo.desafio_backend_sicredi.application.exceptions.voto.VotoJaRegistradoException;
import br.com.joaopmazzo.desafio_backend_sicredi.domain.entities.AssociadoEntity;
import br.com.joaopmazzo.desafio_backend_sicredi.domain.entities.SessaoEntity;
import br.com.joaopmazzo.desafio_backend_sicredi.domain.entities.VotoEntity;
import br.com.joaopmazzo.desafio_backend_sicredi.domain.enums.EscolhaVotoEnum;
import br.com.joaopmazzo.desafio_backend_sicredi.domain.services.AssociadoService;
import br.com.joaopmazzo.desafio_backend_sicredi.domain.services.SessaoService;
import br.com.joaopmazzo.desafio_backend_sicredi.domain.services.VotoService;
import br.com.joaopmazzo.desafio_backend_sicredi.infrastructure.mappers.VotoMapper;
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
public class RegisterVotoUseCaseTest {

    @Mock
    private SessaoService sessaoService;

    @Mock
    private VotoService votoService;

    @Mock
    private AssociadoService associadoService;

    @Mock
    private VotoMapper votoMapper;

    @InjectMocks
    private RegisterVotoUseCase registerVotoUseCase;

    @Test
    @DisplayName("Should register a vote successfully when valid data is provided")
    void shouldRegisterVoteSuccessfully() {
        UUID pautaId = UUID.randomUUID();
        VotoRequestDTO requestDTO = new VotoRequestDTO("12345678900", EscolhaVotoEnum.SIM);
        AssociadoEntity associadoEntity = AssociadoEntity.builder().ableToVote(true).build();
        SessaoEntity sessaoEntity = new SessaoEntity();
        VotoEntity savedVotoEntity = new VotoEntity();
        VotoResponseDTO responseDTO = new VotoResponseDTO();

        when(associadoService.validateAssociado(requestDTO.documento())).thenReturn(associadoEntity);
        when(sessaoService.validateSessao(pautaId)).thenReturn(sessaoEntity);
        doNothing().when(votoService).alreadyVoted(sessaoEntity, associadoEntity);
        when(votoService.saveVoto(any(VotoEntity.class))).thenReturn(savedVotoEntity);
        when(votoMapper.toResponseDTO(savedVotoEntity)).thenReturn(responseDTO);

        VotoResponseDTO result = registerVotoUseCase.execute(pautaId, requestDTO);

        assertEquals(responseDTO, result);
        verify(associadoService).validateAssociado(requestDTO.documento());
        verify(sessaoService).validateSessao(pautaId);
        verify(votoService).alreadyVoted(sessaoEntity, associadoEntity);
        verify(votoService).saveVoto(any(VotoEntity.class));
        verify(votoMapper).toResponseDTO(savedVotoEntity);
    }

    @Test
    @DisplayName("Should throw exception when associado is not found")
    void shouldThrowExceptionWhenAssociadoNotFound() {
        UUID pautaId = UUID.randomUUID();
        VotoRequestDTO requestDTO = new VotoRequestDTO("12345678900", EscolhaVotoEnum.SIM);

        when(associadoService.validateAssociado(requestDTO.documento())).thenThrow(new AssociadoNotFoundException());

        assertThrows(AssociadoNotFoundException.class, () -> registerVotoUseCase.execute(pautaId, requestDTO));
        verify(associadoService).validateAssociado(requestDTO.documento());
        verifyNoInteractions(sessaoService, votoService, votoMapper);
    }

    @Test
    @DisplayName("Should throw exception when associado is not allowed to vote")
    void shouldThrowExceptionWhenAssociadoNotAllowedToVote() {
        UUID pautaId = UUID.randomUUID();
        VotoRequestDTO requestDTO = new VotoRequestDTO("12345678900", EscolhaVotoEnum.NAO);

        when(associadoService.validateAssociado(requestDTO.documento())).thenThrow(AssociadoNaoPodeVotarException.class);

        assertThrows(AssociadoNaoPodeVotarException.class, () -> registerVotoUseCase.execute(pautaId, requestDTO));
        verify(associadoService).validateAssociado(requestDTO.documento());
        verifyNoInteractions(sessaoService, votoService, votoMapper);
    }

    @Test
    @DisplayName("Should throw exception when session is closed")
    void shouldThrowExceptionWhenSessionIsInvalid() {
        UUID pautaId = UUID.randomUUID();
        VotoRequestDTO requestDTO = new VotoRequestDTO("12345678900", EscolhaVotoEnum.NAO);
        AssociadoEntity associadoEntity = new AssociadoEntity();

        when(associadoService.validateAssociado(requestDTO.documento())).thenReturn(associadoEntity);
        when(sessaoService.validateSessao(pautaId)).thenThrow(new SessaoEncerradaException());

        assertThrows(SessaoEncerradaException.class, () -> registerVotoUseCase.execute(pautaId, requestDTO));
        verify(associadoService).validateAssociado(requestDTO.documento());
        verify(sessaoService).validateSessao(pautaId);
        verifyNoInteractions(votoService, votoMapper);
    }

    @Test
    @DisplayName("Should throw exception when associado has already voted")
    void shouldThrowExceptionWhenAssociadoAlreadyVoted() {
        UUID pautaId = UUID.randomUUID();
        VotoRequestDTO requestDTO = new VotoRequestDTO("12345678900", EscolhaVotoEnum.SIM);
        SessaoEntity sessaoEntity = new SessaoEntity();
        AssociadoEntity associadoEntity = new AssociadoEntity();

        when(associadoService.validateAssociado(requestDTO.documento())).thenReturn(associadoEntity);
        when(sessaoService.validateSessao(pautaId)).thenReturn(sessaoEntity);
        doThrow(new VotoJaRegistradoException()).when(votoService).alreadyVoted(sessaoEntity, associadoEntity);

        assertThrows(VotoJaRegistradoException.class, () -> registerVotoUseCase.execute(pautaId, requestDTO));
        verify(associadoService).validateAssociado(requestDTO.documento());
        verify(sessaoService).validateSessao(pautaId);
        verify(votoService).alreadyVoted(sessaoEntity, associadoEntity);
        verifyNoInteractions(votoMapper);
    }

}
