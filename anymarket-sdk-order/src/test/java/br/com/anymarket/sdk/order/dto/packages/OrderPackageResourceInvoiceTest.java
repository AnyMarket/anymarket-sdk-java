package br.com.anymarket.sdk.order.dto.packages;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import static org.junit.Assert.*;

public class OrderPackageResourceInvoiceTest {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void builder_should_create_instance_with_all_fields() {
        UUID packageId = UUID.randomUUID();
        Date issueDate = new Date();

        OrderPackageResourceInvoice invoice = OrderPackageResourceInvoice.builder()
            .packageId(packageId)
            .accessKey("ACCESS-KEY-001")
            .series("1")
            .numberNfe("NF-001")
            .issueDate(issueDate)
            .invoiceLink("http://invoice.example.com")
            .linkNfe("http://nfe.example.com")
            .cfop("6102")
            .companyStateTaxId("IE-001")
            .extraDescription("extra desc")
            .operationType("saida")
            .emissionType("normal")
            .authorizationProtocol("AUTH-001")
            .build();

        assertEquals(packageId, invoice.getPackageId());
        assertEquals("ACCESS-KEY-001", invoice.getAccessKey());
        assertEquals("1", invoice.getSeries());
        assertEquals("NF-001", invoice.getNumberNfe());
        assertEquals(issueDate, invoice.getIssueDate());
        assertEquals("http://invoice.example.com", invoice.getInvoiceLink());
        assertEquals("http://nfe.example.com", invoice.getLinkNfe());
        assertEquals("6102", invoice.getCfop());
        assertEquals("IE-001", invoice.getCompanyStateTaxId());
        assertEquals("extra desc", invoice.getExtraDescription());
        assertEquals("saida", invoice.getOperationType());
        assertEquals("normal", invoice.getEmissionType());
        assertEquals("AUTH-001", invoice.getAuthorizationProtocol());
    }

    @Test
    public void no_args_constructor_and_setters_should_work() {
        UUID packageId = UUID.randomUUID();
        Date issueDate = new Date();

        OrderPackageResourceInvoice invoice = new OrderPackageResourceInvoice();
        invoice.setPackageId(packageId);
        invoice.setAccessKey("ACCESS-KEY-002");
        invoice.setSeries("2");
        invoice.setNumberNfe("NF-002");
        invoice.setIssueDate(issueDate);
        invoice.setInvoiceLink("http://invoice2.example.com");
        invoice.setLinkNfe("http://nfe2.example.com");
        invoice.setCfop("5102");
        invoice.setCompanyStateTaxId("IE-002");
        invoice.setExtraDescription("desc 2");
        invoice.setOperationType("entrada");
        invoice.setEmissionType("contingencia");
        invoice.setAuthorizationProtocol("AUTH-002");

        assertEquals(packageId, invoice.getPackageId());
        assertEquals("ACCESS-KEY-002", invoice.getAccessKey());
        assertEquals("2", invoice.getSeries());
        assertEquals("NF-002", invoice.getNumberNfe());
        assertEquals(issueDate, invoice.getIssueDate());
        assertEquals("http://invoice2.example.com", invoice.getInvoiceLink());
        assertEquals("http://nfe2.example.com", invoice.getLinkNfe());
        assertEquals("5102", invoice.getCfop());
        assertEquals("IE-002", invoice.getCompanyStateTaxId());
        assertEquals("desc 2", invoice.getExtraDescription());
        assertEquals("entrada", invoice.getOperationType());
        assertEquals("contingencia", invoice.getEmissionType());
        assertEquals("AUTH-002", invoice.getAuthorizationProtocol());
    }

    @Test
    public void issueDate_should_round_trip_via_serialization() throws Exception {
        long epochSeconds = System.currentTimeMillis() / 1000;
        Date issueDate = new Date(epochSeconds * 1000);

        OrderPackageResourceInvoice invoice = OrderPackageResourceInvoice.builder()
            .issueDate(issueDate)
            .build();

        String json = objectMapper.writeValueAsString(invoice);
        OrderPackageResourceInvoice deserialized = objectMapper.readValue(json, OrderPackageResourceInvoice.class);

        String expectedFormatted = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX").format(issueDate);
        String actualFormatted = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX").format(deserialized.getIssueDate());
        assertEquals(expectedFormatted, actualFormatted);
    }
}
