package br.com.anymarket.sdk.order.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.math.BigDecimal;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DiscountItemMetadata {

    private DiscountType type;
    private BigDecimal discountProductSeller;
    private BigDecimal discountProductMarketplace;

}
