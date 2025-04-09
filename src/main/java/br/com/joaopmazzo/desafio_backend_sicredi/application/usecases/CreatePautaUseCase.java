package br.com.joaopmazzo.desafio_backend_sicredi.application.usecases;

import br.com.joaopmazzo.desafio_backend_sicredi.application.dtos.request.PautaRequestDTO;
import br.com.joaopmazzo.desafio_backend_sicredi.application.dtos.response.PautaResponseDTO;
import br.com.joaopmazzo.desafio_backend_sicredi.domain.entities.PautaEntity;
import br.com.joaopmazzo.desafio_backend_sicredi.domain.services.PautaService;
import br.com.joaopmazzo.desafio_backend_sicredi.infrastructure.mappers.PautaMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CreatePautaUseCase {

    private final PautaService pautaService;
    private final PautaMapper pautaMapper;

    public PautaResponseDTO execute(PautaRequestDTO dto) {
        PautaEntity entity = pautaMapper.toEntity(dto);

        PautaEntity savedEntity = pautaService.savePauta(entity);

        return pautaMapper.toResponseDTO(savedEntity);
    }

}
