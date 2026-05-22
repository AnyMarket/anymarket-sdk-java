package br.com.anymarket.sdk.order.dto;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.junit.Assert.*;

public class OrderPackageUnitTest {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void should_build_with_only_package_id() {
        UUID id = UUID.randomUUID();
        OrderPackageUnit unit = OrderPackageUnit.builder()
                .packageIdItem(id)
                .build();

        assertNotNull(unit);
        assertEquals(id, unit.getPackageIdItem());
        assertNull(unit.getExternalId());
        assertNull(unit.getInvoice());
        assertNull(unit.getTracking());
        assertNull(unit.getDelivered());
    }

    @Test
    public void should_build_fully_populated_unit() {
        UUID id = UUID.randomUUID();
        InvoicePackage invoice = InvoicePackage.builder()
                .invoiceNumber("NF-001")
                .invoiceKey("key-abc")
                .build();
        ShippingPackage shipping = ShippingPackage.builder()
                .trackingNumber("BR123456789")
                .carrier("Correios")
                .build();
        DeliveryPackage delivery = DeliveryPackage.builder().build();

        OrderPackageUnit unit = OrderPackageUnit.builder()
                .packageIdItem(id)
                .externalId("EXT-42")
                .invoice(invoice)
                .tracking(shipping)
                .delivered(delivery)
                .build();

        assertEquals(id, unit.getPackageIdItem());
        assertEquals("EXT-42", unit.getExternalId());
        assertEquals("NF-001", unit.getInvoice().getInvoiceNumber());
        assertEquals("BR123456789", unit.getTracking().getTrackingNumber());
        assertNotNull(unit.getDelivered());
    }

    @Test
    public void should_set_items_via_setter() {
        ItemsPackage item = new ItemsPackage();
        OrderPackageUnit unit = new OrderPackageUnit();
        unit.setItems(Collections.singletonList(item));

        assertEquals(1, unit.getItems().size());
    }

    @Test
    public void should_support_multiple_items() {
        List<ItemsPackage> items = Arrays.asList(new ItemsPackage(), new ItemsPackage(), new ItemsPackage());
        OrderPackageUnit unit = OrderPackageUnit.builder().items(items).build();

        assertEquals(3, unit.getItems().size());
    }

    @Test
    public void should_be_equal_when_same_package_id_and_external_id() {
        UUID id = UUID.randomUUID();
        OrderPackageUnit u1 = OrderPackageUnit.builder().packageIdItem(id).externalId("EXT-1").build();
        OrderPackageUnit u2 = OrderPackageUnit.builder().packageIdItem(id).externalId("EXT-1").build();

        assertEquals(u1, u2);
        assertEquals(u1.hashCode(), u2.hashCode());
    }

    @Test
    public void should_not_be_equal_when_different_package_id() {
        OrderPackageUnit u1 = OrderPackageUnit.builder().packageIdItem(UUID.randomUUID()).build();
        OrderPackageUnit u2 = OrderPackageUnit.builder().packageIdItem(UUID.randomUUID()).build();

        assertNotEquals(u1, u2);
    }

    @Test
    public void should_serialize_to_json_with_package_id() throws Exception {
        UUID id = UUID.randomUUID();
        OrderPackageUnit unit = OrderPackageUnit.builder()
                .packageIdItem(id)
                .externalId("EXT-99")
                .build();

        String json = objectMapper.writeValueAsString(unit);

        assertTrue(json.contains("packageIdItem"));
        assertTrue(json.contains(id.toString()));
        assertTrue(json.contains("EXT-99"));
    }

    @Test
    public void should_deserialize_from_json() throws Exception {
        UUID id = UUID.randomUUID();
        String json = String.format(
                "{\"packageIdItem\":\"%s\",\"externalId\":\"EXT-77\",\"items\":null,\"dimensions\":null,\"invoice\":null,\"tracking\":null,\"delivered\":null}",
                id);

        OrderPackageUnit unit = objectMapper.readValue(json, OrderPackageUnit.class);

        assertEquals(id, unit.getPackageIdItem());
        assertEquals("EXT-77", unit.getExternalId());
    }
}
