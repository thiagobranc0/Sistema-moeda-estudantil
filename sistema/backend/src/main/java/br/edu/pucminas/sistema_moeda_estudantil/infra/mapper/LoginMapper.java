package br.edu.pucminas.sistema_moeda_estudantil.infra.mapper;

import br.edu.pucminas.sistema_moeda_estudantil.infra.entity.Usuario;
import br.edu.pucminas.sistema_moeda_estudantil.model.LoginRequest;
import br.edu.pucminas.sistema_moeda_estudantil.model.LoginResponseDTO;
import br.edu.pucminas.sistema_moeda_estudantil.model.domain.dto.LoginDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface LoginMapper {
    LoginDTO loginRequestToLoginDTO(LoginRequest loginRequest);

    LoginResponseDTO toLoginResponseDTO(Usuario usuario);
}
