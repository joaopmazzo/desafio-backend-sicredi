package br.com.joaopmazzo.desafio_backend_sicredi.dtos.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class PautaRequestDTO {

    @NotBlank(message = "O título não pode estar em branco.")
    @Size(max = 255, message = "O título deve ter no máximo 255 caracteres.")
    private String titulo;

    @NotBlank(message = "A descrição não pode estar em branco.")
    private String descricao;

}
