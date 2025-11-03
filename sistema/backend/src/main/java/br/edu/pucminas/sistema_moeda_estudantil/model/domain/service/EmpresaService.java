package br.edu.pucminas.sistema_moeda_estudantil.model.domain.service;

import br.edu.pucminas.sistema_moeda_estudantil.infra.boundary.EmpresaBoundaryImpl;
import br.edu.pucminas.sistema_moeda_estudantil.model.domain.dto.EmpresaDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class EmpresaService {

    @Autowired
    private EmpresaBoundaryImpl empresaBoundaryImpl;

    public void createEmpresa(){

    }

    public EmpresaDTO updateEmpresa(UUID id, EmpresaDTO empresaDTO){
        //TODO lembrar que caso não esteja criado, chamar create
        return null;
    }
}
