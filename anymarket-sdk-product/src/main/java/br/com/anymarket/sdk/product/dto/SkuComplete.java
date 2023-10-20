package br.com.anymarket.sdk.product.dto;


import br.com.anymarket.sdk.variation.Variation;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.MoreObjects;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SkuComplete {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("partnerId")
    private String partnerId;

    @JsonProperty("amount")
    private BigDecimal stockAmount;

    @JsonProperty("ean")
    private String ean;

    @JsonProperty("title")
    private String title;

    @JsonProperty("price")
    private BigDecimal price;

    @JsonProperty("additionalTime")
    private Integer additionalTime;

    @JsonProperty(value = "variations")
    private List<Variation> variations = new ArrayList<Variation>();

    @JsonProperty("volumes")
    private Integer volumes = 0;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getStockAmount() {
        return stockAmount;
    }

    public void setStockAmount(BigDecimal stockAmount) {
        this.stockAmount = stockAmount;
    }

    public String getEan() {
        return ean;
    }

    public void setEan(String ean) {
        this.ean = ean;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getAdditionalTime() {
        return additionalTime;
    }

    public void setAdditionalTime(Integer additionalTime) {
        this.additionalTime = additionalTime;
    }

    public String getPartnerId() {
        return partnerId;
    }

    public void setPartnerId(String partnerId) {
        this.partnerId = partnerId;
    }

    public List<Variation> getVariations() {
        return variations;
    }

    public void setVariations(List<Variation> variations) {
        this.variations = variations;
    }

    public Integer getVolumes() {
        return volumes;
    }

    public void setVolumes(Integer volumes) {
        this.volumes = volumes;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
            .add("partnerId", partnerId)
            .add("stockAmount", stockAmount)
            .add("ean", ean)
            .add("title", title)
            .add("price", price)
            .add("variations", variations)
            .add("volumes", volumes)
            .toString();
    }
}
