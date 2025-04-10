package br.com.joaopmazzo.desafio_backend_sicredi.domain.services;

import br.com.joaopmazzo.desafio_backend_sicredi.application.exceptions.associado.AssociadoNaoPodeVotarException;
import br.com.joaopmazzo.desafio_backend_sicredi.application.exceptions.associado.AssociadoNotFoundException;
import br.com.joaopmazzo.desafio_backend_sicredi.domain.entities.AssociadoEntity;
import br.com.joaopmazzo.desafio_backend_sicredi.domain.repositories.AssociadoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AssociadoService {

    private final AssociadoRepository associadoRepository;

    public AssociadoEntity validateAssociado(String documento) {
        AssociadoEntity associado = associadoRepository
                .findByDocumento(documento)
                .orElseThrow(AssociadoNotFoundException::new);
        if (!associado.isAbleToVote()) {
            throw new AssociadoNaoPodeVotarException();
        }
        return associado;
    }

}
