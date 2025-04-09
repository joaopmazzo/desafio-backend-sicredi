package br.com.joaopmazzo.desafio_backend_sicredi.domain.services;

import br.com.joaopmazzo.desafio_backend_sicredi.application.exceptions.pauta.DuplicatePautaException;
import br.com.joaopmazzo.desafio_backend_sicredi.application.exceptions.pauta.PautaNotFoundException;
import br.com.joaopmazzo.desafio_backend_sicredi.domain.entities.PautaEntity;
import br.com.joaopmazzo.desafio_backend_sicredi.domain.repositories.PautaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PautaService {

    private final PautaRepository pautaRepository;

    @Transactional(readOnly = true)
    public PautaEntity findPautaById(UUID id) {
        return pautaRepository
                .findById(id)
                .orElseThrow(PautaNotFoundException::new);
    }

    @Transactional(readOnly = true)
    public Page<PautaEntity> returnPautasPageable(Pageable pageable) {
        return pautaRepository.findAll(pageable);
    }

    @Transactional
    public PautaEntity savePauta(PautaEntity pauta) {
        return pautaRepository.save(pauta);
    }

    @Transactional(readOnly = true)
    public boolean existsByTitulo(String titulo) {
        if (pautaRepository.existsByTitulo(titulo)) throw new DuplicatePautaException();
        return false;
    }

}
