package br.com.joaopmazzo.desafio_backend_sicredi.application.exceptions.voto;

import br.com.joaopmazzo.desafio_backend_sicredi.application.exceptions.base.BusinessException;

public class VotoJaRegistradoException extends BusinessException {
    public VotoJaRegistradoException() {
        super("Voto já registrado nesta sessão.");
    }
}
