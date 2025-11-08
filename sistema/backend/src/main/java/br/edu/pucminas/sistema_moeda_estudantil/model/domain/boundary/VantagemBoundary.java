package br.edu.pucminas.sistema_moeda_estudantil.model.domain.boundary;

import br.edu.pucminas.sistema_moeda_estudantil.model.domain.dto.VantagemDTO;

import java.util.UUID;

public interface VantagemBoundary {
    void createVantagem(VantagemDTO vantagemDTO);

    VantagemDTO getVantagemById(UUID empresaId, UUID vantagemId);
}
