package br.com.joaopmazzo.desafio_backend_sicredi.application.usecases;

import br.com.joaopmazzo.desafio_backend_sicredi.application.dtos.response.PautaResponseDTO;
import br.com.joaopmazzo.desafio_backend_sicredi.domain.entities.PautaEntity;
import br.com.joaopmazzo.desafio_backend_sicredi.domain.services.PautaService;
import br.com.joaopmazzo.desafio_backend_sicredi.infrastructure.mappers.PautaMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

/**
 * Caso de uso responsável por retornar uma lista paginada de pautas.
 */
@Component
@RequiredArgsConstructor
public class ReturnPautasUseCase {

    private final PautaService pautaService;
    private final PautaMapper pautaMapper;

    /**
     * Executa o caso de uso para retornar uma lista paginada de pautas.
     *
     * @param pageable Objeto que contém informações de paginação.
     * @return Página contendo os dados das pautas no formato PautaResponseDTO.
     */
    public Page<PautaResponseDTO> execute(Pageable pageable) {
        // Obtém as pautas paginadas
        Page<PautaEntity> pautas = pautaService.returnPautasPageable(pageable);

        // Converte as entidades PautaEntity para PautaResponseDTO
        return pautas.map(pautaMapper::toResponseDTO);
    }

}
