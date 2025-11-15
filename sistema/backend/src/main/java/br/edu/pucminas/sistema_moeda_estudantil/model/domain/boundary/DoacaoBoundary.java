package br.edu.pucminas.sistema_moeda_estudantil.model.domain.boundary;

import br.edu.pucminas.sistema_moeda_estudantil.model.domain.dto.DoacaoDTO;

public interface DoacaoBoundary {
    DoacaoDTO doar(DoacaoDTO doacaoDTO);
}
