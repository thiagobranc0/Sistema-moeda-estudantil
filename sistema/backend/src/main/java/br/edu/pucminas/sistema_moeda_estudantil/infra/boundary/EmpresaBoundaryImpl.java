package br.edu.pucminas.sistema_moeda_estudantil.infra.boundary;

import br.edu.pucminas.sistema_moeda_estudantil.model.domain.boundary.EmpresaBoundary;
import br.edu.pucminas.sistema_moeda_estudantil.model.domain.dto.EmpresaDTO;

public class EmpresaBoundaryImpl implements EmpresaBoundary {
    @Override
    public void createEmpresa(EmpresaDTO empresaDTO) {
        //TODO chamar repository

    }

    @Override
    public EmpresaDTO updateEmpresa(EmpresaDTO empresaDTO) {
        //TODO chamar repository

        return null;
    }
}
