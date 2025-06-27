package br.com.anymarket.sdk.productsync;

import br.com.anymarket.sdk.product.dto.Characteristics;

public class MarketplaceProductAttribute extends Characteristics {

    private final String id;

    public MarketplaceProductAttribute(String id, String name, String value) {
        this.id = id;
        super.setName(name);
        super.setValue(value);
    }

    public String getId() {
        return id;
    }

    public static MarketplaceProductAttributeBuilder builder() {
        return new MarketplaceProductAttributeBuilder();
    }

    public static class MarketplaceProductAttributeBuilder {

        private String id;
        private String name;
        private String value;

        MarketplaceProductAttributeBuilder() {}

        public MarketplaceProductAttributeBuilder id(String id) {
            this.id = id;
            return this;
        }

        public MarketplaceProductAttributeBuilder name(String name) {
            this.name = name;
            return this;
        }

        public MarketplaceProductAttributeBuilder value(String value) {
            this.value = value;
            return this;
        }

        public MarketplaceProductAttribute build() {
            return new MarketplaceProductAttribute(this.id, this.name, this.value);
        }

    }

}
