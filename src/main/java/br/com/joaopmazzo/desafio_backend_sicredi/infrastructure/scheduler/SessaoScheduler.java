package br.com.joaopmazzo.desafio_backend_sicredi.infrastructure.scheduler;

import br.com.joaopmazzo.desafio_backend_sicredi.domain.entities.SessaoEntity;
import br.com.joaopmazzo.desafio_backend_sicredi.domain.enums.StatusSessaoEnum;
import br.com.joaopmazzo.desafio_backend_sicredi.domain.repositories.SessaoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class SessaoScheduler {

    private final SessaoRepository sessaoRepository;

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
