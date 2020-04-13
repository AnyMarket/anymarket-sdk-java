package br.com.anymarket.sdk.order.filters;

import br.com.anymarket.sdk.order.dto.NewOrderStatus;
import br.com.anymarket.sdk.order.dto.OrderStatus;

/**
 *
 */
public class OrderStatusFilter extends OrderFilter {

    public OrderStatusFilter(OrderStatus status) {
        super(status.toString());
    }

    public OrderStatusFilter(NewOrderStatus status) {
        super(status.toString());
    }

    @Override
    public String getKey() {
        return "status";
    }
}
