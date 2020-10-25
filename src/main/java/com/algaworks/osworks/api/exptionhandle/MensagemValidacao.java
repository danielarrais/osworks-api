package com.algaworks.osworks.api.exptionhandle;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class MensagemValidacao {
    private String campo;
    private String mensagem;
}
