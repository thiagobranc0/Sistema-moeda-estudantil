package br.edu.pucminas.sistema_moeda_estudantil.model.domain.service;

import br.edu.pucminas.sistema_moeda_estudantil.infra.boundary.UsuarioBoundaryImpl;
import br.edu.pucminas.sistema_moeda_estudantil.infra.boundary.VantagemBoundaryImpl;
import br.edu.pucminas.sistema_moeda_estudantil.model.domain.dto.VantagemDTO;
import br.edu.pucminas.sistema_moeda_estudantil.model.domain.exceptions.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class VantagemService {
    @Autowired
    private VantagemBoundaryImpl vantagemBoundaryImpl;

    public void createVantagem(VantagemDTO vantagemDTO){
        vantagemBoundaryImpl.createVantagem(vantagemDTO);
    }

    public VantagemDTO getVantagemById(UUID empresaId, UUID vantagemId) {
        return vantagemBoundaryImpl.getVantagemById(empresaId, vantagemId);
    }

    public List<VantagemDTO> listVantagens(UUID empresaId) {
        return vantagemBoundaryImpl.listVantagens(empresaId);
    }
}
