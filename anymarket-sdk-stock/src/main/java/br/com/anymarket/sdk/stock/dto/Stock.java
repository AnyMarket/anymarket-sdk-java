package br.com.anymarket.sdk.stock.dto;

import br.com.anymarket.sdk.AnymarketPojo;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

import static java.util.Objects.isNull;

/**
 * This class represents Stock info to be sent to AnyMarket by API v2
 */
public class Stock implements AnymarketPojo {

    @JsonProperty("id")
    private Long id;
    @JsonProperty("partnerId")
    private String partnerId;
    @JsonProperty("quantity")
    private BigDecimal quantity;
    @JsonProperty("cost")
    private BigDecimal costPrice;
    @JsonProperty("additionalTime")
    private Integer additionalTime;
    @JsonProperty("stockLocalId")
    private Long stockLocalId;
    @JsonProperty("isKit")
    private boolean isKit;
    @JsonProperty("skuLocationDescription")
    private String skuLocationDescription;
    @JsonProperty("idSku")
    private Long idSku;


    public Stock() {}

    private Stock(Builder builder) {
        if (isNull(builder.id) && isNull(builder.partnerId)) {
            throw new IllegalArgumentException(
                "It must be filled the id or partnerId");
        }
        if (isNull(builder.quantity) && isNull(builder.costPrice)) {
            throw new IllegalArgumentException(
                "It must be filled the quantity or cost");
        }
        this.id = builder.id;
        this.partnerId = builder.partnerId;
        this.quantity = builder.quantity;
        this.costPrice = builder.costPrice;
        this.additionalTime = builder.additionalTime;
        this.stockLocalId = builder.stockLocalId;
        this.isKit = builder.isKit;
        this.skuLocationDescription = builder.skuLocationDescription;
        this.idSku = builder.idSku;
    }

    public static Builder builder() {
        return new Builder();
    }

    public Long getId() {
        return id;
    }

    public String getPartnerId() {
        return partnerId;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    public BigDecimal getCostPrice() {
        return costPrice;
    }

    public Integer getAdditionalTime() {
        return additionalTime;
    }

    public Long getStockLocalId() {
        return stockLocalId;
    }

    public boolean isKit() {
        return isKit;
    }

    public String getSkuLocationDescription() {
        return skuLocationDescription;
    }

    public Long getIdSku() {
        return idSku;
    }

    @Override
    public String getPathURI() {
        return "/stocks";
    }

    public static class Builder {

        private Long id;
        private String partnerId;
        private BigDecimal quantity;
        private BigDecimal costPrice;
        private Integer additionalTime;
        private Long stockLocalId;
        private Boolean isKit;
        private String skuLocationDescription;
        private Long idSku;

        public Builder withId(Long id) {
            this.id = id;
            return this;
        }

        public Builder withPartnerId(String partnerId) {
            this.partnerId = partnerId;
            return this;
        }

        public Builder withQuantity(BigDecimal quantity) {
            this.quantity = quantity;
            return this;
        }

        public Builder withCostPrice(BigDecimal costPrice) {
            this.costPrice = costPrice;
            return this;
        }

        public Builder withAdditionalTime(Integer additionalTime) {
            this.additionalTime = additionalTime;
            return this;
        }

        public Builder withStockLocalId(Long stockLocalId) {
            this.stockLocalId = stockLocalId;
            return this;
        }

        public Builder withIsKit(Boolean isKit) {
            this.isKit = isKit;
            return this;
        }

        public Builder withSkuLocationDescription(String skuLocationDescription) {
            this.skuLocationDescription = skuLocationDescription;
            return this;
        }

        public Builder withIdSku(Long idSku) {
            this.idSku = idSku;
            return this;
        }

        public Stock build() {
            return new Stock(this);
        }

    }

}
