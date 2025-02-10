package br.com.anymarket.sdk.order.dto;

import lombok.Getter;

@Getter
public enum OrderPackageStatus {

    PENDING("Pendente de envio ao marketplace"),
    WAITING("Aguardando confirmação do marketplace"),
    CONFIRMED("Marketplace já confirmou split/trancking code"),
    ERROR("Marketplace retornou erro para split ou pacotes");

    private final String description;

    OrderPackageStatus(String desc) {
        this.description = desc;
    }
}
