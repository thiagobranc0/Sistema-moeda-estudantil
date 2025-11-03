package br.edu.pucminas.sistema_moeda_estudantil.model.domain.boundary;

import br.edu.pucminas.sistema_moeda_estudantil.model.domain.dto.AlunoDTO;

public interface AlunoBoundary {

    void createAluno(AlunoDTO alunoDTO);

    AlunoDTO updateAluno(AlunoDTO alunoDTO);
}
