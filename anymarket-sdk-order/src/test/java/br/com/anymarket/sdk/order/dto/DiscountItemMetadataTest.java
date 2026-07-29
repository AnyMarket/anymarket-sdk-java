package br.com.anymarket.sdk.order.dto;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.*;

public class DiscountItemMetadataTest {

    private final ObjectMapper objectMapper = new ObjectMapper();

    // ── getters / setters ──────────────────────────────────────────────────

    @Test
    public void should_set_and_get_discount_item_metadata_fields() {
        DiscountItemMetadata discountItemMetadata = new DiscountItemMetadata();
        discountItemMetadata.setType(DiscountType.COUPON);
        discountItemMetadata.setDiscountProductSeller(BigDecimal.valueOf(3.00));
        discountItemMetadata.setDiscountProductMarketplace(BigDecimal.valueOf(2.50));

        assertEquals(DiscountType.COUPON, discountItemMetadata.getType());
        assertEquals(BigDecimal.valueOf(3.00), discountItemMetadata.getDiscountProductSeller());
        assertEquals(BigDecimal.valueOf(2.50), discountItemMetadata.getDiscountProductMarketplace());
    }

    // ── JSON serialization ─────────────────────────────────────────────────

    @Test
    public void should_serialize_informed_fields() throws Exception {
        DiscountItemMetadata discountItemMetadata = new DiscountItemMetadata();
        discountItemMetadata.setType(DiscountType.FREE_ITEM);
        discountItemMetadata.setDiscountProductSeller(BigDecimal.valueOf(5.00));

        String json = objectMapper.writeValueAsString(discountItemMetadata);

        assertTrue(json.contains("\"discountProductSeller\":5.0"));
    }

    @Test
    public void should_omit_fields_not_informed() throws Exception {
        DiscountItemMetadata discountItemMetadata = new DiscountItemMetadata();
        discountItemMetadata.setDiscountProductMarketplace(BigDecimal.valueOf(8.00));

        String json = objectMapper.writeValueAsString(discountItemMetadata);

        assertFalse("discountProductSeller não informado não deve aparecer no JSON", json.contains("discountProductSeller"));
        assertFalse("type não informado não deve aparecer no JSON", json.contains("\"type\""));
    }

    // ── equals ─────────────────────────────────────────────────────────────

    @Test
    public void equals_same_instance_should_be_true() {
        DiscountItemMetadata a = build(DiscountType.COUPON, "1.0", "2.0");
        assertEquals(a, a);
    }

    @Test
    public void equals_null_should_be_false() {
        DiscountItemMetadata a = build(DiscountType.COUPON, "1.0", "2.0");
        assertNotEquals(null, a);
    }

    @Test
    public void equals_different_class_should_be_false() {
        DiscountItemMetadata a = build(DiscountType.COUPON, "1.0", "2.0");
        assertNotEquals(a, "string");
    }

    // (cenários "not equals" cobertos por DiscountItemMetadataNotEqualsParamTest)

    // ── hashCode ───────────────────────────────────────────────────────────

    @Test
    public void hashCode_equal_objects_should_have_same_hash() {
        DiscountItemMetadata a = build(DiscountType.COUPON, "1.0", "2.0");
        DiscountItemMetadata b = build(DiscountType.COUPON, "1.0", "2.0");
        assertEquals(a.hashCode(), b.hashCode());
    }

    @Test
    public void hashCode_all_null_fields_should_be_consistent() {
        DiscountItemMetadata a = build(null, null, null);
        DiscountItemMetadata b = build(null, null, null);
        assertEquals(a.hashCode(), b.hashCode());
    }

    @Test
    public void hashCode_with_all_values_should_be_consistent() {
        DiscountItemMetadata a = build(DiscountType.FREE_ITEM, "5.0", "3.0");
        assertEquals(a.hashCode(), a.hashCode());
    }

    // ── toString ───────────────────────────────────────────────────────────

    @Test
    public void toString_should_contain_field_names_and_values() {
        DiscountItemMetadata a = build(DiscountType.COUPON, "1.0", "2.0");
        String result = a.toString();

        assertNotNull(result);
        assertTrue(result.contains("DiscountItemMetadata"));
        assertTrue(result.contains("type"));
        assertTrue(result.contains("COUPON"));
        assertTrue(result.contains("discountProductSeller"));
        assertTrue(result.contains("discountProductMarketplace"));
    }

    @Test
    public void toString_with_null_fields_should_not_throw() {
        DiscountItemMetadata a = build(null, null, null);
        assertNotNull(a.toString());
    }

    // ── canEqual ───────────────────────────────────────────────────────────

    @Test
    public void canEqual_same_type_should_be_true() {
        DiscountItemMetadata a = new DiscountItemMetadata();
        assertTrue(a.canEqual(new DiscountItemMetadata()));
    }

    @Test
    public void canEqual_different_type_should_be_false() {
        DiscountItemMetadata a = new DiscountItemMetadata();
        assertFalse(a.canEqual("other"));
    }

    // ── helper ─────────────────────────────────────────────────────────────

    static DiscountItemMetadata build(DiscountType type, String seller, String marketplace) {
        DiscountItemMetadata m = new DiscountItemMetadata();
        m.setType(type);
        m.setDiscountProductSeller(seller != null ? new BigDecimal(seller) : null);
        m.setDiscountProductMarketplace(marketplace != null ? new BigDecimal(marketplace) : null);
        return m;
    }
}
