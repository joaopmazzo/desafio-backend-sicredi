package br.com.joaopmazzo.desafio_backend_sicredi.controllers;

import br.com.joaopmazzo.desafio_backend_sicredi.dtos.request.PautaRequestDTO;
import br.com.joaopmazzo.desafio_backend_sicredi.dtos.request.SessaoRequestDTO;
import br.com.joaopmazzo.desafio_backend_sicredi.dtos.request.VotoRequestDTO;
import br.com.joaopmazzo.desafio_backend_sicredi.dtos.response.PautaResponseDTO;
import br.com.joaopmazzo.desafio_backend_sicredi.dtos.response.SessaoResponseDTO;
import br.com.joaopmazzo.desafio_backend_sicredi.dtos.response.VotoResponseDTO;
import br.com.joaopmazzo.desafio_backend_sicredi.services.PautaService;
import br.com.joaopmazzo.desafio_backend_sicredi.services.SessaoService;
import br.com.joaopmazzo.desafio_backend_sicredi.services.VotoService;
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

    private final PautaService pautaService;
    private final SessaoService sessaoService;
    private final VotoService votoService;

    // pautas
    @PostMapping
    public ResponseEntity<PautaResponseDTO> createPauta(@Valid @RequestBody PautaRequestDTO dto) {
        PautaResponseDTO pautaResponseDTO = pautaService.createPauta(dto);

        URI location = UriComponentsBuilder
                .fromPath("/api/v1/pautas/{id}")
                .buildAndExpand(pautaResponseDTO.getId())
                .toUri();

        return ResponseEntity.created(location).body(pautaResponseDTO);
    }

    @GetMapping
    public ResponseEntity<Page<PautaResponseDTO>> getPautas(Pageable pageable) {
        Page<PautaResponseDTO> pautaResponseDTO = pautaService.getPautas(pageable);

        return ResponseEntity.ok(pautaResponseDTO);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PautaResponseDTO> getPautaById(@PathVariable UUID id) {
        PautaResponseDTO pautaResponseDTO = pautaService.getPautaById(id);

        return ResponseEntity.ok(pautaResponseDTO);
    }



    // sessao
    @PostMapping("{pautaId}/sessao")
    public ResponseEntity<SessaoResponseDTO> openSessao(@PathVariable("pautaId") UUID pautaId,
                                                        @Valid @RequestBody SessaoRequestDTO dto) {
        SessaoResponseDTO sessaoResponseDTO = sessaoService.abrirSessao(pautaId, dto);

        URI location = UriComponentsBuilder
                .fromPath("/api/v1/pautas/{pautaId}/sessao")
                .buildAndExpand(pautaId)
                .toUri();

        return ResponseEntity.created(location).body(sessaoResponseDTO);
    }

    @GetMapping("{pautaId}/sessao")
    public ResponseEntity<SessaoResponseDTO> getSessao(@PathVariable("pautaId") UUID pautaId) {
        SessaoResponseDTO sessaoResponseDTO = sessaoService.getSessao(pautaId);

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
        VotoResponseDTO votoResponseDTO = votoService.registerVoto(pautaId, dto);

        // TODO: refatorar isso
        URI location = UriComponentsBuilder
                .fromPath("/api/v1/pautas/{pautaId}/sessao/voto")
                .buildAndExpand(pautaId)
                .toUri();

        return ResponseEntity.created(location).body(votoResponseDTO);
    }

}
