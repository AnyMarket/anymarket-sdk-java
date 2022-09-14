package br.com.anymarket.sdk.order.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class OrderItemCustomizationsResource implements Serializable {

    @JsonProperty("customizationType")
    private String customizationType;

    @JsonProperty("customizationQuantity")
    private BigDecimal customizationQuantity;

    @JsonProperty("customizationPrice")
    private BigDecimal customizationPrice;

    @JsonProperty("customizationValue")
    private String customizationValue;

}
