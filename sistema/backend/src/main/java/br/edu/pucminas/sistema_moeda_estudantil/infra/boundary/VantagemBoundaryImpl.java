package br.edu.pucminas.sistema_moeda_estudantil.infra.boundary;

import br.edu.pucminas.sistema_moeda_estudantil.infra.entity.Vantagem;
import br.edu.pucminas.sistema_moeda_estudantil.infra.mapper.VantagemMapper;
import br.edu.pucminas.sistema_moeda_estudantil.model.domain.boundary.VantagemBoundary;
import br.edu.pucminas.sistema_moeda_estudantil.model.domain.dto.VantagemDTO;
import br.edu.pucminas.sistema_moeda_estudantil.model.domain.exceptions.EmpresaForbiddenException;
import br.edu.pucminas.sistema_moeda_estudantil.model.domain.exceptions.UserNotFoundException;
import br.edu.pucminas.sistema_moeda_estudantil.model.repository.EmpresaRepository;
import br.edu.pucminas.sistema_moeda_estudantil.model.repository.VantagemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class VantagemBoundaryImpl implements VantagemBoundary {

    @Autowired
    private VantagemRepository vantagemRepository;

    @Autowired
    private EmpresaRepository empresaRepository;

    @Autowired
    private VantagemMapper vantagemMapper;


    @Override
    public void createVantagem(VantagemDTO vantagemDTO) {
        if(!empresaRepository.existsById(UUID.fromString(vantagemDTO.getEmpresaId()))){
            throw new UserNotFoundException("Empresa não encontrada.");
        }

        var entidadeVantagem = vantagemMapper.vantagemDTOToEntity(vantagemDTO);
        var empresa = empresaRepository.findById(UUID.fromString(vantagemDTO.getEmpresaId())).get();
        entidadeVantagem.setEmpresa(empresa);
        vantagemRepository.save(entidadeVantagem);
    }

    @Override
    public VantagemDTO getVantagemById(UUID empresaId, UUID vantagemId) {
        if(!vantagemRepository.existsById(vantagemId)){
            throw new UserNotFoundException("Vantagem não encontrada.");
        }
        if(!empresaRepository.existsById(empresaId)){
            throw new UserNotFoundException("Empresa não econtrada!");
        }

        var empresa = empresaRepository.findById(empresaId);
        var vantagem = vantagemRepository.findById(vantagemId);

        if(!empresa.get().getUsuario().getId().equals(vantagem.get().getEmpresa().getUsuario().getId())){
            throw new EmpresaForbiddenException("Não há vantagem com esse id vinculada a sua empresa!");
        }

        var responseDTO = vantagemMapper.vantagemToVantagemDTO(vantagem.get());

        responseDTO.setEmpresaId(empresaId.toString());
        responseDTO.setId(vantagemId);

        return responseDTO;
    }

    @Override
    public List<VantagemDTO> listVantagens(UUID empresaId) {
        if(!empresaRepository.existsById(empresaId)){
            throw new UserNotFoundException("Empresa não econtrada!");
        }

        var vantagens = vantagemRepository.findAll().stream()
                .filter(v -> v.getEmpresa().getIdUsuario().equals(empresaId))
                .collect(Collectors.toList());

        return vantagemMapper.vantagemEntityListToVantagemDTOList(vantagens);
    }

    @Override
    public List<VantagemDTO> listAllVantagens() {
        var vantagens = vantagemRepository.findAll();

        return vantagemMapper.vantagemEntityListToVantagemDTOList(vantagens);
    }

    @Override
    public void deleteVantagem(UUID empresaId, UUID vantagemId) {
        if(!vantagemRepository.existsById(vantagemId)){
            throw new UserNotFoundException("Vantagem não encontrada.");
        }
        if(!empresaRepository.existsById(empresaId)){
            throw new UserNotFoundException("Empresa não econtrada!");
        }

        var empresa = empresaRepository.findById(empresaId);
        var vantagem = vantagemRepository.findById(vantagemId);

        if(!empresa.get().getUsuario().getId().equals(vantagem.get().getEmpresa().getUsuario().getId())){
            throw new EmpresaForbiddenException("Não há vantagem com esse id vinculada a sua empresa!");
        }

        vantagemRepository.deleteById(vantagemId);

    }

    @Override
    public VantagemDTO updateVantagem(UUID empresaId, UUID vantagemId, VantagemDTO vantagemDTO) {
        if(!vantagemRepository.existsById(vantagemId)){
            throw new UserNotFoundException("Vantagem não encontrada.");
        }
        if(!empresaRepository.existsById(empresaId)){
            throw new UserNotFoundException("Empresa não econtrada!");
        }

        var empresa = empresaRepository.findById(empresaId);
        var vantagem = vantagemRepository.findById(vantagemId);

        if(!empresa.get().getUsuario().getId().equals(vantagem.get().getEmpresa().getUsuario().getId())){
            throw new EmpresaForbiddenException("Não há vantagem com esse id vinculada a sua empresa!");
        }

        vantagemMapper.updateEntityFromVantagemDTO(vantagemDTO, vantagem.get());
        Vantagem updatedVantagem = vantagemRepository.save(vantagem.get());

        return vantagemMapper.vantagemToVantagemDTOComId(updatedVantagem);

    }
}
