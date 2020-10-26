package com.algaworks.osworks.api.controller;

import com.algaworks.osworks.api.dto.OrdemServicoDTO;
import com.algaworks.osworks.api.dto.OrdemServicoInputDTO;
import com.algaworks.osworks.domain.model.OrdemServico;
import com.algaworks.osworks.domain.repository.OrdemServicoRepository;
import com.algaworks.osworks.domain.service.GestaoOrdemServicoService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/ordens-servico")
public class OrdemServicoController {

    private final OrdemServicoRepository ordemServicoRepository;
    private final GestaoOrdemServicoService gestaoOrdemServicoService;
    private final ModelMapper modelMapper;

    public OrdemServicoController(OrdemServicoRepository ordemServicoRepository, GestaoOrdemServicoService gestaoOrdemServicoService, ModelMapper modelMapper) {
        this.ordemServicoRepository = ordemServicoRepository;
        this.gestaoOrdemServicoService = gestaoOrdemServicoService;
        this.modelMapper = modelMapper;
    }

    // Exemplo de rota indepotentes (que não altera o estado)
    @GetMapping
    public List<OrdemServicoDTO> listar() {
        return toListDTO(ordemServicoRepository.findAll());
    }

    @GetMapping("/{ordemServicoId}")
    public ResponseEntity<OrdemServicoDTO> buscar(@PathVariable Long ordemServicoId) {
        Optional<OrdemServico> optionalOrdemServico = ordemServicoRepository.findById(ordemServicoId);

        if (optionalOrdemServico.isPresent()) {
            OrdemServicoDTO ordemServicoDTO = toDTO(optionalOrdemServico.get());

            return ResponseEntity.ok(ordemServicoDTO);
        }

        return ResponseEntity.notFound().build();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public OrdemServicoDTO criar(@Valid @RequestBody OrdemServicoInputDTO ordemServicoInputDTO) {
        OrdemServico ordemServico = toModel(ordemServicoInputDTO);
        ordemServico = gestaoOrdemServicoService.criar(ordemServico);
        return toDTO(ordemServico);
    }

    @PutMapping("/{ordemServicoId}")
    public ResponseEntity<OrdemServicoDTO> atualizar(@Valid @PathVariable Long ordemServicoId, @RequestBody OrdemServicoInputDTO ordemServicoInputDTO) {
        OrdemServico ordemServico = toModel(ordemServicoInputDTO);

        if (!ordemServicoRepository.existsById(ordemServicoId)) {
            return ResponseEntity.notFound().build();
        }

        ordemServico.setId(ordemServicoId);
        ordemServico = gestaoOrdemServicoService.criar(ordemServico);

        return ResponseEntity.ok(toDTO(ordemServico));
    }

    @DeleteMapping("/{ordemServicoId}")
    public ResponseEntity<Void> adicionar(@PathVariable Long ordemServicoId) {
        if (!ordemServicoRepository.existsById(ordemServicoId)) {
            return ResponseEntity.notFound().build();
        }

        ordemServicoRepository.deleteById(ordemServicoId);
        return ResponseEntity.noContent().build();
    }

    private OrdemServicoDTO toDTO(OrdemServico ordemServico){
        return modelMapper.map(ordemServico, OrdemServicoDTO.class);
    }

    private OrdemServico toModel(OrdemServicoInputDTO ordemServico){
        return modelMapper.map(ordemServico, OrdemServico.class);
    }

    private List<OrdemServicoDTO> toListDTO(List<OrdemServico> ordensServico){
        return ordensServico.stream().map(this::toDTO).collect(Collectors.toList());
    }
}
