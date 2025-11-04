package br.edu.pucminas.sistema_moeda_estudantil.model.domain.boundary;

import br.edu.pucminas.sistema_moeda_estudantil.model.domain.dto.AlunoDTO;

import java.util.UUID;

public interface AlunoBoundary {

    void createAluno(UUID id,AlunoDTO alunoDTO);

    AlunoDTO updateAluno(UUID id, AlunoDTO alunoDTO);

    boolean existsById(UUID id);
}
