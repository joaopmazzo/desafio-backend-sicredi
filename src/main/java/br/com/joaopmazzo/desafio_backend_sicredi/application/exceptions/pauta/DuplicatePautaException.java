package br.com.joaopmazzo.desafio_backend_sicredi.application.exceptions.pauta;

import br.com.joaopmazzo.desafio_backend_sicredi.application.exceptions.base.BusinessException;

public class DuplicatePautaException extends BusinessException {
    public DuplicatePautaException() {
        super("Já existe uma pauta com esse título.");
    }
}
