package br.edu.pucminas.sistema_moeda_estudantil.infra.mapper;

import br.edu.pucminas.sistema_moeda_estudantil.infra.entity.Vantagem;
import br.edu.pucminas.sistema_moeda_estudantil.model.VantagemRequestDTO;
import br.edu.pucminas.sistema_moeda_estudantil.model.VantagemResponseDTO;
import br.edu.pucminas.sistema_moeda_estudantil.model.VantagemUpdateDTO;
import br.edu.pucminas.sistema_moeda_estudantil.model.domain.dto.VantagemDTO;
import org.mapstruct.*;

import java.util.List;
import java.util.Optional;

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

    @Named("vantagemToVantagemDTOComId")
    VantagemDTO vantagemToVantagemDTOComId(Vantagem vantagem);

    @Named("vantagemDTOToVantagemResponseDTOComId")
    VantagemResponseDTO vantagemDTOToVantagemResponseDTOComId(VantagemDTO vantagemDTO);

    @IterableMapping(qualifiedByName = "vantagemToVantagemDTOComId")
    List<VantagemDTO> vantagemEntityListToVantagemDTOList(List<Vantagem> vantagens);

    @IterableMapping(qualifiedByName = "vantagemDTOToVantagemResponseDTOComId")
    List<VantagemResponseDTO> vantagemDTOListToVantagemResponseDTOList(List<VantagemDTO> vantagens);

    @Mapping(target = "empresaId", ignore = true)
    @Mapping(target = "id", ignore = true)
    VantagemDTO vantagemUpdateDTOToVantagemDTO(VantagemUpdateDTO vantagemUpdateDTO);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "empresa", ignore = true)
    @Mapping(target = "id", ignore = true)
    void updateEntityFromVantagemDTO(VantagemDTO vantagemDTO, @MappingTarget Vantagem vantagem);
}
