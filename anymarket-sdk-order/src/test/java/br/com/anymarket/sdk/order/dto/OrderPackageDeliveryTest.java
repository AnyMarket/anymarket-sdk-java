package br.com.anymarket.sdk.order.dto;

import br.com.anymarket.sdk.order.OrderPackageDelivery;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.*;

public class OrderPackageDeliveryTest {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void should_build_delivery_with_delivered_date() {
        Date now = new Date();
        OrderPackageDelivery delivery = OrderPackageDelivery.builder()
                .deliveredDate(now)
                .build();

        assertNotNull(delivery);
        assertEquals(now, delivery.getDeliveredDate());
    }

    @Test
    public void should_build_delivery_with_null_delivered_date() {
        OrderPackageDelivery delivery = OrderPackageDelivery.builder().build();

        assertNotNull(delivery);
        assertNull(delivery.getDeliveredDate());
    }

    @Test
    public void should_set_delivered_date_via_setter() {
        Date date = new Date(1_000_000L);
        OrderPackageDelivery delivery = new OrderPackageDelivery();
        delivery.setDeliveredDate(date);

        assertEquals(date, delivery.getDeliveredDate());
    }

    @Test
    public void should_be_equal_when_same_delivered_date() {
        Date date = new Date(1_000_000L);
        OrderPackageDelivery d1 = OrderPackageDelivery.builder().deliveredDate(date).build();
        OrderPackageDelivery d2 = OrderPackageDelivery.builder().deliveredDate(date).build();

        assertEquals(d1, d2);
        assertEquals(d1.hashCode(), d2.hashCode());
    }

    @Test
    public void should_not_be_equal_when_different_dates() {
        OrderPackageDelivery d1 = OrderPackageDelivery.builder().deliveredDate(new Date(1_000L)).build();
        OrderPackageDelivery d2 = OrderPackageDelivery.builder().deliveredDate(new Date(2_000L)).build();

        assertNotEquals(d1, d2);
    }

    @Test
    public void should_serialize_delivered_date_as_string_not_timestamp() throws Exception {
        Date date = new Date(0L);
        OrderPackageDelivery delivery = OrderPackageDelivery.builder().deliveredDate(date).build();

        String json = objectMapper.writeValueAsString(delivery);

        assertTrue("JSON deve conter campo deliveredDate", json.contains("deliveredDate"));
        assertFalse("Não deve serializar como timestamp numérico puro", json.matches(".*\"deliveredDate\":\\s*0[^.].*"));
    }

    @Test
    public void should_serialize_null_delivered_date_as_null() throws Exception {
        OrderPackageDelivery delivery = OrderPackageDelivery.builder().build();

        String json = objectMapper.writeValueAsString(delivery);

        assertTrue(json.contains("\"deliveredDate\":null"));
    }

    @Test
    public void should_contain_field_name_in_to_string() {
        OrderPackageDelivery delivery = OrderPackageDelivery.builder()
                .deliveredDate(new Date(0L))
                .build();

        assertTrue(delivery.toString().contains("deliveredDate"));
    }
}
