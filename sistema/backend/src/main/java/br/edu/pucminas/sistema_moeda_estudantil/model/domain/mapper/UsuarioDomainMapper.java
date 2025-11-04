package br.edu.pucminas.sistema_moeda_estudantil.model.domain.mapper;

import br.edu.pucminas.sistema_moeda_estudantil.model.domain.dto.AlunoDTO;
import br.edu.pucminas.sistema_moeda_estudantil.model.domain.dto.EmpresaDTO;
import br.edu.pucminas.sistema_moeda_estudantil.model.domain.dto.UsuarioDTO;
import br.edu.pucminas.sistema_moeda_estudantil.model.domain.dto.UsuarioUpdateDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UsuarioDomainMapper {

    @Mapping(target = "cpf", ignore = true)
    @Mapping(target = "rg", ignore = true)
    @Mapping(target = "endereco", ignore = true)
    @Mapping(target = "cnpj", ignore = true)
    UsuarioUpdateDTO toUserUpdateDTO(UsuarioDTO usuarioDTO);

    UsuarioDTO toUserDTO(UsuarioUpdateDTO usuarioUpdateDTO);

}
