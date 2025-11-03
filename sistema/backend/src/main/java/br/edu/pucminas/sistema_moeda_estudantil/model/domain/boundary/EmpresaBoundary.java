package br.edu.pucminas.sistema_moeda_estudantil.model.domain.boundary;

import br.edu.pucminas.sistema_moeda_estudantil.model.domain.dto.EmpresaDTO;

public interface EmpresaBoundary {

    void createEmpresa(EmpresaDTO empresaDTO);

    EmpresaDTO updateEmpresa(EmpresaDTO empresaDTO);
}
