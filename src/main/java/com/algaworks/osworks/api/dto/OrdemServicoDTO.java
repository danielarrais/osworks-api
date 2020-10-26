package com.algaworks.osworks.api.dto;

import com.algaworks.osworks.domain.model.StatusOrdemServico;
import lombok.Data;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Data
public class OrdemServicoDTO {
     private Long id;
     private ClienteResumidoDTO cliente;
     private String descricao;
     private BigDecimal preco;
     private StatusOrdemServico status;
     private OffsetDateTime dataAbertura;
     private OffsetDateTime dataFinalizacao;
}
