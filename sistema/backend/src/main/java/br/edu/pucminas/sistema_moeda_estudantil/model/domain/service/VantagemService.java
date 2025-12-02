package br.edu.pucminas.sistema_moeda_estudantil.model.domain.service;


import br.edu.pucminas.sistema_moeda_estudantil.infra.boundary.VantagemBoundaryImpl;
import br.edu.pucminas.sistema_moeda_estudantil.infra.entity.Empresa;
import br.edu.pucminas.sistema_moeda_estudantil.model.domain.dto.VantagemDTO;

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

    public List<VantagemDTO> listAllVantagens() {
        return vantagemBoundaryImpl.listAllVantagens();
    }

    public void deleteVantagem(UUID empresaId, UUID vantagemId) {
        vantagemBoundaryImpl.deleteVantagem(empresaId, vantagemId);
    }

    public VantagemDTO updateVantagem(UUID empresaId, UUID vantagemId, VantagemDTO vantagem) {
        return vantagemBoundaryImpl.updateVantagem(empresaId, vantagemId, vantagem);
    }

    public Empresa getEmpresaByVantagemId(UUID vantagemId) {
        return vantagemBoundaryImpl.getEmpresaByVantagemId(vantagemId);
    }
}
