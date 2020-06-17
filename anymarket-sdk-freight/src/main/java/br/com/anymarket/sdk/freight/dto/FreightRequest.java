package br.com.anymarket.sdk.freight.dto;

import br.com.anymarket.sdk.MarketPlace;
import br.com.anymarket.sdk.serializer.MarketPlaceProperty;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class FreightRequest {

    @MarketPlaceProperty
    @JsonProperty(value = "marketPlace")
    private MarketPlace marketPlace;

    @JsonProperty("zipCode")
    private String zipCode;

    @JsonProperty("additionalPercentual")
    private BigDecimal additionalPercentual;

    @JsonProperty("timeout")
    private Long timeout;

    @JsonProperty("products")
    private List<FreightProduct> products = new ArrayList<FreightProduct>();

    @JsonProperty("accountName")
    private String accountName;

    public List<FreightProduct> getProducts() {
        return products;
    }

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

    public BigDecimal getAdditionalPercentual() {
        return additionalPercentual;
    }

    public void setAdditionalPercentual(BigDecimal additionalPercentual) {
        this.additionalPercentual = additionalPercentual;
    }

    public Long getTimeout() {
        return timeout;
    }

    public void setTimeout(Long timeout) {
        this.timeout = timeout;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }
}
