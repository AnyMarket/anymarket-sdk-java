package br.com.anymarket.sdk.order.dto.packages;

import org.junit.Test;

import java.util.Collections;

import static org.junit.Assert.*;

public class OrderPackageRequestTest {

    @Test
    public void should_set_and_get_all_fields() {
        OrderPackageReason reason = OrderPackageReason.builder().id("SPLIT").build();
        OrderPackageInput packageInput = OrderPackageInput.builder().externalId("ext-001").build();

        OrderPackageRequest request = new OrderPackageRequest();
        request.setPackageReason(reason);
        request.setPackages(Collections.singletonList(packageInput));

        assertEquals(reason, request.getPackageReason());
        assertEquals(1, request.getPackages().size());
    }

    @Test
    public void builder_should_create_instance_with_all_fields() {
        OrderPackageReason reason = OrderPackageReason.builder().id("CANCEL").build();

        OrderPackageRequest request = OrderPackageRequest.builder()
            .packageReason(reason)
            .packages(Collections.<OrderPackageInput>emptyList())
            .build();

        assertEquals(reason, request.getPackageReason());
        assertTrue(request.getPackages().isEmpty());
    }

    @Test
    public void no_args_constructor_should_create_empty_instance() {
        assertNotNull(new OrderPackageRequest());
    }
}
