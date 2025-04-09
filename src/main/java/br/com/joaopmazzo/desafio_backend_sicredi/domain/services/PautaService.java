package br.com.joaopmazzo.desafio_backend_sicredi.domain.services;

import br.com.joaopmazzo.desafio_backend_sicredi.application.exceptions.pauta.PautaNotFoundException;
import br.com.joaopmazzo.desafio_backend_sicredi.domain.entities.PautaEntity;
import br.com.joaopmazzo.desafio_backend_sicredi.domain.repositories.PautaRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PautaService {

    private final PautaRepository pautaRepository;

    public PautaEntity findPautaById(UUID id) {
        return pautaRepository
                .findById(id)
                .orElseThrow(PautaNotFoundException::new);
    }

    public Page<PautaEntity> returnPautasPageable(Pageable pageable) {
        return pautaRepository.findAll(pageable);
    }

    @Transactional
    public PautaEntity savePauta(PautaEntity pauta) {
        return pautaRepository.save(pauta);
    }
}
