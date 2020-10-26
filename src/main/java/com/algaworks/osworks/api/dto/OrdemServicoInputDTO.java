package com.algaworks.osworks.api.dto;

import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
public class OrdemServicoInputDTO {
    @Valid
    @NotNull
    private ClienteInputIdDTO cliente;

    @NotBlank
    private String descricao;

    @NotNull
    private BigDecimal preco;
}
