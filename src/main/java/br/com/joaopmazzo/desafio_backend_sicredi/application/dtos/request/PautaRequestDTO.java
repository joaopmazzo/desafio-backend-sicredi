package br.com.joaopmazzo.desafio_backend_sicredi.application.dtos.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record PautaRequestDTO(
    @NotBlank(message = "O título não pode estar em branco.")
    @Size(max = 255, message = "O título deve ter no máximo 255 caracteres.")
    String titulo,

    @NotBlank(message = "A descrição não pode estar em branco.")
    String descricao
) {}
