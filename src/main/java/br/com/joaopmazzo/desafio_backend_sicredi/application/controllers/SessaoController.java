package br.com.joaopmazzo.desafio_backend_sicredi.application.controllers;

import br.com.joaopmazzo.desafio_backend_sicredi.application.dtos.response.SessaoResponseDTO;
import br.com.joaopmazzo.desafio_backend_sicredi.application.usecases.ReturnSessoesUseCase;
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
public class SessaoController {

    private final ReturnSessoesUseCase returnSessoesUseCase;

    @GetMapping
    public ResponseEntity<Page<SessaoResponseDTO>> returnAllSessoes(Pageable pageable) {
        Page<SessaoResponseDTO> sessoes = returnSessoesUseCase.execute(pageable);

        return ResponseEntity.ok(sessoes);
    }

}
