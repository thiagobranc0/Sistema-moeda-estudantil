package br.edu.pucminas.sistema_moeda_estudantil.infra.boundary;

import br.edu.pucminas.sistema_moeda_estudantil.infra.enums.TipoUsuario;
import br.edu.pucminas.sistema_moeda_estudantil.infra.mapper.LoginMapper;
import br.edu.pucminas.sistema_moeda_estudantil.infra.mapper.UsuarioMapper;
import br.edu.pucminas.sistema_moeda_estudantil.model.LoginResponseDTO;
import br.edu.pucminas.sistema_moeda_estudantil.model.domain.boundary.LoginBoundary;
import br.edu.pucminas.sistema_moeda_estudantil.model.domain.dto.LoginDTO;
import br.edu.pucminas.sistema_moeda_estudantil.model.repository.AlunoRepository;
import br.edu.pucminas.sistema_moeda_estudantil.model.repository.EmpresaRepository;
import br.edu.pucminas.sistema_moeda_estudantil.model.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
            });
        } else if (usuario.getTipo() == TipoUsuario.EMPRESA) {
            empresaRepository.findById(id).ifPresent(empresa -> {
                loginResponseDTO.setCnpj(empresa.getCnpj());
            });
        }

        loginResponseDTO.setId(id);

        return loginResponseDTO;

    }
}
