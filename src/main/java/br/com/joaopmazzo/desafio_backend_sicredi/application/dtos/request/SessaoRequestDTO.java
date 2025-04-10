package br.com.joaopmazzo.desafio_backend_sicredi.application.dtos.request;

import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SessaoRequestDTO {

    @Positive(message = "O tempo de duração deve ser um valor positivo.")
    @Builder.Default
    private Integer duracaoEmMinutos = 1;

}
