package br.com.joaopmazzo.desafio_backend_sicredi.repositories;

import br.com.joaopmazzo.desafio_backend_sicredi.entities.PautaEntity;
import br.com.joaopmazzo.desafio_backend_sicredi.entities.SessaoEntity;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.UUID;

public interface SessaoRepository extends PagingAndSortingRepository<SessaoEntity, UUID> {
}
