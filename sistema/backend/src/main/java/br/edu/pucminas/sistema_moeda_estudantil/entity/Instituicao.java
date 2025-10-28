package br.edu.pucminas.sistema_moeda_estudantil.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity @Table(name = "instituicao", uniqueConstraints = {
        @UniqueConstraint(name = "uk_instituicao_nome", columnNames = {"nome"})
})
public class Instituicao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(nullable = false, length = 160)
    private String nome;


    @Builder.Default
    @OneToMany(mappedBy = "instituicao", cascade = CascadeType.ALL, orphanRemoval = false)
    private List<Departamento> departamentos = new ArrayList<>();
}
