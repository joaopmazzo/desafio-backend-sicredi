package br.com.joaopmazzo.desafio_backend_sicredi.application.exceptions.associado;

import br.com.joaopmazzo.desafio_backend_sicredi.application.exceptions.base.BusinessException;

public class AssociadoNaoPodeVotarException extends BusinessException {
    public AssociadoNaoPodeVotarException() {
        super("Associado nao pode votar.");
    }
}
