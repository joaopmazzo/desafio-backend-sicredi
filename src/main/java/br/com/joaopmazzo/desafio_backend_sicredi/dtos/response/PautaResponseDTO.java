package br.com.joaopmazzo.desafio_backend_sicredi.dtos.response;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class PautaResponseDTO {

    private UUID id;

    private String titulo;

    private String descricao;

    private LocalDateTime criadoEm;

}
