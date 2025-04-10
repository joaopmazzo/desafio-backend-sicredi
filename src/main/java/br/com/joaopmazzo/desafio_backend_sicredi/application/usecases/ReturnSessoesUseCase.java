package br.com.joaopmazzo.desafio_backend_sicredi.application.usecases;

import br.com.joaopmazzo.desafio_backend_sicredi.application.dtos.response.SessaoResponseDTO;
import br.com.joaopmazzo.desafio_backend_sicredi.domain.entities.SessaoEntity;
import br.com.joaopmazzo.desafio_backend_sicredi.domain.services.SessaoService;
import br.com.joaopmazzo.desafio_backend_sicredi.infrastructure.mappers.SessaoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ReturnSessoesUseCase {

    private final SessaoService sessaoService;
    private final SessaoMapper sessaoMapper;

    public Page<SessaoResponseDTO> execute(Pageable pageable) {
        Page<SessaoEntity> sessoes = sessaoService.returnPautasPageable(pageable);

        return sessoes.map(sessaoMapper::toResponseDTO);
    }

}
