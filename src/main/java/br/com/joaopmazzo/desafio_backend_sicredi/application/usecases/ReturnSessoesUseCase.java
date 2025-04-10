package br.com.joaopmazzo.desafio_backend_sicredi.application.usecases;

import br.com.joaopmazzo.desafio_backend_sicredi.application.dtos.response.SessaoResponseDTO;
import br.com.joaopmazzo.desafio_backend_sicredi.domain.entities.SessaoEntity;
import br.com.joaopmazzo.desafio_backend_sicredi.domain.services.SessaoService;
import br.com.joaopmazzo.desafio_backend_sicredi.infrastructure.mappers.SessaoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

/**
 * Caso de uso responsável por retornar uma lista paginada de sessões.
 */
@Component
@RequiredArgsConstructor
public class ReturnSessoesUseCase {

    private final SessaoService sessaoService;
    private final SessaoMapper sessaoMapper;

    /**
     * Executa o caso de uso para retornar uma lista paginada de sessões.
     *
     * @param pageable Objeto que contém informações de paginação.
     * @return Página contendo os dados das sessões no formato SessaoResponseDTO.
     */
    public Page<SessaoResponseDTO> execute(Pageable pageable) {
        // Obtém as sessões paginadas
        Page<SessaoEntity> sessoes = sessaoService.returnPautasPageable(pageable);

        // Converte as entidades SessaoEntity para SessaoResponseDTO
        return sessoes.map(sessaoMapper::toResponseDTO);
    }

}
