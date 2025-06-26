package br.com.anymarket.sdk.productsync;

import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.util.Objects;

public interface ProductWithMandatoryInfo {

    String getSku();
    default boolean hasSku() {
        return StringUtils.isNotBlank(getSku());
    }

    BigDecimal getPrice();
    default boolean hasPrice() {
        return Objects.nonNull(getPrice());
    }

    BigDecimal getStock();
    default boolean hasStock() {
        return Objects.nonNull(getStock());
    }

}
