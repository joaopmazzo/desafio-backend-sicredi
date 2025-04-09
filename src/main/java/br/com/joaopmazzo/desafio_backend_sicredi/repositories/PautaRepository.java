package br.com.joaopmazzo.desafio_backend_sicredi.repositories;

import br.com.joaopmazzo.desafio_backend_sicredi.entities.PautaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PautaRepository extends JpaRepository<PautaEntity, UUID> {
}
