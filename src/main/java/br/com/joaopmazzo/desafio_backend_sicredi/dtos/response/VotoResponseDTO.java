package br.com.joaopmazzo.desafio_backend_sicredi.dtos.response;

import br.com.joaopmazzo.desafio_backend_sicredi.enums.EscolhaVotoEnum;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class VotoResponseDTO {

    private UUID id;

    private SessaoResponseDTO sessao;

    private AssociadoResponseDTO associado;

    private EscolhaVotoEnum aFavor;

    private LocalDateTime registradoEm;

}
