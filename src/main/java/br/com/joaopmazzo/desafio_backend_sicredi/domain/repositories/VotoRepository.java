package br.com.joaopmazzo.desafio_backend_sicredi.domain.repositories;

import br.com.joaopmazzo.desafio_backend_sicredi.domain.entities.AssociadoEntity;
import br.com.joaopmazzo.desafio_backend_sicredi.domain.entities.SessaoEntity;
import br.com.joaopmazzo.desafio_backend_sicredi.domain.entities.VotoEntity;
import br.com.joaopmazzo.desafio_backend_sicredi.domain.enums.EscolhaVotoEnum;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface VotoRepository extends JpaRepository<VotoEntity, UUID> {

    /**
     * Verifica se existe um voto registrado para um associado em uma determinada sessão.
     *
     * @param sessao Entidade SessaoEntity representando a sessão de votação.
     * @param associado Entidade AssociadoEntity representando o associado.
     * @return true se existir um voto registrado para o associado na sessão, caso contrário false.
     */
    boolean existsBySessaoAndAssociado(SessaoEntity sessao, AssociadoEntity associado);

    /**
     * Conta o número de votos a favor ou contra em uma sessão específica.
     *
     * @param sessao      Entidade SessaoEntity representando a sessão de votação.
     * @param escolhaVoto Enum representando a escolha do voto (a favor ou contra).
     * @return O número de votos a favor ou contra na sessão.
     */
    long countBySessaoAndAFavor(SessaoEntity sessao, EscolhaVotoEnum escolhaVoto);

}
