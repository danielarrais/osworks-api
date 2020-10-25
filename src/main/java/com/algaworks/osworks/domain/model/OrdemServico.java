package com.algaworks.osworks.domain.model;

import com.algaworks.osworks.domain.ValidationGroups;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.groups.ConvertGroup;
import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class OrdemServico {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Valid
    @ConvertGroup(to = ValidationGroups.ClienteId.class)
    @ManyToOne
    private Cliente cliente;

    @NotBlank
    private String descricao;

    @NotNull
    private BigDecimal preco;

    @Enumerated(EnumType.STRING)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private StatusOrdemServico status;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private OffsetDateTime dataAbertura;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private OffsetDateTime dataFinalizacao;
}
