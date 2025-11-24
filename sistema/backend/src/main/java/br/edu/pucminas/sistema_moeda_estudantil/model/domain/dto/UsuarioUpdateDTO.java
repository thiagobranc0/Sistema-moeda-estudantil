package br.edu.pucminas.sistema_moeda_estudantil.model.domain.dto;

import br.edu.pucminas.sistema_moeda_estudantil.infra.enums.TipoUsuario;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class UsuarioUpdateDTO {
    private String nome;
    private String email;
    private String senha;
    private TipoUsuario tipo;
    private Double saldo;

    private String cpf;
    private String rg;
    private String endereco;

    private Long departamentoId;

    private String cnpj;

}
