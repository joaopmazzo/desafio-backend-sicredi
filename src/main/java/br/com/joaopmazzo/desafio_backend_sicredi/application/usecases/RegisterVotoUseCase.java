package br.com.joaopmazzo.desafio_backend_sicredi.application.usecases;

import br.com.joaopmazzo.desafio_backend_sicredi.application.dtos.request.VotoRequestDTO;
import br.com.joaopmazzo.desafio_backend_sicredi.application.dtos.response.VotoResponseDTO;
import br.com.joaopmazzo.desafio_backend_sicredi.domain.entities.AssociadoEntity;
import br.com.joaopmazzo.desafio_backend_sicredi.domain.entities.SessaoEntity;
import br.com.joaopmazzo.desafio_backend_sicredi.domain.entities.VotoEntity;
import br.com.joaopmazzo.desafio_backend_sicredi.domain.services.AssociadoService;
import br.com.joaopmazzo.desafio_backend_sicredi.domain.services.SessaoService;
import br.com.joaopmazzo.desafio_backend_sicredi.domain.services.VotoService;
import br.com.joaopmazzo.desafio_backend_sicredi.infrastructure.mappers.VotoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class RegisterVotoUseCase {

    private final SessaoService sessaoService;
    private final VotoService votoService;
    private final AssociadoService associadoService;
    private final VotoMapper votoMapper;

    public VotoResponseDTO execute(UUID pautaId, VotoRequestDTO dto) {
        // Retornar a sessão pela pauta, status e data não expirada ainda
        SessaoEntity sessao = sessaoService.validateSessao(pautaId);

        // retorna o associado e valida se o mesmo pode votar
        AssociadoEntity associado = associadoService.validateAssociado(dto.documento());

        // Validar se o associado já votou, se sim jogar uma exceção
        votoService.alreadyVoted(sessao, associado);

        // Criar entidade para computar o voto com os atributos necessários
        VotoEntity voto = VotoEntity.builder()
                .sessao(sessao)
                .associado(associado)
                .aFavor(dto.aFavor())
                .build();

        // Salvar a entidade no banco de dados
        VotoEntity savedVoto = votoService.saveVoto(voto);

        // Retornar falando que o voto foi computado com sucesso
        return votoMapper.toResponseDTO(savedVoto);
    }

}
