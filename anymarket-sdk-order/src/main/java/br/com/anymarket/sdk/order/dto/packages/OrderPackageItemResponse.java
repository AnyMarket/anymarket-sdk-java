package br.com.anymarket.sdk.order.dto.packages;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class OrderPackageItemResponse {

    private String sku;
    private BigDecimal quantity;
    private Long salesOrderItemId;
    private String skuInMarketplace;
    private String idInMarketplace;
}
