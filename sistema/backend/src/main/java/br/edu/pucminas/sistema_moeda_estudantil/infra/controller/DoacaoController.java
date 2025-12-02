package br.edu.pucminas.sistema_moeda_estudantil.infra.controller;


import br.edu.pucminas.sistema_moeda_estudantil.controller.DoacaoApi;

import br.edu.pucminas.sistema_moeda_estudantil.infra.mapper.DoacaoMapper;
import br.edu.pucminas.sistema_moeda_estudantil.model.DoacaoRequestDTO;
import br.edu.pucminas.sistema_moeda_estudantil.model.DoacaoResponseDTO;
import br.edu.pucminas.sistema_moeda_estudantil.model.domain.service.DoacaoService;
import br.edu.pucminas.sistema_moeda_estudantil.model.domain.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.UUID;

@Controller
public class DoacaoController implements DoacaoApi {

    @Autowired
    private DoacaoMapper doacaoMapper;

    @Autowired
    private DoacaoService doacaoService;

    @Autowired
    private EmailService emailService;

    @Override
    public ResponseEntity<DoacaoResponseDTO> donate(UUID professorId, DoacaoRequestDTO doacaoRequestDTO) {
        var doacaoDTO = doacaoMapper.doacaoRequestDTOToDoacaoDTO(doacaoRequestDTO);
        doacaoDTO.setIdProfessor(professorId);
        doacaoDTO = doacaoService.doar(doacaoDTO);
        var doacaoResponseDTO = doacaoMapper.doacaoDTOToDoacaoResponseDTO(doacaoDTO);
        emailService.sendDonationReceivedNotification(doacaoRequestDTO.getEmail(), doacaoResponseDTO.getNomeProfessor(), doacaoResponseDTO.getMensagem(), doacaoResponseDTO.getValor());
        return ResponseEntity.ok(doacaoResponseDTO);
    }

    @Override
    public ResponseEntity<List<DoacaoResponseDTO>> getProfessorDonations(UUID professorId) {
        var doacoesDTO = doacaoService.getProfessorDonations(professorId);
        var doacoesReponseDTO = doacaoMapper.doacaoDTOListToDoacaoResponseDTOList(doacoesDTO);
        return ResponseEntity.ok(doacoesReponseDTO);
    }

    @Override
    public ResponseEntity<List<DoacaoResponseDTO>> getAlunoDonations(UUID alunoId) {
        var doacoesDTO = doacaoService.getAlunoDonations(alunoId);
        var doacoesReponseDTO = doacaoMapper.doacaoDTOListToDoacaoResponseDTOList(doacoesDTO);
        return ResponseEntity.ok(doacoesReponseDTO);
    }
}
