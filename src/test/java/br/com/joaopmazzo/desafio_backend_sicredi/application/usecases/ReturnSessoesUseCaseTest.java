package br.com.joaopmazzo.desafio_backend_sicredi.application.usecases;

import br.com.joaopmazzo.desafio_backend_sicredi.application.dtos.response.SessaoResponseDTO;
import br.com.joaopmazzo.desafio_backend_sicredi.domain.entities.SessaoEntity;
import br.com.joaopmazzo.desafio_backend_sicredi.domain.services.SessaoService;
import br.com.joaopmazzo.desafio_backend_sicredi.infrastructure.mappers.SessaoMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ReturnSessoesUseCaseTest {

    @Mock
    private SessaoService sessaoService;

    @Mock
    private SessaoMapper sessaoMapper;

    @InjectMocks
    private ReturnSessoesUseCase returnSessoesUseCase;

    @Test
    @DisplayName("Should return paginated SessaoResponseDTO when valid pageable is provided")
    void shouldReturnPaginatedSessaoResponseDTOWhenValidPageableProvided() {
        Pageable pageable = mock(Pageable.class);
        SessaoEntity sessaoEntity = new SessaoEntity();
        SessaoResponseDTO sessaoResponseDTO = new SessaoResponseDTO();

        Page<SessaoEntity> sessaoEntities = new PageImpl<>(List.of(sessaoEntity, sessaoEntity));
        Page<SessaoResponseDTO> expectedResponse = new PageImpl<>(List.of(sessaoResponseDTO, sessaoResponseDTO));

        when(sessaoService.returnPautasPageable(pageable)).thenReturn(sessaoEntities);
        when(sessaoMapper.toResponseDTO(any(SessaoEntity.class))).thenReturn(sessaoResponseDTO);

        Page<SessaoResponseDTO> result = returnSessoesUseCase.execute(pageable);

        assertEquals(expectedResponse.getContent(), result.getContent());
        assertEquals(expectedResponse.getTotalElements(), result.getTotalElements());
        verify(sessaoService).returnPautasPageable(pageable);
        verify(sessaoMapper, times(2)).toResponseDTO(any(SessaoEntity.class));
        assertFalse(result.isEmpty());
    }

    @Test
    @DisplayName("Should return empty page when no sessions are found")
    void shouldReturnEmptyPageWhenNoSessionsFound() {
        Pageable pageable = mock(Pageable.class);
        Page<SessaoEntity> emptySessaoEntities = Page.empty();

        when(sessaoService.returnPautasPageable(pageable)).thenReturn(emptySessaoEntities);

        Page<SessaoResponseDTO> result = returnSessoesUseCase.execute(pageable);

        assertTrue(result.isEmpty());
        verify(sessaoService).returnPautasPageable(pageable);
        verifyNoInteractions(sessaoMapper);
    }

}
