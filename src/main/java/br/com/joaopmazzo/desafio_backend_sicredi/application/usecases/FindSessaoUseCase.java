package br.com.joaopmazzo.desafio_backend_sicredi.application.usecases;

import br.com.joaopmazzo.desafio_backend_sicredi.application.dtos.response.SessaoResponseDTO;
import br.com.joaopmazzo.desafio_backend_sicredi.domain.entities.SessaoEntity;
import br.com.joaopmazzo.desafio_backend_sicredi.domain.services.SessaoService;
import br.com.joaopmazzo.desafio_backend_sicredi.infrastructure.mappers.SessaoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class FindSessaoUseCase {

    private final SessaoService sessaoService;
    private final SessaoMapper sessaoMapper;

    public SessaoResponseDTO execute(UUID pautaId) {
        SessaoEntity sessao = sessaoService.findSessaoByPautaId(pautaId);

        return sessaoMapper.toResponseDTO(sessao);
    }

}
