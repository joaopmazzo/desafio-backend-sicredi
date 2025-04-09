package br.com.joaopmazzo.desafio_backend_sicredi.application.exceptions.sessao;

import br.com.joaopmazzo.desafio_backend_sicredi.application.exceptions.base.ResourceNotFoundException;

public class SessaoNotFoundException extends ResourceNotFoundException {
    public SessaoNotFoundException() {
        super("Nenhuma sessão encontrada");
    }
}
