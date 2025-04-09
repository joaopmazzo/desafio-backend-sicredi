package br.com.joaopmazzo.desafio_backend_sicredi.dtos.request;

import jakarta.validation.constraints.Positive;

public record SessaoRequestDTO(
        @Positive(message = "O tempo de duração deve ser um valor positivo.")
        Integer duracaoEmMinutos
) {
    public SessaoRequestDTO {
        if (duracaoEmMinutos == null) {
            duracaoEmMinutos = 1;
        }
    }
}
