package br.com.joaopmazzo.desafio_backend_sicredi.dtos.request;

import br.com.joaopmazzo.desafio_backend_sicredi.enums.VotoEnum;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.UUID;

@Data
public class VotoRequestDTO {

    @NotNull(message = "O ID da sessão não pode ser nulo.")
    private UUID sessaoId;

    @NotNull(message = "O ID do associado não pode ser nulo.")
    private UUID associadoId;

    @NotNull(message = "O status do voto não pode ser nulo.")
    private VotoEnum status;

}
