package br.com.anymarket.sdk.order.dto;

import br.com.anymarket.sdk.MarketPlace;
import br.com.anymarket.sdk.serializer.MarketPlaceProperty;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.collect.Lists;

import java.math.BigDecimal;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class OrderReservation {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("marketPlaceId")
    private String marketPlaceId;

    @MarketPlaceProperty
    @JsonProperty("marketPlace")
    private MarketPlace marketPlace;

    @JsonProperty("total")
    private BigDecimal total;

    @JsonProperty("items")
    private List<OrderReservationItems> items = Lists.newArrayList();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMarketPlaceId() {
        return marketPlaceId;
    }

    public void setMarketPlaceId(String marketPlaceId) {
        this.marketPlaceId = marketPlaceId;
    }

    public MarketPlace getMarketPlace() {
        return marketPlace;
    }

    public void setMarketPlace(MarketPlace marketPlace) {
        this.marketPlace = marketPlace;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public List<OrderReservationItems> getItems() {
        return items;
    }

    public void setItems(List<OrderReservationItems> items) {
        this.items = items;
    }
}
