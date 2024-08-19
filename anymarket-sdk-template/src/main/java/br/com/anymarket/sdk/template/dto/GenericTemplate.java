package br.com.anymarket.sdk.template.dto;

import br.com.anymarket.sdk.MarketPlace;
import com.fasterxml.jackson.annotation.JsonProperty;

public class GenericTemplate {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("name")
    private String name;

    @JsonProperty("isActive")
    private boolean isActive;

    @JsonProperty("marketPlace")
    private MarketPlace marketPlace;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public MarketPlace getMarketPlace() {
        return marketPlace;
    }

    public void setMarketPlace(MarketPlace marketPlace) {
        this.marketPlace = marketPlace;
    }
}
