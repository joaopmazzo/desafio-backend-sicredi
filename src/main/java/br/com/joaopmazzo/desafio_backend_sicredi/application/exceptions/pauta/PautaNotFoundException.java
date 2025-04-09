package br.com.joaopmazzo.desafio_backend_sicredi.application.exceptions.pauta;

import br.com.joaopmazzo.desafio_backend_sicredi.application.exceptions.base.ResourceNotFoundException;

public class PautaNotFoundException extends ResourceNotFoundException {
    public PautaNotFoundException() {
        super("Pauta não encontrada");
    }
}
