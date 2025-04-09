package br.com.joaopmazzo.desafio_backend_sicredi.repositories;

import br.com.joaopmazzo.desafio_backend_sicredi.entities.AssociadoEntity;
import br.com.joaopmazzo.desafio_backend_sicredi.entities.SessaoEntity;
import br.com.joaopmazzo.desafio_backend_sicredi.entities.VotoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface VotoRepository extends JpaRepository<VotoEntity, UUID> {

    boolean existsBySessaoAndAssociado(SessaoEntity sessao, AssociadoEntity associado);

}
