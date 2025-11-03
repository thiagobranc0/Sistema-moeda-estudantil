package br.edu.pucminas.sistema_moeda_estudantil.infra.controller;

import br.edu.pucminas.sistema_moeda_estudantil.controller.UserApi;
import br.edu.pucminas.sistema_moeda_estudantil.model.UserRequest;
import org.springframework.http.ResponseEntity;

public class UserControllerImpl implements UserApi {
    @Override
    public ResponseEntity<Void> userRegistration(UserRequest userRequest) {
        return null;
    }
}
