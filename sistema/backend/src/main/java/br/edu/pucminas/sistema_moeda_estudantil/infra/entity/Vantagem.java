package br.edu.pucminas.sistema_moeda_estudantil.infra.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity @Table(name = "vantagem")
public class Vantagem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(name = "descricao", nullable = false, length = 255)
    private String descricao;


    @Column(name = "custo", nullable = false, precision = 19, scale = 2)
    private BigDecimal custo;


    @Column(name = "foto", length = 255)
    private String foto;


    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "empresa_id", nullable = false, foreignKey = @ForeignKey(name = "fk_vantagem_empresa"))
    private Empresa empresa;
}
