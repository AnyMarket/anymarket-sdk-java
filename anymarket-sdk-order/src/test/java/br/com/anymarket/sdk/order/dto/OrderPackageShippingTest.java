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
        ShippingPackage shipping = ShippingPackage.builder()
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
        ShippingPackage shipping = ShippingPackage.builder().build();

        assertNotNull(shipping);
        assertNull(shipping.getTrackingNumber());
        assertNull(shipping.getCarrier());
        assertNull(shipping.getShippedDate());
    }

    @Test
    public void should_build_with_only_tracking_number() {
        ShippingPackage shipping = ShippingPackage.builder()
                .trackingNumber("JD123456789BR")
                .build();

        assertEquals("JD123456789BR", shipping.getTrackingNumber());
        assertNull(shipping.getCarrier());
        assertNull(shipping.getShippedDate());
    }

    @Test
    public void should_set_and_get_all_fields_via_setter() {
        Date date = new Date(5_000_000L);
        ShippingPackage shipping = new ShippingPackage();
        shipping.setTrackingNumber("UP999999999BR");
        shipping.setCarrier("Jadlog");
        shipping.setShippedDate(date);

        assertEquals("UP999999999BR", shipping.getTrackingNumber());
        assertEquals("Jadlog", shipping.getCarrier());
        assertEquals(date, shipping.getShippedDate());
    }

    @Test
    public void should_serialize_all_fields_to_json() throws Exception {
        ShippingPackage shipping = ShippingPackage.builder()
                .trackingNumber("BR123456789BR")
                .carrier("Correios")
                .build();

        String json = objectMapper.writeValueAsString(shipping);

        assertTrue(json.contains("\"trackingNumber\":\"BR123456789BR\""));
        assertTrue(json.contains("\"carrier\":\"Correios\""));
    }

    @Test
    public void should_serialize_shipped_date_not_as_raw_timestamp() throws Exception {
        Date date = new Date(0L);
        ShippingPackage shipping = ShippingPackage.builder()
                .shippedDate(date)
                .build();

        String json = objectMapper.writeValueAsString(shipping);

        assertTrue("JSON deve conter campo shippedDate", json.contains("shippedDate"));
        assertFalse("Não deve serializar como epoch 0 bruto", json.matches(".*\"shippedDate\":\\s*0[^.].*"));
    }

    @Test
    public void should_deserialize_from_json() throws Exception {
        String json = "{\"trackingNumber\":\"DE999999999BR\",\"carrier\":\"DHL\",\"shippedDate\":null}";

        ShippingPackage shipping = objectMapper.readValue(json, ShippingPackage.class);

        assertEquals("DE999999999BR", shipping.getTrackingNumber());
        assertEquals("DHL", shipping.getCarrier());
        assertNull(shipping.getShippedDate());
    }

    @Test
    public void should_serialize_and_deserialize_roundtrip() throws Exception {
        ShippingPackage original = ShippingPackage.builder()
                .trackingNumber("RT-001")
                .carrier("FedEx")
                .build();

        String json = objectMapper.writeValueAsString(original);
        ShippingPackage deserialized = objectMapper.readValue(json, ShippingPackage.class);

        assertEquals(original.getTrackingNumber(), deserialized.getTrackingNumber());
        assertEquals(original.getCarrier(), deserialized.getCarrier());
    }
}
