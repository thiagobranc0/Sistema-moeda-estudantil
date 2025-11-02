package br.edu.pucminas.sistema_moeda_estudantil.model.repository;

import br.edu.pucminas.sistema_moeda_estudantil.infra.entity.Aluno;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AlunoRepository extends JpaRepository<Aluno, UUID> {
}
