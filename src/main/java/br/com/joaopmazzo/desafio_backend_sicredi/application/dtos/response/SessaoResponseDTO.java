package br.com.joaopmazzo.desafio_backend_sicredi.application.dtos.response;

import br.com.joaopmazzo.desafio_backend_sicredi.domain.enums.StatusSessaoEnum;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class SessaoResponseDTO {

    private UUID id;

    private PautaResponseDTO pauta;

    private StatusSessaoEnum status;

    private LocalDateTime inicio;

    private LocalDateTime termino;

}
