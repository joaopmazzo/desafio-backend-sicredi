package br.com.joaopmazzo.desafio_backend_sicredi.domain.repositories;

import br.com.joaopmazzo.desafio_backend_sicredi.domain.entities.AssociadoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface AssociadoRepository extends JpaRepository<AssociadoEntity, UUID> {

    /**
     * Busca um associado pelo documento fornecido.
     *
     * @param documento Documento do associado a ser buscado.
     * @return Um Optional contendo a entidade AssociadoEntity correspondente, se encontrada.
     */
    Optional<AssociadoEntity> findByDocumento(String documento);

}
