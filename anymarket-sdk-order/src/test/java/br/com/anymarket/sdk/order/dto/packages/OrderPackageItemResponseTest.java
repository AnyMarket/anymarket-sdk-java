package br.com.anymarket.sdk.order.dto.packages;

import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.*;

public class OrderPackageItemResponseTest {

    @Test
    public void should_set_and_get_all_fields() {
        OrderPackageItemResponse item = new OrderPackageItemResponse();
        item.setSku("SKU-001");
        item.setQuantity(new BigDecimal("2.0"));
        item.setSalesOrderItemId(100L);
        item.setSkuInMarketplace("MKT-SKU-001");
        item.setIdInMarketplace("MKT-ID-001");

        assertEquals("SKU-001", item.getSku());
        assertEquals(new BigDecimal("2.0"), item.getQuantity());
        assertEquals(Long.valueOf(100L), item.getSalesOrderItemId());
        assertEquals("MKT-SKU-001", item.getSkuInMarketplace());
        assertEquals("MKT-ID-001", item.getIdInMarketplace());
    }

    @Test
    public void builder_should_create_instance_with_all_fields() {
        OrderPackageItemResponse item = OrderPackageItemResponse.builder()
            .sku("SKU-002")
            .quantity(BigDecimal.TEN)
            .salesOrderItemId(200L)
            .skuInMarketplace("MKT-SKU-002")
            .idInMarketplace("MKT-ID-002")
            .build();

        assertEquals("SKU-002", item.getSku());
        assertEquals(BigDecimal.TEN, item.getQuantity());
        assertEquals(Long.valueOf(200L), item.getSalesOrderItemId());
        assertEquals("MKT-SKU-002", item.getSkuInMarketplace());
        assertEquals("MKT-ID-002", item.getIdInMarketplace());
    }

    @Test
    public void no_args_constructor_should_create_empty_instance() {
        assertNotNull(new OrderPackageItemResponse());
    }
}
