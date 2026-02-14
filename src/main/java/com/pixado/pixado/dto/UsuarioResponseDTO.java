package com.pixado.pixado.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.UUID;

@Schema(description = "Resposta com os dados do usuário cadastrado")
public class UsuarioResponseDTO {
    
    @Schema(description = "ID único do usuário", example = "123e4567-e89b-12d3-a456-426614174000")
    public UUID id;
    
    @Schema(description = "Nome completo do usuário", example = "João da Silva")
    public String nome;
    
    @Schema(description = "Nome do banco/provedor de pagamento", example = "GERENCIANET")
    public String banco;
    
    @Schema(description = "Chave PIX do usuário", example = "joao@email.com")
    public String chavePix;
}
