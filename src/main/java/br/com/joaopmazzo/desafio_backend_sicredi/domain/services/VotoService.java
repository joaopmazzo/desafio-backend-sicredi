package br.com.joaopmazzo.desafio_backend_sicredi.domain.services;

import br.com.joaopmazzo.desafio_backend_sicredi.application.exceptions.voto.VotoJaRegistradoException;
import br.com.joaopmazzo.desafio_backend_sicredi.domain.entities.AssociadoEntity;
import br.com.joaopmazzo.desafio_backend_sicredi.domain.entities.SessaoEntity;
import br.com.joaopmazzo.desafio_backend_sicredi.domain.entities.VotoEntity;
import br.com.joaopmazzo.desafio_backend_sicredi.domain.repositories.VotoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class VotoService {

    private final VotoRepository votoRepository;

    @Transactional(readOnly = true)
    public void alreadyVoted(SessaoEntity sessao, AssociadoEntity associado) {
        if (votoRepository.existsBySessaoAndAssociado(sessao, associado))
            throw new VotoJaRegistradoException();
    }

    @Transactional
    public VotoEntity saveVoto(VotoEntity voto) {
        return votoRepository.save(voto);
    }

}