package br.com.anymarket.sdk.productsync.variation;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MarketplaceProductVariationAttribute {

    private String nameId;
    private String name;
    private String valueId;
    private String value;

}
