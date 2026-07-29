package br.com.anymarket.sdk.order.dto;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import java.util.Arrays;
import java.util.Collection;

import static br.com.anymarket.sdk.order.dto.DiscountItemMetadataTest.build;
import static org.junit.Assert.assertNotEquals;

/**
 * Testa que dois objetos com ao menos um campo diferente nunca são iguais,
 * cobrindo os branches de cada campo no equals() gerado pelo @Data do Lombok.
 * O objeto base é sempre (COUPON, "1.0", "2.0").
 */
@RunWith(Parameterized.class)
public class DiscountItemMetadataNotEqualsParamTest {

    @Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
            {DiscountType.FREE_ITEM, "1.0", "2.0"},  // type diferente
            {DiscountType.COUPON,    "9.0", "2.0"},  // seller diferente
            {DiscountType.COUPON,    "1.0", "9.0"},  // marketplace diferente
            {null,                   "1.0", "2.0"},  // type nulo
            {DiscountType.COUPON,    null,  "2.0"},  // seller nulo
            {DiscountType.COUPON,    "1.0", null },  // marketplace nulo
        });
    }

    private final DiscountType bType;
    private final String bSeller;
    private final String bMarketplace;

    public DiscountItemMetadataNotEqualsParamTest(DiscountType bType, String bSeller, String bMarketplace) {
        this.bType = bType;
        this.bSeller = bSeller;
        this.bMarketplace = bMarketplace;
    }

    @Test
    public void objects_with_different_field_should_not_be_equal() {
        DiscountItemMetadata a = build(DiscountType.COUPON, "1.0", "2.0");
        DiscountItemMetadata b = build(bType, bSeller, bMarketplace);
        assertNotEquals(a, b);
    }
}

