package br.com.anymarket.sdk.order.filters;

public class CreatedBeforeFilter extends OrderFilter {
    public CreatedBeforeFilter(String value) {
        super(value);
    }

    @Override
    public String getKey() {
        return "createdBefore";
    }
}
