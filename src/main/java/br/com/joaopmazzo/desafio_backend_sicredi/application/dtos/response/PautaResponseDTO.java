package br.com.joaopmazzo.desafio_backend_sicredi.application.dtos.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PautaResponseDTO {

    private UUID id;

    private String titulo;

    private String descricao;

    private LocalDateTime criadoEm;

}
