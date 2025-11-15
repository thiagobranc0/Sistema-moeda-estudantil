package br.edu.pucminas.sistema_moeda_estudantil.infra.mapper;


import br.edu.pucminas.sistema_moeda_estudantil.infra.entity.Aluno;
import br.edu.pucminas.sistema_moeda_estudantil.infra.entity.DoacaoMoeda;
import br.edu.pucminas.sistema_moeda_estudantil.infra.entity.Professor;
import br.edu.pucminas.sistema_moeda_estudantil.model.DoacaoRequestDTO;
import br.edu.pucminas.sistema_moeda_estudantil.model.DoacaoResponseDTO;
import br.edu.pucminas.sistema_moeda_estudantil.model.domain.dto.DoacaoDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.UUID;

@Mapper(componentModel = "spring")
public interface DoacaoMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "idProfessor", ignore = true)
    @Mapping(target = "nomeProfessor", ignore = true)
    @Mapping(target = "nomeAluno", ignore = true)
    @Mapping(target = "dataDoacao", ignore = true)
    DoacaoDTO doacaoRequestDTOToDoacaoDTO(DoacaoRequestDTO doacaoRequestDTO);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "aluno", source = "idAluno")
    @Mapping(target = "professor", source = "idProfessor")
    DoacaoMoeda doacaoDTOToDoacaoEntity(DoacaoDTO doacaoDTO);

    @Mapping(target = "idProfessor", source = "professor.idUsuario")
    @Mapping(target = "idAluno", source = "aluno.idUsuario")
    DoacaoDTO doacaoEntityDTOToDoacaoDTO(DoacaoMoeda doacao);

    DoacaoResponseDTO doacaoDTOToDoacaoResponseDTO(DoacaoDTO doacaoDTO);

    default Aluno toAluno(UUID id) {
        if (id == null) return null;
        Aluno aluno = new Aluno();
        aluno.setIdUsuario(id);
        return aluno;
    }

    default Professor toProfessor(UUID id) {
        if (id == null) return null;
        Professor prof = new Professor();
        prof.setIdUsuario(id);
        return prof;
    }

    default OffsetDateTime map(LocalDateTime value) {
        return value != null ? value.atOffset(ZoneOffset.UTC) : null;
    }

    default LocalDateTime map(OffsetDateTime value) {
        return value != null ? value.toLocalDateTime() : null;
    }
}
