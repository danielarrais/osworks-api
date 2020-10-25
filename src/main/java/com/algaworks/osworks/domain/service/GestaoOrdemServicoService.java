package com.algaworks.osworks.domain.service;

import com.algaworks.osworks.domain.exception.NegocioException;
import com.algaworks.osworks.domain.model.Cliente;
import com.algaworks.osworks.domain.model.OrdemServico;
import com.algaworks.osworks.domain.model.StatusOrdemServico;
import com.algaworks.osworks.domain.repository.ClienteRepository;
import com.algaworks.osworks.domain.repository.OrdemServicoRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class GestaoOrdemServicoService {
    private final OrdemServicoRepository ordemServicoRepository;
    private final ClienteRepository clienteRepository;

    public GestaoOrdemServicoService(OrdemServicoRepository ordemServicoRepository, ClienteRepository clienteRepository) {
        this.ordemServicoRepository = ordemServicoRepository;
        this.clienteRepository = clienteRepository;
    }

    public OrdemServico criar(OrdemServico ordemServico) {
        verificarSeClienteExiste(ordemServico.getCliente());

        ordemServico.setStatus(StatusOrdemServico.ABERTA);
        ordemServico.setDataAbertura(LocalDateTime.now());

        recarregarCliente(ordemServico);

        return ordemServicoRepository.save(ordemServico);
    }

    private void verificarSeClienteExiste(Cliente cliente) {
        if (!clienteRepository.existsById(cliente.getId())) {
            throw new NegocioException("Cliente não encontrado!");
        }
    }

    private void recarregarCliente(OrdemServico ordemServico) {
        var cliente = clienteRepository.findById(ordemServico.getCliente().getId());
        ordemServico.setCliente(cliente.orElse(null));
    }
}
