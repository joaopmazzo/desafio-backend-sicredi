package br.com.joaopmazzo.desafio_backend_sicredi.repositories;

import br.com.joaopmazzo.desafio_backend_sicredi.entities.SessaoEntity;
import br.com.joaopmazzo.desafio_backend_sicredi.enums.StatusSessaoEnum;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

public interface SessaoRepository extends JpaRepository<SessaoEntity, UUID> {

    @Query("SELECT COUNT(s) > 0 FROM SessaoEntity s WHERE s.pauta.id = :pautaId AND s.status <> 'CANCELADO'")
    boolean existsByPautaIdAndStatusNotLikeCancelado(UUID pautaId);

    Optional<SessaoEntity> findByPautaId(UUID pautaId);

    Page<SessaoEntity> findAllByStatusAndTerminoLessThanEqual(StatusSessaoEnum statusSessaoEnum, LocalDateTime now, Pageable pageable);
}
