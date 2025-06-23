package br.com.anymarket.sdk.productsync.variation;

public class MarketplaceProductVariationAttribute {

    private String nameId;
    private String name;
    private String valueId;
    private String value;

    public MarketplaceProductVariationAttribute() {}

    public MarketplaceProductVariationAttribute(String nameId, String name, String valueId, String value) {
        this.nameId = nameId;
        this.name = name;
        this.valueId = valueId;
        this.value = value;
    }

    public String getNameId() {
        return nameId;
    }

    public void setNameId(String nameId) {
        this.nameId = nameId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValueId() {
        return valueId;
    }

    public void setValueId(String valueId) {
        this.valueId = valueId;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public static MarketplaceProductVariationAttributeBuilder builder() {
        return new MarketplaceProductVariationAttributeBuilder();
    }

    public static class MarketplaceProductVariationAttributeBuilder {

        private String nameId;
        private String name;
        private String valueId;
        private String value;

        MarketplaceProductVariationAttributeBuilder() {}

        public MarketplaceProductVariationAttributeBuilder nameId(String nameId) {
            this.nameId = nameId;
            return this;
        }

        public MarketplaceProductVariationAttributeBuilder name(String name) {
            this.name = name;
            return this;
        }

        public MarketplaceProductVariationAttributeBuilder valueId(String valueId) {
            this.valueId = valueId;
            return this;
        }

        public MarketplaceProductVariationAttributeBuilder value(String value) {
            this.value = value;
            return this;
        }

        public MarketplaceProductVariationAttribute build() {
            return new MarketplaceProductVariationAttribute(this.nameId, this.name, this.valueId, this.value);
        }

    }

}
