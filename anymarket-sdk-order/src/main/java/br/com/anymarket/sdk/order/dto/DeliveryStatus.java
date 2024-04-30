package br.com.anymarket.sdk.order.dto;

/**
 * Created by gyowannyqueiroz on 5/16/16.
 */
public enum DeliveryStatus {
    UNKNOWN("Desconhecido"),
    IN_TRANSIT("Em trânsito"),
    DELIVERED("Entregue"),
    DELAYED("Atrasado"),
    DELIVERED_LATE("Entregue atrasado"),
    PACKING("Em preparação"),
    HOLD_FOR_PICKUP("Aguardando coleta"),
    DELAYED_PICKUP("Coleta atrasada"),
    HOLD_FOR_SHIPPED("Aguardando envio"),
    DELAYED_SHIPPING("Envio atrasado"),
    SHIPPED("Enviado");

    private final String description;

    private DeliveryStatus(String desc) {
        description = desc;
    }

    public String getDescription() {
        return description;
    }
}
