package br.com.joaopmazzo.desafio_backend_sicredi.services;

import br.com.joaopmazzo.desafio_backend_sicredi.dtos.request.SessaoRequestDTO;
import br.com.joaopmazzo.desafio_backend_sicredi.dtos.response.SessaoResponseDTO;
import br.com.joaopmazzo.desafio_backend_sicredi.entities.PautaEntity;
import br.com.joaopmazzo.desafio_backend_sicredi.entities.SessaoEntity;
import br.com.joaopmazzo.desafio_backend_sicredi.enums.StatusSessaoEnum;
import br.com.joaopmazzo.desafio_backend_sicredi.mappers.SessaoMapper;
import br.com.joaopmazzo.desafio_backend_sicredi.repositories.SessaoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SessaoService {

    private final PautaService pautaService;
    private final SessaoRepository sessaoRepository;
    private final SessaoMapper sessaoMapper;

    public SessaoResponseDTO abrirSessao(UUID pautaID, SessaoRequestDTO sessaoRequestDTO) {
        PautaEntity pauta = pautaService.findPautaById(pautaID);

        existsSessaoByPautaIdAndStatusNotLikeCancelado(pautaID);

        SessaoEntity entity = sessaoMapper.toEntity(sessaoRequestDTO);
        entity.setPauta(pauta);

        SessaoEntity savedEntity = sessaoRepository.save(entity);

        return sessaoMapper.toResponseDTO(savedEntity);
    }

    public Page<SessaoResponseDTO> getAllSessoes(Pageable pageable) {
        Page<SessaoEntity> sessoes = sessaoRepository.findAll(pageable);

        return sessoes.map(sessaoMapper::toResponseDTO);
    }

    public SessaoResponseDTO getSessao(UUID pautaID) {
        SessaoEntity sessao = findSessaoByPautaId(pautaID);

        return sessaoMapper.toResponseDTO(sessao);
    }

    public SessaoEntity findSessaoByPautaId(UUID id) {
        return sessaoRepository
                .findByPautaId(id)
                .orElseThrow(() -> new RuntimeException("Nenhuma sessão em andamento para a pauta informada"));
    }

    public void existsSessaoByPautaIdAndStatusNotLikeCancelado(UUID pautaID) {
        if (sessaoRepository.existsByPautaIdAndStatusNotLikeCancelado(pautaID)) {
            throw new RuntimeException("Já existe uma sessão aberta para esta pauta");
        }
    }

    @Scheduled(fixedRate = 60000)
    private void finishSessoesExpiradas() {
        int pageSize = 50; // Tamanho do lote
        int page = 0;

        while (true) {
            // Busca sessões expiradas em lotes
            Page<SessaoEntity> sessoesExpiradas = sessaoRepository
                    .findAllByStatusAndTerminoLessThanEqual(
                            StatusSessaoEnum.EM_ANDAMENTO,
                            LocalDateTime.now(),
                            PageRequest.of(page, pageSize));

            if (sessoesExpiradas.isEmpty()) {
                break; // Sai do loop se não houver mais dados
            }

            // Atualiza o status das sessões para "ENCERRADO"
            sessoesExpiradas.forEach(sessao -> sessao.setStatus(StatusSessaoEnum.ENCERRADA));
            sessaoRepository.saveAll(sessoesExpiradas);

            // Publica os resultados na fila de mensageria
            sessoesExpiradas.forEach(sessao -> {
                // Lógica para publicar na fila (exemplo)
            });

            page++;
        }
    }

}
