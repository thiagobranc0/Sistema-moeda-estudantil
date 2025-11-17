package br.edu.pucminas.sistema_moeda_estudantil.infra.entity;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "doacao_moeda")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DoacaoMoeda {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_professor", nullable = false,
            foreignKey = @ForeignKey(name = "fk_doacao_professor"))
    private Professor professor;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_aluno", nullable = false,
            foreignKey = @ForeignKey(name = "fk_doacao_aluno"))
    private Aluno aluno;

    @Column(nullable = false, precision = 19, scale = 2)
    private BigDecimal valor;

    @Column(columnDefinition = "TEXT")
    private String mensagem;

    @Column(name = "data_doacao", nullable = false)
    private LocalDateTime dataDoacao = LocalDateTime.now();
}
