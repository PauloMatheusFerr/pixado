package com.pixado.pixado.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Schema(description = "Resposta com os dados da transação PIX")
public class TransacaoResponseDTO {
    
    @Schema(description = "ID único da transação", example = "987e6543-e21b-45d3-b789-426614174111")
    public UUID idTransacao;
    
    @Schema(description = "Imagem do QR Code em base64", example = "iVBORw0KGgoAAAANSUhEUgAAAAUA...")
    public String qrCode;
    
    @Schema(description = "Payload do PIX Copia e Cola", example = "00020126360014br.gov.bcb.pix...")
    public String payload;
    
    @Schema(description = "Status da transação", example = "PENDENTE", allowableValues = {"PENDENTE", "PAGO", "EXPIRADO", "CANCELADO"})
    public String status;
    
    @Schema(description = "Valor da transação em reais", example = "150.00")
    public BigDecimal valor;
    
    @Schema(description = "Nome do cliente pagador", example = "Maria Oliveira")
    public String nomeCliente;
    
    @Schema(description = "Data e hora do pagamento (null se ainda não pago)", example = "2026-02-14T14:30:00")
    public LocalDateTime dataPagamento;
}
