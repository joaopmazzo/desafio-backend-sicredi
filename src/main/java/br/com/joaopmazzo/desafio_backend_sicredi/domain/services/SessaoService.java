package br.com.joaopmazzo.desafio_backend_sicredi.domain.services;

import br.com.joaopmazzo.desafio_backend_sicredi.application.exceptions.sessao.SessaoEncerradaException;
import br.com.joaopmazzo.desafio_backend_sicredi.application.exceptions.sessao.SessaoJaAbertaException;
import br.com.joaopmazzo.desafio_backend_sicredi.application.exceptions.sessao.SessaoNotFoundException;
import br.com.joaopmazzo.desafio_backend_sicredi.domain.entities.SessaoEntity;
import br.com.joaopmazzo.desafio_backend_sicredi.domain.enums.StatusSessaoEnum;
import br.com.joaopmazzo.desafio_backend_sicredi.domain.repositories.SessaoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Camada de serviço para sessões de votação.
 */
@Service
@RequiredArgsConstructor
public class SessaoService {

    private final SessaoRepository sessaoRepository;

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
