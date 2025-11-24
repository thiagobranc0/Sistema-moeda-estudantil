package br.edu.pucminas.sistema_moeda_estudantil.infra.boundary;

import br.edu.pucminas.sistema_moeda_estudantil.infra.entity.ResgateAluno;
import br.edu.pucminas.sistema_moeda_estudantil.infra.mapper.ResgateMapper;
import br.edu.pucminas.sistema_moeda_estudantil.model.domain.boundary.ResgateBoundary;
import br.edu.pucminas.sistema_moeda_estudantil.model.domain.dto.ResgateAlunoDTO;
import br.edu.pucminas.sistema_moeda_estudantil.model.domain.exceptions.SaldoInsuficienteException;
import br.edu.pucminas.sistema_moeda_estudantil.model.domain.exceptions.UserNotFoundException;
import br.edu.pucminas.sistema_moeda_estudantil.model.repository.AlunoRepository;
import br.edu.pucminas.sistema_moeda_estudantil.model.repository.ResgateAlunoRepository;
import br.edu.pucminas.sistema_moeda_estudantil.model.repository.UsuarioRepository;
import br.edu.pucminas.sistema_moeda_estudantil.model.repository.VantagemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class ResgateBoundaryImpl implements ResgateBoundary {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private AlunoRepository alunoRepository;

    @Autowired
    private VantagemRepository vantagemRepository;

    @Autowired
    private ResgateAlunoRepository resgateAlunoRepository;

    @Autowired
    private ResgateMapper resgateMapper;

    @Override
    public ResgateAlunoDTO redeem(String alunoEmail, UUID vantagemId) {
        if(!usuarioRepository.existsByEmail(alunoEmail)) {
            throw new UserNotFoundException("Aluno não encontrado!");
        }

        var alunoResgatante = alunoRepository.findById(usuarioRepository.findByEmail(alunoEmail).getId()).get();

        if(alunoResgatante.getSaldo() == null || alunoResgatante.getSaldo().equals(BigDecimal.ZERO)) {
            throw new SaldoInsuficienteException("Saldo insuficiente para realizar o resgate!");
        }

        var vantagemResgatada = vantagemRepository.findById(vantagemId).get();

        if (alunoResgatante.getSaldo().compareTo(vantagemResgatada.getCusto()) < 0) {
            throw new SaldoInsuficienteException("Saldo insuficiente para realizar o resgate!");
        }

        alunoResgatante.setSaldo(alunoResgatante.getSaldo().subtract(vantagemResgatada.getCusto()));
        alunoRepository.save(alunoResgatante);

        var resgate = ResgateAluno.builder()
                .aluno(alunoResgatante)
                .vantagem(vantagemResgatada)
                .valor(vantagemResgatada.getCusto())
                .dataResgate(LocalDateTime.now())
                .build();

        resgate = resgateAlunoRepository.save(resgate);

        return resgateMapper.toResgateAlunoDTO(resgate);
    }

    @Override
    public List<ResgateAlunoDTO> getPreviousRedeems(String alunoEmail) {
        if(!usuarioRepository.existsByEmail(alunoEmail)) {
            throw new UserNotFoundException("Aluno não encontrado!");
        }

        return resgateMapper.toResgateAlunoDTOList(resgateAlunoRepository.findAllByAluno_Usuario_EmailOrderByDataResgateDesc(alunoEmail));
    }
}
