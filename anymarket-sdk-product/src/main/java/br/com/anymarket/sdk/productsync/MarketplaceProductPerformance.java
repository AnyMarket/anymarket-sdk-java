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
public class MarketplaceProductPerformance {

    private Long soldCount;
    private BigDecimal quality;

}
