package br.com.anymarket.sdk.order.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class OrderItemCustomizationResource {

    @JsonProperty("item_customization_type")
    private String customizationType;

    @JsonProperty("item_customization_quantity")
    private BigDecimal customizationQuantity;

    @JsonProperty("item_customization_price")
    private BigDecimal customizationPrice;

    @JsonProperty("item_customization_value")
    private String customizationValue;

}
