package br.edu.pucminas.sistema_moeda_estudantil.repository;

import br.edu.pucminas.sistema_moeda_estudantil.entity.Aluno;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AlunoRepository extends JpaRepository<Aluno, UUID> {
}
