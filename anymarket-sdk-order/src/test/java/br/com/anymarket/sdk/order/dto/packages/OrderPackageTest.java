package br.com.anymarket.sdk.order.dto.packages;

import org.junit.Test;

import java.util.Collections;

import static org.junit.Assert.*;

public class OrderPackageTest {

    @Test
    public void should_set_and_get_all_fields() {
        OrderPackageReason reason = OrderPackageReason.builder().id("SPLIT").description("Split").build();
        OrderPackageUnit unit = new OrderPackageUnit();

        OrderPackage orderPackage = new OrderPackage();
        orderPackage.setOrderId(1L);
        orderPackage.setPackageId(2L);
        orderPackage.setOi("OI-001");
        orderPackage.setOrderIdInMarketplace("MKT-001");
        orderPackage.setPackageReason(reason);
        orderPackage.setPackages(Collections.singletonList(unit));

        assertEquals(Long.valueOf(1L), orderPackage.getOrderId());
        assertEquals(Long.valueOf(2L), orderPackage.getPackageId());
        assertEquals("OI-001", orderPackage.getOi());
        assertEquals("MKT-001", orderPackage.getOrderIdInMarketplace());
        assertEquals(reason, orderPackage.getPackageReason());
        assertEquals(1, orderPackage.getPackages().size());
    }

    @Test
    public void builder_should_create_instance_with_all_fields() {
        OrderPackageReason reason = OrderPackageReason.builder().id("SPLIT").build();

        OrderPackage orderPackage = OrderPackage.builder()
            .orderId(10L)
            .packageId(20L)
            .oi("OI-002")
            .orderIdInMarketplace("MKT-002")
            .packageReason(reason)
            .packages(Collections.<OrderPackageUnit>emptyList())
            .build();

        assertEquals(Long.valueOf(10L), orderPackage.getOrderId());
        assertEquals(Long.valueOf(20L), orderPackage.getPackageId());
        assertEquals("OI-002", orderPackage.getOi());
        assertEquals("MKT-002", orderPackage.getOrderIdInMarketplace());
        assertEquals(reason, orderPackage.getPackageReason());
        assertTrue(orderPackage.getPackages().isEmpty());
    }

    @Test
    public void no_args_constructor_should_create_empty_instance() {
        OrderPackage orderPackage = new OrderPackage();
        assertNotNull(orderPackage);
    }
}
