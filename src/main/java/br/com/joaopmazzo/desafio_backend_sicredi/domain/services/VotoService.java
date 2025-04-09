package br.com.joaopmazzo.desafio_backend_sicredi.domain.services;

import br.com.joaopmazzo.desafio_backend_sicredi.application.dtos.request.VotoRequestDTO;
import br.com.joaopmazzo.desafio_backend_sicredi.application.dtos.response.VotoResponseDTO;
import br.com.joaopmazzo.desafio_backend_sicredi.domain.entities.AssociadoEntity;
import br.com.joaopmazzo.desafio_backend_sicredi.domain.entities.SessaoEntity;
import br.com.joaopmazzo.desafio_backend_sicredi.domain.entities.VotoEntity;
import br.com.joaopmazzo.desafio_backend_sicredi.domain.enums.StatusSessaoEnum;
import br.com.joaopmazzo.desafio_backend_sicredi.domain.repositories.AssociadoRepository;
import br.com.joaopmazzo.desafio_backend_sicredi.domain.repositories.VotoRepository;
import br.com.joaopmazzo.desafio_backend_sicredi.infrastructure.mappers.VotoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class VotoService {

    private final SessaoService sessaoService;
    private final VotoRepository votoRepository;
    private final AssociadoRepository associadoRepository;
    private final VotoMapper votoMapper;

    public VotoResponseDTO registerVoto(UUID pautaId, VotoRequestDTO dto) {
        // Retornar a sessão pela pauta, status e data não expirada ainda
        SessaoEntity sessao = sessaoService.findSessaoByPautaId(pautaId);

        if (sessao.getStatus() == StatusSessaoEnum.ENCERRADA || sessao.getTermino().isBefore(LocalDateTime.now()))
            throw new RuntimeException("Não é possível registrar o voto, pois a sessão esta encerrada.");

        // retorna o associado e valida se o mesmo pode votar
        AssociadoEntity associado = associadoRepository
                .findByDocumento(dto.documento())
                .orElseThrow(
                        () -> new RuntimeException("Associado não encontrado.")
                );
        if (!associado.isAbleToVote()) throw new RuntimeException("Desculpe, mas você nao pode votar.");

        // Validar se o associado já votou, se sim jogar uma exceção
        boolean jaVotou = votoRepository.existsBySessaoAndAssociado(sessao, associado);
        if (jaVotou) throw new RuntimeException("O associado já registrou um voto nesta sessão.");

        // Criar entidade para computar o voto com os atributos necessários
        VotoEntity voto = VotoEntity.builder()
                .sessao(sessao)
                .associado(associado)
                .aFavor(dto.aFavor())
                .build();

        // Salvar a entidade no banco de dados
        VotoEntity savedVoto = votoRepository.save(voto);

        // Retornar falando que o voto foi computado com sucesso
        return votoMapper.toResponseDTO(savedVoto);
    }
}