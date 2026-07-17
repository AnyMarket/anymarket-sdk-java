package br.com.anymarket.sdk.order.dto;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Collections;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
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
}
