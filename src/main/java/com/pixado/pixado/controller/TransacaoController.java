package com.pixado.pixado.controller;
import com.pixado.pixado.dto.TransacaoRequestDTO;
import com.pixado.pixado.dto.TransacaoResponseDTO;
import com.pixado.pixado.service.TransacaoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api")
@CrossOrigin
@Tag(name = "Transações PIX", description = "Endpoints para gerar QR Codes PIX e verificar status de pagamentos")
public class TransacaoController {

    @Autowired
    private TransacaoService service;

    @PostMapping("/qrcode")
    @Operation(
        summary = "Gerar QR Code PIX", 
        description = "Cria uma nova transação PIX e gera o QR Code correspondente para pagamento"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200", 
            description = "QR Code gerado com sucesso",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = TransacaoResponseDTO.class)
            )
        ),
        @ApiResponse(
            responseCode = "400", 
            description = "Dados inválidos ou usuário não encontrado",
            content = @Content
        ),
        @ApiResponse(
            responseCode = "500", 
            description = "Erro ao gerar QR Code",
            content = @Content
        )
    })
    public TransacaoResponseDTO gerarQrCode(@RequestBody TransacaoRequestDTO dto) {
        return service.gerarTransacao(dto);
    }

    @GetMapping("/status/{idTransacao}")
    @Operation(
        summary = "Verificar status da transação", 
        description = "Consulta o status atual de uma transação PIX pelo seu ID"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200", 
            description = "Status da transação retornado com sucesso",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = TransacaoResponseDTO.class)
            )
        ),
        @ApiResponse(
            responseCode = "404", 
            description = "Transação não encontrada",
            content = @Content
        ),
        @ApiResponse(
            responseCode = "500", 
            description = "Erro ao verificar status",
            content = @Content
        )
    })
    public TransacaoResponseDTO verificarStatus(
        @Parameter(description = "ID único da transação", example = "987e6543-e21b-45d3-b789-426614174111")
        @PathVariable UUID idTransacao
    ) {
        return service.verificarStatus(idTransacao);
    }
}
