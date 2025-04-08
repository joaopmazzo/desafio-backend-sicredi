package br.com.joaopmazzo.desafio_backend_sicredi.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "pautas", uniqueConstraints = {
        @UniqueConstraint(columnNames = "titulo", name = "UK_titulo")
})
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PautaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private String titulo;

    @Column(nullable = false, columnDefinition = "text")
    private String descricao;

    @Column(nullable = false, updatable = false)
    @CreationTimestamp
    private LocalDateTime criadoEm;

}
