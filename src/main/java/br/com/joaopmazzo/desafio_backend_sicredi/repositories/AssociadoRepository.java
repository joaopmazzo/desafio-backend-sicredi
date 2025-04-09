package br.com.joaopmazzo.desafio_backend_sicredi.repositories;

import br.com.joaopmazzo.desafio_backend_sicredi.entities.AssociadoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface AssociadoRepository extends JpaRepository<AssociadoEntity, UUID> {

    Optional<AssociadoEntity> findByDocumento(String documento);

}
