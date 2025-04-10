package br.com.joaopmazzo.desafio_backend_sicredi.application.usecases;

import br.com.joaopmazzo.desafio_backend_sicredi.application.dtos.request.SessaoRequestDTO;
import br.com.joaopmazzo.desafio_backend_sicredi.application.dtos.response.SessaoResponseDTO;
import br.com.joaopmazzo.desafio_backend_sicredi.application.exceptions.pauta.PautaNotFoundException;
import br.com.joaopmazzo.desafio_backend_sicredi.application.exceptions.sessao.SessaoJaAbertaException;
import br.com.joaopmazzo.desafio_backend_sicredi.domain.entities.PautaEntity;
import br.com.joaopmazzo.desafio_backend_sicredi.domain.entities.SessaoEntity;
import br.com.joaopmazzo.desafio_backend_sicredi.domain.services.PautaService;
import br.com.joaopmazzo.desafio_backend_sicredi.domain.services.SessaoService;
import br.com.joaopmazzo.desafio_backend_sicredi.infrastructure.mappers.SessaoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * Caso de uso responsável por abrir uma nova sessão de votação para uma pauta.
 */
@Component
@RequiredArgsConstructor
public class AbrirSessaoUseCase {

    private final PautaService pautaService;
    private final SessaoService sessaoService;
    private final SessaoMapper sessaoMapper;

    /**
     * Executa o caso de uso para abrir uma nova sessão de votação para uma pauta.
     *
     * @param pautaId ID da pauta para a qual a sessão será aberta.
     * @param dto Objeto contendo os dados da sessão a ser criada.
     * @return DTO de resposta contendo os detalhes da sessão criada.
     * @throws PautaNotFoundException se a pauta associada ao ID fornecido não for encontrada.
     * @throws SessaoJaAbertaException se já existir uma sessão ativa ou não cancelada para a pauta.
     */
    public SessaoResponseDTO execute(UUID pautaId, SessaoRequestDTO dto) {
        // Busca a pauta pelo ID
        PautaEntity pauta = pautaService.findPautaById(pautaId);

        // Verifica se já existe uma sessão para a pauta
        sessaoService.existsSessaoByPautaId(pautaId);

        // Converte o DTO de requisição para a entidade SessaoEntity e setta a pauta
        SessaoEntity entity = sessaoMapper.toEntity(dto);
        entity.setPauta(pauta);

        // Salva a entidade no banco de dados
        SessaoEntity savedEntity = sessaoService.saveSessao(entity);

        // Converte a entidade salva para o DTO de resposta
        return sessaoMapper.toResponseDTO(savedEntity);
    }

}
