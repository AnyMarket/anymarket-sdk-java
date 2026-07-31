package br.com.anymarket.sdk.order.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class DiscountMetadata {

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    List<DiscountType> type;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    List<DiscountDetails> discountDetails = new ArrayList<>();
}
