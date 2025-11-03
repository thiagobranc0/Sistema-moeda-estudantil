package br.edu.pucminas.sistema_moeda_estudantil.model.domain.boundary;

import br.edu.pucminas.sistema_moeda_estudantil.model.domain.dto.UsuarioDTO;

import java.util.UUID;

public interface UsuarioBoundary {

    void createUsuario(UsuarioDTO usuarioDTO);

    UsuarioDTO getUsuarioById(UUID id);
}
