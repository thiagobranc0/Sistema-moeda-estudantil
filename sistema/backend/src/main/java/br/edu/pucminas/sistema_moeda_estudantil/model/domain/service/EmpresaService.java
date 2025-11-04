package br.edu.pucminas.sistema_moeda_estudantil.model.domain.service;

import br.edu.pucminas.sistema_moeda_estudantil.infra.boundary.EmpresaBoundaryImpl;
import br.edu.pucminas.sistema_moeda_estudantil.model.domain.dto.EmpresaDTO;
import br.edu.pucminas.sistema_moeda_estudantil.model.domain.exceptions.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class EmpresaService {

    @Autowired
    private EmpresaBoundaryImpl empresaBoundaryImpl;

    public void createEmpresa(EmpresaDTO empresaDTO, UUID id){
        empresaBoundaryImpl.createEmpresa(empresaDTO, id);
    }

    public boolean existsById(UUID id){
        if(id == null){
            throw new UserNotFoundException("Usuário não encontrado.");
        }
        return empresaBoundaryImpl.existsById(id);
    }
    public EmpresaDTO updateEmpresa(UUID id, EmpresaDTO empresaDTO){
        if(!existsById(id)){
            createEmpresa(empresaDTO, id);
        }
        return empresaBoundaryImpl.updateEmpresa(id, empresaDTO);
    }
}
