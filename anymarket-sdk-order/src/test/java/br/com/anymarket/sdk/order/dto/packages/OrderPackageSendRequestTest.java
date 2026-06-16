package br.com.anymarket.sdk.order.dto.packages;

import br.com.anymarket.sdk.order.dto.OrderStatus;
import br.com.anymarket.sdk.order.dto.TrackingResource;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class OrderPackageSendRequestTest {

    @Test
    public void constructor_should_always_set_status_to_paid_waiting_delivery() {
        TrackingResource tracking = new TrackingResource();
        tracking.setNumber("TRACK-001");

        OrderPackageSendRequest request = new OrderPackageSendRequest(tracking);

        assertEquals(OrderStatus.PAID_WAITING_DELIVERY, request.getStatus());
    }

    @Test
    public void constructor_should_set_tracking_resource() {
        TrackingResource tracking = new TrackingResource();
        tracking.setNumber("TRACK-002");
        tracking.setCarrier("Correios");

        OrderPackageSendRequest request = new OrderPackageSendRequest(tracking);

        assertNotNull(request.getTracking());
        assertEquals("TRACK-002", request.getTracking().getNumber());
        assertEquals("Correios", request.getTracking().getCarrier());
    }

    @Test
    public void constructor_with_null_tracking_should_set_status() {
        OrderPackageSendRequest request = new OrderPackageSendRequest(null);

        assertEquals(OrderStatus.PAID_WAITING_DELIVERY, request.getStatus());
    }
}
