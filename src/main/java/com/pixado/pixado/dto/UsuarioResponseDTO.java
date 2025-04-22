package com.pixado.pixado.dto;

import com.pixado.pixado.model.Usuario;
import com.pixado.pixado.provider.BancoProvider;

import java.util.UUID;

public class UsuarioResponseDTO {
    public UsuarioResponseDTO(Usuario usuario) {
        this.id = usuario.getId();
        this.nome = usuario.getNome();
        this.banco = usuario.getBanco();
        this.chavePix = usuario.getChavePix();
    }

    public UUID id;
    public String nome;
    public BancoProvider banco;
    public String chavePix;

}

