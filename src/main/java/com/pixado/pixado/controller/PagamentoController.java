package com.pixado.pixado.controller;

import com.pixado.pixado.dto.PagamentoRequestDTO;
import com.pixado.pixado.dto.PagamentoResponseDTO;
import com.pixado.pixado.dto.PagamentoResumoDTO;
import com.pixado.pixado.dto.PagamentoStatusDTO;
import com.pixado.pixado.service.PagamentoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

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
    @GetMapping("/status/{txid}")
    public ResponseEntity<PagamentoStatusDTO> verificarStatusPagamento(
            @PathVariable String txid,
            @RequestParam UUID usuarioId
    ) {
        PagamentoStatusDTO dto = pagamentoService.verificarPagamentoDetalhado(txid, usuarioId);
        return ResponseEntity.ok(dto);
    }
    @GetMapping("/todos")
    public ResponseEntity<List<PagamentoResumoDTO>> listarPagamentosPorUsuario(@RequestParam UUID usuarioId) {
        List<PagamentoResumoDTO> lista = pagamentoService.listarPagamentosPorUsuario(usuarioId);
        return ResponseEntity.ok(lista);
    }


}
