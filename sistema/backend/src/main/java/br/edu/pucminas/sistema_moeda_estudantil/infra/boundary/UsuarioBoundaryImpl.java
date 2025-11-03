package br.edu.pucminas.sistema_moeda_estudantil.infra.boundary;

import br.edu.pucminas.sistema_moeda_estudantil.infra.mapper.UsuarioMapper;
import br.edu.pucminas.sistema_moeda_estudantil.model.domain.boundary.UsuarioBoundary;
import br.edu.pucminas.sistema_moeda_estudantil.model.domain.dto.UsuarioDTO;
import br.edu.pucminas.sistema_moeda_estudantil.model.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UsuarioBoundaryImpl implements UsuarioBoundary {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private UsuarioMapper usuarioMapper;

    @Override
    public void createUsuario(UsuarioDTO usuarioDTO) {
        var user = usuarioMapper.toEntity(usuarioDTO);
        usuarioRepository.save(user);
    }

    @Override
    public UsuarioDTO getUsuarioById(UUID id) {
        return null;
    }
}
