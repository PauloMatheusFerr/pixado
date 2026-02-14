package com.pixado.pixado.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Dados para cadastro de um novo usuário")
public class UsuarioRequestDTO {
    
    @Schema(description = "Nome completo do usuário", example = "João da Silva", required = true)
    public String nome;
    
    @Schema(description = "Chave PIX do usuário (e-mail, telefone, CPF ou chave aleatória)", example = "joao@email.com", required = true)
    public String chavePix;
    
    @Schema(description = "Nome do banco/provedor de pagamento", example = "GERENCIANET", required = true)
    public String banco;
    
    @Schema(description = "ID do cliente fornecido pelo provedor", example = "Client_Id_abc123", required = true)
    public String clientId;
    
    @Schema(description = "Chave secreta do cliente fornecida pelo provedor", example = "Client_Secret_xyz789", required = true)
    public String clientSecret;
    
    @Schema(description = "Caminho do arquivo do certificado digital (.p12)", example = "/certificados/producao.p12", required = true)
    public String caminhoCertificado;
}