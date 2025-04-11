package br.com.joaopmazzo.desafio_backend_sicredi.application.dtos.request;

import br.com.joaopmazzo.desafio_backend_sicredi.domain.enums.EscolhaVotoEnum;
import jakarta.validation.constraints.NotNull;

public record VotoRequestDTO(
    @NotNull(message = "O documento não pode ser nulo.")
    String documento,

    @NotNull(message = "A escolha do voto não pode ser nulo.")
    EscolhaVotoEnum escolhaVoto
) {}
