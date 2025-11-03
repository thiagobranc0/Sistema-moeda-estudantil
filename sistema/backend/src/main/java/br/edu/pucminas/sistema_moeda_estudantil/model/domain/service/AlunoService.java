package br.edu.pucminas.sistema_moeda_estudantil.model.domain.service;

import br.edu.pucminas.sistema_moeda_estudantil.model.domain.dto.AlunoDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AlunoService {

    @Autowired


    public void createAluno(){

    }

    public void updateAluno(UUID id, AlunoDTO alunoDTO){
        //TODO lembrar que caso não esteja criado, chamar create

    }
}
