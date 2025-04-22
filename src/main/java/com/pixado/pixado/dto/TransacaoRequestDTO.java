package com.pixado.pixado.dto;

import java.math.BigDecimal;
import java.util.UUID;

public class TransacaoRequestDTO {
    public UUID idUsuario;
    public String nomeCliente;
    public String chavePix;
    public String descricao;
    public BigDecimal valor;

}
