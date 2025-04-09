package br.com.joaopmazzo.desafio_backend_sicredi.services;

import br.com.joaopmazzo.desafio_backend_sicredi.dtos.request.PautaRequestDTO;
import br.com.joaopmazzo.desafio_backend_sicredi.dtos.response.PautaResponseDTO;
import br.com.joaopmazzo.desafio_backend_sicredi.entities.PautaEntity;
import br.com.joaopmazzo.desafio_backend_sicredi.mappers.PautaMapper;
import br.com.joaopmazzo.desafio_backend_sicredi.repositories.PautaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PautaService {

    private final PautaRepository pautaRepository;
    private final PautaMapper pautaMapper;

    public PautaResponseDTO createPauta(PautaRequestDTO dto) {
        PautaEntity entity = PautaMapper.INSTANCE.toEntity(dto);

        PautaEntity savedEntity = pautaRepository.save(entity);

        return pautaMapper.toResponseDTO(savedEntity);
    }

    public Page<PautaResponseDTO> getPautas(Pageable pageable) {
        Page<PautaEntity> pautas = pautaRepository.findAll(pageable);

        return pautas.map(pautaMapper::toResponseDTO);
    }

    public PautaResponseDTO getPautaById(UUID id) {
        PautaEntity pauta = findPautaById(id);

        return pautaMapper.toResponseDTO(pauta);
    }

    public PautaEntity findPautaById(UUID id) {
        return pautaRepository
                .findById(id)
                .orElseThrow(
                        () -> new RuntimeException("Pauta não encontrada com o id: " + id)
                );
    }
}
