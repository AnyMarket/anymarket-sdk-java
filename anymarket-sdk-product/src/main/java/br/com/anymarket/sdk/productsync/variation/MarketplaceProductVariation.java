package br.com.anymarket.sdk.productsync.variation;

import br.com.anymarket.sdk.productsync.ProductWithMandatoryInfo;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static java.util.Collections.emptyList;

public class MarketplaceProductVariation implements ProductWithMandatoryInfo {

    private String sku;
    private BigDecimal price;
    private BigDecimal stock;
    private List<MarketplaceProductVariationAttribute> attributes = new ArrayList<>();
    private String ean;

    public MarketplaceProductVariation() {}

    public MarketplaceProductVariation(
        String sku,
        BigDecimal price,
        BigDecimal stock,
        List<MarketplaceProductVariationAttribute> attributes,
        String ean
    ) {
        this.sku = sku;
        this.price = price;
        this.stock = stock;
        this.attributes = attributes;
        this.ean = ean;
    }

    @Override
    public String getSku() {
        return sku;
    }

    @Override
    public BigDecimal getPrice() {
        return price;
    }

    @Override
    public BigDecimal getStock() {
        return stock;
    }

    public List<MarketplaceProductVariationAttribute> getAttributes() {
        return attributes;
    }

    public static MarketplaceProductVariationBuilder builder() {
        return new MarketplaceProductVariationBuilder();
    }

    public static class MarketplaceProductVariationBuilder {

        private String sku;
        private BigDecimal price;
        private BigDecimal stock;
        private List<MarketplaceProductVariationAttribute> attributes = new ArrayList<>();
        private String ean;

        MarketplaceProductVariationBuilder() {}

        public MarketplaceProductVariationBuilder sku(String sku) {
            this.sku = sku;
            return this;
        }

        public MarketplaceProductVariationBuilder price(BigDecimal price) {
            this.price = price;
            return this;
        }

        public MarketplaceProductVariationBuilder stock(BigDecimal stock) {
            this.stock = stock;
            return this;
        }

        public MarketplaceProductVariationBuilder attributes(List<MarketplaceProductVariationAttribute> attributes) {
            this.attributes = attributes;
            return this;
        }

        public MarketplaceProductVariationBuilder ean(String ean) {
            this.ean = ean;
            return this;
        }

        public MarketplaceProductVariation build() {
            return new MarketplaceProductVariation(
                this.sku,
                this.price,
                this.stock,
                getValues(this.attributes),
                this.ean
            );
        }

        private <T> List<T> getValues(List<T> attributes) {
            return hasValues(attributes) ? attributes : emptyList();
        }

        private boolean hasValues(List<?> valueList) {
            return Objects.nonNull(valueList) && !valueList.isEmpty();
        }

    }

}
