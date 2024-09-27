package br.com.anymarket.sdk.order.filters;

public class OrderTransmissionStatusFilter extends OrderFilter {

    public OrderTransmissionStatusFilter(String value) {
        super(value);
    }

    @Override
    public String getKey() {
        return "transmissionStatus";
    }
}
