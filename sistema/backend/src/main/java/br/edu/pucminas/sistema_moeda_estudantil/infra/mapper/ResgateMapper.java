package br.edu.pucminas.sistema_moeda_estudantil.infra.mapper;



import br.edu.pucminas.sistema_moeda_estudantil.infra.entity.ResgateAluno;
import br.edu.pucminas.sistema_moeda_estudantil.model.ResgateAlunoResponseDTO;
import br.edu.pucminas.sistema_moeda_estudantil.model.domain.dto.ResgateAlunoDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.util.List;

@Mapper(componentModel = "spring")
public interface ResgateMapper {
    @Mapping(source = "resgateAluno.aluno.idUsuario",target = "idAluno")
    @Mapping(source = "resgateAluno.aluno.usuario.email",target = "email")
    @Mapping(source = "resgateAluno.vantagem.id",target = "idVantagem")
    @Mapping(source = "resgateAluno.vantagem.descricao",target = "descricao")
    @Mapping(source = "resgateAluno.valor",target = "custo")
    @Mapping(source = "resgateAluno.vantagem.foto",target = "foto")
    ResgateAlunoDTO toResgateAlunoDTO(ResgateAluno resgateAluno);

    ResgateAlunoResponseDTO toResgateAlunoResponseDTO(ResgateAlunoDTO resgateAlunoDTO);

    List<ResgateAlunoDTO> toResgateAlunoDTOList(List<ResgateAluno> resgatesAluno);

    List<ResgateAlunoResponseDTO> toResgateAlunoResponseDTOList(List<ResgateAlunoDTO> resgatesAlunoDTO);


    default OffsetDateTime map(LocalDateTime value) {
        if (value == null) {
            return null;
        }
        return value.atZone(ZoneId.systemDefault()).toOffsetDateTime();
    }
}
