package br.com.anymarket.sdk.parameter.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.MoreObjects;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ParameterXmlNfeImporter {
    @JsonProperty("id")
    private Long id;

    @JsonProperty("oi")
    private String oi;

    @JsonProperty("active")
    private boolean active;

    @JsonProperty("email")
    private String email;

    @JsonProperty("marketplaceDeliveries")
    private List<ParameterMarketplaceDelivery> marketplaceDeliveries;

    public Long getId() {
        return this.id;
    }

    public String getOi() {
        return this.oi;
    }

    public boolean isActive() {
        return this.active;
    }

    public String getEmail() {
        return this.email;
    }

    public List<ParameterMarketplaceDelivery> getMarketplaceDeliveries() {
        return this.marketplaceDeliveries;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public void setOi(final String oi) {
        this.oi = oi;
    }

    public void setActive(final boolean active) {
        this.active = active;
    }

    public void setEmail(final String email) {
        this.email = email;
    }

    public void setMarketplaceDeliveries(final List<ParameterMarketplaceDelivery> marketplaceDeliveries) {
        this.marketplaceDeliveries = marketplaceDeliveries;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("id", id)
                .add("oi", oi)
                .add("active", active)
                .add("email", email)
                .add("marketplaceDeliveries", marketplaceDeliveries)
                .toString();
    }
}
