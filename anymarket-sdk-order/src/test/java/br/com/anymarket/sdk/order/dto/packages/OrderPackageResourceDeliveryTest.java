package br.com.anymarket.sdk.order.dto.packages;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import static org.junit.Assert.*;

public class OrderPackageResourceDeliveryTest {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void builder_should_create_instance_with_all_fields() {
        UUID packageId = UUID.randomUUID();
        Date deliveredDate = new Date();

        OrderPackageResourceDelivery delivery = OrderPackageResourceDelivery.builder()
            .packageId(packageId)
            .deliveredDate(deliveredDate)
            .build();

        assertEquals(packageId, delivery.getPackageId());
        assertEquals(deliveredDate, delivery.getDeliveredDate());
    }

    @Test
    public void no_args_constructor_and_setters_should_work() {
        UUID packageId = UUID.randomUUID();
        Date deliveredDate = new Date();

        OrderPackageResourceDelivery delivery = new OrderPackageResourceDelivery();
        delivery.setPackageId(packageId);
        delivery.setDeliveredDate(deliveredDate);

        assertEquals(packageId, delivery.getPackageId());
        assertEquals(deliveredDate, delivery.getDeliveredDate());
    }

    @Test
    public void deliveredDate_should_round_trip_via_serialization() throws Exception {
        long epochSeconds = System.currentTimeMillis() / 1000;
        Date deliveredDate = new Date(epochSeconds * 1000);

        OrderPackageResourceDelivery delivery = OrderPackageResourceDelivery.builder()
            .deliveredDate(deliveredDate)
            .build();

        String json = objectMapper.writeValueAsString(delivery);
        OrderPackageResourceDelivery deserialized = objectMapper.readValue(json, OrderPackageResourceDelivery.class);

        String expectedFormatted = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX").format(deliveredDate);
        String actualFormatted = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX").format(deserialized.getDeliveredDate());
        assertEquals(expectedFormatted, actualFormatted);
    }
}
