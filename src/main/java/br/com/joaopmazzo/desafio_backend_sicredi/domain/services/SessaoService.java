package br.com.joaopmazzo.desafio_backend_sicredi.domain.services;

import br.com.joaopmazzo.desafio_backend_sicredi.application.dtos.enums.ResultadoVotacaoEnum;
import br.com.joaopmazzo.desafio_backend_sicredi.application.dtos.response.ResultadoResponseDTO;
import br.com.joaopmazzo.desafio_backend_sicredi.application.exceptions.sessao.SessaoEncerradaException;
import br.com.joaopmazzo.desafio_backend_sicredi.application.exceptions.sessao.SessaoJaAbertaException;
import br.com.joaopmazzo.desafio_backend_sicredi.application.exceptions.sessao.SessaoNotFoundException;
import br.com.joaopmazzo.desafio_backend_sicredi.domain.entities.SessaoEntity;
import br.com.joaopmazzo.desafio_backend_sicredi.domain.enums.EscolhaVotoEnum;
import br.com.joaopmazzo.desafio_backend_sicredi.domain.enums.StatusSessaoEnum;
import br.com.joaopmazzo.desafio_backend_sicredi.domain.repositories.SessaoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

/**
 * Camada de serviço para sessões de votação.
 */
@Service
@RequiredArgsConstructor
public class SessaoService {

    private final SessaoRepository sessaoRepository;
    private final VotoService votoService;

    /**
     * Conta o número de votos a favor e contra de uma sessão.
     *
     * @param sessao Sessão a ser contabilizada.
     * @return Objeto ResultadoResponseDTO com os resultados da votação.
     */
    @Transactional(readOnly = true)
    public ResultadoResponseDTO contabilizaResultado(SessaoEntity sessao) {

        // retorna os dados da votação
        long totalAFavor = votoService.countBySessaoAndEscolhaVoto(sessao, EscolhaVotoEnum.SIM);
        long totalContra = votoService.countBySessaoAndEscolhaVoto(sessao, EscolhaVotoEnum.NAO);
        long totalVotos = totalAFavor + totalContra;

        // verifica qual o resultado da votação
        ResultadoVotacaoEnum resultadoVotacao = Objects.equals(sessao.getStatus(), StatusSessaoEnum.ENCERRADA)
                ? null : (totalAFavor > totalContra)
                ? ResultadoVotacaoEnum.DEFERIDA : (totalAFavor < totalContra)
                ? ResultadoVotacaoEnum.INDEFERIDA : ResultadoVotacaoEnum.INCONCLUSIVA;

        return new ResultadoResponseDTO(
                sessao.getPauta().getId(),
                sessao.getPauta().getTitulo(),
                totalAFavor,
                totalContra,
                totalVotos,
                resultadoVotacao
        );
    }

    /**
     * Busca uma sessão pelo ID da pauta associada.
     *
     * @param id ID da pauta.
     * @return Entidade SessaoEntity correspondente à pauta.
     * @throws SessaoNotFoundException se nenhuma sessão for encontrada para o ID da pauta fornecido.
     */
    @Transactional(readOnly = true)
    public SessaoEntity findSessaoByPautaId(UUID id) {
        return sessaoRepository
                .findByPautaId(id)
                .orElseThrow(SessaoNotFoundException::new);
    }

    /**
     * Busca paginada de todas as sessões com status "EM_ANDAMENTO" e término menor ou igual ao horário atual.
     *
     * @param pageable Objeto de paginação.
     * @return Página contendo as entidades SessaoEntity.
     */
    public Page<SessaoEntity> findAllByStatusAndTerminoLessThanEqual(Pageable pageable) {
        return sessaoRepository
                .findAllByStatusAndTerminoLessThanEqual(
                        StatusSessaoEnum.EM_ANDAMENTO,
                        LocalDateTime.now(),
                        pageable);
    }

    /**
     * Verifica se já existe uma sessão aberta para uma pauta específica.
     *
     * @param pautaID ID da pauta.
     * @throws SessaoJaAbertaException se já existir uma sessão aberta para a pauta.
     */
    @Transactional(readOnly = true)
    public void existsSessaoByPautaId(UUID pautaID) {
        if (sessaoRepository.existsByPautaId(pautaID)) {
            throw new SessaoJaAbertaException();
        }
    }

    /**
     * Retorna uma lista paginada de todas as sessões.
     *
     * @param pageable Objeto de paginação.
     * @return Página contendo as entidades SessaoEntity.
     */
    @Transactional(readOnly = true)
    public Page<SessaoEntity> returnPautasPageable(Pageable pageable) {
        return sessaoRepository.findAll(pageable);
    }

    /**
     * Salva uma nova sessão no banco de dados.
     *
     * @param entity Entidade SessaoEntity a ser salva.
     * @return Entidade SessaoEntity salva.
     */
    @Transactional
    public SessaoEntity saveSessao(SessaoEntity entity) {
        return sessaoRepository.save(entity);
    }

    /**
     * Salva uma lista de sessões no banco de dados.
     *
     * @param entitiesList Lista de entidades SessaoEntity a serem salvas.
     */
    @Transactional
    public void saveAllSessao(Page<SessaoEntity> entitiesList) {
        sessaoRepository.saveAll(entitiesList);
    }

    /**
     * Valida se uma sessão está ativa e não encerrada.
     *
     * @param pautaId ID da pauta associada à sessão.
     * @return Entidade SessaoEntity validada.
     * @throws SessaoNotFoundException se nenhuma sessão for encontrada para o ID da pauta fornecido.
     * @throws SessaoEncerradaException se a sessão estiver encerrada ou com o término anterior ao momento atual.
     */
    public SessaoEntity validateSessao(UUID pautaId) {
        SessaoEntity sessao = findSessaoByPautaId(pautaId);
        if (sessao.getStatus() == StatusSessaoEnum.ENCERRADA || sessao.getTermino().isBefore(LocalDateTime.now())) {
            throw new SessaoEncerradaException();
        }
        return sessao;
    }

}
