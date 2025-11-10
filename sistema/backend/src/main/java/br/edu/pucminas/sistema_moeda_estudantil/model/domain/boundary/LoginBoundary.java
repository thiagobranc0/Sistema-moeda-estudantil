package br.edu.pucminas.sistema_moeda_estudantil.model.domain.boundary;

import br.edu.pucminas.sistema_moeda_estudantil.model.domain.dto.LoginDTO;
import br.edu.pucminas.sistema_moeda_estudantil.model.domain.dto.UsuarioUpdateDTO;

public interface LoginBoundary {
    UsuarioUpdateDTO login(LoginDTO loginDTO);
}
