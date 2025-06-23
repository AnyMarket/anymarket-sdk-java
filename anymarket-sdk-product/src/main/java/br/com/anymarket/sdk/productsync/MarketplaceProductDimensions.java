package br.com.anymarket.sdk.productsync;

import java.math.BigDecimal;

public class MarketplaceProductDimensions {

    private BigDecimal weight = BigDecimal.ZERO;
    private BigDecimal height = BigDecimal.ZERO;
    private BigDecimal width = BigDecimal.ZERO;
    private BigDecimal length = BigDecimal.ZERO;

    public MarketplaceProductDimensions() {}

    public MarketplaceProductDimensions(
        BigDecimal weight,
        BigDecimal height,
        BigDecimal width,
        BigDecimal length
    ) {
        this.weight = weight;
        this.height = height;
        this.width = width;
        this.length = length;
    }

    public BigDecimal getWeight() {
        return weight;
    }

    public BigDecimal getHeight() {
        return height;
    }

    public BigDecimal getWidth() {
        return width;
    }

    public BigDecimal getLength() {
        return length;
    }

    public static MarketplaceProductDimensionsBuilder builder() {
        return new MarketplaceProductDimensionsBuilder();
    }

    public static class MarketplaceProductDimensionsBuilder {

        private BigDecimal weight;
        private BigDecimal height;
        private BigDecimal width;
        private BigDecimal length;

        MarketplaceProductDimensionsBuilder() {}

        public MarketplaceProductDimensionsBuilder weight(BigDecimal weight) {
            this.weight = weight;
            return this;
        }

        public MarketplaceProductDimensionsBuilder height(BigDecimal height) {
            this.height = height;
            return this;
        }

        public MarketplaceProductDimensionsBuilder width(BigDecimal width) {
            this.width = width;
            return this;
        }

        public MarketplaceProductDimensionsBuilder length(BigDecimal length) {
            this.length = length;
            return this;
        }

        public MarketplaceProductDimensions build() {
            return new MarketplaceProductDimensions(this.weight, this.height, this.width, this.length);
        }

    }

}
