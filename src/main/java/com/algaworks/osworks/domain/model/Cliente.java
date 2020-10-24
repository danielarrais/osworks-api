package com.algaworks.osworks.domain.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class Cliente {
    private Long id;
    private String email;
    private String nome;
    private String telefone;
}
