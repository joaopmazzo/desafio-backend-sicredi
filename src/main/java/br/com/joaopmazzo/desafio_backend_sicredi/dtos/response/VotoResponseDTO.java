package br.com.joaopmazzo.desafio_backend_sicredi.dtos.response;

import br.com.joaopmazzo.desafio_backend_sicredi.enums.VotoEnum;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class VotoResponseDTO {

    private UUID id;

    private UUID sessaoId;

    private UUID associadoId;

    private VotoEnum status;

    private LocalDateTime registradoEm;

}
