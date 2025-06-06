package br.com.anymarket.sdk.productsync;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MarketplaceProductDimensions {

    private BigDecimal weight = BigDecimal.ZERO;
    private BigDecimal height = BigDecimal.ZERO;
    private BigDecimal width = BigDecimal.ZERO;
    private BigDecimal length = BigDecimal.ZERO;

}
