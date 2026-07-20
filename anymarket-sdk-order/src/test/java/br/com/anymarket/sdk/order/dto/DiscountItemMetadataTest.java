package br.com.anymarket.sdk.order.dto;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class DiscountItemMetadataTest {

    private final ObjectMapper objectMapper = new ObjectMapper();

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
}
