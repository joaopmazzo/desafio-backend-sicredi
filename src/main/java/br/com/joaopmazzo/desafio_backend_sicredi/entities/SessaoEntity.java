package br.com.joaopmazzo.desafio_backend_sicredi.entities;

import br.com.joaopmazzo.desafio_backend_sicredi.enums.StatusSessaoEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "sessoes", uniqueConstraints = {
        @UniqueConstraint(columnNames = "pauta_id", name = "UK_pauta")
})
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SessaoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @OneToOne()
    @JoinColumn(name = "pauta_id")
    private PautaEntity pauta;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    @Builder.Default
    private StatusSessaoEnum status = StatusSessaoEnum.EM_ANDAMENTO;

    @Column(nullable = false, updatable = false)
    @CreationTimestamp
    private LocalDateTime inicio;

    @Column(nullable = false)
    private LocalDateTime termino;

}
