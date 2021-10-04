package br.com.anymarket.sdk.profile.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Seller {

    @JsonProperty("businessName")
    private String businessName;

    @JsonProperty("country")
    private SellerCountry sellerCountry;

    public String getBusinessName() {
        return businessName;
    }

    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }

    public SellerCountry getSellerCountry() {
        return sellerCountry;
    }

    public void setSellerCountry(SellerCountry sellerCountry) {
        this.sellerCountry = sellerCountry;
    }
}
