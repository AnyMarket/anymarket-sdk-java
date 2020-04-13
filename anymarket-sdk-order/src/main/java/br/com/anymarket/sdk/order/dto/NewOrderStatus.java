package br.com.anymarket.sdk.order.dto;

import java.util.Arrays;
import java.util.List;

import static java.lang.String.format;

public enum NewOrderStatus {

    PENDING("Pendente", OrderStatus.PENDING),
    DELIVERY_ISSUE("Problema no Envio", OrderStatus.DELIVERY_ISSUE),
    PAID("Pago", OrderStatus.PAID_WAITING_SHIP, PENDING),
    INVOICED("Faturado", OrderStatus.INVOICED, PENDING, PAID),
    SHIPPED("Enviado", OrderStatus.PAID_WAITING_DELIVERY, PENDING, PAID, INVOICED),
    DELIVERED("Entregue", OrderStatus.CONCLUDED, PENDING, PAID, INVOICED, SHIPPED),
    CANCELED("Cancelado", OrderStatus.CANCELED, PENDING, PAID, INVOICED, SHIPPED, DELIVERED);

    private static final String INVALID_STATUS = "%s não é um status de pedido válido.";

    private final List<NewOrderStatus> incomes;
    private final String description;
    private OrderStatus oldValue;

    NewOrderStatus(String description, OrderStatus oldValue, NewOrderStatus... incomes) {
        this.description = description;
        this.oldValue = oldValue;
        this.incomes = Arrays.asList(incomes);
    }

    public static NewOrderStatus fromOldValue(OrderStatus oldValue) {
        for (NewOrderStatus status : NewOrderStatus.values()) {
            if (status.getOldValue().equals(oldValue)) {
                return status;
            }
        }
        throw new IllegalArgumentException(format(INVALID_STATUS, oldValue.toString()));
    }

    public String getDescription() {
        return description;
    }

    public OrderStatus getOldValue() {
        return oldValue;
    }

    public Boolean acceptTransitionFrom(NewOrderStatus status) {
        return equals(status) || incomes.contains(status);
    }

    public Boolean isPendingOrPaid() {
        return this.equals(PENDING) || this.equals(PAID);
    }

    public Boolean isConfirmed() {
        return this.equals(INVOICED) || this.equals(SHIPPED) || this.equals(DELIVERED);
    }

}
