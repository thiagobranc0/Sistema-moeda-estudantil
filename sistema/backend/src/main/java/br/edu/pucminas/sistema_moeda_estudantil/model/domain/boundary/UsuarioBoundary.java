package br.edu.pucminas.sistema_moeda_estudantil.model.domain.boundary;

import br.edu.pucminas.sistema_moeda_estudantil.model.domain.dto.UsuarioDTO;

public interface UsuarioBoundary {

    void createUsuario(UsuarioDTO usuarioDTO);
}
