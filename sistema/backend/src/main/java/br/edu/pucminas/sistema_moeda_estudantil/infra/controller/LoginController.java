package br.edu.pucminas.sistema_moeda_estudantil.infra.controller;

import br.edu.pucminas.sistema_moeda_estudantil.controller.LoginApi;
import br.edu.pucminas.sistema_moeda_estudantil.infra.mapper.LoginMapper;
import br.edu.pucminas.sistema_moeda_estudantil.model.LoginRequest;
import br.edu.pucminas.sistema_moeda_estudantil.model.LoginResponseDTO;
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

    @Override
    public ResponseEntity<LoginResponseDTO> login(LoginRequest loginRequest) {
        var loginDTO = loginMapper.loginRequestToLoginDTO(loginRequest);
        var response = loginService.login(loginDTO);
        return ResponseEntity.ok(response);
    }
}
