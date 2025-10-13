package br.com.anymarket.sdk;

import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class MarketPlaceTest {

    @Test
    public void should_return_enum_when_name_exists() {
        MarketPlace result = MarketPlace.fromName("Amazon");
        assertNotNull(result);
        assertEquals(MarketPlace.AMAZON, result);
    }

    @Test
    public void should_return_enum_when_name_is_case_insensitive() {
        MarketPlace result = MarketPlace.fromName("mercado livre");
        assertNotNull(result);
        assertEquals(MarketPlace.MERCADO_LIVRE, result);
    }

    @Test
    public void should_return_null_when_name_does_not_exist() {
        MarketPlace result = MarketPlace.fromName("Inexistente");
        assertNull(result);
    }

    @Test
    public void should_return_null_when_name_is_blank_or_null() {
        assertNull(MarketPlace.fromName(""));
        assertNull(MarketPlace.fromName("   "));
        assertNull(MarketPlace.fromName(null));
    }

    @Test
    public void should_return_list_of_marketplaces_containing_param() {
        List<MarketPlace> results = MarketPlace.nameContaining("Amazon");
        assertFalse(results.isEmpty());
        assertTrue(results.contains(MarketPlace.AMAZON) || results.contains(MarketPlace.AMAZON_GLOBAL_API));
    }

    @Test
    public void should_return_list_case_insensitive() {
        List<MarketPlace> results = MarketPlace.nameContaining("mercado");
        assertTrue(results.contains(MarketPlace.MERCADO_LIVRE));
    }

    @Test
    public void should_return_empty_list_when_no_match_found() {
        List<MarketPlace> results = MarketPlace.nameContaining("Inexistente");
        assertTrue(results.isEmpty());
    }

    @Test
    public void should_return_queue_name_without_underscores() {
        assertEquals("AMAZONSPDS", MarketPlace.AMAZON_SPDS.getQueueName());
        assertEquals("B2WNEWAPI", MarketPlace.B2W_NEW_API.getQueueName());
    }

    @Test
    public void should_set_and_get_description() {
        MarketPlace marketplace = MarketPlace.AMAZON;
        String original = marketplace.getDescription();

        marketplace.setDescription("Amazon Teste");
        assertEquals("Amazon Teste", marketplace.getDescription());

        marketplace.setDescription(original);
    }

    @Test
    public void should_return_default_flags_for_simple_constructor() {
        MarketPlace marketplace = MarketPlace.ECOMMERCE;
        assertFalse(marketplace.isSpecificConsumer());
        assertFalse(marketplace.isAnyBindEnabled());
        assertFalse(marketplace.isAllowsChangeIdInMarketplace());
    }

    @Test
    public void should_return_custom_flags_for_different_constructors() {
        assertTrue(MarketPlace.MERCADO_LIVRE.isSpecificConsumer());
        assertTrue(MarketPlace.MERCADO_LIVRE.isAnyBindEnabled());
        assertFalse(MarketPlace.MERCADO_LIVRE.isAllowsChangeIdInMarketplace());

        assertTrue(MarketPlace.SHEIN.isAnyBindEnabled());
        assertTrue(MarketPlace.SHEIN.isAllowsChangeIdInMarketplace());
    }

    @Test
    public void should_contain_expected_marketplace_in_enum_values() {
        boolean containsAmazon = false;
        for (MarketPlace mp : MarketPlace.values()) {
            if (mp == MarketPlace.AMAZON) {
                containsAmazon = true;
                break;
            }
        }
        assertTrue(containsAmazon);
    }
}
