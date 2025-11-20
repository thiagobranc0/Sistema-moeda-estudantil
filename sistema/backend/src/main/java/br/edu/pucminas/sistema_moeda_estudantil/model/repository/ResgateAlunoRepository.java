package br.edu.pucminas.sistema_moeda_estudantil.model.repository;

import br.edu.pucminas.sistema_moeda_estudantil.infra.entity.ResgateAluno;
import br.edu.pucminas.sistema_moeda_estudantil.infra.entity.Vantagem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ResgateAlunoRepository extends JpaRepository<ResgateAluno, UUID> {

    List<ResgateAluno> findAllByAluno_Usuario_EmailOrderByDataResgateDesc(String email);
}
