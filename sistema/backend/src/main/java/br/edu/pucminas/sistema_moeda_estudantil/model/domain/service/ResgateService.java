package br.edu.pucminas.sistema_moeda_estudantil.model.domain.service;

import br.edu.pucminas.sistema_moeda_estudantil.infra.boundary.ResgateBoundaryImpl;
import br.edu.pucminas.sistema_moeda_estudantil.model.domain.dto.ResgateAlunoDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ResgateService {

    @Autowired
    private ResgateBoundaryImpl resgateBoundaryImpl;

    public ResgateAlunoDTO redeem(String alunoEmail, UUID vantagemId) {
        return resgateBoundaryImpl.redeem(alunoEmail, vantagemId);
    }

    public List<ResgateAlunoDTO> getPreviousRedeems(String alunoEmail) {
        return resgateBoundaryImpl.getPreviousRedeems(alunoEmail);
    }
}
