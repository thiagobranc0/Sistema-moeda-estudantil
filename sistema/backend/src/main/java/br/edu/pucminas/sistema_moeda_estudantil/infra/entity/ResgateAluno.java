package br.edu.pucminas.sistema_moeda_estudantil.infra.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "resgate_aluno")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResgateAluno {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_vantagem", nullable = false,
            foreignKey = @ForeignKey(name = "fk_resgate_vantagem"))
    private Vantagem vantagem;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_aluno", nullable = false,
            foreignKey = @ForeignKey(name = "fk_resgate_aluno"))
    private Aluno aluno;

    @Column(nullable = false, precision = 19, scale = 2)
    private BigDecimal valor;

    @Column(name = "data_resgate", nullable = false)
    private LocalDateTime dataResgate = LocalDateTime.now();
}
