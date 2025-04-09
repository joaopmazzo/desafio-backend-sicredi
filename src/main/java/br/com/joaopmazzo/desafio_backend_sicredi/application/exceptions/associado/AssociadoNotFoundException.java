package br.com.joaopmazzo.desafio_backend_sicredi.application.exceptions.associado;

import br.com.joaopmazzo.desafio_backend_sicredi.application.exceptions.base.ResourceNotFoundException;

public class AssociadoNotFoundException extends ResourceNotFoundException {
    public AssociadoNotFoundException() {
        super("Associado não encontrado");
    }
}
