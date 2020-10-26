package com.algaworks.osworks.api.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class ClienteInputIdDTO {
    @NotNull
    private Long id;
}
