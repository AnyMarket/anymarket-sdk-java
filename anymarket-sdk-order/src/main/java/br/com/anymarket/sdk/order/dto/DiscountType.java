package br.com.anymarket.sdk.order.dto;

import com.fasterxml.jackson.annotation.JsonEnumDefaultValue;

public enum DiscountType {

    COUPON,
    FREE_ITEM,
    COIN,
    PIX_DISCOUNT,
    LOYALTY,
    BUNDLE,
    @JsonEnumDefaultValue
    PROMOTIONAL

}
