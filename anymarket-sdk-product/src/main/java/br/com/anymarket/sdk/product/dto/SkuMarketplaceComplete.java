package br.com.anymarket.sdk.product.dto;

import br.com.anymarket.sdk.MarketPlace;
import br.com.anymarket.sdk.SKUMpOrigin;
import br.com.anymarket.sdk.dto.AvailableStockDTO;
import br.com.anymarket.sdk.dto.IndividualAvailableStockDTO;
import br.com.anymarket.sdk.serializer.MarketPlaceProperty;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SkuMarketplaceComplete implements Serializable {

    @JsonProperty(value = "id")
    private Long id;

    @JsonProperty(value = "accountName")
    private String accountName;

    @JsonProperty(value = "idAccount")
    private Long idAccount;

    @JsonProperty(value = "idInMarketplace")
    private String idInMarketplace;

    @MarketPlaceProperty
    @JsonProperty(value = "marketPlace")
    private MarketPlace marketPlace;

    @JsonProperty(value = "index")
    private Integer index;

    @JsonProperty(value = "publicationStatus")
    private String publicationStatus;

    @JsonProperty(value = "transmissionStatus")
    private String transmissionStatus;

    @JsonProperty(value = "errorMsg")
    private String errorMsg;

    @JsonProperty(value = "marketplaceStatus")
    private String statusInMarketplace;

    @JsonProperty(value = "price")
    private BigDecimal price;

    @JsonProperty(value = "priceFactor")
    private BigDecimal priceFactor;

    @JsonProperty(value = "discountPrice")
    private BigDecimal discountPrice;

    @JsonProperty(value = "skuInMarketplace")
    private String skuInMarketplace;

    @JsonProperty(value = "fields")
    private Map<String, String> fields = new HashMap<>();

    @JsonProperty(value = "availableStock")
    private AvailableStockDTO availableStock;

    @JsonProperty(value = "availableStocks")
    private List<IndividualAvailableStockDTO> individualAvailableStocks;

    @JsonProperty(value = "marketplaceItemCode")
    private String marketplaceItemCode;

    @JsonProperty(value = "attributes")
    private Map<String, String> attributes = new HashMap<>();

    @JsonProperty(value = "sku")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private SkuResource sku;

    @JsonProperty(value = "skuMarketPlaceMessage")
    private SkuMarketPlaceMessage skuMarketPlaceMessage;

    @JsonProperty(value = "warnings")
    private Set<String> warnings;

    @JsonProperty(value = "origin")
    private SKUMpOrigin origin;

    @JsonProperty(value = "variationId")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String variationId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public Long getIdAccount() {
        return idAccount;
    }

    public void setIdAccount(Long idAccount) {
        this.idAccount = idAccount;
    }

    public String getIdInMarketplace() {
        return idInMarketplace;
    }

    public void setIdInMarketplace(String idInMarketplace) {
        this.idInMarketplace = idInMarketplace;
    }

    public MarketPlace getMarketPlace() {
        return marketPlace;
    }

    public void setMarketPlace(MarketPlace marketPlace) {
        this.marketPlace = marketPlace;
    }

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

    public String getPublicationStatus() {
        return publicationStatus;
    }

    public void setPublicationStatus(String publicationStatus) {
        this.publicationStatus = publicationStatus;
    }

    public String getTransmissionStatus() {
        return transmissionStatus;
    }

    public void setTransmissionStatus(String transmissionStatus) {
        this.transmissionStatus = transmissionStatus;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public String getStatusInMarketplace() {
        return statusInMarketplace;
    }

    public void setStatusInMarketplace(String statusInMarketplace) {
        this.statusInMarketplace = statusInMarketplace;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getPriceFactor() {
        return priceFactor;
    }

    public void setPriceFactor(BigDecimal priceFactor) {
        this.priceFactor = priceFactor;
    }

    public BigDecimal getDiscountPrice() {
        return discountPrice;
    }

    public void setDiscountPrice(BigDecimal discountPrice) {
        this.discountPrice = discountPrice;
    }

    public String getSkuInMarketplace() {
        return skuInMarketplace;
    }

    public void setSkuInMarketplace(String skuInMarketplace) {
        this.skuInMarketplace = skuInMarketplace;
    }

    public Map<String, String> getFields() {
        return fields;
    }

    public void setFields(Map<String, String> fields) {
        this.fields = fields;
    }

    public AvailableStockDTO getAvailableStock() {
        return availableStock;
    }

    public void setAvailableStock(AvailableStockDTO availableStock) {
        this.availableStock = availableStock;
    }

    public List<IndividualAvailableStockDTO> getIndividualAvailableStocks() {
        return individualAvailableStocks;
    }

    public void setIndividualAvailableStocks(List<IndividualAvailableStockDTO> individualAvailableStocks) {
        this.individualAvailableStocks = individualAvailableStocks;
    }

    public String getMarketplaceItemCode() {
        return marketplaceItemCode;
    }

    public void setMarketplaceItemCode(String marketplaceItemCode) {
        this.marketplaceItemCode = marketplaceItemCode;
    }

    public Map<String, String> getAttributes() {
        return attributes;
    }

    public void setAttributes(Map<String, String> attributes) {
        this.attributes = attributes;
    }

    public SkuResource getSku() {
        return sku;
    }

    public void setSku(SkuResource sku) {
        this.sku = sku;
    }

    public SkuMarketPlaceMessage getSkuMarketPlaceMessage() {
        return skuMarketPlaceMessage;
    }

    public void setSkuMarketPlaceMessage(SkuMarketPlaceMessage skuMarketPlaceMessage) {
        this.skuMarketPlaceMessage = skuMarketPlaceMessage;
    }

    public Set<String> getWarnings() {
        return warnings;
    }

    public void setWarnings(Set<String> warnings) {
        this.warnings = warnings;
    }

    public SKUMpOrigin getOrigin() {
        return origin;
    }

    public void setOrigin(SKUMpOrigin origin) {
        this.origin = origin;
    }

    public String getVariationId() {
        return variationId;
    }

    public void setVariationId(String variationId) {
        this.variationId = variationId;
    }
}
