package br.edu.pucminas.sistema_moeda_estudantil.model.domain.service;

import br.edu.pucminas.sistema_moeda_estudantil.model.domain.boundary.DoacaoBoundary;
import br.edu.pucminas.sistema_moeda_estudantil.model.domain.dto.DoacaoDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class DoacaoService {

    @Autowired
    private DoacaoBoundary doacaoBoundary;

    public DoacaoDTO doar(DoacaoDTO doacaoDTO){
        return doacaoBoundary.doar(doacaoDTO);
    }

    public List<DoacaoDTO> getProfessorDonations(UUID professorId) {
        return doacaoBoundary.getProfessorDonations(professorId);
    }

    public List<DoacaoDTO> getAlunoDonations(UUID alunoId) {
        return doacaoBoundary.getAlunoDonations(alunoId);
    }
}
