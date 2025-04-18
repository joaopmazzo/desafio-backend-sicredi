package br.com.joaopmazzo.desafio_backend_sicredi.application.controllers;

import br.com.joaopmazzo.desafio_backend_sicredi.application.dtos.request.PautaRequestDTO;
import br.com.joaopmazzo.desafio_backend_sicredi.application.dtos.request.SessaoRequestDTO;
import br.com.joaopmazzo.desafio_backend_sicredi.application.dtos.request.VotoRequestDTO;
import br.com.joaopmazzo.desafio_backend_sicredi.application.dtos.response.PautaResponseDTO;
import br.com.joaopmazzo.desafio_backend_sicredi.application.dtos.response.ResultadoResponseDTO;
import br.com.joaopmazzo.desafio_backend_sicredi.application.dtos.response.SessaoResponseDTO;
import br.com.joaopmazzo.desafio_backend_sicredi.application.dtos.response.VotoResponseDTO;
import br.com.joaopmazzo.desafio_backend_sicredi.application.usecases.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping(value = "/api/v1/pautas", produces = "application/json")
@RequiredArgsConstructor
@Tag(name = "Pautas", description = "Gerenciamento de pautas, sessões de votação e votos")
public class PautaController {

    private final CreatePautaUseCase createPautaUseCase;
    private final ReturnPautasUseCase returnPautasUseCase;
    private final FindPautaUseCase findPautaUseCase;

    private final AbrirSessaoUseCase abrirSessaoUseCase;
    private final FindSessaoUseCase findSessaoUseCase;

    private final RegisterVotoUseCase registerVotoUseCase;

    private final FindResultadoPautaUseCase findResultadoPautaUseCase;

    @PostMapping
    @Operation(summary = "Criar uma nova pauta", description = "Cria uma nova pauta para votação.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Pauta criada com sucesso",
                    content = @Content(schema = @Schema(implementation = PautaResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Já existe uma pauta com esse título.", content = @Content)
    })
    public ResponseEntity<PautaResponseDTO> createPauta(@Valid @RequestBody PautaRequestDTO dto) {
        log.info("Recebendo requisição para criar pauta: {}", dto);
        PautaResponseDTO pautaResponseDTO = createPautaUseCase.execute(dto);

        URI location = UriComponentsBuilder
                .fromPath("/api/v1/pautas/{id}")
                .buildAndExpand(pautaResponseDTO.getId())
                .toUri();

        log.info("Pauta criada com sucesso: {}", pautaResponseDTO.getId());
        return ResponseEntity.created(location).body(pautaResponseDTO);
    }

    @GetMapping
    @Operation(summary = "Listar pautas", description = "Retorna uma lista paginada de pautas.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista de pautas retornada com sucesso",
                    content = @Content(schema = @Schema(implementation = Page.class)))
    })
    public ResponseEntity<Page<PautaResponseDTO>> getPautas(Pageable pageable) {
        Page<PautaResponseDTO> pautaResponseDTO = returnPautasUseCase.execute(pageable);

        return ResponseEntity.ok(pautaResponseDTO);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar pauta por ID", description = "Retorna os detalhes de uma pauta específica.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Pauta encontrada",
                    content = @Content(schema = @Schema(implementation = PautaResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "Pauta não encontrada", content = @Content)
    })
    public ResponseEntity<PautaResponseDTO> getPautaById(@PathVariable UUID id) {
        PautaResponseDTO pautaResponseDTO = findPautaUseCase.execute(id);

        return ResponseEntity.ok(pautaResponseDTO);
    }


    @PostMapping("{pautaId}/sessao")
    @Operation(summary = "Abrir sessão de votação", description = "Abre uma nova sessão de votação para uma pauta.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Sessão aberta com sucesso",
                    content = @Content(schema = @Schema(implementation = SessaoResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "Pauta não encontrada", content = @Content),
            @ApiResponse(responseCode = "400", description = "Já existe uma sessão aberta", content = @Content)
    })
    public ResponseEntity<SessaoResponseDTO> openSessao(@PathVariable("pautaId") UUID pautaId,
                                                        @Valid @RequestBody SessaoRequestDTO dto) {
        log.info("Recebendo requisição para iniciar uma sessao: ID da pauta={} dto={}", pautaId, dto);
        SessaoResponseDTO sessaoResponseDTO = abrirSessaoUseCase.execute(pautaId, dto);

        URI location = UriComponentsBuilder
                .fromPath("/api/v1/pautas/{pautaId}/sessao")
                .buildAndExpand(pautaId)
                .toUri();

        log.info("Sessão iniciada com sucesso: {}", sessaoResponseDTO.getId());
        return ResponseEntity.created(location).body(sessaoResponseDTO);
    }

    @GetMapping("{pautaId}/sessao")
    @Operation(summary = "Buscar sessão de votação", description = "Retorna os detalhes de uma sessão de votação específica.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Sessão encontrada",
                    content = @Content(schema = @Schema(implementation = SessaoResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "Sessão não encontrada", content = @Content)
    })
    public ResponseEntity<SessaoResponseDTO> getSessao(@PathVariable("pautaId") UUID pautaId) {
        SessaoResponseDTO sessaoResponseDTO = findSessaoUseCase.execute(pautaId);

        return ResponseEntity.ok(sessaoResponseDTO);
    }


    @PostMapping("{pautaId}/voto")
    @Operation(summary = "Registrar voto", description = "Registra um voto em uma sessão de votação.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Voto registrado com sucesso",
                    content = @Content(schema = @Schema(implementation = VotoResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "Sessão não encontrada ou Associado não encontrado.", content = @Content),
            @ApiResponse(responseCode = "400", description = "Sessão encerrada, Associado não pode votar ou Voto já registrado nesta sessão.", content = @Content)
    })
    public ResponseEntity<VotoResponseDTO> registerVoto(@PathVariable("pautaId") UUID pautaId,
                                                        @Valid @RequestBody VotoRequestDTO dto) {
        log.info("Recebendo requisição para registrar voto: ID da pauta={} dto={}", pautaId, dto);
        VotoResponseDTO votoResponseDTO = registerVotoUseCase.execute(pautaId, dto);

        log.info("Voto registrado com sucesso: {}", votoResponseDTO.getId());
        return ResponseEntity.created(null).body(votoResponseDTO);
    }

    @GetMapping("{pautaId}/resultado")
    @Operation(summary = "Resultado da votação", description = "Retorna o resultado da votação de uma pauta.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Resultado da votação encontrado",
                    content = @Content(schema = @Schema(implementation = ResultadoResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "Sessão não encontrada.", content = @Content)
    })
    public ResponseEntity<ResultadoResponseDTO> getResultado(@PathVariable("pautaId") UUID pautaId) {
        ResultadoResponseDTO resultadoResponseDTO = findResultadoPautaUseCase.execute(pautaId);

        return ResponseEntity.ok(resultadoResponseDTO);
    }

}
