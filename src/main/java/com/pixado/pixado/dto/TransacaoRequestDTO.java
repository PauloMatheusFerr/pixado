package com.pixado.pixado.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;
import java.util.UUID;

@Schema(description = "Dados para criar uma transação PIX e gerar QR Code")
public class TransacaoRequestDTO {
    
    @Schema(description = "ID do usuário que está gerando a cobrança", example = "123e4567-e89b-12d3-a456-426614174000", required = true)
    public UUID idUsuario;
    
    @Schema(description = "Nome do cliente que irá pagar", example = "Maria Oliveira", required = true)
    public String nomeCliente;
    
    @Schema(description = "Chave PIX de destino", example = "destino@email.com", required = true)
    public String chavePix;
    
    @Schema(description = "Descrição do pagamento", example = "Pagamento de serviço de consultoria", required = true)
    public String descricao;
    
    @Schema(description = "Valor da transação em reais", example = "150.00", required = true)
    public BigDecimal valor;
}
