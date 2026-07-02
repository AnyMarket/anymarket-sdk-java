package br.com.anymarket.sdk.order.dto.packages;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class OrderPackageItem {

    @JsonProperty("packageItemId")
    private UUID packageItemId;
    @JsonProperty("sku")
    private String sku;
    @JsonProperty("quantity")
    private BigDecimal quantity;
    @JsonProperty("salesOrderItemId")
    private Long salesOrderItemId;
    @JsonProperty("skuInMarketplace")
    private String skuInMarketplace;
    @JsonProperty("idInMarketplace")
    private String idInMarketplace;
}
