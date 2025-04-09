package br.com.joaopmazzo.desafio_backend_sicredi.application.usecases;

import br.com.joaopmazzo.desafio_backend_sicredi.application.dtos.response.PautaResponseDTO;
import br.com.joaopmazzo.desafio_backend_sicredi.domain.entities.PautaEntity;
import br.com.joaopmazzo.desafio_backend_sicredi.domain.services.PautaService;
import br.com.joaopmazzo.desafio_backend_sicredi.infrastructure.mappers.PautaMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ReturnPautasUseCase {

    private final PautaService pautaService;
    private final PautaMapper pautaMapper;

    public Page<PautaResponseDTO> execute(Pageable pageable) {
        Page<PautaEntity> pautas = pautaService.returnPautasPageable(pageable);

        return pautas.map(pautaMapper::toResponseDTO);
    }

}
