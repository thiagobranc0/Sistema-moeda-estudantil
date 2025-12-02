package br.edu.pucminas.sistema_moeda_estudantil.model.domain.boundary;

import br.edu.pucminas.sistema_moeda_estudantil.infra.entity.Empresa;
import br.edu.pucminas.sistema_moeda_estudantil.model.domain.dto.VantagemDTO;

import java.util.List;
import java.util.UUID;

public interface VantagemBoundary {
    void createVantagem(VantagemDTO vantagemDTO);

    VantagemDTO getVantagemById(UUID empresaId, UUID vantagemId);

    List<VantagemDTO> listVantagens(UUID empresaId);

    void deleteVantagem(UUID empresaId, UUID vantagemId);

    VantagemDTO updateVantagem(UUID empresaId, UUID vantagemId, VantagemDTO vantagemDTO);

    List<VantagemDTO> listAllVantagens();

    Empresa getEmpresaByVantagemId(UUID vantagemId);
}
