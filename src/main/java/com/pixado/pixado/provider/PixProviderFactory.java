package com.pixado.pixado.provider;

public class PixProviderFactory {
    public static PixProvider getProvider(String banco) {
        if (banco == null) {
            throw new IllegalArgumentException("Banco não informado");
        }

        return switch (banco.trim().toUpperCase()) {
            case "GERENCIANET" -> new GerencianetPixProvider();
            default -> throw new IllegalArgumentException("Banco não suportado: " + banco);
        };
    }
}
