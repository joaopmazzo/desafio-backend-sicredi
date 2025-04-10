package br.com.joaopmazzo.desafio_backend_sicredi.application.controllers;

import br.com.joaopmazzo.desafio_backend_sicredi.application.dtos.response.SessaoResponseDTO;
import br.com.joaopmazzo.desafio_backend_sicredi.application.usecases.ReturnSessoesUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/sessoes")
@RequiredArgsConstructor
@Tag(name = "Sessões", description = "Gerenciamento de sessões de votação")
public class SessaoController {

    private final ReturnSessoesUseCase returnSessoesUseCase;

    @GetMapping
    @Operation(summary = "Listar sessões", description = "Retorna uma lista paginada de sessões de votação.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista de sessões retornada com sucesso",
                    content = @Content(schema = @Schema(implementation = Page.class)))
    })
    public ResponseEntity<Page<SessaoResponseDTO>> returnAllSessoes(Pageable pageable) {
        Page<SessaoResponseDTO> sessoes = returnSessoesUseCase.execute(pageable);

        return ResponseEntity.ok(sessoes);
    }

}
