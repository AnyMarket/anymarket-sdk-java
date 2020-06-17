package br.com.anymarket.sdk.freight.dto;

import br.com.anymarket.sdk.MarketPlace;
import br.com.anymarket.sdk.serializer.MarketPlaceProperty;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FreightResponse {

    @MarketPlaceProperty
    @JsonProperty(value = "marketPlace")
    private MarketPlace marketPlace;

    @JsonProperty("zipCode")
    private String zipCode;

    @JsonProperty("products")
    private List<FreightProduct> products = new ArrayList<FreightProduct>();

    @JsonProperty("quotes")
    private List<FreightQuote> quotes = new ArrayList<FreightQuote>();

    @JsonProperty
    private Map<String, BigDecimal> missingSkus = new HashMap<String, BigDecimal>();

    @JsonProperty
    private FreightQuote defaultFreight;

    public MarketPlace getMarketPlace() {
        return marketPlace;
    }

    public void setMarketPlace(MarketPlace marketPlace) {
        this.marketPlace = marketPlace;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public List<FreightProduct> getProducts() {
        return products;
    }

    public List<FreightQuote> getQuotes() {
        return quotes;
    }

    public Map<String, BigDecimal> getMissingSkus() {
        return missingSkus;
    }

    public FreightQuote getDefaultFreight() {
        return defaultFreight;
    }

    public void setDefaultFreight(FreightQuote defaultFreight) {
        this.defaultFreight = defaultFreight;
    }

}
