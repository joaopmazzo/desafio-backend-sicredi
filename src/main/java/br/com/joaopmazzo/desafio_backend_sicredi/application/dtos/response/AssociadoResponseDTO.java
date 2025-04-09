package br.com.joaopmazzo.desafio_backend_sicredi.application.dtos.response;

import lombok.Data;

import java.util.UUID;

@Data
public class AssociadoResponseDTO {

    private UUID id;

    private String documento;

    private boolean ableToVote;

}
