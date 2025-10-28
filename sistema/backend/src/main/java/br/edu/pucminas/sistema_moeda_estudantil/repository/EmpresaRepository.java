package br.edu.pucminas.sistema_moeda_estudantil.repository;

import br.edu.pucminas.sistema_moeda_estudantil.entity.Empresa;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface EmpresaRepository extends JpaRepository<Empresa, UUID> {
}
