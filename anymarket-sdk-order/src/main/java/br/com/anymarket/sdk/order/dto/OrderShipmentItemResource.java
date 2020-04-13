package br.com.anymarket.sdk.order.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.MoreObjects;
import com.google.common.collect.Lists;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class OrderShipmentItemResource {

    @JsonProperty("sku")
    private SimpleSkuResource sku;

    @JsonProperty("amount")
    private BigDecimal amount;

    @JsonProperty("unit")
    private BigDecimal unit;

    @JsonProperty("gross")
    private BigDecimal gross;

    @JsonProperty("total")
    private BigDecimal total;

    @JsonProperty("discount")
    private BigDecimal discount;

    @JsonProperty("shippings")
    private List<OrderItemShippingResource> shippings = new ArrayList();

    @JsonProperty("marketPlaceId")
    private String marketPlaceId;

    @JsonProperty("orderItemId")
    private Long orderItemId;

    @JsonProperty("idInMarketPlace")
    private String idInMarketPlace;

    @JsonProperty("skuInMarketplace")
    private String skuInMarketplace;

    @JsonProperty("listingType")
    private String listingType;

    @JsonProperty("stocks")
    private List<OrderItemStockResource> stocks = Lists.newArrayList();

    public OrderShipmentItemResource() {
        super();
    }

    public SimpleSkuResource getSku() {
        return sku;
    }

    public void setSku(SimpleSkuResource sku) {
        this.sku = sku;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getUnit() {
        return unit;
    }

    public void setUnit(BigDecimal unit) {
        this.unit = unit;
    }

    public BigDecimal getGross() {
        return gross;
    }

    public void setGross(BigDecimal gross) {
        this.gross = gross;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public BigDecimal getDiscount() {
        return discount;
    }

    public void setDiscount(BigDecimal discount) {
        this.discount = discount;
    }

    public String getIdInMarketPlace() {
        return idInMarketPlace;
    }

    public void setIdInMarketPlace(String idInMarketPlace) {
        this.idInMarketPlace = idInMarketPlace;
    }

    public String getSkuInMarketplace() {
        return skuInMarketplace;
    }

    public void setSkuInMarketplace(String skuInMarketplace) {
        this.skuInMarketplace = skuInMarketplace;
    }

    public String getMarketPlaceId() {
        return marketPlaceId;
    }

    public void setMarketPlaceId(String marketPlaceId) {
        this.marketPlaceId = marketPlaceId;
    }

    public List<OrderItemShippingResource> getShippings() {
        return shippings;
    }

    public void setShippings(List<OrderItemShippingResource> shippings) {
        this.shippings = shippings;
    }

    public Long getOrderItemId() {
        return orderItemId;
    }

    public void setOrderItemId(Long orderItemId) {
        this.orderItemId = orderItemId;
    }

    public String getListingType() {
        return listingType;
    }

    public void setListingType(String listingType) {
        this.listingType = listingType;
    }

    public List<OrderItemStockResource> getStocks() {
        return stocks;
    }

    public void setStocks(List<OrderItemStockResource> stocks) {
        this.stocks = stocks;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("sku", sku)
                .add("amount", amount)
                .add("unit", unit)
                .add("gross", gross)
                .add("total", total)
                .add("discount", discount)
                .add("marketPlaceId", marketPlaceId)
                .add("idInMarketPlace", idInMarketPlace)
                .add("skuInMarketplace", skuInMarketplace)
                .add("shippings", shippings)
                .add("stocks", stocks)
                .toString();
    }
}
