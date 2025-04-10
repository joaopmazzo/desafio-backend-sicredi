package br.com.joaopmazzo.desafio_backend_sicredi.domain.services;

import br.com.joaopmazzo.desafio_backend_sicredi.application.exceptions.voto.VotoJaRegistradoException;
import br.com.joaopmazzo.desafio_backend_sicredi.domain.entities.AssociadoEntity;
import br.com.joaopmazzo.desafio_backend_sicredi.domain.entities.SessaoEntity;
import br.com.joaopmazzo.desafio_backend_sicredi.domain.entities.VotoEntity;
import br.com.joaopmazzo.desafio_backend_sicredi.domain.repositories.VotoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Camada de serviço para votos em sessões de votação.
 */
@Service
@RequiredArgsConstructor
public class VotoService {

    private final VotoRepository votoRepository;

    /**
     * Verifica se um associado já votou em uma determinada sessão.
     *
     * @param sessao Entidade representando a sessão de votação.
     * @param associado Entidade representando o associado.
     * @throws VotoJaRegistradoException se o associado já tiver registrado um voto na sessão.
     */
    @Transactional(readOnly = true)
    public void alreadyVoted(SessaoEntity sessao, AssociadoEntity associado) {
        if (votoRepository.existsBySessaoAndAssociado(sessao, associado))
            throw new VotoJaRegistradoException();
    }

    /**
     * Salva um voto no banco de dados.
     *
     * @param voto Entidade representando o voto a ser salvo.
     * @return A entidade VotoEntity salva.
     */
    @Transactional
    public VotoEntity saveVoto(VotoEntity voto) {
        return votoRepository.save(voto);
    }

}