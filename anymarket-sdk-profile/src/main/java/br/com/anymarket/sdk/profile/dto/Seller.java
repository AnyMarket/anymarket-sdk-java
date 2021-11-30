package br.com.anymarket.sdk.profile.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Seller {

    @JsonProperty("country")
    private String sellerCountry;

    public String getSellerCountry() {
        return sellerCountry;
    }

    public void setSellerCountry(String sellerCountry) {
        this.sellerCountry = sellerCountry;
    }
}
