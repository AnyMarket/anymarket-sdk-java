package br.com.anymarket.sdk.order.dto;

/**
 * Created by gyowannyqueiroz on 5/16/16.
 */
public enum DeliveryStatus {
    UNKNOWN("Desconhecido", "1"),
    IN_TRANSIT("Em trânsito", null),
    DELIVERED("Entregue", null),
    DELAYED("Atrasado", null),
    DELIVERED_LATE("Entregue atrasado", null),
    PACKING("Em preparação", "2"),
    HOLD_FOR_PICKUP("Aguardando coleta", "3"),
    DELAYED_PICKUP("Coleta atrasada", "4"),
    HOLD_FOR_SHIPPED("Aguardando envio", "5"),
    DELAYED_SHIPPING("Envio atrasado", "6"),
    SHIPPED("Enviado", "7"),
    SHIP_CONFIRMED("Ship Confirmed","8"),
    QUARANTINE("Quarentena","9");

    private final String description;
    private final String code;

    private DeliveryStatus(String desc, String code) {
        description = desc;
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public String getCode() {
        return this.code;
    }

}