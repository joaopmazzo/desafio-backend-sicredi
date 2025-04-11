package br.com.joaopmazzo.desafio_backend_sicredi.domain.entities;

import br.com.joaopmazzo.desafio_backend_sicredi.domain.enums.EscolhaVotoEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "votos", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"sessao_id", "associado_id"}, name = "UK_sessao_associado")
})
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class VotoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne()
    @JoinColumn(name = "sessao_id", nullable = false)
    private SessaoEntity sessao;

    @ManyToOne
    @JoinColumn(name = "associado_id", nullable = false)
    private AssociadoEntity associado;

    @Column(name = "escolha_voto", nullable = false)
    @Enumerated(EnumType.STRING)
    private EscolhaVotoEnum escolhaVoto;

    @Column(nullable = false, updatable = false)
    @CreationTimestamp
    private LocalDateTime registradoEm;

}
