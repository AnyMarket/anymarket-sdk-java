package br.com.anymarket.sdk.productsync;

public class MarketplaceProductCategory {

    private String name;

    public String getName() {
        return name;
    }

    public static MarketplaceProductCategoryBuilder builder() {
        return new MarketplaceProductCategoryBuilder();
    }

    public MarketplaceProductCategory() {}

    public MarketplaceProductCategory(String name) {
        this.name = name;
    }

    public static class MarketplaceProductCategoryBuilder {

        private String name;

        MarketplaceProductCategoryBuilder() {}

        public MarketplaceProductCategoryBuilder name(String name) {
            this.name = name;
            return this;
        }

        public MarketplaceProductCategory build() {
            return new MarketplaceProductCategory(this.name);
        }

    }

}
