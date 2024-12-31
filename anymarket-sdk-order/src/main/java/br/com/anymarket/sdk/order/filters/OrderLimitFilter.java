package br.com.anymarket.sdk.order.filters;

public class OrderLimitFilter extends OrderFilter{

    public OrderLimitFilter(String value) {
        super(value);
    }

    @Override
    public String getKey() {
        return "limit";
    }
}
