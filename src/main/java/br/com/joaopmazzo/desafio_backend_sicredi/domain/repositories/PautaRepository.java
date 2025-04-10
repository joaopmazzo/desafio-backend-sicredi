package br.com.joaopmazzo.desafio_backend_sicredi.domain.repositories;

import br.com.joaopmazzo.desafio_backend_sicredi.domain.entities.PautaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PautaRepository extends JpaRepository<PautaEntity, UUID> {

    /**
     * Verifica se existe uma pauta com o título fornecido.
     *
     * @param titulo Título da pauta a ser verificada.
     * @return true se existir uma pauta com o título fornecido, caso contrário false.
     */
    boolean existsByTitulo(String titulo);

}
