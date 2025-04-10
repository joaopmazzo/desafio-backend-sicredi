package br.com.joaopmazzo.desafio_backend_sicredi.application.usecases;

import br.com.joaopmazzo.desafio_backend_sicredi.application.dtos.response.PautaResponseDTO;
import br.com.joaopmazzo.desafio_backend_sicredi.application.exceptions.pauta.PautaNotFoundException;
import br.com.joaopmazzo.desafio_backend_sicredi.domain.services.PautaService;
import br.com.joaopmazzo.desafio_backend_sicredi.infrastructure.mappers.PautaMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * Caso de uso responsável por buscar uma pauta pelo seu ID.
 */
@Component
@RequiredArgsConstructor
public class FindPautaUseCase {

    private final PautaService pautaService;
    private final PautaMapper pautaMapper;

    /**
     * Executa o caso de uso para buscar uma pauta pelo ID.
     *
     * @param id ID da pauta a ser buscada.
     * @return DTO contendo os detalhes da pauta encontrada.
     * @throws PautaNotFoundException se nenhuma pauta for encontrada para o ID fornecido.
     */
    public PautaResponseDTO execute(UUID id) {
        // Busca a pauta pelo ID
        var pauta = pautaService.findPautaById(id);

        // Converte a entidade PautaEntity para o DTO PautaResponseDTO
        return pautaMapper.toResponseDTO(pauta);
    }

}
