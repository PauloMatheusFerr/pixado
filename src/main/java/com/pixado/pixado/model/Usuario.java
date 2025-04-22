package com.pixado.pixado.model;

import com.pixado.pixado.provider.BancoProvider;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import jakarta.persistence.*;
import java.util.UUID;

@Entity
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private String nome;
    private String chavePix;
    private String clientId;
    private String clientSecret;
    private String caminhoCertificado;

    @Enumerated(EnumType.STRING)
    private BancoProvider banco;

    // Getters e setters

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getChavePix() {
        return chavePix;
    }

    public void setChavePix(String chavePix) {
        this.chavePix = chavePix;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getClientSecret() {
        return clientSecret;
    }

    public void setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
    }

    public String getCaminhoCertificado() {
        return caminhoCertificado;
    }

    public void setCaminhoCertificado(String caminhoCertificado) {
        this.caminhoCertificado = caminhoCertificado;
    }

    public BancoProvider getBanco() {
        return banco;
    }

    public void setBanco(BancoProvider banco) {
        this.banco = banco;
    }
}
