package br.edu.pucminas.sistema_moeda_estudantil.repository;

import br.edu.pucminas.sistema_moeda_estudantil.entity.Vantagem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface VantagemRepository extends JpaRepository<Vantagem, UUID> {
}
