package br.edu.pucminas.sistema_moeda_estudantil.model.domain.service;

import br.edu.pucminas.sistema_moeda_estudantil.model.domain.boundary.DoacaoBoundary;
import br.edu.pucminas.sistema_moeda_estudantil.model.domain.dto.DoacaoDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DoacaoService {

    @Autowired
    private DoacaoBoundary doacaoBoundary;

    public DoacaoDTO doar(DoacaoDTO doacaoDTO){
        return doacaoBoundary.doar(doacaoDTO);
    }
}
