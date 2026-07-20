package br.com.anymarket.sdk.order.dto;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Collections;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class OrderItemResourceDiscountItemMetadataTest {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void should_default_discount_item_metadata_to_empty_list() {
        OrderItemResource orderItemResource = new OrderItemResource();

        assertTrue(orderItemResource.getDiscountItemMetadata().isEmpty());
    }

    @Test
    public void should_set_and_get_discount_item_metadata() {
        DiscountItemMetadata discountItemMetadata = new DiscountItemMetadata();
        discountItemMetadata.setType(DiscountType.COUPON);
        discountItemMetadata.setDiscountProductSeller(BigDecimal.valueOf(3.00));

        OrderItemResource orderItemResource = new OrderItemResource();
        orderItemResource.setDiscountItemMetadata(Collections.singletonList(discountItemMetadata));

        assertEquals(1, orderItemResource.getDiscountItemMetadata().size());
        assertEquals(DiscountType.COUPON, orderItemResource.getDiscountItemMetadata().get(0).getType());
    }

    @Test
    public void should_roundtrip_discount_item_metadata_through_json() throws Exception {
        DiscountItemMetadata discountItemMetadata = new DiscountItemMetadata();
        discountItemMetadata.setType(DiscountType.FREE_ITEM);
        discountItemMetadata.setDiscountProductMarketplace(BigDecimal.valueOf(4.00));

        OrderItemResource orderItemResource = new OrderItemResource();
        orderItemResource.setDiscountItemMetadata(Collections.singletonList(discountItemMetadata));

        String json = objectMapper.writeValueAsString(orderItemResource);
        OrderItemResource deserialized = objectMapper.readValue(json, OrderItemResource.class);

        assertEquals(1, deserialized.getDiscountItemMetadata().size());
        assertEquals(BigDecimal.valueOf(4.00), deserialized.getDiscountItemMetadata().get(0).getDiscountProductMarketplace());
    }
}
