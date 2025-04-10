package br.com.joaopmazzo.desafio_backend_sicredi.application.usecases;

import br.com.joaopmazzo.desafio_backend_sicredi.application.dtos.request.PautaRequestDTO;
import br.com.joaopmazzo.desafio_backend_sicredi.application.dtos.response.PautaResponseDTO;
import br.com.joaopmazzo.desafio_backend_sicredi.application.exceptions.pauta.DuplicatePautaException;
import br.com.joaopmazzo.desafio_backend_sicredi.domain.entities.PautaEntity;
import br.com.joaopmazzo.desafio_backend_sicredi.domain.services.PautaService;
import br.com.joaopmazzo.desafio_backend_sicredi.infrastructure.mappers.PautaMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * Caso de uso responsável por criar uma nova pauta.
 */
@Component
@RequiredArgsConstructor
public class CreatePautaUseCase {

    private final PautaService pautaService;
    private final PautaMapper pautaMapper;

    /**
     * Executa o caso de uso para criar uma nova pauta.
     *
     * @param dto Objeto contendo os dados da pauta a ser criada.
     * @return DTO de resposta contendo os detalhes da pauta criada.
     * @throws DuplicatePautaException se já existir uma pauta com o mesmo título.
     */
    public PautaResponseDTO execute(PautaRequestDTO dto) {
        // Verifica se já existe uma pauta com o título fornecido
        pautaService.existsByTitulo(dto.titulo());

        // Converte o DTO de requisição para a entidade PautaEntity
        PautaEntity entity = pautaMapper.toEntity(dto);

        // Salva a entidade no banco de dados
        PautaEntity savedEntity = pautaService.savePauta(entity);

        // Converte a entidade salva para o DTO de resposta
        return pautaMapper.toResponseDTO(savedEntity);
    }

}
