package br.edu.pucminas.sistema_moeda_estudantil.infra.controller;

import br.edu.pucminas.sistema_moeda_estudantil.controller.UserApi;
import br.edu.pucminas.sistema_moeda_estudantil.infra.mapper.UsuarioMapper;
import br.edu.pucminas.sistema_moeda_estudantil.model.UserRequest;
import br.edu.pucminas.sistema_moeda_estudantil.model.UsuarioUpdateRequestDTO;
import br.edu.pucminas.sistema_moeda_estudantil.model.UsuarioUpdateResponseDTO;
import br.edu.pucminas.sistema_moeda_estudantil.model.domain.dto.UsuarioUpdateDTO;
import br.edu.pucminas.sistema_moeda_estudantil.model.domain.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import java.util.UUID;


@Controller
public class UsuarioControllerImpl implements UserApi {

    @Autowired
    private UsuarioMapper usuarioMapper;

    @Autowired
    private UsuarioService usuarioService;

    @Override
    public ResponseEntity<Void> deleteUserById(UUID id) {
        usuarioService.deleteUsuario(id);
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<UsuarioUpdateResponseDTO> getUserById(UUID id) {
        var usuarioDTO = usuarioService.getUsuarioById(id);
        var userUpdateResponseDTO = usuarioMapper.toUsuarioUpdateResponseDTO(usuarioDTO);
        userUpdateResponseDTO.setSaldo(usuarioDTO.getSaldo().doubleValue());
        return ResponseEntity.ok(userUpdateResponseDTO);
    }

    @Override
    public ResponseEntity<UsuarioUpdateResponseDTO> updateUser(UUID id, UsuarioUpdateRequestDTO usuarioUpdateRequestDTO) {
        var userUpdateDTO = usuarioMapper.toUsuarioUpdateDTO(usuarioUpdateRequestDTO);
        userUpdateDTO = usuarioService.updateUsuario(id, userUpdateDTO);
        var userUpdateResponse = usuarioMapper.toUsuarioUpdateResponseDTO(userUpdateDTO);
        return ResponseEntity.ok(userUpdateResponse);
    }

    @Override
    public ResponseEntity<Void> userRegistration(UserRequest userRequest) {
        var usuario = usuarioMapper.toUserDTO(userRequest);
        usuarioService.createUsuario(usuario);
        return ResponseEntity.ok().build();
    }
}
