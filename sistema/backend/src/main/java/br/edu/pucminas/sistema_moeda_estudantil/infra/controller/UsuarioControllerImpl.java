package br.edu.pucminas.sistema_moeda_estudantil.infra.controller;

import br.edu.pucminas.sistema_moeda_estudantil.controller.UserApi;
import br.edu.pucminas.sistema_moeda_estudantil.infra.mapper.UsuarioMapper;
import br.edu.pucminas.sistema_moeda_estudantil.model.UserRequest;
import br.edu.pucminas.sistema_moeda_estudantil.model.domain.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;


@Controller
public class UsuarioControllerImpl implements UserApi {

    @Autowired
    private UsuarioMapper usuarioMapper;

    @Autowired
    private UsuarioService usuarioService;

    @Override
    public ResponseEntity<Void> userRegistration(UserRequest userRequest) {
        var usuario = usuarioMapper.toUserDTO(userRequest);
        usuarioService.createUsuario(usuario);
        return ResponseEntity.ok().build();
    }
}
