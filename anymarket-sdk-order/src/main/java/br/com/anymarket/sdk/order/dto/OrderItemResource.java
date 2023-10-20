package br.com.anymarket.sdk.order.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.MoreObjects;
import com.google.common.collect.Lists;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class OrderItemResource {

    @JsonProperty("sku")
    private SimpleSkuResource sku;

    @JsonProperty("skuKit")
    private SimpleSkuKitResource skuKit;

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

    @JsonProperty("customizations")
    private List<OrderItemCustomizationsResource> customizations = new ArrayList<>();

    @JsonProperty("marketPlaceId")
    private String marketPlaceId;

    @JsonProperty("orderItemId")
    private Long orderItemId;

    @JsonProperty("idInMarketPlace")
    private String idInMarketPlace;

    @JsonProperty("listingType")
    private String listingType;

    @JsonProperty("stocks")
    private List<OrderItemStockResource> stocks = Lists.newArrayList();

    @JsonProperty("officialStoreId")
    private String officialStoreId;

    @JsonProperty("officialStoreName")
    private String officialStoreName;

    @JsonProperty("product")
    private SimpleProductResource product;


    public String getIdInMarketPlace() {
        return idInMarketPlace;
    }

    public void setIdInMarketPlace(String idInMarketPlace) {
        this.idInMarketPlace = idInMarketPlace;
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

    public List<OrderItemShippingResource> getShippings() {
        return shippings;
    }

    public void setShippings(List<OrderItemShippingResource> shippings) {
        this.shippings = shippings;
    }

    public String getMarketPlaceId() {
        return marketPlaceId;
    }

    public void setMarketPlaceId(String marketPlaceId) {
        this.marketPlaceId = marketPlaceId;
    }

    public Long getOrderItemId() {
        return orderItemId;
    }

    public void setOrderItemId(final Long orderItemId) {
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

    public String getOfficialStoreId() {
        return officialStoreId;
    }

    public void setOfficialStoreId(String officialStoreId) {
        this.officialStoreId = officialStoreId;
    }

    public String getOfficialStoreName() {
        return officialStoreName;
    }

    public void setOfficialStoreName(String officialStoreName) {
        this.officialStoreName = officialStoreName;
    }

    public SimpleSkuKitResource getSkuKit() {
        return skuKit;
    }

    public void setSkuKit(SimpleSkuKitResource skuKit) {
        this.skuKit = skuKit;
    }

    public List<OrderItemCustomizationsResource> getCustomizations() {
        return customizations;
    }

    public void setCustomizations(List<OrderItemCustomizationsResource> customizations) {
        this.customizations = customizations;
    }

    public SimpleProductResource getProduct() {
        return product;
    }

    public void setProduct(SimpleProductResource product) {
        this.product = product;
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
            .add("shippings", shippings)
            .add("marketPlaceId", marketPlaceId)
            .add("stocks", stocks)
            .add("officialStoreId", officialStoreId)
            .add("officialStoreName", officialStoreName)
            .add("product", product)
            .toString();
    }
}
