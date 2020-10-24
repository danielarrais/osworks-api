package com.algaworks.osworks.api.controller;

import com.algaworks.osworks.domain.model.Cliente;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
public class ClienteController {

    @GetMapping("/clientes")
    public List<Cliente> listar() {
        var cliente1 = Cliente.builder()
                .nome("Daniel Arrais")
                .email("danielarrais@gmail.com")
                .telefone("(63) 981529639")
                .id(1L).build();

        var cliente2 = Cliente.builder()
                .nome("Lucas Arrais")
                .email("lucasarrais@gmail.com")
                .telefone("(63) 981234234")
                .id(2L).build();

        return Arrays.asList(cliente1, cliente2);
    }
}
