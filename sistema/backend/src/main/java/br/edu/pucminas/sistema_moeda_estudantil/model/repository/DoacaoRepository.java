package br.edu.pucminas.sistema_moeda_estudantil.model.repository;

import br.edu.pucminas.sistema_moeda_estudantil.infra.entity.DoacaoMoeda;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface DoacaoRepository extends JpaRepository<DoacaoMoeda, UUID> {
    List<DoacaoMoeda> findAllByProfessor_IdUsuarioOrderByDataDoacaoDesc(UUID idProfessor);
    List<DoacaoMoeda> findAllByAluno_IdUsuarioOrderByDataDoacaoDesc(UUID idProfessor);
}
