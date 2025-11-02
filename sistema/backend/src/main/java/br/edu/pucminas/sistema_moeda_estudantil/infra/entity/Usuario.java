package br.edu.pucminas.sistema_moeda_estudantil.infra.entity;


import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity @Table(name = "usuario", uniqueConstraints = {
        @UniqueConstraint(name = "uk_usuario_email", columnNames = {"email"})
})
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;


    @Column(nullable = false, length = 120)
    private String nome;


    @Column(nullable = false, length = 160)
    private String email;


    @Column(nullable = false, length = 120)
    private String senha;


    @OneToOne(mappedBy = "usuario", fetch = FetchType.LAZY)
    private Aluno aluno;


    @OneToOne(mappedBy = "usuario", fetch = FetchType.LAZY)
    private Professor professor;


    @OneToOne(mappedBy = "usuario", fetch = FetchType.LAZY)
    private Empresa empresa;
}