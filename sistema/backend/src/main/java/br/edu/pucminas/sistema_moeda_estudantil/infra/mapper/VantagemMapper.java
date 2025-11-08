package br.edu.pucminas.sistema_moeda_estudantil.infra.mapper;

import br.edu.pucminas.sistema_moeda_estudantil.infra.entity.Vantagem;
import br.edu.pucminas.sistema_moeda_estudantil.model.VantagemRequestDTO;
import br.edu.pucminas.sistema_moeda_estudantil.model.VantagemResponseDTO;
import br.edu.pucminas.sistema_moeda_estudantil.model.domain.dto.VantagemDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface VantagemMapper {
    @Mapping(target = "empresaId", ignore = true)
    @Mapping(target = "id", ignore = true)
    VantagemDTO vantagemRequestToVantagemDTO(VantagemRequestDTO vantagemRequestDTO);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "empresa", ignore = true)
    Vantagem vantagemDTOToEntity(VantagemDTO vantagemDTO);

    @Mapping(target = "empresaId", ignore = true)
    @Mapping(target = "id", ignore = true)
    VantagemDTO vantagemToVantagemDTO(Vantagem vantagem);

    @Mapping(target = "id", ignore = true)
    VantagemResponseDTO vantagemDTOToVantagemResponseDTO(VantagemDTO vantagemDTO);
}
