package br.edu.pucminas.sistema_moeda_estudantil.infra.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity @Table(name = "departamento")
public class Departamento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(nullable = false, length = 120)
    private String nome;


    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "instituicao_id", nullable = false, foreignKey = @ForeignKey(name = "fk_departamento_instituicao"))
    private Instituicao instituicao;


    @Builder.Default
    @OneToMany(mappedBy = "departamento", cascade = CascadeType.ALL)
    private List<Professor> professores = new ArrayList<>();
}
