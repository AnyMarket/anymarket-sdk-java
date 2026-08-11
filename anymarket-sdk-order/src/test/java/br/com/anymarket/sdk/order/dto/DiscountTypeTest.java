package br.com.anymarket.sdk.order.dto;

import com.fasterxml.jackson.annotation.JsonEnumDefaultValue;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class DiscountTypeTest {

    private static final List<String> EXPECTED_NAMES = Arrays.asList(
        "COUPON", "FREE_ITEM", "COIN", "PIX_DISCOUNT", "LOYALTY", "BUNDLE", "PROMOTIONAL");

    @Test
    public void should_declare_the_seven_supported_discount_types() {
        List<String> actualNames = new ArrayList<String>();
        for (DiscountType type : DiscountType.values()) {
            actualNames.add(type.name());
        }

        assertEquals(EXPECTED_NAMES.size(), actualNames.size());
        assertTrue("nomes divergentes entre as SDKs: " + actualNames,
            actualNames.containsAll(EXPECTED_NAMES));
    }

    @Test
    public void should_keep_pre_existing_values_unchanged() {
        assertEquals("COUPON", DiscountType.COUPON.name());
        assertEquals("FREE_ITEM", DiscountType.FREE_ITEM.name());
        assertTrue(DiscountType.COUPON.ordinal() < DiscountType.FREE_ITEM.ordinal());
    }

    @Test
    public void should_resolve_every_expected_name_through_value_of() {
        for (String name : EXPECTED_NAMES) {
            assertEquals(name, DiscountType.valueOf(name).name());
        }
    }

    @Test
    public void should_serialize_each_value_as_its_name() throws Exception {
        ObjectMapper mapper = new ObjectMapper();

        for (String name : EXPECTED_NAMES) {
            assertEquals("\"" + name + "\"",
                mapper.writeValueAsString(DiscountType.valueOf(name)));
        }
    }

    @Test
    public void should_annotate_promotional_as_the_fallback_value() throws Exception {
        assertTrue("PROMOTIONAL precisa carregar @JsonEnumDefaultValue para o fallback funcionar",
            DiscountType.class.getField(DiscountType.PROMOTIONAL.name())
                .isAnnotationPresent(JsonEnumDefaultValue.class));
    }
}
