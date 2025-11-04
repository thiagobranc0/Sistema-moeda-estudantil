package br.edu.pucminas.sistema_moeda_estudantil.infra.boundary;

import br.edu.pucminas.sistema_moeda_estudantil.infra.mapper.AlunoMapper;
import br.edu.pucminas.sistema_moeda_estudantil.model.domain.boundary.AlunoBoundary;
import br.edu.pucminas.sistema_moeda_estudantil.model.domain.dto.AlunoDTO;
import br.edu.pucminas.sistema_moeda_estudantil.model.repository.AlunoRepository;
import br.edu.pucminas.sistema_moeda_estudantil.model.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class AlunoBoundaryImpl implements AlunoBoundary {

    @Autowired
    private AlunoRepository alunoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private AlunoMapper alunoMapper;

    @Override
    public void createAluno(UUID id, AlunoDTO alunoDTO) {
        var aluno = alunoMapper.toEntity(alunoDTO);
        var usuario = usuarioRepository.findById(id);
        aluno.setUsuario(usuario.get());
        alunoRepository.save(aluno);
    }

    @Override
    public AlunoDTO updateAluno(UUID id, AlunoDTO alunoDTO) {
        var aluno = alunoMapper.toEntity(alunoDTO);
        aluno.setIdUsuario(id);
        aluno = alunoRepository.save(aluno);
        return alunoMapper.toAlunoDTO(aluno);
    }

    @Override
    public boolean existsById(UUID id) {
        return alunoRepository.existsById(id);
    }
}
