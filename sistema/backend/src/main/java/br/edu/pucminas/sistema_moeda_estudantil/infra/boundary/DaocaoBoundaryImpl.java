package br.edu.pucminas.sistema_moeda_estudantil.infra.boundary;


import br.edu.pucminas.sistema_moeda_estudantil.infra.entity.Aluno;
import br.edu.pucminas.sistema_moeda_estudantil.infra.entity.DoacaoMoeda;
import br.edu.pucminas.sistema_moeda_estudantil.infra.entity.Professor;
import br.edu.pucminas.sistema_moeda_estudantil.infra.mapper.DoacaoMapper;
import br.edu.pucminas.sistema_moeda_estudantil.model.domain.boundary.DoacaoBoundary;
import br.edu.pucminas.sistema_moeda_estudantil.model.domain.dto.DoacaoDTO;
import br.edu.pucminas.sistema_moeda_estudantil.model.domain.exceptions.UserNotFoundException;
import br.edu.pucminas.sistema_moeda_estudantil.model.domain.exceptions.ValorInvalidoException;
import br.edu.pucminas.sistema_moeda_estudantil.model.repository.AlunoRepository;
import br.edu.pucminas.sistema_moeda_estudantil.model.repository.DoacaoRepository;
import br.edu.pucminas.sistema_moeda_estudantil.model.repository.ProfessorRepository;
import br.edu.pucminas.sistema_moeda_estudantil.model.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
public class DaocaoBoundaryImpl implements DoacaoBoundary {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private ProfessorRepository professorRepository;

    @Autowired
    private AlunoRepository alunoRepository;

    @Autowired
    private DoacaoMapper doacaoMapper;

    @Autowired
    private DoacaoRepository doacaoRepository;

    @Override
    public DoacaoDTO doar(DoacaoDTO doacaoDTO) {
        if(!alunoRepository.existsById(doacaoDTO.getIdAluno())) {
            throw new UserNotFoundException("Aluno não encontrado!");
        }

        if(!professorRepository.existsById(doacaoDTO.getIdProfessor())) {
            throw new UserNotFoundException("Professor não encontrado!");
        }

        if (doacaoDTO.getValor() == null || doacaoDTO.getValor().compareTo(BigDecimal.ZERO) <= 0) {
            throw new ValorInvalidoException("O valor da doação deve ser maior que zero.");
        }

        Professor doador = professorRepository.findById(doacaoDTO.getIdProfessor()).get();
        doador.setSaldo(doador.getSaldo().subtract(doacaoDTO.getValor()));
        professorRepository.save(doador);

        Aluno destinatario = alunoRepository.findById(doacaoDTO.getIdAluno()).get();
        destinatario.setSaldo(destinatario.getSaldo().add(doacaoDTO.getValor()));
        alunoRepository.save(destinatario);

        var doacao = doacaoMapper.doacaoDTOToDoacaoEntity(doacaoDTO);
        doacao.setDataDoacao(LocalDateTime.now());

        doacao = doacaoRepository.save(doacao);

        doacaoDTO = doacaoMapper.doacaoEntityDTOToDoacaoDTO(doacao);

        doacaoDTO.setNomeAluno(usuarioRepository.findById(doacaoDTO.getIdAluno()).get().getNome());
        doacaoDTO.setNomeProfessor(usuarioRepository.findById(doacaoDTO.getIdProfessor()).get().getNome());
        doacaoDTO.setIdAluno(usuarioRepository.findById(doacaoDTO.getIdAluno()).get().getId());
        doacaoDTO.setIdProfessor(usuarioRepository.findById(doacaoDTO.getIdProfessor()).get().getId());

        return doacaoDTO;

    }
}
