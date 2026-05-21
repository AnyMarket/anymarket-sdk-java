package br.com.anymarket.sdk.order.dto;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.*;

public class OrderPackageShippingTest {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void should_build_with_all_fields() {
        Date date = new Date(1_000_000L);
        OrderPackageShipping shipping = OrderPackageShipping.builder()
                .trackingNumber("BR123456789BR")
                .carrier("Correios")
                .shippedDate(date)
                .build();

        assertEquals("BR123456789BR", shipping.getTrackingNumber());
        assertEquals("Correios", shipping.getCarrier());
        assertEquals(date, shipping.getShippedDate());
    }

    @Test
    public void should_build_with_no_fields() {
        OrderPackageShipping shipping = OrderPackageShipping.builder().build();

        assertNotNull(shipping);
        assertNull(shipping.getTrackingNumber());
        assertNull(shipping.getCarrier());
        assertNull(shipping.getShippedDate());
    }

    @Test
    public void should_build_with_only_tracking_number() {
        OrderPackageShipping shipping = OrderPackageShipping.builder()
                .trackingNumber("JD123456789BR")
                .build();

        assertEquals("JD123456789BR", shipping.getTrackingNumber());
        assertNull(shipping.getCarrier());
        assertNull(shipping.getShippedDate());
    }

    @Test
    public void should_set_and_get_all_fields_via_setter() {
        Date date = new Date(5_000_000L);
        OrderPackageShipping shipping = new OrderPackageShipping();
        shipping.setTrackingNumber("UP999999999BR");
        shipping.setCarrier("Jadlog");
        shipping.setShippedDate(date);

        assertEquals("UP999999999BR", shipping.getTrackingNumber());
        assertEquals("Jadlog", shipping.getCarrier());
        assertEquals(date, shipping.getShippedDate());
    }

    @Test
    public void should_serialize_all_fields_to_json() throws Exception {
        OrderPackageShipping shipping = OrderPackageShipping.builder()
                .trackingNumber("BR123456789BR")
                .carrier("Correios")
                .build();

        String json = objectMapper.writeValueAsString(shipping);

        assertTrue(json.contains("\"trackingNumber\":\"BR123456789BR\""));
        assertTrue(json.contains("\"carrier\":\"Correios\""));
    }

    @Test
    public void should_serialize_null_shipped_date_as_null() throws Exception {
        OrderPackageShipping shipping = OrderPackageShipping.builder()
                .trackingNumber("T001")
                .build();

        String json = objectMapper.writeValueAsString(shipping);

        assertTrue(json.contains("\"shippedDate\":null"));
    }

    @Test
    public void should_serialize_shipped_date_not_as_raw_timestamp() throws Exception {
        Date date = new Date(0L);
        OrderPackageShipping shipping = OrderPackageShipping.builder()
                .shippedDate(date)
                .build();

        String json = objectMapper.writeValueAsString(shipping);

        assertTrue("JSON deve conter campo shippedDate", json.contains("shippedDate"));
        assertFalse("Não deve serializar como epoch 0 bruto", json.matches(".*\"shippedDate\":\\s*0[^.].*"));
    }

    @Test
    public void should_deserialize_from_json() throws Exception {
        String json = "{\"trackingNumber\":\"DE999999999BR\",\"carrier\":\"DHL\",\"shippedDate\":null}";

        OrderPackageShipping shipping = objectMapper.readValue(json, OrderPackageShipping.class);

        assertEquals("DE999999999BR", shipping.getTrackingNumber());
        assertEquals("DHL", shipping.getCarrier());
        assertNull(shipping.getShippedDate());
    }

    @Test
    public void should_serialize_and_deserialize_roundtrip() throws Exception {
        OrderPackageShipping original = OrderPackageShipping.builder()
                .trackingNumber("RT-001")
                .carrier("FedEx")
                .build();

        String json = objectMapper.writeValueAsString(original);
        OrderPackageShipping deserialized = objectMapper.readValue(json, OrderPackageShipping.class);

        assertEquals(original.getTrackingNumber(), deserialized.getTrackingNumber());
        assertEquals(original.getCarrier(), deserialized.getCarrier());
    }
}
