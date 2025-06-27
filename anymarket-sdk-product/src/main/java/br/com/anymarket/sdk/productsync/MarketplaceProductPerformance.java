package br.com.anymarket.sdk.productsync;

import java.math.BigDecimal;

public class MarketplaceProductPerformance {

    private Long soldCount;
    private BigDecimal quality;

    public MarketplaceProductPerformance() {}

    public MarketplaceProductPerformance(Long soldCount, BigDecimal quality) {
        this.soldCount = soldCount;
        this.quality = quality;
    }

    public BigDecimal getQuality() {
        return quality;
    }

    public Long getSoldCount() {
        return soldCount;
    }

    public static MarketplaceProductPerformanceBuilder builder() {
        return new MarketplaceProductPerformanceBuilder();
    }

    public static class MarketplaceProductPerformanceBuilder {

        private Long soldCount;
        private BigDecimal quality;

        MarketplaceProductPerformanceBuilder() {}

        public MarketplaceProductPerformanceBuilder soldCount(Long soldCount) {
            this.soldCount = soldCount;
            return this;
        }

        public MarketplaceProductPerformanceBuilder quality(BigDecimal quality) {
            this.quality = quality;
            return this;
        }

        public MarketplaceProductPerformance build() {
            return new MarketplaceProductPerformance(this.soldCount, this.quality);
        }

    }

}
