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
@Entity @Table(name = "professor", uniqueConstraints = {
        @UniqueConstraint(name = "uk_professor_cpf", columnNames = {"cpf"})
})
public class Professor {
    @Id
    @Column(name = "id_usuario")
    private UUID idUsuario;


    @MapsId
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_usuario", nullable = false, foreignKey = @ForeignKey(name = "fk_professor_usuario"))
    private Usuario usuario;


    @Column(nullable = false, length = 14)
    private String cpf;


    @Column(nullable = false, precision = 19, scale = 2)
    private BigDecimal saldo = BigDecimal.ZERO;


    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "departamento_id", nullable = false, foreignKey = @ForeignKey(name = "fk_professor_departamento"))
    private Departamento departamento;
}
