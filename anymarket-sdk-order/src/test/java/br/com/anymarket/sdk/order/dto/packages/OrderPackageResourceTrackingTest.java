package br.com.anymarket.sdk.order.dto.packages;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import static org.junit.Assert.*;

public class OrderPackageResourceTrackingTest {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void builder_should_create_instance_with_all_fields() {
        UUID packageId = UUID.randomUUID();
        Date estimateDate = new Date();
        Date shippedDate = new Date();

        OrderPackageResourceTracking tracking = OrderPackageResourceTracking.builder()
            .packageId(packageId)
            .url("http://tracking.example.com")
            .number("TRACK-001")
            .carrier("Correios")
            .carrierDocument("12.345.678/0001-90")
            .estimateDate(estimateDate)
            .shippedDate(shippedDate)
            .build();

        assertEquals(packageId, tracking.getPackageId());
        assertEquals("http://tracking.example.com", tracking.getUrl());
        assertEquals("TRACK-001", tracking.getNumber());
        assertEquals("Correios", tracking.getCarrier());
        assertEquals("12.345.678/0001-90", tracking.getCarrierDocument());
        assertEquals(estimateDate, tracking.getEstimateDate());
        assertEquals(shippedDate, tracking.getShippedDate());
    }

    @Test
    public void no_args_constructor_and_setters_should_work() {
        UUID packageId = UUID.randomUUID();
        Date estimateDate = new Date();
        Date shippedDate = new Date();

        OrderPackageResourceTracking tracking = new OrderPackageResourceTracking();
        tracking.setPackageId(packageId);
        tracking.setUrl("http://tracking2.example.com");
        tracking.setNumber("TRACK-002");
        tracking.setCarrier("Jadlog");
        tracking.setCarrierDocument("98.765.432/0001-10");
        tracking.setEstimateDate(estimateDate);
        tracking.setShippedDate(shippedDate);

        assertEquals(packageId, tracking.getPackageId());
        assertEquals("http://tracking2.example.com", tracking.getUrl());
        assertEquals("TRACK-002", tracking.getNumber());
        assertEquals("Jadlog", tracking.getCarrier());
        assertEquals("98.765.432/0001-10", tracking.getCarrierDocument());
        assertEquals(estimateDate, tracking.getEstimateDate());
        assertEquals(shippedDate, tracking.getShippedDate());
    }

    @Test
    public void estimateDate_should_round_trip_via_serialization() throws Exception {
        long epochSeconds = System.currentTimeMillis() / 1000;
        Date estimateDate = new Date(epochSeconds * 1000);

        OrderPackageResourceTracking tracking = OrderPackageResourceTracking.builder()
            .estimateDate(estimateDate)
            .build();

        String json = objectMapper.writeValueAsString(tracking);
        OrderPackageResourceTracking deserialized = objectMapper.readValue(json, OrderPackageResourceTracking.class);

        String expectedFormatted = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX").format(estimateDate);
        String actualFormatted = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX").format(deserialized.getEstimateDate());
        assertEquals(expectedFormatted, actualFormatted);
    }

    @Test
    public void shippedDate_should_round_trip_via_serialization() throws Exception {
        long epochSeconds = System.currentTimeMillis() / 1000;
        Date shippedDate = new Date(epochSeconds * 1000);

        OrderPackageResourceTracking tracking = OrderPackageResourceTracking.builder()
            .shippedDate(shippedDate)
            .build();

        String json = objectMapper.writeValueAsString(tracking);
        OrderPackageResourceTracking deserialized = objectMapper.readValue(json, OrderPackageResourceTracking.class);

        String expectedFormatted = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX").format(shippedDate);
        String actualFormatted = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX").format(deserialized.getShippedDate());
        assertEquals(expectedFormatted, actualFormatted);
    }
}
