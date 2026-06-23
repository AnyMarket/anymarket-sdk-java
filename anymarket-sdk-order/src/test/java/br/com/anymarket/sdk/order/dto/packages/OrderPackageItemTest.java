package br.com.anymarket.sdk.order.dto.packages;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.UUID;

import static org.junit.Assert.*;

public class OrderPackageItemTest {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void builder_should_create_instance_with_all_fields() {
        UUID packageItemId = UUID.randomUUID();

        OrderPackageItem item = OrderPackageItem.builder()
            .packageItemId(packageItemId)
            .sku("SKU-001")
            .quantity(new BigDecimal("2.0"))
            .salesOrderItemId(100L)
            .skuInMarketplace("MKT-SKU-001")
            .idInMarketplace("MKT-ID-001")
            .build();

        assertEquals(packageItemId, item.getPackageItemId());
        assertEquals("SKU-001", item.getSku());
        assertEquals(new BigDecimal("2.0"), item.getQuantity());
        assertEquals(Long.valueOf(100L), item.getSalesOrderItemId());
        assertEquals("MKT-SKU-001", item.getSkuInMarketplace());
        assertEquals("MKT-ID-001", item.getIdInMarketplace());
    }

    @Test
    public void no_args_constructor_and_setters_should_work() {
        UUID packageItemId = UUID.randomUUID();

        OrderPackageItem item = new OrderPackageItem();
        item.setPackageItemId(packageItemId);
        item.setSku("SKU-002");
        item.setQuantity(BigDecimal.TEN);
        item.setSalesOrderItemId(200L);
        item.setSkuInMarketplace("MKT-SKU-002");
        item.setIdInMarketplace("MKT-ID-002");

        assertEquals(packageItemId, item.getPackageItemId());
        assertEquals("SKU-002", item.getSku());
        assertEquals(BigDecimal.TEN, item.getQuantity());
        assertEquals(Long.valueOf(200L), item.getSalesOrderItemId());
        assertEquals("MKT-SKU-002", item.getSkuInMarketplace());
        assertEquals("MKT-ID-002", item.getIdInMarketplace());
    }

    @Test
    public void json_properties_should_match_field_names() throws Exception {
        UUID packageItemId = UUID.randomUUID();

        OrderPackageItem item = OrderPackageItem.builder()
            .packageItemId(packageItemId)
            .sku("SKU-003")
            .quantity(new BigDecimal("3.0"))
            .salesOrderItemId(300L)
            .skuInMarketplace("MKT-SKU-003")
            .idInMarketplace("MKT-ID-003")
            .build();

        String json = objectMapper.writeValueAsString(item);

        assertTrue(json.contains("\"packageItemId\""));
        assertTrue(json.contains("\"sku\""));
        assertTrue(json.contains("\"quantity\""));
        assertTrue(json.contains("\"salesOrderItemId\""));
        assertTrue(json.contains("\"skuInMarketplace\""));
        assertTrue(json.contains("\"idInMarketplace\""));

        OrderPackageItem deserialized = objectMapper.readValue(json, OrderPackageItem.class);
        assertEquals(packageItemId, deserialized.getPackageItemId());
        assertEquals("SKU-003", deserialized.getSku());
        assertEquals(new BigDecimal("3.0"), deserialized.getQuantity());
        assertEquals(Long.valueOf(300L), deserialized.getSalesOrderItemId());
        assertEquals("MKT-SKU-003", deserialized.getSkuInMarketplace());
        assertEquals("MKT-ID-003", deserialized.getIdInMarketplace());
    }
}
