package br.edu.pucminas.sistema_moeda_estudantil.infra.mapper;

import br.edu.pucminas.sistema_moeda_estudantil.infra.entity.Empresa;
import br.edu.pucminas.sistema_moeda_estudantil.model.domain.dto.EmpresaDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;


@Mapper(componentModel = "spring")
public interface EmpresaMapper {

    @Mapping(target = "idUsuario", ignore = true)
    @Mapping(target = "usuario", ignore = true)
    @Mapping(target = "vantagens", ignore = true)
    Empresa toEntity(EmpresaDTO empresaDTO);

    EmpresaDTO toEmpresaDTO(Empresa empresa);
}
