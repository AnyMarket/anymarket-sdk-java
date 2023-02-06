package br.com.anymarket.sdk.order.filters;

public class CreatedAfterFilter extends OrderFilter {
    public CreatedAfterFilter(String value) {
        super(value);
    }

    @Override
    public String getKey() {
        return "createdAfter";
    }
}
