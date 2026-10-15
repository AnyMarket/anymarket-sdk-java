package br.com.anymarket.sdk.freight;

import br.com.anymarket.sdk.freight.dto.FreightProduct;
import br.com.anymarket.sdk.freight.dto.FreightResponse;

import java.math.BigDecimal;
import java.math.RoundingMode;

public final class FreightHelper {

    private static BigDecimal BIG_100 = BigDecimal.valueOf(100);
    private static final int SCALE_5 = 5;
    private static final int SCALE_2 = 2;

    private FreightHelper() {
    }

    public static BigDecimal getTotalWeight(FreightResponse response) {
        BigDecimal total = BigDecimal.ZERO;
        for (FreightProduct p: response.getProducts()) {
            if (p.getDimensions() != null) {
                total = total.add(p.getDimensions().getWeight().multiply(p.getAmount()));
            }
        }
        return total;
    }

    public static BigDecimal getPriceRatingByWeight(BigDecimal totalWeight, BigDecimal totalPrice, FreightProduct product) {
        BigDecimal percent = product.getDimensions().getWeight()
                .divide(totalWeight, SCALE_5, RoundingMode.HALF_UP).multiply(BIG_100);
        BigDecimal priceOfTotal = totalPrice.multiply(percent)
                .divide(BIG_100, SCALE_5, RoundingMode.HALF_UP);

        return priceOfTotal
                .multiply(product.getAmount())
                .divide(BigDecimal.ONE, SCALE_2, RoundingMode.HALF_UP);
    }
}
