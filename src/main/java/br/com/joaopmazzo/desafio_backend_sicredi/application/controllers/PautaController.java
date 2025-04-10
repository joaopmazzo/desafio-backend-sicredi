package br.com.joaopmazzo.desafio_backend_sicredi.application.controllers;

import br.com.joaopmazzo.desafio_backend_sicredi.application.dtos.request.PautaRequestDTO;
import br.com.joaopmazzo.desafio_backend_sicredi.application.dtos.request.SessaoRequestDTO;
import br.com.joaopmazzo.desafio_backend_sicredi.application.dtos.request.VotoRequestDTO;
import br.com.joaopmazzo.desafio_backend_sicredi.application.dtos.response.PautaResponseDTO;
import br.com.joaopmazzo.desafio_backend_sicredi.application.dtos.response.SessaoResponseDTO;
import br.com.joaopmazzo.desafio_backend_sicredi.application.dtos.response.VotoResponseDTO;
import br.com.joaopmazzo.desafio_backend_sicredi.application.usecases.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/pautas")
@RequiredArgsConstructor
public class PautaController {

    private final CreatePautaUseCase createPautaUseCase;
    private final ReturnPautasUseCase returnPautasUseCase;
    private final FindPautaUseCase findPautaUseCase;

    private final AbrirSessaoUseCase abrirSessaoUseCase;
    private final FindSessaoUseCase findSessaoUseCase;

    private final RegisterVotoUseCase registerVotoUseCase;

    // pautas
    @PostMapping
    public ResponseEntity<PautaResponseDTO> createPauta(@Valid @RequestBody PautaRequestDTO dto) {
        PautaResponseDTO pautaResponseDTO = createPautaUseCase.execute(dto);

        URI location = UriComponentsBuilder
                .fromPath("/api/v1/pautas/{id}")
                .buildAndExpand(pautaResponseDTO.getId())
                .toUri();

        return ResponseEntity.created(location).body(pautaResponseDTO);
    }

    @GetMapping
    public ResponseEntity<Page<PautaResponseDTO>> getPautas(Pageable pageable) {
        Page<PautaResponseDTO> pautaResponseDTO = returnPautasUseCase.execute(pageable);

        return ResponseEntity.ok(pautaResponseDTO);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PautaResponseDTO> getPautaById(@PathVariable UUID id) {
        PautaResponseDTO pautaResponseDTO = findPautaUseCase.execute(id);

        return ResponseEntity.ok(pautaResponseDTO);
    }



    // sessao
    @PostMapping("{pautaId}/sessao")
    public ResponseEntity<SessaoResponseDTO> openSessao(@PathVariable("pautaId") UUID pautaId,
                                                        @Valid @RequestBody SessaoRequestDTO dto) {
        SessaoResponseDTO sessaoResponseDTO = abrirSessaoUseCase.execute(pautaId, dto);

        URI location = UriComponentsBuilder
                .fromPath("/api/v1/pautas/{pautaId}/sessao")
                .buildAndExpand(pautaId)
                .toUri();

        return ResponseEntity.created(location).body(sessaoResponseDTO);
    }

    @GetMapping("{pautaId}/sessao")
    public ResponseEntity<SessaoResponseDTO> getSessao(@PathVariable("pautaId") UUID pautaId) {
        SessaoResponseDTO sessaoResponseDTO = findSessaoUseCase.execute(pautaId);

        return ResponseEntity.ok(sessaoResponseDTO);
    }

//    @PostMapping("{pautaId}/sessao/resultado")
//    public ResponseEntity<VotoResponseDTO> resultSessao(@PathVariable("pautaId") UUID pautaId) {
//        VotoResponseDTO votoResponseDTO = votoService.registerVoto(pautaId);
//
//        return ResponseEntity.ok();
//    }



    @PostMapping("{pautaId}/sessao/voto")
    public ResponseEntity<VotoResponseDTO> registerVoto(@PathVariable("pautaId") UUID pautaId,
                                                        @Valid @RequestBody VotoRequestDTO dto) {
        VotoResponseDTO votoResponseDTO = registerVotoUseCase.execute(pautaId, dto);

        // TODO: refatorar isso
        URI location = UriComponentsBuilder
                .fromPath("/api/v1/pautas/{pautaId}/sessao/voto")
                .buildAndExpand(pautaId)
                .toUri();

        return ResponseEntity.created(location).body(votoResponseDTO);
    }

}
