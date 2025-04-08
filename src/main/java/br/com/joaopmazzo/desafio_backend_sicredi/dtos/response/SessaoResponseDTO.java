package br.com.joaopmazzo.desafio_backend_sicredi.dtos.response;

import br.com.joaopmazzo.desafio_backend_sicredi.enums.StatusSessaoEnum;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class SessaoResponseDTO {

    private UUID id;

    private UUID pautaId;

    private StatusSessaoEnum status;

    private LocalDateTime inicio;

    private LocalDateTime termino;

}
