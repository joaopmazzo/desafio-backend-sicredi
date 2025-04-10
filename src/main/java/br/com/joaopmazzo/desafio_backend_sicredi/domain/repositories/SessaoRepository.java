package br.com.joaopmazzo.desafio_backend_sicredi.domain.repositories;

import br.com.joaopmazzo.desafio_backend_sicredi.domain.entities.SessaoEntity;
import br.com.joaopmazzo.desafio_backend_sicredi.domain.enums.StatusSessaoEnum;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

public interface SessaoRepository extends JpaRepository<SessaoEntity, UUID> {

    /**
     * Verifica se existe uma sessão associada a uma pauta específica.
     *
     * @param pautaId ID da pauta a ser verificada.
     * @return true se existir uma sessão associada à pauta, caso contrário false.
     */
    boolean existsByPautaId(UUID pautaId);

    /**
     * Busca uma sessão pelo ID da pauta associada.
     *
     * @param pautaId ID da pauta.
     * @return Um Optional contendo a entidade SessaoEntity correspondente, se encontrada.
     */
    Optional<SessaoEntity> findByPautaId(UUID pautaId);

    /**
     * Retorna uma lista paginada de sessões com um status específico e término menor ou igual a um momento fornecido.
     *
     * @param statusSessaoEnum Status da sessão a ser filtrado.
     * @param now Momento atual para comparação com o término da sessão.
     * @param pageable Objeto de paginação.
     * @return Página contendo as entidades SessaoEntity que atendem aos critérios.
     */
    Page<SessaoEntity> findAllByStatusAndTerminoLessThanEqual(StatusSessaoEnum statusSessaoEnum, LocalDateTime now, Pageable pageable);
}
