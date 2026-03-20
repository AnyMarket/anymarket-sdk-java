package br.com.anymarket.sdk.order.dto;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class InvoiceResourceTest {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void should_serialize_and_deserialize_invoice_with_fiscal_metadata() throws Exception {
        String payload = "{\"accessKey\":\"A1\",\"series\":\"1\",\"number\":\"100\",\"fiscalMetadata\":{\"operationType\":\"saida\",\"emissionType\":\"normal\",\"authorizationProtocol\":\"12345\"}}";

        InvoiceResource invoice = objectMapper.readValue(payload, InvoiceResource.class);

        assertNotNull(invoice.getFiscalMetadata());
        assertEquals("saida", invoice.getFiscalMetadata().getOperationType());
        assertEquals("normal", invoice.getFiscalMetadata().getEmissionType());
        assertEquals("12345", invoice.getFiscalMetadata().getAuthorizationProtocol());

        String json = objectMapper.writeValueAsString(invoice);
        assertTrue(json.contains("\"fiscalMetadata\""));
        assertTrue(json.contains("\"operationType\":\"saida\""));
        assertTrue(json.contains("\"emissionType\":\"normal\""));
        assertTrue(json.contains("\"authorizationProtocol\":\"12345\""));
    }

    @Test
    public void should_include_fiscal_metadata_in_to_string() {
        InvoiceResource invoice = new InvoiceResource();
        FiscalMetadataResource fiscalMetadata = new FiscalMetadataResource();
        fiscalMetadata.setOperationType("entrada");
        fiscalMetadata.setEmissionType("contingencia");
        fiscalMetadata.setAuthorizationProtocol("999");
        invoice.setFiscalMetadata(fiscalMetadata);

        String text = invoice.toString();

        assertTrue(text.contains("fiscalMetadata"));
    }
}
