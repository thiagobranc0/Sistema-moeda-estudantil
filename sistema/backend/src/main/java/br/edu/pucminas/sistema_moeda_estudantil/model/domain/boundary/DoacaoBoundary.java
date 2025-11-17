package br.edu.pucminas.sistema_moeda_estudantil.model.domain.boundary;

import br.edu.pucminas.sistema_moeda_estudantil.model.domain.dto.DoacaoDTO;

import java.util.List;
import java.util.UUID;

public interface DoacaoBoundary {
    DoacaoDTO doar(DoacaoDTO doacaoDTO);

    List<DoacaoDTO> getProfessorDonations(UUID professorId);

    List<DoacaoDTO> getAlunoDonations(UUID alunoId);
}
