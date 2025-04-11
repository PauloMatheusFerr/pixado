package com.pixado.pixado.controller;
import com.pixado.pixado.dto.TransacaoRequestDTO;
import com.pixado.pixado.dto.TransacaoResponseDTO;
import com.pixado.pixado.service.TransacaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class TransacaoController {

    @Autowired
    private TransacaoService service;

    @PostMapping("/qrcode")
    public TransacaoResponseDTO gerarQrCode(@RequestBody TransacaoRequestDTO dto) {
        return service.gerarTransacao(dto);
    }

    @GetMapping("/status/{idTransacao}")
    public TransacaoResponseDTO verificarStatus(@PathVariable UUID idTransacao) {
        return service.verificarStatus(idTransacao);
    }
}
