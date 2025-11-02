package br.edu.pucminas.sistema_moeda_estudantil.infra.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity @Table(name = "empresa", uniqueConstraints = {
        @UniqueConstraint(name = "uk_empresa_cnpj", columnNames = {"cnpj"})
})
public class Empresa {
    @Id
    @Column(name = "id_usuario")
    private UUID idUsuario;


    @MapsId
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_usuario", nullable = false, foreignKey = @ForeignKey(name = "fk_empresa_usuario"))
    private Usuario usuario;


    @Column(nullable = false, length = 18)
    private String cnpj;


    @Builder.Default
    @OneToMany(mappedBy = "empresa", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Vantagem> vantagens = new ArrayList<>();
}
