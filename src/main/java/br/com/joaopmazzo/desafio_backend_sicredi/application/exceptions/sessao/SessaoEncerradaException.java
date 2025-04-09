package br.com.joaopmazzo.desafio_backend_sicredi.application.exceptions.sessao;

import br.com.joaopmazzo.desafio_backend_sicredi.application.exceptions.base.BusinessException;

public class SessaoEncerradaException extends BusinessException {
    public SessaoEncerradaException() {
        super("Sessão encerrada, não é possível registrar o voto.");
    }
}
