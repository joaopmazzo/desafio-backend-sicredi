package br.com.joaopmazzo.desafio_backend_sicredi.application.usecases;

import br.com.joaopmazzo.desafio_backend_sicredi.application.dtos.request.SessaoRequestDTO;
import br.com.joaopmazzo.desafio_backend_sicredi.application.dtos.response.SessaoResponseDTO;
import br.com.joaopmazzo.desafio_backend_sicredi.domain.entities.PautaEntity;
import br.com.joaopmazzo.desafio_backend_sicredi.domain.entities.SessaoEntity;
import br.com.joaopmazzo.desafio_backend_sicredi.domain.services.PautaService;
import br.com.joaopmazzo.desafio_backend_sicredi.domain.services.SessaoService;
import br.com.joaopmazzo.desafio_backend_sicredi.infrastructure.mappers.SessaoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class AbrirSessaoUseCase {

    private final PautaService pautaService;
    private final SessaoService sessaoService;
    private final SessaoMapper sessaoMapper;

    public SessaoResponseDTO execute(UUID pautaId, SessaoRequestDTO dto) {
        PautaEntity pauta = pautaService.findPautaById(pautaId);

        sessaoService.existsSessaoByPautaIdAndStatusNotLikeCancelado(pautaId);

        SessaoEntity entity = sessaoMapper.toEntity(dto);
        entity.setPauta(pauta);

        SessaoEntity savedEntity = sessaoService.saveSessao(entity);

        return sessaoMapper.toResponseDTO(savedEntity);
    }

}
