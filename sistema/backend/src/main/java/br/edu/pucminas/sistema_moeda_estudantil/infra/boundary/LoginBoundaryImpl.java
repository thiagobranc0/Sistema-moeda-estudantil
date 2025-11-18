package br.edu.pucminas.sistema_moeda_estudantil.infra.boundary;

import br.edu.pucminas.sistema_moeda_estudantil.infra.enums.TipoUsuario;
import br.edu.pucminas.sistema_moeda_estudantil.infra.mapper.LoginMapper;
import br.edu.pucminas.sistema_moeda_estudantil.infra.mapper.UsuarioMapper;
import br.edu.pucminas.sistema_moeda_estudantil.model.LoginResponseDTO;
import br.edu.pucminas.sistema_moeda_estudantil.model.domain.boundary.LoginBoundary;
import br.edu.pucminas.sistema_moeda_estudantil.model.domain.dto.LoginDTO;
import br.edu.pucminas.sistema_moeda_estudantil.model.repository.AlunoRepository;
import br.edu.pucminas.sistema_moeda_estudantil.model.repository.EmpresaRepository;
import br.edu.pucminas.sistema_moeda_estudantil.model.repository.ProfessorRepository;
import br.edu.pucminas.sistema_moeda_estudantil.model.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class LoginBoundaryImpl implements LoginBoundary {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private UsuarioMapper usuarioMapper;

    @Autowired
    private AlunoRepository alunoRepository;

    @Autowired
    private EmpresaRepository empresaRepository;

    @Autowired
    private LoginMapper loginMapper;

    @Autowired
    private ProfessorRepository professorRepository;

    @Override
    public LoginResponseDTO login(LoginDTO loginDTO) {
        var usuario = usuarioRepository.findByEmail(loginDTO.getEmail());

        var id = usuario.getId();

        LoginResponseDTO loginResponseDTO = loginMapper.toLoginResponseDTO(usuario);

        if (usuario.getTipo() == TipoUsuario.ALUNO) {
            alunoRepository.findById(id).ifPresent(aluno -> {
                loginResponseDTO.setCpf(aluno.getCpf());
                loginResponseDTO.setRg(aluno.getRg());
                loginResponseDTO.setEndereco(aluno.getEndereco());
                if(aluno.getSaldo() == null) {
                    aluno.setSaldo(BigDecimal.ZERO);
                }
                loginResponseDTO.setSaldo(aluno.getSaldo().doubleValue());
            });
        } else if (usuario.getTipo() == TipoUsuario.EMPRESA) {
            empresaRepository.findById(id).ifPresent(empresa -> {
                loginResponseDTO.setCnpj(empresa.getCnpj());
            });
        } else if (usuario.getTipo() == TipoUsuario.PROFESSOR) {
            professorRepository.findById(id).ifPresent(professor -> {
                loginResponseDTO.setCpf(professor.getCpf());
                if(professor.getSaldo() == null) {
                    professor.setSaldo(BigDecimal.ZERO);
                }
                loginResponseDTO.setSaldo(professor.getSaldo().doubleValue());
                loginResponseDTO.setDepartamentoId( professor.getDepartamento().getId());
            });
        }

        loginResponseDTO.setId(id);

        return loginResponseDTO;

    }
}
