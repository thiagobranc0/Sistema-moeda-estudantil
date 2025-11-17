package br.edu.pucminas.sistema_moeda_estudantil.model.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class DoacaoDTO {
    UUID id;
    String email;
    UUID idProfessor;
    String nomeProfessor;
    UUID idAluno;
    String nomeAluno;
    String mensagem;
    BigDecimal valor;
    LocalDateTime dataDoacao;
}
