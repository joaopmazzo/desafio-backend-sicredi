package br.com.joaopmazzo.desafio_backend_sicredi.domain.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Table(name = "associados", uniqueConstraints = {
        @UniqueConstraint(columnNames = "documento", name = "UK_documento")
})
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AssociadoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private String documento;

    @Column(nullable = false, name = "able_to_vote")
    private boolean ableToVote;

}
