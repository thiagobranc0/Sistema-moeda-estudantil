package br.edu.pucminas.sistema_moeda_estudantil.infra.controller;

import br.edu.pucminas.sistema_moeda_estudantil.controller.ResgateApi;
import br.edu.pucminas.sistema_moeda_estudantil.infra.mapper.ResgateMapper;
import br.edu.pucminas.sistema_moeda_estudantil.model.ResgateAlunoResponseDTO;
import br.edu.pucminas.sistema_moeda_estudantil.model.domain.dto.ResgateAlunoDTO;
import br.edu.pucminas.sistema_moeda_estudantil.model.domain.service.ResgateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.UUID;

@Controller
public class ResgateController implements ResgateApi {

    @Autowired
    private ResgateMapper resgateMapper;

    @Autowired
    private ResgateService resgateService;

    @Override
    public ResponseEntity<ResgateAlunoResponseDTO> redeem(String alunoEmail, UUID vantagemId) {
        var resgateAlunoDTO = resgateService.redeem(alunoEmail, vantagemId);
        var resgateAlunoResponseDTO = resgateMapper.toResgateAlunoResponseDTO(resgateAlunoDTO);
        return ResponseEntity.ok(resgateAlunoResponseDTO);
    }

    @Override
    public ResponseEntity<List<ResgateAlunoResponseDTO>> getPreviousRedeems(String alunoEmail) {
        var resgatesAlunoDTO = resgateService.getPreviousRedeems(alunoEmail);
        var resgatesAlunoResponseDTO = resgateMapper.toResgateAlunoResponseDTOList(resgatesAlunoDTO);
        return ResponseEntity.ok(resgatesAlunoResponseDTO);
    }
}
