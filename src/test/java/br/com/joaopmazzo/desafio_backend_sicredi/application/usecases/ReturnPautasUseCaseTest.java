package br.com.joaopmazzo.desafio_backend_sicredi.application.usecases;

import br.com.joaopmazzo.desafio_backend_sicredi.application.dtos.response.PautaResponseDTO;
import br.com.joaopmazzo.desafio_backend_sicredi.domain.entities.PautaEntity;
import br.com.joaopmazzo.desafio_backend_sicredi.domain.services.PautaService;
import br.com.joaopmazzo.desafio_backend_sicredi.infrastructure.mappers.PautaMapper;
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
public class ReturnPautasUseCaseTest {

    @Mock
    private PautaService pautaService;

    @Mock
    private PautaMapper pautaMapper;

    @InjectMocks
    private ReturnPautasUseCase returnPautasUseCase;

    @Test
    @DisplayName("Should return a paginated list of PautaResponseDTO when valid pageable is provided")
    void shouldReturnPaginatedListOfPautaResponseDTOWhenValidPageableProvided() {
        Pageable pageable = mock(Pageable.class);
        PautaEntity pautaEntity = new PautaEntity();
        PautaResponseDTO pautaResponseDTO = new PautaResponseDTO();

        Page<PautaEntity> pautaEntities = new PageImpl<>(List.of(pautaEntity, pautaEntity));
        Page<PautaResponseDTO> expectedResponse = new PageImpl<>(List.of(pautaResponseDTO, pautaResponseDTO));

        when(pautaService.returnPautasPageable(pageable)).thenReturn(pautaEntities);
        when(pautaMapper.toResponseDTO(any(PautaEntity.class))).thenReturn(pautaResponseDTO);

        Page<PautaResponseDTO> result = returnPautasUseCase.execute(pageable);

        assertEquals(expectedResponse.getContent(), result.getContent());
        assertEquals(expectedResponse.getTotalElements(), result.getTotalElements());
        verify(pautaService).returnPautasPageable(pageable);
        verify(pautaMapper, times(2)).toResponseDTO(any(PautaEntity.class));
        assertFalse(result.isEmpty());
    }

    @Test
    @DisplayName("Should return an empty page when no Pautas are found")
    void shouldReturnEmptyPageWhenNoPautasFound() {
        Pageable pageable = mock(Pageable.class);
        Page<PautaEntity> emptyPautaEntities = Page.empty();

        when(pautaService.returnPautasPageable(pageable)).thenReturn(emptyPautaEntities);

        Page<PautaResponseDTO> result = returnPautasUseCase.execute(pageable);

        assertTrue(result.isEmpty());
        verify(pautaService).returnPautasPageable(pageable);
        verifyNoInteractions(pautaMapper);
    }

}
