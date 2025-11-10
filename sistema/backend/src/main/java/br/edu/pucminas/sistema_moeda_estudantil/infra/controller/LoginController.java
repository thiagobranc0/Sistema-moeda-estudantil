package br.edu.pucminas.sistema_moeda_estudantil.infra.controller;

import br.edu.pucminas.sistema_moeda_estudantil.controller.LoginApi;
import br.edu.pucminas.sistema_moeda_estudantil.infra.mapper.LoginMapper;
import br.edu.pucminas.sistema_moeda_estudantil.infra.mapper.UsuarioMapper;
import br.edu.pucminas.sistema_moeda_estudantil.model.LoginRequest;
import br.edu.pucminas.sistema_moeda_estudantil.model.UsuarioUpdateResponseDTO;
import br.edu.pucminas.sistema_moeda_estudantil.model.domain.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

@Controller
public class LoginController implements LoginApi {

    @Autowired
    LoginMapper loginMapper;

    @Autowired
    LoginService loginService;

    @Autowired
    UsuarioMapper usuarioMapper;
    @Override
    public ResponseEntity<UsuarioUpdateResponseDTO> login(LoginRequest loginRequest) {
        var loginDTO = loginMapper.loginRequestToLoginDTO(loginRequest);
        var updateUserDTO = loginService.login(loginDTO);
        var userResponseDTO = usuarioMapper.toUsuarioUpdateResponseDTO(updateUserDTO);
        return ResponseEntity.ok(userResponseDTO);
    }
}
