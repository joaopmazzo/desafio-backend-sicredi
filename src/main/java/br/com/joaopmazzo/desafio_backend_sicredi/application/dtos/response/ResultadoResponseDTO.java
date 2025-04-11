package br.com.joaopmazzo.desafio_backend_sicredi.application.dtos.response;

import br.com.joaopmazzo.desafio_backend_sicredi.application.dtos.enums.ResultadoVotacaoEnum;

import java.util.UUID;

public record ResultadoResponseDTO(
        UUID pautaId,
        String tituloPauta,
        long totalAFavor,
        long totalContra,
        long totalVotos,
        ResultadoVotacaoEnum resultado
) {}
