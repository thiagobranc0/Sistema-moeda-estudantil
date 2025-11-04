package br.edu.pucminas.sistema_moeda_estudantil.model.domain.service;

import br.edu.pucminas.sistema_moeda_estudantil.infra.boundary.AlunoBoundaryImpl;
import br.edu.pucminas.sistema_moeda_estudantil.model.domain.boundary.AlunoBoundary;
import br.edu.pucminas.sistema_moeda_estudantil.model.domain.dto.AlunoDTO;
import br.edu.pucminas.sistema_moeda_estudantil.model.domain.exceptions.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AlunoService {

    @Autowired
    private AlunoBoundaryImpl alunoBoundaryImpl;

    public void createAluno(AlunoDTO alunoDTO, UUID id) {
        alunoBoundaryImpl.createAluno(id, alunoDTO);
    }

    public boolean existsById(UUID id) {
        if (id == null) {
            throw new UserNotFoundException("Usuário não encontrado.");
        }
        return alunoBoundaryImpl.existsById(id);
    }

    public AlunoDTO updateAluno(UUID id, AlunoDTO alunoDTO) {
        if (!existsById(id)) {
            createAluno(alunoDTO, id);
        }
        return alunoBoundaryImpl.updateAluno(id, alunoDTO);
    }
}


