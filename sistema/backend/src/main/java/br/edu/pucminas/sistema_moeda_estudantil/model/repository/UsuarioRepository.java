package br.edu.pucminas.sistema_moeda_estudantil.model.repository;

import br.edu.pucminas.sistema_moeda_estudantil.infra.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UsuarioRepository extends JpaRepository<Usuario, UUID> {
}
