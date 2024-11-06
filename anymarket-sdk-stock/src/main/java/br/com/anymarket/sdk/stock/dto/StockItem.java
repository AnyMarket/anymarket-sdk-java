package br.com.anymarket.sdk.stock.dto;

import br.com.anymarket.sdk.AnymarketPojo;
import br.com.anymarket.sdk.serializer.SDKDateSerializer;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.math.BigDecimal;
import java.util.Date;

import static java.util.Objects.isNull;

@JsonIgnoreProperties(ignoreUnknown = true)
public class StockItem implements AnymarketPojo {

    @JsonProperty("stockKeepingUnit")
    private StockKeepingUnit stockKeepingUnit;
    @JsonProperty("stockLocal")
    private StockLocal stockLocal;
    @JsonProperty("amount")
    private BigDecimal amount;
    @JsonProperty("reservationAmount")
    private BigDecimal reservationAmount;
    @JsonProperty("availableAmount")
    private BigDecimal availableAmount;
    @JsonProperty("price")
    private BigDecimal price;
    @JsonProperty("active")
    private Boolean active;
    @JsonProperty("lastStockUpdate")
    @JsonSerialize(using = SDKDateSerializer.class)
    private Date lastStockUpdate;
    @JsonProperty("additionalTime")
    private Integer additionalTime;
    @JsonProperty("isKit")
    private boolean isKit;
    @JsonProperty("skuLocationDescription")
    private String skuLocationDescription;


    public StockItem() {}

    private StockItem(StockItem.Builder builder) {
        if (isNull(builder.stockKeepingUnit) && isNull(builder.stockLocal)) {
            throw new IllegalArgumentException(
                    "It must be filled the stockKeepingUnit or stockLocal");
        }
        if (isNull(builder.amount) && isNull(builder.price) && isNull(builder.active)) {
            throw new IllegalArgumentException("It must be filled the amount or price or active");
        }

        this.stockKeepingUnit = builder.stockKeepingUnit;
        this.stockLocal = builder.stockLocal;
        this.amount = builder.amount;
        this.reservationAmount = builder.reservationAmount;
        this.availableAmount = builder.availableAmount;
        this.price = builder.price;
        this.active = builder.active;
        this.lastStockUpdate = builder.lastStockUpdate;
        this.additionalTime = builder.additionalTime;
        this.isKit = builder.isKit;
        this.skuLocationDescription = builder.skuLocationDescription;
    }

    public static class Builder {
        private StockKeepingUnit stockKeepingUnit;
        private StockLocal stockLocal;
        private BigDecimal amount;
        private BigDecimal reservationAmount;
        private BigDecimal availableAmount;
        private BigDecimal price;
        private Boolean active;
        private Date lastStockUpdate;
        private Integer additionalTime;
        private Boolean isKit;
        private String skuLocationDescription;

        public Builder withStockKeepingUnit(StockKeepingUnit stockKeepingUnit) {
            this.stockKeepingUnit = stockKeepingUnit;
            return this;
        }

        public Builder withStockLocal(StockLocal stockLocal) {
            this.stockLocal = stockLocal;
            return this;
        }

        public Builder withAmount(BigDecimal amount) {
            this.amount = amount;
            return this;
        }

        public Builder withReservationAmount(BigDecimal reservationAmount) {
            this.reservationAmount = reservationAmount;
            return this;
        }

        public Builder withAvailableAmount(BigDecimal availableAmount) {
            this.availableAmount = availableAmount;
            return this;
        }

        public Builder withPrice(BigDecimal price) {
            this.price = price;
            return this;
        }

        public Builder withActive(Boolean active) {
            this.active = active;
            return this;
        }

        public Builder withLastStockUpdate(Date lastStockUpdate) {
            this.lastStockUpdate = lastStockUpdate;
            return this;
        }

        public Builder withAdditionalTime(Integer additionalTime) {
            this.additionalTime = additionalTime;
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

        public StockItem build() {
            return new StockItem(this);
        }
    }

    @Override
    public String getPathURI() {
        return "/stocks";
    }

    public static StockItem.Builder builder() {
        return new StockItem.Builder();
    }

    public StockKeepingUnit getStockKeepingUnit() {
        return stockKeepingUnit;
    }

    public StockLocal getStockLocal() {
        return stockLocal;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public BigDecimal getReservationAmount() {
        return reservationAmount;
    }

    public BigDecimal getAvailableAmount() {
        return availableAmount;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public Boolean getActive() {
        return active;
    }

    public Date getLastStockUpdate() {
        return lastStockUpdate;
    }

    public Integer getAdditionalTime() {
        return additionalTime;
    }
    public Boolean isKit() {
        return isKit;
    }

    public String getSkuLocationDescription() {
        return skuLocationDescription;
    }

}
