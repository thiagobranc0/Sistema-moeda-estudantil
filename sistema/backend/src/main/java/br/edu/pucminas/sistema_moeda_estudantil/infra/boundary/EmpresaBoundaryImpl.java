package br.edu.pucminas.sistema_moeda_estudantil.infra.boundary;

import br.edu.pucminas.sistema_moeda_estudantil.infra.mapper.EmpresaMapper;
import br.edu.pucminas.sistema_moeda_estudantil.model.domain.boundary.EmpresaBoundary;
import br.edu.pucminas.sistema_moeda_estudantil.model.domain.dto.EmpresaDTO;
import br.edu.pucminas.sistema_moeda_estudantil.model.repository.EmpresaRepository;
import br.edu.pucminas.sistema_moeda_estudantil.model.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class EmpresaBoundaryImpl implements EmpresaBoundary {

    @Autowired
    private EmpresaRepository empresaRepository;

    @Autowired
    private EmpresaMapper empresaMapper;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public void createEmpresa(EmpresaDTO empresaDTO, UUID id) {
        var empresa  = empresaMapper.toEntity(empresaDTO);
        var usuario = usuarioRepository.findById(id);
        empresa.setUsuario(usuario.get());
        empresaRepository.save(empresa);
    }

    @Override
    public EmpresaDTO updateEmpresa(UUID id, EmpresaDTO empresaDTO) {
        var empresa  = empresaMapper.toEntity(empresaDTO);
        empresa.setIdUsuario(id);
        empresa = empresaRepository.save(empresa);
        return empresaMapper.toEmpresaDTO(empresa);
    }

    @Override
    public boolean existsById(UUID id) {
        return empresaRepository.existsById(id);
    }
}
