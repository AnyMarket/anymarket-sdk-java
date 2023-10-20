package br.com.anymarket.sdk.product.dto;

import br.com.anymarket.sdk.variation.Variation;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static com.fasterxml.jackson.annotation.JsonProperty.Access.READ_ONLY;

public class SkuResource {
    @JsonProperty("id")
    private Long id;

    @JsonProperty("title")
    private String title;

    @JsonProperty("partnerId")
    private String partnerId;

    @JsonProperty("ean")
    private String ean;

    @JsonProperty("price")
    private BigDecimal price;

    @JsonProperty("sellPrice")
    private BigDecimal sellPrice;

    @JsonProperty("amount")
    private BigDecimal amount;

    @JsonProperty("additionalTime")
    private Integer additionalTime = 0;

    @JsonProperty(value = "variations", access = READ_ONLY)
    private List<Variation> variations = new ArrayList();

    @JsonProperty(value = "product") // atributo utilizado da API de marketplace
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private ProductBase product;

    @JsonProperty("volumes")
    private Integer volumes = 0;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPartnerId() {
        return partnerId;
    }

    public void setPartnerId(String partnerId) {
        this.partnerId = partnerId;
    }

    public String getEan() {
        return ean;
    }

    public void setEan(String ean) {
        this.ean = ean;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getSellPrice() {
        return sellPrice;
    }

    public void setSellPrice(BigDecimal sellPrice) {
        this.sellPrice = sellPrice;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Integer getAdditionalTime() {
        return additionalTime;
    }

    public void setAdditionalTime(Integer additionalTime) {
        this.additionalTime = additionalTime;
    }

    public List<Variation> getVariations() {
        return variations;
    }

    public void setVariations(List<Variation> variations) {
        this.variations = variations;
    }

    public ProductBase getProduct() {
        return product;
    }

    public void setProduct(ProductBase product) {
        this.product = product;
    }

    public Integer getVolumes() {
        return volumes;
    }

    public void setVolumes(Integer volumes) {
        this.volumes = volumes;
    }
}
