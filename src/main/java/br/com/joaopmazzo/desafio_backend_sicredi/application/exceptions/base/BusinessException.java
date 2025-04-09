package br.com.joaopmazzo.desafio_backend_sicredi.application.exceptions.base;

public class BusinessException extends RuntimeException {
    public BusinessException(String message) {
        super(message);
    }
}