package br.edu.pucminas.sistema_moeda_estudantil.model.domain.boundary;

import br.edu.pucminas.sistema_moeda_estudantil.infra.entity.ResgateAluno;
import br.edu.pucminas.sistema_moeda_estudantil.model.domain.dto.ResgateAlunoDTO;

import java.util.List;
import java.util.UUID;

public interface ResgateBoundary {
    ResgateAlunoDTO redeem(String alunoEmail, UUID vantagemId);

    List<ResgateAlunoDTO> getPreviousRedeems(String alunoEmail);
}
