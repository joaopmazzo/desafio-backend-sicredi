package br.com.joaopmazzo.desafio_backend_sicredi.application.usecases;

import br.com.joaopmazzo.desafio_backend_sicredi.application.dtos.response.PautaResponseDTO;
import br.com.joaopmazzo.desafio_backend_sicredi.domain.services.PautaService;
import br.com.joaopmazzo.desafio_backend_sicredi.infrastructure.mappers.PautaMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class FindPautaUseCase {

    private final PautaService pautaService;
    private final PautaMapper pautaMapper;

    public PautaResponseDTO execute(UUID id) {
        var pauta = pautaService.findPautaById(id);

        return pautaMapper.toResponseDTO(pauta);
    }

}
