package br.edu.pucminas.sistema_moeda_estudantil.infra.controller;

import br.edu.pucminas.sistema_moeda_estudantil.controller.VantagemApi;
import br.edu.pucminas.sistema_moeda_estudantil.infra.mapper.VantagemMapper;
import br.edu.pucminas.sistema_moeda_estudantil.model.VantagemRequestDTO;
import br.edu.pucminas.sistema_moeda_estudantil.model.VantagemResponseDTO;
import br.edu.pucminas.sistema_moeda_estudantil.model.domain.dto.VantagemDTO;
import br.edu.pucminas.sistema_moeda_estudantil.model.domain.service.VantagemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.UUID;

@Controller
public class EmpresaController implements VantagemApi {

    @Autowired
    private VantagemMapper vantagemMapper;

    @Autowired
    private VantagemService vantagemService;


    @Override
    public ResponseEntity<Void> createVantagem(UUID empresaId, VantagemRequestDTO vantagemRequestDTO) {
        var vantagemDTO = vantagemMapper.vantagemRequestToVantagemDTO(vantagemRequestDTO);
        vantagemDTO.setEmpresaId(empresaId.toString());
        vantagemService.createVantagem(vantagemDTO);
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<VantagemResponseDTO> getVantagemById(UUID empresaId, UUID vantagemId) {
        VantagemDTO vantagemDTO = vantagemService.getVantagemById(empresaId, vantagemId);
        VantagemResponseDTO responseDTO = vantagemMapper.vantagemDTOToVantagemResponseDTO(vantagemDTO);
        responseDTO.setId(vantagemId);
        return ResponseEntity.ok(responseDTO);
    }

    @Override
    public ResponseEntity<List<VantagemResponseDTO>> listVantagens(UUID empresaId) {
        var vantagens = vantagemService.listVantagens(empresaId);
        var vantagenResponse = vantagemMapper.vantagemDTOListToVantagemResponseDTOList(vantagens);
        return ResponseEntity.ok(vantagenResponse);
    }
}
