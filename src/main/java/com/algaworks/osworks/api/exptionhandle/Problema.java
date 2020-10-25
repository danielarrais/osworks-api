package com.algaworks.osworks.api.exptionhandle;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Problema {
    private Integer status;
    private LocalDateTime dataHora;
    private String titulo;
    private List<MensagemValidacao> errosValidacao;
}
