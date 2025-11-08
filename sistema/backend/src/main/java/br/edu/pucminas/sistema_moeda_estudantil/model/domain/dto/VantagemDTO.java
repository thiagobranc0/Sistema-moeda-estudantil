package br.edu.pucminas.sistema_moeda_estudantil.model.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class VantagemDTO {
    String empresaId;
    String descricao;
    BigDecimal custo;
    String foto;
    UUID id;
}
