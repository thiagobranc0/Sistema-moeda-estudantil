package br.edu.pucminas.sistema_moeda_estudantil.model.domain.service;

import br.edu.pucminas.sistema_moeda_estudantil.infra.boundary.UsuarioBoundaryImpl;
import br.edu.pucminas.sistema_moeda_estudantil.model.domain.dto.AlunoDTO;
import br.edu.pucminas.sistema_moeda_estudantil.model.domain.dto.EmpresaDTO;
import br.edu.pucminas.sistema_moeda_estudantil.model.domain.dto.UsuarioDTO;
import br.edu.pucminas.sistema_moeda_estudantil.model.domain.dto.UsuarioUpdateDTO;
import br.edu.pucminas.sistema_moeda_estudantil.model.domain.exceptions.UserNotFoundException;
import br.edu.pucminas.sistema_moeda_estudantil.model.domain.mapper.UsuarioDomainMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

import static br.edu.pucminas.sistema_moeda_estudantil.infra.enums.TipoUsuario.*;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    @Autowired
    private UsuarioBoundaryImpl usuarioBoundaryImpl;

    @Autowired
    private EmpresaService empresaService;

    @Autowired
    private AlunoService alunoService;

    @Autowired
    private UsuarioDomainMapper usuarioDomainMapper;

    public void createUsuario(UsuarioDTO usuarioDTO) {
        usuarioBoundaryImpl.createUsuario(usuarioDTO);
    }

    public void deleteUsuario(UUID id) {
        if (!existsUsuarioByid(id)) {
            throw new UserNotFoundException("O usuário não existe");
        }
        usuarioBoundaryImpl.deleteUsuario(id);
    }


    public UsuarioUpdateDTO getUsuarioById(UUID id) {
        if (id == null) {
            throw new UserNotFoundException("O usuário não existe");
        }
        var usuario = usuarioBoundaryImpl.getUsuarioById(id);


        if (usuario == null) {
            throw new UserNotFoundException("O usuário não existe");
        }
        return usuario;
    }

    public UsuarioDTO getUsuarioDtoById(UUID id){
        if (id == null) {
            throw new UserNotFoundException("O usuário não existe");
        }
        var usuario = usuarioBoundaryImpl.getUsuarioById(id);

        if (usuario == null) {
            throw new UserNotFoundException("O usuário não existe");
        }
        return usuarioDomainMapper.toUserDTO(usuario);
    }

    public boolean existsUsuarioByid(UUID id) {
        if (id == null) {
            throw new UserNotFoundException("O usuário não existe");
        }
        return usuarioBoundaryImpl.existsById(id);
    }

    public UsuarioDTO updateUsuario(UUID id, UsuarioDTO usuarioDTO) {
        if (!existsUsuarioByid(id)) {
            throw new UserNotFoundException("O usuário não existe");
        }
        return usuarioBoundaryImpl.updateUsuario(id, usuarioDTO);
    }

    public UsuarioUpdateDTO updateUsuario(UUID id, UsuarioUpdateDTO updateDTO) {

        if (!existsUsuarioByid(id)) {
            throw new UserNotFoundException("O usuário não existe");
        }

        var usuario = getUsuarioDtoById(id);

        if (updateDTO.getNome() != null) usuario.setNome(updateDTO.getNome());
        if (updateDTO.getEmail() != null) usuario.setEmail(updateDTO.getEmail());
        if (updateDTO.getSenha() != null) usuario.setSenha(updateDTO.getSenha());

        usuario = updateUsuario(id, usuario);

        var usuarioUpdateDomain = usuarioDomainMapper.toUserUpdateDTO(usuario);

        switch (usuario.getTipoEnum()) {
            case ALUNO:
                if (temDadosAluno(updateDTO)) {
                    AlunoDTO alunoDTO = new AlunoDTO();
                    alunoDTO.setCpf(updateDTO.getCpf());
                    alunoDTO.setRg(updateDTO.getRg());
                    alunoDTO.setEndereco(updateDTO.getEndereco());
                    alunoService.updateAluno(id, alunoDTO);
                    usuarioUpdateDomain.setRg(alunoDTO.getRg());
                    usuarioUpdateDomain.setEndereco(alunoDTO.getEndereco());
                    usuarioUpdateDomain.setCpf(alunoDTO.getCpf());
                }
                break;

            case EMPRESA:
                if (temDadosEmpresa(updateDTO)) {
                    EmpresaDTO empresaDTO = new EmpresaDTO();
                    empresaDTO.setCnpj(updateDTO.getCnpj());
                    empresaService.updateEmpresa(id, empresaDTO);
                    usuarioUpdateDomain.setCnpj(empresaDTO.getCnpj());
                }
                break;
        }
        return usuarioUpdateDomain;
    }

    private boolean temDadosAluno(UsuarioUpdateDTO dto) {

        return dto.getCpf() != null || dto.getRg() != null || dto.getEndereco() != null;
    }


    private boolean temDadosEmpresa(UsuarioUpdateDTO dto) {
        return dto.getCnpj() != null;
    }


}
