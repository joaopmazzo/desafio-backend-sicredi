package br.com.joaopmazzo.desafio_backend_sicredi.repositories;

import br.com.joaopmazzo.desafio_backend_sicredi.entities.VotoEntity;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.UUID;

public interface VotoRepository extends PagingAndSortingRepository<VotoEntity, UUID> {
}
