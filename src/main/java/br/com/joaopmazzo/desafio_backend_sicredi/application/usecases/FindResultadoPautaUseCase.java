package br.com.joaopmazzo.desafio_backend_sicredi.application.usecases;

import br.com.joaopmazzo.desafio_backend_sicredi.application.dtos.response.ResultadoResponseDTO;
import br.com.joaopmazzo.desafio_backend_sicredi.application.exceptions.sessao.SessaoNotFoundException;
import br.com.joaopmazzo.desafio_backend_sicredi.domain.entities.SessaoEntity;
import br.com.joaopmazzo.desafio_backend_sicredi.domain.services.SessaoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * Caso de uso responsável por encontrar o resultado de uma pauta.
 */
@Component
@RequiredArgsConstructor
public class FindResultadoPautaUseCase {

    private final SessaoService sessaoService;

    /**
     * Executa o caso de uso para encontrar o resultado de uma pauta.
     *
     * @param idPauta UUID da pauta para a qual o resultado será calculado.
     * @return ResultadoResponseDTO contendo o resultado da pauta.
     * @throws SessaoNotFoundException se a sessão associada à pauta não for encontrada.
     */
    public ResultadoResponseDTO execute(UUID idPauta) {
        SessaoEntity sessao = sessaoService.findSessaoByPautaId(idPauta);

        return sessaoService.contabilizaResultado(sessao);
    }

}
