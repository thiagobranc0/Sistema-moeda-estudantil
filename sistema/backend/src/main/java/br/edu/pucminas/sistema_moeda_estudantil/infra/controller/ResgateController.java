package br.edu.pucminas.sistema_moeda_estudantil.infra.controller;

import br.edu.pucminas.sistema_moeda_estudantil.controller.ResgateApi;
import br.edu.pucminas.sistema_moeda_estudantil.infra.entity.Empresa;
import br.edu.pucminas.sistema_moeda_estudantil.infra.mapper.ResgateMapper;
import br.edu.pucminas.sistema_moeda_estudantil.model.ResgateAlunoResponseDTO;
import br.edu.pucminas.sistema_moeda_estudantil.model.domain.dto.ResgateAlunoDTO;
import br.edu.pucminas.sistema_moeda_estudantil.model.domain.service.EmailService;
import br.edu.pucminas.sistema_moeda_estudantil.model.domain.service.ResgateService;
import br.edu.pucminas.sistema_moeda_estudantil.model.domain.service.VantagemService;
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

    @Autowired
    private EmailService emailService;

    @Autowired
    private VantagemService vantagemService;

    @Override
    public ResponseEntity<ResgateAlunoResponseDTO> redeem(String alunoEmail, UUID vantagemId) {
        var resgateAlunoDTO = resgateService.redeem(alunoEmail, vantagemId);
        var resgateAlunoResponseDTO = resgateMapper.toResgateAlunoResponseDTO(resgateAlunoDTO);
        emailService.sendStudentRedeemNotification(resgateAlunoDTO.getEmail(), resgateAlunoResponseDTO.getDescricao());
        Empresa empresa = vantagemService.getEmpresaByVantagemId(vantagemId);
        emailService.sendBusinessRedeemNotification(empresa.getUsuario().getEmail(), resgateAlunoResponseDTO.getDescricao());
        return ResponseEntity.ok(resgateAlunoResponseDTO);
    }

    @Override
    public ResponseEntity<List<ResgateAlunoResponseDTO>> getPreviousRedeems(String alunoEmail) {
        var resgatesAlunoDTO = resgateService.getPreviousRedeems(alunoEmail);
        var resgatesAlunoResponseDTO = resgateMapper.toResgateAlunoResponseDTOList(resgatesAlunoDTO);
        return ResponseEntity.ok(resgatesAlunoResponseDTO);
    }
}
