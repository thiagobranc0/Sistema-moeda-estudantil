package br.edu.pucminas.sistema_moeda_estudantil.model.domain.service;

import br.edu.pucminas.sistema_moeda_estudantil.infra.boundary.UsuarioBoundaryImpl;
import br.edu.pucminas.sistema_moeda_estudantil.model.domain.dto.AlunoDTO;
import br.edu.pucminas.sistema_moeda_estudantil.model.domain.dto.EmpresaDTO;
import br.edu.pucminas.sistema_moeda_estudantil.model.domain.dto.UsuarioDTO;
import br.edu.pucminas.sistema_moeda_estudantil.model.domain.dto.UsuarioUpdateDTO;
import br.edu.pucminas.sistema_moeda_estudantil.model.domain.exceptions.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

import static br.edu.pucminas.sistema_moeda_estudantil.infra.enums.TipoUsuario.*;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    @Autowired
    private UsuarioBoundaryImpl usuarioBoundary;

    @Autowired
    private EmpresaService empresaService;

    @Autowired
    private AlunoService alunoService;

    public void createUsuario(UsuarioDTO usuarioDTO){
        usuarioBoundary.createUsuario(usuarioDTO);
    }

    public void deleteUsuario(UsuarioDTO usuarioDTO){

    }


    public UsuarioDTO getUsuarioById(UUID id){
        if(id == null){
            throw new UserNotFoundException("O usuário não existe");
        }
        var usuario = usuarioBoundaryImpl.getUsuarioById(id);

        if(usuario == null){
            throw  new UserNotFoundException("O usuário não existe");
        }
        return usuario;
    }

    public boolean existsUsuarioByid(UUID id){
        if(id == null){
            throw  new UserNotFoundException("O usuário não existe");
        }
        return usuarioBoundary.existsUsuarioByid(id);
    }

    public UsuarioDTO updateUsuario(UUID id,UsuarioDTO usuarioDTO){
        if(!existsUsuarioByid(id)){
            throw  new UserNotFoundException("O usuário não existe");
        }
        return usuarioBoundary.saveUsuario(usuario);
    }

    public UsuarioUpdateDTO updateUsuario(UUID id, UsuarioUpdateDTO updateDTO){

        UsuarioDTO usuario = getUsuarioById(id);

        if (updateDTO.getNome() != null) usuario.setNome(updateDTO.getNome());
        if (updateDTO.getEmail() != null) usuario.setEmail(updateDTO.getEmail());
        if (updateDTO.getSenha() != null) usuario.setSenha(updateDTO.getSenha());

        usuario = updateUsuario(id,usuario);

        switch (usuario.getTipo()) {
            case ALUNO:
                if (temDadosAluno(updateDTO)) {
                    AlunoDTO alunoDTO = new AlunoDTO();
                    alunoDTO.setCpf(updateDTO.getCpf());
                    alunoDTO.setRg(updateDTO.getRg());
                    alunoDTO.setEndereco(updateDTO.getEndereco());
                    alunoService.updateAluno(id, alunoDTO);
                }
                break;

            case EMPRESA:
                if (temDadosEmpresa(updateDTO)) {
                    EmpresaDTO empresaDTO = new EmpresaDTO();
                    empresaDTO.setCnpj(updateDTO.getCnpj());
                    empresaService.updateEmpresa(id, empresaDTO);
                }
                break;
        }

    }

    private boolean temDadosAluno(UsuarioUpdateDTO dto) {

        return dto.getCpf() != null || dto.getRg() != null || dto.getEndereco() != null;
    }


    private boolean temDadosEmpresa(UsuarioUpdateDTO dto) {
        return dto.getCnpj() != null;
    }


}
