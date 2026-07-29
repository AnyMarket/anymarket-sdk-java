package br.com.anymarket.sdk.order.dto;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import java.util.Arrays;
import java.util.Collection;

import static br.com.anymarket.sdk.order.dto.DiscountItemMetadataTest.build;
import static org.junit.Assert.assertEquals;

/**
 * Testa que dois objetos construídos com os mesmos valores são sempre iguais,
 * cobrindo combinações com campos nulos (branches gerados pelo @Data do Lombok).
 */
@RunWith(Parameterized.class)
public class DiscountItemMetadataEqualsParamTest {

    @Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
            {DiscountType.COUPON, "1.0", "2.0"},
            {null,                "1.0", "2.0"},
            {DiscountType.COUPON, null,  "2.0"},
            {DiscountType.COUPON, "1.0", null },
            {null,                null,  null  },
        });
    }

    private final DiscountType type;
    private final String seller;
    private final String marketplace;

    public DiscountItemMetadataEqualsParamTest(DiscountType type, String seller, String marketplace) {
        this.type = type;
        this.seller = seller;
        this.marketplace = marketplace;
    }

    @Test
    public void equal_objects_should_be_equal() {
        DiscountItemMetadata a = build(type, seller, marketplace);
        DiscountItemMetadata b = build(type, seller, marketplace);
        assertEquals(a, b);
    }

    @Test
    public void equal_objects_should_have_same_hash_code() {
        DiscountItemMetadata a = build(type, seller, marketplace);
        DiscountItemMetadata b = build(type, seller, marketplace);
        assertEquals(a.hashCode(), b.hashCode());
    }
}

