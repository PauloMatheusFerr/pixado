package com.pixado.pixado.dto;

import com.pixado.pixado.provider.BancoProvider;

public class UsuarioRequestDTO {
    public String nome;
    public String chavePix;
    public BancoProvider banco;
    public String clientId;
    public String clientSecret;
    public String caminhoCertificado;
}