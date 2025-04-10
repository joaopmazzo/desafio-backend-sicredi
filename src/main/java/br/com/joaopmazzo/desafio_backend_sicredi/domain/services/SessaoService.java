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

@Service
@RequiredArgsConstructor
public class SessaoService {

    private final SessaoRepository sessaoRepository;

    @Transactional(readOnly = true)
    public SessaoEntity findSessaoByPautaId(UUID id) {
        return sessaoRepository
                .findByPautaId(id)
                .orElseThrow(SessaoNotFoundException::new);
    }

    @Transactional(readOnly = true)
    public void existsSessaoByPautaId(UUID pautaID) {
        if (sessaoRepository.existsByPautaId(pautaID)) {
            throw new SessaoJaAbertaException();
        }
    }

    @Transactional(readOnly = true)
    public Page<SessaoEntity> returnPautasPageable(Pageable pageable) {
        return sessaoRepository.findAll(pageable);
    }

    @Transactional
    public SessaoEntity saveSessao(SessaoEntity entity) {
        return sessaoRepository.save(entity);
    }

    public SessaoEntity validateSessao(UUID pautaId) {
        SessaoEntity sessao = findSessaoByPautaId(pautaId);
        if (sessao.getStatus() == StatusSessaoEnum.ENCERRADA || sessao.getTermino().isBefore(LocalDateTime.now())) {
            throw new SessaoEncerradaException();
        }
        return sessao;
    }

}
