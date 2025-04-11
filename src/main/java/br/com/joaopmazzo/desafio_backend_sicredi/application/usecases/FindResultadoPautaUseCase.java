package br.com.joaopmazzo.desafio_backend_sicredi.application.usecases;

import br.com.joaopmazzo.desafio_backend_sicredi.application.dtos.response.ResultadoResponseDTO;
import br.com.joaopmazzo.desafio_backend_sicredi.domain.entities.SessaoEntity;
import br.com.joaopmazzo.desafio_backend_sicredi.domain.services.SessaoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class FindResultadoPautaUseCase {

    private final SessaoService sessaoService;

    public ResultadoResponseDTO execute(UUID idPauta) {
        SessaoEntity sessao = sessaoService.findSessaoByPautaId(idPauta);

        return sessaoService.contabilizaResultado(sessao);
    }

}
