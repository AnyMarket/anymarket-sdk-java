package br.com.anymarket.sdk.order.dto;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class FiscalMetadataResourceTest {

    @Test
    public void should_set_and_get_fiscal_metadata_fields() {
        FiscalMetadataResource fiscalMetadata = new FiscalMetadataResource();
        fiscalMetadata.setOperationType("saida");
        fiscalMetadata.setEmissionType("normal");
        fiscalMetadata.setAuthorizationProtocol("12345");

        assertEquals("saida", fiscalMetadata.getOperationType());
        assertEquals("normal", fiscalMetadata.getEmissionType());
        assertEquals("12345", fiscalMetadata.getAuthorizationProtocol());
    }
}
