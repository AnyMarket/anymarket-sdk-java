package br.com.anymarket.sdk.order.dto;

import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

public class DiscountDetailsTest {

    @Test
    public void should_set_and_get_fields_from_data_annotation() {
        DiscountDetails details = new DiscountDetails();
        details.setType(DiscountType.COUPON);
        details.setDiscountOrderSeller(new BigDecimal("10.00"));
        details.setDiscountOrderMarketplace(new BigDecimal("5.90"));

        assertEquals(DiscountType.COUPON, details.getType());
        assertEquals(new BigDecimal("10.00"), details.getDiscountOrderSeller());
        assertEquals(new BigDecimal("5.90"), details.getDiscountOrderMarketplace());
    }

    @Test
    public void should_be_equal_and_have_same_hashcode_when_all_fields_match() {
        DiscountDetails left = discountDetails(DiscountType.COUPON, "10.00", "5.90");
        DiscountDetails right = discountDetails(DiscountType.COUPON, "10.00", "5.90");

        assertEquals(left, right);
        assertEquals(left.hashCode(), right.hashCode());
    }

    @Test
    public void should_be_equal_to_itself() {
        DiscountDetails details = discountDetails(DiscountType.COUPON, "10.00", "5.90");

        assertEquals(details, details);
    }

    @Test
    public void should_not_be_equal_to_null_or_other_type() {
        DiscountDetails details = discountDetails(DiscountType.COUPON, "10.00", "5.90");

        assertNotEquals(details, null);
        assertNotEquals(details, "not-a-discount-details");
    }

    @Test
    public void should_not_be_equal_when_type_differs() {
        DiscountDetails left = discountDetails(DiscountType.COUPON, "10.00", "5.90");
        DiscountDetails right = discountDetails(DiscountType.FREE_ITEM, "10.00", "5.90");

        assertNotEquals(left, right);
        assertNotEquals(right, left);
    }

    @Test
    public void should_not_be_equal_when_seller_amount_differs() {
        DiscountDetails left = discountDetails(DiscountType.COUPON, "10.00", "5.90");
        DiscountDetails right = discountDetails(DiscountType.COUPON, "11.00", "5.90");

        assertNotEquals(left, right);
        assertNotEquals(right, left);
    }

    @Test
    public void should_not_be_equal_when_marketplace_amount_differs() {
        DiscountDetails left = discountDetails(DiscountType.COUPON, "10.00", "5.90");
        DiscountDetails right = discountDetails(DiscountType.COUPON, "10.00", "6.90");

        assertNotEquals(left, right);
        assertNotEquals(right, left);
    }

    @Test
    public void should_be_equal_when_all_fields_are_null() {
        DiscountDetails left = new DiscountDetails();
        DiscountDetails right = new DiscountDetails();

        assertEquals(left, right);
        assertEquals(left.hashCode(), right.hashCode());
    }

    @Test
    public void should_not_be_equal_when_nullability_differs() {
        DiscountDetails leftWithNullType = discountDetails(null, "10.00", "5.90");
        DiscountDetails rightWithType = discountDetails(DiscountType.COUPON, "10.00", "5.90");

        DiscountDetails leftWithNullSeller = discountDetails(DiscountType.COUPON, null, "5.90");
        DiscountDetails rightWithSeller = discountDetails(DiscountType.COUPON, "10.00", "5.90");

        DiscountDetails leftWithNullMarketplace = discountDetails(DiscountType.COUPON, "10.00", null);
        DiscountDetails rightWithMarketplace = discountDetails(DiscountType.COUPON, "10.00", "5.90");

        assertNotEquals(leftWithNullType, rightWithType);
        assertNotEquals(leftWithNullSeller, rightWithSeller);
        assertNotEquals(leftWithNullMarketplace, rightWithMarketplace);
    }

    @Test
    public void should_not_be_equal_when_can_equal_is_false() {
        DiscountDetails base = discountDetails(DiscountType.COUPON, "10.00", "5.90");

        DiscountDetailsWithStrictCanEqual strict = new DiscountDetailsWithStrictCanEqual();
        strict.setType(DiscountType.COUPON);
        strict.setDiscountOrderSeller(new BigDecimal("10.00"));
        strict.setDiscountOrderMarketplace(new BigDecimal("5.90"));

        assertNotEquals(base, strict);
    }

    @Test
    public void should_generate_to_string_with_field_names_and_values() {
        DiscountDetails details = discountDetails(DiscountType.COUPON, "10.00", "5.90");

        String asString = details.toString();

        assertTrue(asString.contains("DiscountDetails"));
        assertTrue(asString.contains("type"));
        assertTrue(asString.contains("discountOrderSeller"));
        assertTrue(asString.contains("discountOrderMarketplace"));
        assertTrue(asString.contains("COUPON"));
    }

    @Test
    public void should_generate_to_string_with_null_fields() {
        DiscountDetails details = new DiscountDetails();

        String asString = details.toString();

        assertTrue(asString.contains("DiscountDetails"));
        assertTrue(asString.contains("type=null"));
        assertTrue(asString.contains("discountOrderSeller=null"));
        assertTrue(asString.contains("discountOrderMarketplace=null"));
    }

    @Test
    public void should_generate_hashcode_when_fields_are_null() {
        DiscountDetails details = new DiscountDetails();

        assertTrue(details.hashCode() != 0);
    }

    private DiscountDetails discountDetails(DiscountType type, String sellerValue, String marketplaceValue) {
        DiscountDetails details = new DiscountDetails();
        details.setType(type);
        if (sellerValue != null) {
            details.setDiscountOrderSeller(new BigDecimal(sellerValue));
        }
        if (marketplaceValue != null) {
            details.setDiscountOrderMarketplace(new BigDecimal(marketplaceValue));
        }
        return details;
    }

    private static class DiscountDetailsWithStrictCanEqual extends DiscountDetails {
        @Override
        protected boolean canEqual(Object other) {
            return false;
        }
    }
}

