package com.pixado.pixado.dto;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public class TransacaoResponseDTO {
    public UUID idTransacao;
    public String qrCode;
    public String payload;
    public String status;
    public BigDecimal valor;
    public String nomeCliente;
    public LocalDateTime dataPagamento;
}
