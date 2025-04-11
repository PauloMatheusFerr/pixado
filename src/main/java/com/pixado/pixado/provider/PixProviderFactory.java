package com.pixado.pixado.provider;

public class PixProviderFactory {
    public static PixProvider getProvider(String banco) {
        return switch (banco.toUpperCase()) {
            case "GERENCIANET" -> new GerencianetPixProvider();
            default -> throw new IllegalArgumentException("Banco não suportado: " + banco);
        };
    }
}
