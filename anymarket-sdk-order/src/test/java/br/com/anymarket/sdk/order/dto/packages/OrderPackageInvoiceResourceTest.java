package br.com.anymarket.sdk.order.dto.packages;

import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.*;

public class OrderPackageInvoiceResourceTest {

    @Test
    public void should_set_and_get_all_fields() {
        Date date = new Date();
        OrderPackageInvoiceResource resource = new OrderPackageInvoiceResource();
        resource.setPackageId("pkg-001");
        resource.setDate(date);
        resource.setNumber("NF-001");
        resource.setAccessKey("KEY-001");
        resource.setSeries("1");
        resource.setInvoiceLink("http://invoice.example.com");
        resource.setLinkNfe("http://nfe.example.com");
        resource.setCfop("6102");
        resource.setCompanyStateTaxId("IE-001");
        resource.setExtraDescription("extra desc");
        resource.setOperationType("saida");
        resource.setEmissionType("normal");
        resource.setAuthorizationProtocol("AUTH-001");

        assertEquals("pkg-001", resource.getPackageId());
        assertEquals(date, resource.getDate());
        assertEquals("NF-001", resource.getNumber());
        assertEquals("KEY-001", resource.getAccessKey());
        assertEquals("1", resource.getSeries());
        assertEquals("http://invoice.example.com", resource.getInvoiceLink());
        assertEquals("http://nfe.example.com", resource.getLinkNfe());
        assertEquals("6102", resource.getCfop());
        assertEquals("IE-001", resource.getCompanyStateTaxId());
        assertEquals("extra desc", resource.getExtraDescription());
        assertEquals("saida", resource.getOperationType());
        assertEquals("normal", resource.getEmissionType());
        assertEquals("AUTH-001", resource.getAuthorizationProtocol());
    }

    @Test
    public void builder_should_create_instance_with_all_fields() {
        Date date = new Date();
        OrderPackageInvoiceResource resource = OrderPackageInvoiceResource.builder()
            .packageId("pkg-002")
            .date(date)
            .number("NF-002")
            .accessKey("KEY-002")
            .series("2")
            .invoiceLink("http://invoice2.example.com")
            .linkNfe("http://nfe2.example.com")
            .cfop("5102")
            .companyStateTaxId("IE-002")
            .extraDescription("desc 2")
            .operationType("entrada")
            .emissionType("contingencia")
            .authorizationProtocol("AUTH-002")
            .build();

        assertEquals("pkg-002", resource.getPackageId());
        assertEquals(date, resource.getDate());
        assertEquals("NF-002", resource.getNumber());
        assertEquals("KEY-002", resource.getAccessKey());
        assertEquals("2", resource.getSeries());
        assertEquals("http://invoice2.example.com", resource.getInvoiceLink());
        assertEquals("http://nfe2.example.com", resource.getLinkNfe());
        assertEquals("5102", resource.getCfop());
        assertEquals("IE-002", resource.getCompanyStateTaxId());
        assertEquals("desc 2", resource.getExtraDescription());
        assertEquals("entrada", resource.getOperationType());
        assertEquals("contingencia", resource.getEmissionType());
        assertEquals("AUTH-002", resource.getAuthorizationProtocol());
    }

    @Test
    public void no_args_constructor_should_create_empty_instance() {
        assertNotNull(new OrderPackageInvoiceResource());
    }
}
