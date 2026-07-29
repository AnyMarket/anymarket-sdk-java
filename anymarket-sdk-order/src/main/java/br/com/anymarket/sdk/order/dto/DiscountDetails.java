package br.com.anymarket.sdk.order.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class DiscountDetails {

    private DiscountType type;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private BigDecimal discountOrderSeller;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private BigDecimal discountOrderMarketplace;

}
