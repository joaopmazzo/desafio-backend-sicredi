package br.com.joaopmazzo.desafio_backend_sicredi.dtos.request;

import br.com.joaopmazzo.desafio_backend_sicredi.enums.EscolhaVotoEnum;
import jakarta.validation.constraints.NotNull;

public record VotoRequestDTO(
    @NotNull(message = "O documento não pode ser nulo.")
    String documento,

    @NotNull(message = "O status do voto não pode ser nulo.")
    EscolhaVotoEnum aFavor
) {}
