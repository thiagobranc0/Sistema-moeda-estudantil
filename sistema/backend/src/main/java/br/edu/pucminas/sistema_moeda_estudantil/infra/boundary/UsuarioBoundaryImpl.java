package br.edu.pucminas.sistema_moeda_estudantil.infra.boundary;

import br.edu.pucminas.sistema_moeda_estudantil.infra.entity.Aluno;
import br.edu.pucminas.sistema_moeda_estudantil.infra.enums.TipoUsuario;
import br.edu.pucminas.sistema_moeda_estudantil.infra.mapper.UsuarioMapper;
import br.edu.pucminas.sistema_moeda_estudantil.model.domain.boundary.UsuarioBoundary;
import br.edu.pucminas.sistema_moeda_estudantil.model.domain.dto.UsuarioDTO;
import br.edu.pucminas.sistema_moeda_estudantil.model.domain.dto.UsuarioUpdateDTO;
import br.edu.pucminas.sistema_moeda_estudantil.model.repository.AlunoRepository;
import br.edu.pucminas.sistema_moeda_estudantil.model.repository.EmpresaRepository;
import br.edu.pucminas.sistema_moeda_estudantil.model.repository.ProfessorRepository;
import br.edu.pucminas.sistema_moeda_estudantil.model.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.UUID;

@Service
public class UsuarioBoundaryImpl implements UsuarioBoundary {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private UsuarioMapper usuarioMapper;

    @Autowired
    private AlunoRepository alunoRepository;

    @Autowired
    private EmpresaRepository empresaRepository;

    @Autowired
    private ProfessorRepository professorRepository;

    @Override
    public void createUsuario(UsuarioDTO usuarioDTO) {
        var user = usuarioMapper.toEntity(usuarioDTO);
        usuarioRepository.save(user);
    }

    @Override
    public UsuarioUpdateDTO getUsuarioById(UUID id) {
        var usuario = usuarioRepository.findById(id);

        UsuarioUpdateDTO usuarioUpdateDTO = usuarioMapper.toUsuarioUpdateDTO(usuario.get());

        if (usuario.get().getTipo() == TipoUsuario.ALUNO) {
            alunoRepository.findById(id).ifPresent(aluno -> {
                usuarioUpdateDTO.setCpf(aluno.getCpf());
                usuarioUpdateDTO.setRg(aluno.getRg());
                usuarioUpdateDTO.setEndereco(aluno.getEndereco());
                Aluno usuarioSalvo = alunoRepository.findById(id).get();
                if(usuarioSalvo.getSaldo() == null) {
                    usuarioSalvo.setSaldo(BigDecimal.ZERO);
                }

                usuarioUpdateDTO.setSaldo(usuarioSalvo.getSaldo().doubleValue());
                System.out.println(usuarioUpdateDTO.getSaldo());
            });
        } else if (usuario.get().getTipo() == TipoUsuario.EMPRESA) {
            empresaRepository.findById(id).ifPresent(empresa -> {
                usuarioUpdateDTO.setCnpj(empresa.getCnpj());
            });
        } else if (usuario.get().getTipo() == TipoUsuario.PROFESSOR) {
            professorRepository.findById(id).ifPresent(professor -> {
                usuarioUpdateDTO.setCpf(professor.getCpf());
                if(professor.getSaldo() == null) {
                    professor.setSaldo(BigDecimal.ZERO);
                }
                usuarioUpdateDTO.setSaldo(professor.getSaldo().doubleValue());
                usuarioUpdateDTO.setDepartamentoId( professor.getDepartamento().getId());
            });
        }
        return usuarioUpdateDTO;
    }

    @Override
    public boolean existsById(UUID id) {
        return usuarioRepository.existsById(id);
    }

    @Override
    public UsuarioDTO updateUsuario(UUID id, UsuarioDTO usuarioDTO) {
        var user = usuarioMapper.toEntity(usuarioDTO);
        user.setId(id);
        user = usuarioRepository.save(user);
        return usuarioMapper.toUserDTO(user);
    }

    @Override
    public void deleteUsuario(UUID id) {
        var usuario = usuarioRepository.findById(id);

        if (usuario.get().getTipo() == TipoUsuario.ALUNO) {
            alunoRepository.findById(id).ifPresent(aluno -> {
                alunoRepository.deleteById(id);
            });
        } else if (usuario.get().getTipo() == TipoUsuario.EMPRESA) {
            empresaRepository.findById(id).ifPresent(empresa -> {
                empresaRepository.deleteById(id);
            });
        }
        usuarioRepository.deleteById(id);
    }
}
