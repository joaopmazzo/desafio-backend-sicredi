package br.com.joaopmazzo.desafio_backend_sicredi.dtos.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.util.UUID;

@Data
public class SessaoRequestDTO {

    @NotNull(message = "O ID da pauta não pode ser nulo.")
    private UUID pautaId;

    @Positive(message = "O tempo de duração deve ser um valor positivo.")
    private Integer tempoDuracao = 1;

}
