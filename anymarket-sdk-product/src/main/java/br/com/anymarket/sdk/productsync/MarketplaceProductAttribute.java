package br.com.anymarket.sdk.productsync;

import br.com.anymarket.sdk.product.dto.Characteristics;
import lombok.Getter;

@Getter
public class MarketplaceProductAttribute extends Characteristics {

    private final String id;

    @lombok.Builder
    public MarketplaceProductAttribute(String id, String name, String value) {
        this.id = id;
        super.setName(name);
        super.setValue(value);
    }

}
