package com.pixado.pixado.provider;

public class PixProviderFactory {
    public static PixProvider getProvider(BancoProvider banco) {
        return switch (banco.name()) {
            case "GERENCIANET" -> new GerencianetPixProvider();
            default -> throw new IllegalArgumentException("Banco não suportado: " + banco);
        };
    }
}
