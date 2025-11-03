package br.edu.pucminas.sistema_moeda_estudantil.infra.mapper;

import br.edu.pucminas.sistema_moeda_estudantil.infra.entity.Usuario;
import br.edu.pucminas.sistema_moeda_estudantil.infra.enums.TipoUsuario;
import br.edu.pucminas.sistema_moeda_estudantil.model.UserRequest;
import br.edu.pucminas.sistema_moeda_estudantil.model.domain.dto.UsuarioDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface UsuarioMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "aluno", ignore = true)
    @Mapping(target = "professor", ignore = true)
    @Mapping(target = "empresa", ignore = true)
    @Mapping(source = "tipo", target = "tipo", qualifiedByName = "stringToTipoUsuario")
    Usuario toEntity(UsuarioDTO usuarioDTO);

    @Mapping(source = "tipo", target = "tipo", qualifiedByName = "tipoEnumToString")
    UsuarioDTO toUserDTO(UserRequest userRequest);

    @Named("stringToTipoUsuario")
    default TipoUsuario stringToTipoUsuario(String tipo) {
        return tipo != null ? TipoUsuario.valueOf(tipo) : null;
    }

    @Named("tipoUsuarioToString")
    default String tipoUsuarioToString(TipoUsuario tipo) {
        return tipo != null ? tipo.name() : null;
    }

    @Named("tipoEnumToString")
    default String tipoEnumToString(UserRequest.TipoEnum tipo) {
        return tipo != null ? tipo.name() : null;
    }
}
