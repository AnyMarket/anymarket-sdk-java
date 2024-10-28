package br.com.anymarket.sdk.order.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class OrderReturnItemResource {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("orderItemId")
    private Long orderItemId;

    @JsonProperty("title")
    private String title;

    @JsonProperty("skuId")
    private Long skuId;

    @JsonProperty("marketplaceSkuId")
    private String marketplaceSkuId;

    @JsonProperty("quantity")
    private BigDecimal quantity;
}
