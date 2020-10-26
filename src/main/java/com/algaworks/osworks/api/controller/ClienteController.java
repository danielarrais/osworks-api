package com.algaworks.osworks.api.controller;

import com.algaworks.osworks.api.dto.ClienteDTO;
import com.algaworks.osworks.domain.model.Cliente;
import com.algaworks.osworks.domain.repository.ClienteRepository;
import com.algaworks.osworks.domain.service.CadastroClienteService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/clientes")
public class ClienteController {

    private final ClienteRepository clienteRepository;
    private final CadastroClienteService cadastroClienteService;
    private final ModelMapper modelMapper;

    public ClienteController(ClienteRepository clienteRepository, CadastroClienteService cadastroClienteService, ModelMapper modelMapper) {
        this.clienteRepository = clienteRepository;
        this.cadastroClienteService = cadastroClienteService;
        this.modelMapper = modelMapper;
    }

    // Exemplo de rota indepotentes (que não altera o estado)
    @GetMapping
    public List<ClienteDTO> listar() {
        return toListDTO(clienteRepository.findAll());
    }

    @GetMapping("/{clienteId}")
    public ResponseEntity<ClienteDTO> buscar(@PathVariable Long clienteId) {
        Optional<Cliente> optionalCliente = clienteRepository.findById(clienteId);

        if (optionalCliente.isPresent()) {
            ClienteDTO ordemServicoDTO = toDTO(optionalCliente.get());

            return ResponseEntity.ok(ordemServicoDTO);
        }

        return ResponseEntity.notFound().build();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ClienteDTO adicionar(@Valid @RequestBody ClienteDTO clienteDTO) {
        Cliente cliente = toModel(clienteDTO);
        cliente = cadastroClienteService.salvar(cliente);

        return toDTO(cliente);
    }

    @PutMapping("/{clienteId}")
    public ResponseEntity<ClienteDTO> atualizar(@Valid @PathVariable Long clienteId, @RequestBody ClienteDTO clienteDTO) {
        Cliente cliente = toModel(clienteDTO);

        if (!clienteRepository.existsById(clienteId)) {
            return ResponseEntity.notFound().build();
        }

        cliente.setId(clienteId);
        cliente = cadastroClienteService.salvar(cliente);

        return ResponseEntity.ok(toDTO(cliente));
    }


    @DeleteMapping("/{clienteId}")
    public ResponseEntity<Void> adicionar(@PathVariable Long clienteId) {
        if (!clienteRepository.existsById(clienteId)) {
            return ResponseEntity.notFound().build();
        }

        clienteRepository.deleteById(clienteId);
        return ResponseEntity.noContent().build();
    }

    private ClienteDTO toDTO(Cliente ordemServico) {
        return modelMapper.map(ordemServico, ClienteDTO.class);
    }

    private Cliente toModel(ClienteDTO ordemServico) {
        return modelMapper.map(ordemServico, Cliente.class);
    }

    private List<ClienteDTO> toListDTO(List<Cliente> ordensServico) {
        return ordensServico.stream().map(this::toDTO).collect(Collectors.toList());
    }

}
