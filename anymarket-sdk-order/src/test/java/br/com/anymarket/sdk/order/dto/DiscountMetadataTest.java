package br.com.anymarket.sdk.order.dto;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

public class DiscountMetadataTest {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void should_serialize_both_origin_amounts_when_both_informed() throws Exception {
        DiscountDetails details = new DiscountDetails();
        details.setType(DiscountType.COUPON);
        details.setDiscountOrderSeller(new BigDecimal("10.00"));
        details.setDiscountOrderMarketplace(new BigDecimal("5.90"));

        DiscountMetadata metadata = new DiscountMetadata();
        metadata.setType(Collections.singletonList(DiscountType.COUPON));
        metadata.setDiscountDetails(Collections.singletonList(details));

        String json = objectMapper.writeValueAsString(metadata);

        assertTrue(json.contains("\"discountOrderSeller\":10.00"));
        assertTrue(json.contains("\"discountOrderMarketplace\":5.90"));

        DiscountMetadata parsed = objectMapper.readValue(json, DiscountMetadata.class);
        assertEquals(1, parsed.getDiscountDetails().size());
        assertEquals(new BigDecimal("10.00"), parsed.getDiscountDetails().get(0).getDiscountOrderSeller());
        assertEquals(new BigDecimal("5.90"), parsed.getDiscountDetails().get(0).getDiscountOrderMarketplace());
    }

    @Test
    public void should_omit_seller_amount_when_only_marketplace_informed() throws Exception {
        DiscountDetails details = new DiscountDetails();
        details.setDiscountOrderMarketplace(new BigDecimal("20.00"));

        DiscountMetadata metadata = new DiscountMetadata();
        metadata.setDiscountDetails(Collections.singletonList(details));

        String json = objectMapper.writeValueAsString(metadata);

        assertTrue(json.contains("\"discountOrderMarketplace\":20.00"));
        assertFalse(json.contains("discountOrderSeller"));
    }

    @Test
    public void should_omit_discount_details_when_no_origin_informed() throws Exception {
        DiscountMetadata metadata = new DiscountMetadata();
        metadata.setType(Collections.singletonList(DiscountType.FREE_ITEM));

        String json = objectMapper.writeValueAsString(metadata);

        assertFalse(json.contains("discountDetails"));
        assertTrue(json.contains("\"type\":[\"FREE_ITEM\"]"));
    }

    @Test
    public void should_not_affect_existing_type_field_contract() throws Exception {
        DiscountMetadata metadata = new DiscountMetadata();
        metadata.setType(Collections.singletonList(DiscountType.COUPON));

        String json = objectMapper.writeValueAsString(metadata);
        DiscountMetadata parsed = objectMapper.readValue(json, DiscountMetadata.class);

        assertEquals(Collections.singletonList(DiscountType.COUPON), parsed.getType());
    }

    @Test
    public void should_set_and_get_fields_from_data_annotation() {
        DiscountDetails firstDetail = discountDetails(DiscountType.COUPON, "10.00", "5.00");
        DiscountDetails secondDetail = discountDetails(DiscountType.FREE_ITEM, "2.00", "1.00");

        DiscountMetadata metadata = new DiscountMetadata();
        metadata.setType(Arrays.asList(DiscountType.COUPON, DiscountType.FREE_ITEM));
        metadata.setDiscountDetails(Arrays.asList(firstDetail, secondDetail));

        assertEquals(Arrays.asList(DiscountType.COUPON, DiscountType.FREE_ITEM), metadata.getType());
        assertEquals(Arrays.asList(firstDetail, secondDetail), metadata.getDiscountDetails());
    }

    @Test
    public void should_be_equal_and_have_same_hashcode_when_all_fields_match() {
        DiscountMetadata left = metadataWith(
                Arrays.asList(DiscountType.COUPON, DiscountType.FREE_ITEM),
                Arrays.asList(
                        discountDetails(DiscountType.COUPON, "10.00", "5.00"),
                        discountDetails(DiscountType.FREE_ITEM, "2.00", "1.00")
                )
        );

        DiscountMetadata right = metadataWith(
                Arrays.asList(DiscountType.COUPON, DiscountType.FREE_ITEM),
                Arrays.asList(
                        discountDetails(DiscountType.COUPON, "10.00", "5.00"),
                        discountDetails(DiscountType.FREE_ITEM, "2.00", "1.00")
                )
        );

        assertEquals(left, right);
        assertEquals(left.hashCode(), right.hashCode());
    }

    @Test
    public void should_not_be_equal_when_type_differs() {
        DiscountMetadata left = metadataWith(
                Collections.singletonList(DiscountType.COUPON),
                Collections.singletonList(discountDetails(DiscountType.COUPON, "10.00", "5.00"))
        );

        DiscountMetadata right = metadataWith(
                Collections.singletonList(DiscountType.FREE_ITEM),
                Collections.singletonList(discountDetails(DiscountType.COUPON, "10.00", "5.00"))
        );

        assertNotEquals(left, right);
    }

    @Test
    public void should_not_be_equal_when_discount_details_differs() {
        DiscountMetadata left = metadataWith(
                Collections.singletonList(DiscountType.COUPON),
                Collections.singletonList(discountDetails(DiscountType.COUPON, "10.00", "5.00"))
        );

        DiscountMetadata right = metadataWith(
                Collections.singletonList(DiscountType.COUPON),
                Collections.singletonList(discountDetails(DiscountType.COUPON, "11.00", "5.00"))
        );

        assertNotEquals(left, right);
    }

    @Test
    public void should_not_be_equal_to_null_or_other_type() {
        DiscountMetadata metadata = metadataWith(
                Collections.singletonList(DiscountType.COUPON),
                Collections.singletonList(discountDetails(DiscountType.COUPON, "10.00", "5.00"))
        );

        assertNotEquals(metadata, null);
        assertNotEquals(metadata, "not-a-discount-metadata");
    }

    @Test
    public void should_generate_to_string_with_field_names_and_values() {
        DiscountMetadata metadata = metadataWith(
                Collections.singletonList(DiscountType.COUPON),
                Collections.singletonList(discountDetails(DiscountType.COUPON, "10.00", "5.00"))
        );

        String asString = metadata.toString();

        assertTrue(asString.contains("DiscountMetadata"));
        assertTrue(asString.contains("type"));
        assertTrue(asString.contains("discountDetails"));
        assertTrue(asString.contains("COUPON"));
    }

    private DiscountMetadata metadataWith(java.util.List<DiscountType> types, java.util.List<DiscountDetails> details) {
        DiscountMetadata metadata = new DiscountMetadata();
        metadata.setType(types);
        metadata.setDiscountDetails(details);
        return metadata;
    }

    private DiscountDetails discountDetails(DiscountType type, String sellerValue, String marketplaceValue) {
        DiscountDetails details = new DiscountDetails();
        details.setType(type);
        details.setDiscountOrderSeller(new BigDecimal(sellerValue));
        details.setDiscountOrderMarketplace(new BigDecimal(marketplaceValue));
        return details;
    }
}
