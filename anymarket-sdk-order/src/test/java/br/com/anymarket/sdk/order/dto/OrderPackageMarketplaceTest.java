package br.com.anymarket.sdk.order.dto;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.junit.Assert.*;

public class OrderPackageMarketplaceTest {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void should_build_with_minimum_fields() {
        OrderPackageMarketplace dto = OrderPackageMarketplace.builder()
                .orderId(1L)
                .packageId(10L)
                .build();

        assertNotNull(dto);
        assertEquals(Long.valueOf(1L), dto.getOrderId());
        assertEquals(Long.valueOf(10L), dto.getPackageId());
        assertNull(dto.getEvent());
        assertNull(dto.getPackages());
    }

    @Test
    public void should_build_with_all_fields() {
        List<OrderPackageUnit> units = Collections.singletonList(
                OrderPackageUnit.builder().packageIdItem(UUID.randomUUID()).build()
        );

        PackageReasonMarketplace reason = PackageReasonMarketplace.builder()
                .reasonId("R1").description("Motivo teste").build();

        OrderPackageMarketplace dto = OrderPackageMarketplace.builder()
                .orderId(100L)
                .packageId(200L)
                .event(EventPackage.UPDATED_SHIPPED_PACKAGE)
                .oi("OI-ABC")
                .orderIdInMarketplace("MKT-9999")
                .packageReasonMarketplace(reason)
                .packages(units)
                .build();

        assertEquals(Long.valueOf(100L), dto.getOrderId());
        assertEquals(Long.valueOf(200L), dto.getPackageId());
        assertEquals(EventPackage.UPDATED_SHIPPED_PACKAGE, dto.getEvent());
        assertEquals("OI-ABC", dto.getOi());
        assertEquals("MKT-9999", dto.getOrderIdInMarketplace());
        assertNotNull(dto.getPackageReasonMarketplace());
        assertEquals("R1", dto.getPackageReasonMarketplace().getReasonId());
        assertEquals(1, dto.getPackages().size());
    }

    @Test
    public void should_accept_all_event_package_values() {
        for (EventPackage event : EventPackage.values()) {
            OrderPackageMarketplace dto = OrderPackageMarketplace.builder()
                    .event(event)
                    .build();
            assertEquals(event, dto.getEvent());
        }
    }

    @Test
    public void should_hold_multiple_package_units() {
        List<OrderPackageUnit> units = Arrays.asList(
                OrderPackageUnit.builder().packageIdItem(UUID.randomUUID()).externalId("A").build(),
                OrderPackageUnit.builder().packageIdItem(UUID.randomUUID()).externalId("B").build()
        );

        OrderPackageMarketplace dto = OrderPackageMarketplace.builder()
                .orderId(1L)
                .packages(units)
                .build();

        assertEquals(2, dto.getPackages().size());
        assertEquals("A", dto.getPackages().get(0).getExternalId());
        assertEquals("B", dto.getPackages().get(1).getExternalId());
    }

    @Test
    public void should_allow_empty_packages_list() {
        OrderPackageMarketplace dto = OrderPackageMarketplace.builder()
                .orderId(1L)
                .packages(Collections.<OrderPackageUnit>emptyList())
                .build();

        assertNotNull(dto.getPackages());
        assertTrue(dto.getPackages().isEmpty());
    }

    @Test
    public void should_be_equal_when_same_order_and_package_id() {
        OrderPackageMarketplace d1 = OrderPackageMarketplace.builder().orderId(1L).packageId(2L).build();
        OrderPackageMarketplace d2 = OrderPackageMarketplace.builder().orderId(1L).packageId(2L).build();

        assertEquals(d1, d2);
        assertEquals(d1.hashCode(), d2.hashCode());
    }

    @Test
    public void should_not_be_equal_when_different_order_id() {
        OrderPackageMarketplace d1 = OrderPackageMarketplace.builder().orderId(1L).build();
        OrderPackageMarketplace d2 = OrderPackageMarketplace.builder().orderId(2L).build();

        assertNotEquals(d1, d2);
    }

    @Test
    public void should_serialize_event_as_string() throws Exception {
        OrderPackageMarketplace dto = OrderPackageMarketplace.builder()
                .orderId(1L)
                .event(EventPackage.CREATED_PACKAGE)
                .build();

        String json = objectMapper.writeValueAsString(dto);

        assertTrue(json.contains("\"event\":\"CREATED_PACKAGE\""));
        assertTrue(json.contains("\"orderId\":1"));
    }

    @Test
    public void should_deserialize_from_json() throws Exception {
        String json = "{\"orderId\":55,\"packageId\":77,\"event\":\"UPDATED_DELIVERED_PACKAGE\"," +
                "\"oi\":null,\"orderIdInMarketplace\":\"MKT-123\",\"packageReasonMarketplace\":null,\"packages\":null}";

        OrderPackageMarketplace dto = objectMapper.readValue(json, OrderPackageMarketplace.class);

        assertEquals(Long.valueOf(55L), dto.getOrderId());
        assertEquals(Long.valueOf(77L), dto.getPackageId());
        assertEquals(EventPackage.UPDATED_DELIVERED_PACKAGE, dto.getEvent());
        assertEquals("MKT-123", dto.getOrderIdInMarketplace());
    }

    @Test
    public void should_serialize_nested_package_units() throws Exception {
        UUID unitId = UUID.randomUUID();
        OrderPackageUnit unit = OrderPackageUnit.builder()
                .packageIdItem(unitId)
                .externalId("EXT-1")
                .invoice(OrderPackageInvoice.builder().invoiceNumber("NF-001").build())
                .tracking(OrderPackageShipping.builder().trackingNumber("T123").carrier("Correios").build())
                .delivered(OrderPackageDelivery.builder().build())
                .build();

        OrderPackageMarketplace dto = OrderPackageMarketplace.builder()
                .orderId(1L)
                .event(EventPackage.UPDATED_SHIPPED_PACKAGE)
                .packages(Collections.singletonList(unit))
                .build();

        String json = objectMapper.writeValueAsString(dto);

        assertTrue(json.contains("\"invoiceNumber\":\"NF-001\""));
        assertTrue(json.contains("\"trackingNumber\":\"T123\""));
        assertTrue(json.contains("\"carrier\":\"Correios\""));
        assertTrue(json.contains(unitId.toString()));
    }
}
