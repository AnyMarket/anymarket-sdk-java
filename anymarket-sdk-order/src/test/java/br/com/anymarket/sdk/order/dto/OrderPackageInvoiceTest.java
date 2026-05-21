package br.com.anymarket.sdk.order.dto;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.*;

public class OrderPackageInvoiceTest {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void should_build_with_all_fields() {
        Date date = new Date(1_000_000L);
        OrderPackageInvoice invoice = OrderPackageInvoice.builder()
                .invoiceNumber("NF-001")
                .invoiceUrl("https://nfe.example.com/NF-001.xml")
                .invoiceKey("12345678901234567890123456789012345678901234")
                .invoiceDate(date)
                .build();

        assertEquals("NF-001", invoice.getInvoiceNumber());
        assertEquals("https://nfe.example.com/NF-001.xml", invoice.getInvoiceUrl());
        assertEquals("12345678901234567890123456789012345678901234", invoice.getInvoiceKey());
        assertEquals(date, invoice.getInvoiceDate());
    }

    @Test
    public void should_build_with_no_fields() {
        OrderPackageInvoice invoice = OrderPackageInvoice.builder().build();

        assertNotNull(invoice);
        assertNull(invoice.getInvoiceNumber());
        assertNull(invoice.getInvoiceUrl());
        assertNull(invoice.getInvoiceKey());
        assertNull(invoice.getInvoiceDate());
    }

    @Test
    public void should_set_and_get_fields_via_setter() {
        Date date = new Date(2_000_000L);
        OrderPackageInvoice invoice = new OrderPackageInvoice();
        invoice.setInvoiceNumber("NF-002");
        invoice.setInvoiceUrl("https://nfe.example.com/NF-002.xml");
        invoice.setInvoiceKey("KEY-XYZ");
        invoice.setInvoiceDate(date);

        assertEquals("NF-002", invoice.getInvoiceNumber());
        assertEquals("https://nfe.example.com/NF-002.xml", invoice.getInvoiceUrl());
        assertEquals("KEY-XYZ", invoice.getInvoiceKey());
        assertEquals(date, invoice.getInvoiceDate());
    }

    @Test
    public void should_be_equal_when_same_fields() {
        Date date = new Date(0L);
        OrderPackageInvoice i1 = OrderPackageInvoice.builder()
                .invoiceNumber("NF-001").invoiceKey("KEY-1").invoiceDate(date).build();
        OrderPackageInvoice i2 = OrderPackageInvoice.builder()
                .invoiceNumber("NF-001").invoiceKey("KEY-1").invoiceDate(date).build();

        assertEquals(i1, i2);
        assertEquals(i1.hashCode(), i2.hashCode());
    }

    @Test
    public void should_not_be_equal_when_different_invoice_number() {
        OrderPackageInvoice i1 = OrderPackageInvoice.builder().invoiceNumber("NF-001").build();
        OrderPackageInvoice i2 = OrderPackageInvoice.builder().invoiceNumber("NF-002").build();

        assertNotEquals(i1, i2);
    }

    @Test
    public void should_not_be_equal_when_different_invoice_key() {
        OrderPackageInvoice i1 = OrderPackageInvoice.builder().invoiceKey("KEY-A").build();
        OrderPackageInvoice i2 = OrderPackageInvoice.builder().invoiceKey("KEY-B").build();

        assertNotEquals(i1, i2);
    }

    @Test
    public void should_contain_field_names_in_to_string() {
        OrderPackageInvoice invoice = OrderPackageInvoice.builder()
                .invoiceNumber("NF-999")
                .build();

        String text = invoice.toString();

        assertTrue(text.contains("invoiceNumber"));
        assertTrue(text.contains("NF-999"));
    }

    @Test
    public void should_serialize_all_fields_to_json() throws Exception {
        OrderPackageInvoice invoice = OrderPackageInvoice.builder()
                .invoiceNumber("NF-001")
                .invoiceUrl("https://nfe.example.com/NF-001.xml")
                .invoiceKey("KEY-123")
                .build();

        String json = objectMapper.writeValueAsString(invoice);

        assertTrue(json.contains("\"invoiceNumber\":\"NF-001\""));
        assertTrue(json.contains("\"invoiceUrl\":\"https://nfe.example.com/NF-001.xml\""));
        assertTrue(json.contains("\"invoiceKey\":\"KEY-123\""));
    }

    @Test
    public void should_serialize_null_invoice_date_as_null() throws Exception {
        OrderPackageInvoice invoice = OrderPackageInvoice.builder()
                .invoiceNumber("NF-001")
                .build();

        String json = objectMapper.writeValueAsString(invoice);

        assertTrue(json.contains("\"invoiceDate\":null"));
    }

    @Test
    public void should_serialize_invoice_date_not_as_raw_timestamp() throws Exception {
        Date date = new Date(0L);
        OrderPackageInvoice invoice = OrderPackageInvoice.builder()
                .invoiceDate(date)
                .build();

        String json = objectMapper.writeValueAsString(invoice);

        assertTrue("JSON deve conter campo invoiceDate", json.contains("invoiceDate"));
        assertFalse("Não deve serializar como epoch 0 bruto", json.matches(".*\"invoiceDate\":\\s*0[^.].*"));
    }

    @Test
    public void should_deserialize_from_json() throws Exception {
        String json = "{\"invoiceNumber\":\"NF-042\",\"invoiceUrl\":\"https://nfe.example.com\",\"invoiceKey\":\"K42\",\"invoiceDate\":null}";

        OrderPackageInvoice invoice = objectMapper.readValue(json, OrderPackageInvoice.class);

        assertEquals("NF-042", invoice.getInvoiceNumber());
        assertEquals("https://nfe.example.com", invoice.getInvoiceUrl());
        assertEquals("K42", invoice.getInvoiceKey());
        assertNull(invoice.getInvoiceDate());
    }
}
