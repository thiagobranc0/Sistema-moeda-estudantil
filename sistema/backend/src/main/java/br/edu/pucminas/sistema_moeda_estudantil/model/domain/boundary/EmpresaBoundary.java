package br.edu.pucminas.sistema_moeda_estudantil.model.domain.boundary;

import br.edu.pucminas.sistema_moeda_estudantil.model.domain.dto.EmpresaDTO;

import java.util.UUID;

public interface EmpresaBoundary {

    void createEmpresa(EmpresaDTO empresaDTO, UUID id);

    EmpresaDTO updateEmpresa(UUID id, EmpresaDTO empresaDTO);

    boolean existsById(UUID id);
}
