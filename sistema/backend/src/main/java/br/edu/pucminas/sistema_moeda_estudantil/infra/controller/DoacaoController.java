package br.edu.pucminas.sistema_moeda_estudantil.infra.controller;


import br.edu.pucminas.sistema_moeda_estudantil.controller.DoacaoApi;

import br.edu.pucminas.sistema_moeda_estudantil.infra.mapper.DoacaoMapper;
import br.edu.pucminas.sistema_moeda_estudantil.model.DoacaoRequestDTO;
import br.edu.pucminas.sistema_moeda_estudantil.model.DoacaoResponseDTO;
import br.edu.pucminas.sistema_moeda_estudantil.model.domain.service.DoacaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import java.util.UUID;

@Controller
public class DoacaoController implements DoacaoApi {

    @Autowired
    private DoacaoMapper doacaoMapper;

    @Autowired
    private DoacaoService doacaoService;

    @Override
    public ResponseEntity<DoacaoResponseDTO> donate(UUID professorId, DoacaoRequestDTO doacaoRequestDTO) {
        var doacaoDTO = doacaoMapper.doacaoRequestDTOToDoacaoDTO(doacaoRequestDTO);
        doacaoDTO.setIdProfessor(professorId);
        doacaoDTO = doacaoService.doar(doacaoDTO);
        var doacaoResponseDTO = doacaoMapper.doacaoDTOToDoacaoResponseDTO(doacaoDTO);
        return ResponseEntity.ok(doacaoResponseDTO);
    }
}
