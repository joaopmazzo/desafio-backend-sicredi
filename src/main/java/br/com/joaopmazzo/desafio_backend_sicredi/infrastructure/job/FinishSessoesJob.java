package br.com.joaopmazzo.desafio_backend_sicredi.infrastructure.job;

import br.com.joaopmazzo.desafio_backend_sicredi.application.dtos.response.ResultadoResponseDTO;
import br.com.joaopmazzo.desafio_backend_sicredi.domain.entities.SessaoEntity;
import br.com.joaopmazzo.desafio_backend_sicredi.domain.enums.StatusSessaoEnum;
import br.com.joaopmazzo.desafio_backend_sicredi.domain.services.SessaoService;
import br.com.joaopmazzo.desafio_backend_sicredi.infrastructure.messaging.ResultadoSessaoProducer;
import lombok.RequiredArgsConstructor;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

/**
 * Scheduler responsável por finalizar sessões expiradas periodicamente.
 * <p>
 * Este componente verifica sessões com status "EM_ANDAMENTO" cujo data de término
 * já tenha passado e realiza as seguintes ações:
 * - Atualiza o status da sessão para "ENCERRADA".
 * - Calcula o resultado da votação da sessão.
 * - Publica o resultado em uma fila de mensageria.
 * - Persiste as alterações no banco de dados.
 */
@Component
@DisallowConcurrentExecution
@RequiredArgsConstructor
public class FinishSessoesJob implements Job {

    private final SessaoService sessaoService;
    private final ResultadoSessaoProducer resultadoSessaoProducer;

    @Override
    public void execute(JobExecutionContext context) {
        // Configuração inicial de paginação (50 registros por página)
        Pageable pageable = PageRequest.of(0, 50);

        while (true) {
            // Busca sessões expiradas em lotes com base no status e no término
            Page<SessaoEntity> sessoesExpiradas = sessaoService
                    .findAllByStatusAndTerminoLessThanEqual(pageable);

            // Sai do loop se não houver mais sessões a processar
            if (sessoesExpiradas.isEmpty()) break;

            sessoesExpiradas.forEach(sessao -> {
                // Atualiza o status da sessão para "ENCERRADA"
                sessao.setStatus(StatusSessaoEnum.ENCERRADA);

                // Calcula o resultado da votação da sessão
                ResultadoResponseDTO resultado = sessaoService.contabilizaResultado(sessao);

                // Publica o resultado na fila de mensageria
                resultadoSessaoProducer.sendMessage(resultado);
            });

            // Salva as alterações no banco de dados e avança para a próxima página
            sessaoService.saveAllSessao(sessoesExpiradas);
            pageable.next();
        }
    }
}
