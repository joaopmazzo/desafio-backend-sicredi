package br.com.joaopmazzo.desafio_backend_sicredi.application.usecases;

import br.com.joaopmazzo.desafio_backend_sicredi.application.dtos.request.VotoRequestDTO;
import br.com.joaopmazzo.desafio_backend_sicredi.application.dtos.response.VotoResponseDTO;
import br.com.joaopmazzo.desafio_backend_sicredi.application.exceptions.associado.AssociadoNaoPodeVotarException;
import br.com.joaopmazzo.desafio_backend_sicredi.application.exceptions.associado.AssociadoNotFoundException;
import br.com.joaopmazzo.desafio_backend_sicredi.application.exceptions.sessao.SessaoEncerradaException;
import br.com.joaopmazzo.desafio_backend_sicredi.application.exceptions.sessao.SessaoNotFoundException;
import br.com.joaopmazzo.desafio_backend_sicredi.application.exceptions.voto.VotoJaRegistradoException;
import br.com.joaopmazzo.desafio_backend_sicredi.domain.entities.AssociadoEntity;
import br.com.joaopmazzo.desafio_backend_sicredi.domain.entities.SessaoEntity;
import br.com.joaopmazzo.desafio_backend_sicredi.domain.entities.VotoEntity;
import br.com.joaopmazzo.desafio_backend_sicredi.domain.services.AssociadoService;
import br.com.joaopmazzo.desafio_backend_sicredi.domain.services.SessaoService;
import br.com.joaopmazzo.desafio_backend_sicredi.domain.services.VotoService;
import br.com.joaopmazzo.desafio_backend_sicredi.infrastructure.mappers.VotoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * Caso de uso responsável por registrar um voto em uma sessão de votação.
 */
@Component
@RequiredArgsConstructor
public class RegisterVotoUseCase {

    private final SessaoService sessaoService;
    private final VotoService votoService;
    private final AssociadoService associadoService;
    private final VotoMapper votoMapper;

    /**
     * Executa o caso de uso para registrar um voto em uma sessão de votação.
     *
     * @param pautaId ID da pauta associada à sessão de votação.
     * @param dto Objeto contendo os dados do voto a ser registrado.
     * @return DTO de resposta contendo os detalhes do voto registrado.
     * @throws SessaoNotFoundException se a sessão não for encontrada.
     * @throws SessaoEncerradaException se a sessão ja esta encerrada.
     * @throws AssociadoNotFoundException se o associado não for encontrado.
     * @throws AssociadoNaoPodeVotarException se o associado não for elegível para votar.
     * @throws VotoJaRegistradoException se o associado já tiver votado na sessão.
     */
    public VotoResponseDTO execute(UUID pautaId, VotoRequestDTO dto) {
        // Retorna a sessão pela pauta, status e que ainda não foi encerrada
        SessaoEntity sessao = sessaoService.validateSessao(pautaId);

        // retorna o associado e valida se o mesmo pode votar
        AssociadoEntity associado = associadoService.validateAssociado(dto.documento());

        // Valida se o associado já votou, se sim joga uma exceção
        votoService.alreadyVoted(sessao, associado);

        // Cria a entidade para computar o voto com os atributos necessários
        VotoEntity voto = VotoEntity.builder()
                .sessao(sessao)
                .associado(associado)
                .aFavor(dto.aFavor())
                .build();

        // Salva a entidade no banco de dados
        VotoEntity savedVoto = votoService.saveVoto(voto);

        // Retorna o voto computado
        return votoMapper.toResponseDTO(savedVoto);
    }

}
