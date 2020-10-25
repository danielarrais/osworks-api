package com.algaworks.osworks.domain.service;

import com.algaworks.osworks.domain.exception.NegocioException;
import com.algaworks.osworks.domain.model.Cliente;
import com.algaworks.osworks.domain.repository.ClienteRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CadastroClienteService {
    private final ClienteRepository clienteRepository;

    public CadastroClienteService(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    public Cliente salvar(Cliente cliente) {
        if (existeClienteComEmailIgual(cliente)) {
            throw new NegocioException("Já existe um usuário cadastrado com este email");
        }

        return clienteRepository.save(cliente);
    }

    private Boolean existeClienteComEmailIgual(Cliente cliente) {
        var id = Optional.ofNullable(cliente.getId()).orElse(0L);
        return clienteRepository.existsByEmailAndIdNot(cliente.getEmail(), id);
    }
}
