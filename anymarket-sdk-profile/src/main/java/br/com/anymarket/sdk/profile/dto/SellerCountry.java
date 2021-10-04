package br.com.anymarket.sdk.profile.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SellerCountry {

    @JsonProperty("name")
    private String name;

    @JsonProperty("initials")
    private String initials;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getInitials() {
        return initials;
    }

    public void setInitials(String initials) {
        this.initials = initials;
    }
}
