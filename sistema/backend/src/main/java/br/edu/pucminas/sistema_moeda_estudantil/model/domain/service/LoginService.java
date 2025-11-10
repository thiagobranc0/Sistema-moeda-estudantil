package br.edu.pucminas.sistema_moeda_estudantil.model.domain.service;

import br.edu.pucminas.sistema_moeda_estudantil.model.domain.boundary.LoginBoundary;
import br.edu.pucminas.sistema_moeda_estudantil.model.domain.dto.LoginDTO;
import br.edu.pucminas.sistema_moeda_estudantil.model.domain.dto.UsuarioUpdateDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginService {

    @Autowired private LoginBoundary loginBoundary;

    public UsuarioUpdateDTO login(LoginDTO loginDTO) {
        return loginBoundary.login(loginDTO);
    }
}
