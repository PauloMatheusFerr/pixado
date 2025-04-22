package com.pixado.pixado.controller;

import com.pixado.pixado.dto.PagamentoRequestDTO;
import com.pixado.pixado.dto.PagamentoResponseDTO;
import com.pixado.pixado.service.PagamentoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/pagamentos")
public class PagamentoController {

    private final PagamentoService pagamentoService;

    public PagamentoController(PagamentoService pagamentoService) {
        this.pagamentoService = pagamentoService;
    }

    @PostMapping("/gerar")
    public ResponseEntity<PagamentoResponseDTO> gerarPagamento(@RequestBody PagamentoRequestDTO dto) {
        PagamentoResponseDTO response = pagamentoService.gerarPagamento(dto);
        return ResponseEntity.ok(response);
    }
}
