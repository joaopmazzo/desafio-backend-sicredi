package br.com.joaopmazzo.desafio_backend_sicredi.application.usecases;

import br.com.joaopmazzo.desafio_backend_sicredi.application.dtos.response.SessaoResponseDTO;
import br.com.joaopmazzo.desafio_backend_sicredi.application.exceptions.sessao.SessaoNotFoundException;
import br.com.joaopmazzo.desafio_backend_sicredi.domain.entities.SessaoEntity;
import br.com.joaopmazzo.desafio_backend_sicredi.domain.services.SessaoService;
import br.com.joaopmazzo.desafio_backend_sicredi.infrastructure.mappers.SessaoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * Caso de uso responsável por buscar uma sessão de votação associada a uma pauta.
 */
@Component
@RequiredArgsConstructor
public class FindSessaoUseCase {

    private final SessaoService sessaoService;
    private final SessaoMapper sessaoMapper;

    /**
     * Executa o caso de uso para buscar uma sessão de votação pelo ID da pauta.
     *
     * @param pautaId ID da pauta associada à sessão de votação.
     * @return DTO contendo os detalhes da sessão encontrada.
     * @throws SessaoNotFoundException se nenhuma sessão for encontrada para o ID da pauta fornecido.
     */
    public SessaoResponseDTO execute(UUID pautaId) {
        // Busca a sessão associada ao ID da pauta
        SessaoEntity sessao = sessaoService.findSessaoByPautaId(pautaId);

        // Converte a entidade SessaoEntity para o DTO SessaoResponseDTO
        return sessaoMapper.toResponseDTO(sessao);
    }

}
