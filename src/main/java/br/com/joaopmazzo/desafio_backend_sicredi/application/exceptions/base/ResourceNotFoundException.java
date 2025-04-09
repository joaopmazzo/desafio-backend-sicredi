package br.com.joaopmazzo.desafio_backend_sicredi.application.exceptions.base;

public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String message) {
        super(message);
    }
}
