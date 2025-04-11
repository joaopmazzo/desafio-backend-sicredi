package br.com.joaopmazzo.desafio_backend_sicredi.application.dtos.request;

import br.com.joaopmazzo.desafio_backend_sicredi.domain.enums.EscolhaVotoEnum;
import br.com.joaopmazzo.desafio_backend_sicredi.domain.validations.CpfCnpj;
import jakarta.validation.constraints.NotNull;

public record VotoRequestDTO(
    @CpfCnpj
    String documento,

    @NotNull(message = "A escolha do voto não pode ser nulo.")
    EscolhaVotoEnum escolhaVoto
) {}
