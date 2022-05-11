package br.com.anymarket.sdk.parameter.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.MoreObjects;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ParameterMarketplaceDelivery {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("deliveryService")
    private MarketPlaceDelivery deliveryService;

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public MarketPlaceDelivery getDeliveryService() {
        return deliveryService;
    }

    public void setDeliveryService(final MarketPlaceDelivery deliveryService) {
        this.deliveryService = deliveryService;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("id", id)
                .add("deliveryService", deliveryService)
                .toString();
    }
}
