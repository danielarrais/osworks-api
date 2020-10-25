package com.algaworks.osworks.api.controller;

import com.algaworks.osworks.domain.model.OrdemServico;
import com.algaworks.osworks.domain.repository.OrdemServicoRepository;
import com.algaworks.osworks.domain.service.GestaoOrdemServicoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/ordens-servico")
public class OrdemServicoController {

    private final OrdemServicoRepository ordemServicoRepository;
    private final GestaoOrdemServicoService gestaoOrdemServicoService;

    public OrdemServicoController(OrdemServicoRepository ordemServicoRepository, GestaoOrdemServicoService gestaoOrdemServicoService) {
        this.ordemServicoRepository = ordemServicoRepository;
        this.gestaoOrdemServicoService = gestaoOrdemServicoService;
    }

    // Exemplo de rota indepotentes (que não altera o estado)
    @GetMapping
    public List<OrdemServico> listar() {
        return ordemServicoRepository.findAll();
    }

    @GetMapping("/{ordemServicoId}")
    public ResponseEntity<OrdemServico> buscar(@PathVariable Long ordemServicoId) {
        Optional<OrdemServico> optionalOrdemServico = ordemServicoRepository.findById(ordemServicoId);

        return optionalOrdemServico
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public OrdemServico criar(@Valid @RequestBody OrdemServico ordemServico) {
        return gestaoOrdemServicoService.criar(ordemServico);
    }

    @PutMapping("/{ordemServicoId}")
    public ResponseEntity<OrdemServico> atualizar(@Valid @PathVariable Long ordemServicoId, @RequestBody OrdemServico ordemServico) {
        if (!ordemServicoRepository.existsById(ordemServicoId)) {
            return ResponseEntity.notFound().build();
        }

        ordemServico.setId(ordemServicoId);
        ordemServico = gestaoOrdemServicoService.criar(ordemServico);

        return ResponseEntity.ok(ordemServico);
    }



    @DeleteMapping("/{ordemServicoId}")
    public ResponseEntity<Void> adicionar(@PathVariable Long ordemServicoId) {
        if (!ordemServicoRepository.existsById(ordemServicoId)) {
            return ResponseEntity.notFound().build();
        }

        ordemServicoRepository.deleteById(ordemServicoId);
        return ResponseEntity.noContent().build();
    }
}
