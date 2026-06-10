package br.com.anymarket.sdk.order.dto;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.*;

public class OrderPackageDeliveryTest {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void should_build_delivery_with_delivered_date() {
        Date now = new Date();
        DeliveryPackage delivery = DeliveryPackage.builder()
                .deliveredDate(now)
                .build();

        assertNotNull(delivery);
        assertEquals(now, delivery.getDeliveredDate());
    }

    @Test
    public void should_build_delivery_with_null_delivered_date() {
        DeliveryPackage delivery = DeliveryPackage.builder().build();

        assertNotNull(delivery);
        assertNull(delivery.getDeliveredDate());
    }

    @Test
    public void should_set_delivered_date_via_setter() {
        Date date = new Date(1_000_000L);
        DeliveryPackage delivery = new DeliveryPackage();
        delivery.setDeliveredDate(date);

        assertEquals(date, delivery.getDeliveredDate());
    }

    @Test
    public void should_serialize_delivered_date_as_string_not_timestamp() throws Exception {
        Date date = new Date(0L);
        DeliveryPackage delivery = DeliveryPackage.builder().deliveredDate(date).build();

        String json = objectMapper.writeValueAsString(delivery);

        assertTrue("JSON deve conter campo deliveredDate", json.contains("deliveredDate"));
        assertFalse("Não deve serializar como timestamp numérico puro", json.matches(".*\"deliveredDate\":\\s*0[^.].*"));
    }

    @Test
    public void should_serialize_null_delivered_date_as_null() throws Exception {
        DeliveryPackage delivery = DeliveryPackage.builder().build();

        String json = objectMapper.writeValueAsString(delivery);

        assertTrue(json.contains("\"deliveredDate\":null"));
    }
}
