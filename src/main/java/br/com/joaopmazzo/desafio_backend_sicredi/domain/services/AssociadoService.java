package br.com.joaopmazzo.desafio_backend_sicredi.domain.services;

import br.com.joaopmazzo.desafio_backend_sicredi.application.exceptions.associado.AssociadoNaoPodeVotarException;
import br.com.joaopmazzo.desafio_backend_sicredi.application.exceptions.associado.AssociadoNotFoundException;
import br.com.joaopmazzo.desafio_backend_sicredi.domain.entities.AssociadoEntity;
import br.com.joaopmazzo.desafio_backend_sicredi.domain.repositories.AssociadoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * Camada de serviço para Associados.
 */
@Service
@RequiredArgsConstructor
public class AssociadoService {

    private final AssociadoRepository associadoRepository;

    /**
     * Valida se um associado existe e está apto a votar.
     *
     * @param documento Documento do associado a ser validado.
     * @return Entidade AssociadoEntity validada.
     * @throws AssociadoNotFoundException se o associado não for encontrado pelo documento fornecido.
     * @throws AssociadoNaoPodeVotarException se o associado não estiver apto a votar.
     */
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
