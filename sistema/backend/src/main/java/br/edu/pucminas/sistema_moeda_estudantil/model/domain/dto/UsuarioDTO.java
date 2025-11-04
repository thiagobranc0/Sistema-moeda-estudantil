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
public class UsuarioDTO {
    private String nome;
    private String email;
    private String senha;
    private String tipo;

    public TipoUsuario getTipoEnum() {
        return tipo != null ? TipoUsuario.valueOf(this.tipo) : null;
    }
}
