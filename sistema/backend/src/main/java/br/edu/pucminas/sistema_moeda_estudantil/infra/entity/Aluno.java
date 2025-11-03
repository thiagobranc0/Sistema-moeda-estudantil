package br.edu.pucminas.sistema_moeda_estudantil.infra.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "aluno", uniqueConstraints = {
        @UniqueConstraint(name = "uk_aluno_cpf", columnNames = {"cpf"}),
        @UniqueConstraint(name = "uk_aluno_rg", columnNames = {"rg"})
})
public class Aluno {
    @Id
    @Column(name = "id_usuario")
    private UUID idUsuario;

    @MapsId
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_usuario", nullable = false, foreignKey = @ForeignKey(name = "fk_aluno_usuario"))
    private Usuario usuario;

    @Column(nullable = false, length = 14)
    private String cpf;


    @Column(nullable = false, length = 20)
    private String rg;


    @Column(nullable = false, length = 255)
    private String endereco;


    @Column(nullable = false, precision = 19, scale = 2)
    private BigDecimal saldo = BigDecimal.ZERO;
}
