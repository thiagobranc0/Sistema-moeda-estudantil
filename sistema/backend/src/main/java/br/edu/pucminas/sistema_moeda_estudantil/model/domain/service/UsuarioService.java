package br.edu.pucminas.sistema_moeda_estudantil.model.domain.service;

import br.edu.pucminas.sistema_moeda_estudantil.infra.boundary.UsuarioBoundaryImpl;
import br.edu.pucminas.sistema_moeda_estudantil.model.domain.dto.UsuarioDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    @Autowired
    private UsuarioBoundaryImpl usuarioBoundary;

    public void createUsuario(UsuarioDTO usuarioDTO){
        usuarioBoundary.createUsuario(usuarioDTO);
    }

    public void deleteUsuario(UsuarioDTO usuarioDTO){

    }


}
