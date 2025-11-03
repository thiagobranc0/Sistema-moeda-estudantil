package br.edu.pucminas.sistema_moeda_estudantil.infra.mapper;


import br.edu.pucminas.sistema_moeda_estudantil.infra.entity.Aluno;
import br.edu.pucminas.sistema_moeda_estudantil.model.domain.dto.AlunoDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AlunoMapper {

    @Mapping(target = "idUsuario", ignore = true)
    @Mapping(target = "usuario", ignore = true)
    @Mapping(target = "saldo", ignore = true)
    Aluno toEntity(AlunoDTO alunoDTO);

    AlunoDTO toAlunoDTO(Aluno aluno);
}
