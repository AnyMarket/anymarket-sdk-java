package br.com.anymarket.sdk.order.dto.packages;

import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.*;

public class OrderPackageDeliverRequestTest {

    @Test
    public void should_set_and_get_all_fields() {
        Date date = new Date();

        OrderPackageDeliverRequest request = new OrderPackageDeliverRequest();
        request.setPackageId("pkg-001");
        request.setDeliveredDate(date);

        assertEquals("pkg-001", request.getPackageId());
        assertEquals(date, request.getDeliveredDate());
    }

    @Test
    public void builder_should_create_instance_with_all_fields() {
        Date date = new Date();

        OrderPackageDeliverRequest request = OrderPackageDeliverRequest.builder()
            .packageId("pkg-002")
            .deliveredDate(date)
            .build();

        assertEquals("pkg-002", request.getPackageId());
        assertEquals(date, request.getDeliveredDate());
    }

    @Test
    public void no_args_constructor_should_create_empty_instance() {
        assertNotNull(new OrderPackageDeliverRequest());
    }
}
