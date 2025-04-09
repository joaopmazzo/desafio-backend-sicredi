package br.com.joaopmazzo.desafio_backend_sicredi.application.exceptions.sessao;

import br.com.joaopmazzo.desafio_backend_sicredi.application.exceptions.base.BusinessException;

public class SessaoJaAbertaException extends BusinessException {
    public SessaoJaAbertaException() {
        super("Já existe uma sessão aberta");
    }
}
