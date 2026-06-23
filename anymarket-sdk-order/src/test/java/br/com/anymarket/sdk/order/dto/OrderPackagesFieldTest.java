package br.com.anymarket.sdk.order.dto;

import br.com.anymarket.sdk.order.dto.packages.OrderPackageResourceTrackingCode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;

import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;

public class OrderPackagesFieldTest {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void should_get_and_set_packages() {
        OrderPackageResourceTrackingCode pkg = OrderPackageResourceTrackingCode.builder()
            .trackingCode("TC-001")
            .build();

        Order order = new Order();
        order.setPackages(Collections.singletonList(pkg));

        List<OrderPackageResourceTrackingCode> packages = order.getPackages();
        assertNotNull(packages);
        assertEquals(1, packages.size());
        assertEquals("TC-001", packages.get(0).getTrackingCode());
    }

    @Test
    public void should_serialize_order_with_packages() throws Exception {
        OrderPackageResourceTrackingCode pkg = OrderPackageResourceTrackingCode.builder()
            .trackingCode("TC-002")
            .build();

        Order order = new Order();
        order.setPackages(Collections.singletonList(pkg));

        String json = objectMapper.writeValueAsString(order);

        assertTrue(json.contains("\"packages\""));
        assertTrue(json.contains("TC-002"));
    }

    @Test
    public void should_deserialize_order_without_packages_without_error() throws Exception {
        String json = "{\"id\":1,\"status\":null}";

        Order order = objectMapper.readValue(json, Order.class);

        assertNotNull(order);
        assertEquals(Long.valueOf(1L), order.getId());
        assertNull(order.getPackages());
    }

    @Test
    public void packages_should_appear_in_toString() {
        OrderPackageResourceTrackingCode pkg = OrderPackageResourceTrackingCode.builder()
            .trackingCode("TC-003")
            .build();

        Order order = new Order();
        order.setPackages(Collections.singletonList(pkg));

        assertTrue(order.toString().contains("packages"));
    }

    @Test
    public void should_get_and_set_hasPackages() {
        Order order = new Order();
        assertFalse(order.isHasPackages());

        order.setHasPackages(true);
        assertTrue(order.isHasPackages());
    }

    @Test
    public void should_serialize_hasPackages() throws Exception {
        Order order = new Order();
        order.setHasPackages(true);

        String json = objectMapper.writeValueAsString(order);

        assertTrue(json.contains("\"hasPackages\":true"));
    }

    @Test
    public void should_deserialize_hasPackages() throws Exception {
        String json = "{\"id\":1,\"hasPackages\":true}";

        Order order = objectMapper.readValue(json, Order.class);

        assertTrue(order.isHasPackages());
    }

    @Test
    public void hasPackages_should_appear_in_toString() {
        Order order = new Order();
        order.setHasPackages(true);

        assertTrue(order.toString().contains("hasPackages"));
    }
}
