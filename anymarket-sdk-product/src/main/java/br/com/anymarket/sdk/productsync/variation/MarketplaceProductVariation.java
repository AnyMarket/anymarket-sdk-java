package br.com.anymarket.sdk.productsync.variation;

import br.com.anymarket.sdk.productsync.ProductWithMandatoryInfo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MarketplaceProductVariation implements ProductWithMandatoryInfo {

    private String sku;
    private BigDecimal price;
    private BigDecimal stock;
    @lombok.Builder.Default
    private List<MarketplaceProductVariationAttribute> attributes = new ArrayList<>();

}
