package com.algaworks.osworks.api.exptionhandle;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;
import java.util.List;

@Getter
@Setter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Problema {
    private Integer status;
    private OffsetDateTime dataHora;
    private String titulo;
    private List<MensagemValidacao> errosValidacao;
}
