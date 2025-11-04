package br.edu.pucminas.sistema_moeda_estudantil.model.domain.boundary;

import br.edu.pucminas.sistema_moeda_estudantil.model.domain.dto.UsuarioDTO;
import br.edu.pucminas.sistema_moeda_estudantil.model.domain.dto.UsuarioUpdateDTO;

import java.util.UUID;

public interface UsuarioBoundary {

    void createUsuario(UsuarioDTO usuarioDTO);

    UsuarioUpdateDTO getUsuarioById(UUID id);

    boolean existsById(UUID id);

    UsuarioDTO updateUsuario(UUID id, UsuarioDTO usuarioDTO);

    void deleteUsuario(UUID id);
}
