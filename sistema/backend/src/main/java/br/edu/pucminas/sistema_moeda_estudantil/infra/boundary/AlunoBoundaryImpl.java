package br.edu.pucminas.sistema_moeda_estudantil.infra.boundary;

import br.edu.pucminas.sistema_moeda_estudantil.model.domain.boundary.AlunoBoundary;
import br.edu.pucminas.sistema_moeda_estudantil.model.domain.dto.AlunoDTO;

public class AlunoBoundaryImpl implements AlunoBoundary {
    @Override
    public void createAluno(AlunoDTO alunoDTO) {
        //TODO chamar repository
    }

    @Override
    public AlunoDTO updateAluno(AlunoDTO alunoDTO) {
        //TODO chamar repository
        return null;
    }
}
