package br.com.anymarket.sdk.product.dto;


import br.com.anymarket.sdk.brand.dto.Brand;
import br.com.anymarket.sdk.categories.dto.SimpleCategory;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.google.common.base.MoreObjects;

import java.math.BigDecimal;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Product {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("title")
    private String title;

    @JsonProperty("description")
    private String description;

    @JsonProperty("model")
    private String model;

    @JsonProperty("nbm")
    private Nbm nbm;

    @JsonProperty("videoUrl")
    private URL youtubeVideoUrl;

    @JsonProperty("characteristics")
    private List<Characteristics> characteristics = new ArrayList<Characteristics>();

    @JsonProperty("origin")
    @JsonDeserialize(using = OriginDeserializer.class)
    @JsonSerialize(using = OriginSerializer.class)
    private Origin origin;

    @JsonProperty("category")
    private SimpleCategory category;

    @JsonProperty("height")
    private BigDecimal height;

    @JsonProperty("length")
    private BigDecimal length;

    @JsonProperty("width")
    private BigDecimal width;

    @JsonProperty("weight")
    private BigDecimal weight;

    @JsonProperty("images")
    private List<Image> images;

    @JsonProperty("brand")
    private Brand brand;

    @JsonProperty("skus")
    private List<Sku> skus;

    @JsonProperty("warrantyTime")
    private Integer warrantyTime;

    @JsonProperty("warrantyText")
    private String warrantyText;

    @JsonProperty("priceFactor")
    private BigDecimal priceFactor = BigDecimal.ONE;

    @JsonProperty("calculatedPrice")
    private boolean calculatedPrice = true;

    @JsonProperty("hasVariations")
    private boolean hasVariations = false;

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public Nbm getNbm() {
        return nbm;
    }

    public void setNbm(Nbm nbm) {
        this.nbm = nbm;
    }

    public URL getYoutubeVideoUrl() {
        return youtubeVideoUrl;
    }

    public void setYoutubeVideoUrl(URL youtubeVideoUrl) {
        this.youtubeVideoUrl = youtubeVideoUrl;
    }

    public List<Characteristics> getCharacteristics() {
        return characteristics;
    }

    public void setCharacteristics(List<Characteristics> characteristics) {
        this.characteristics = characteristics;
    }

    public Origin getOrigin() {
        return origin;
    }

    public void setOrigin(Origin origin) {
        this.origin = origin;
    }

    public SimpleCategory getCategory() {
        return category;
    }

    public void setCategory(SimpleCategory category) {
        this.category = category;
    }

    public BigDecimal getHeight() {
        return height;
    }

    public void setHeight(BigDecimal height) {
        this.height = height;
    }

    public BigDecimal getLength() {
        return length;
    }

    public void setLength(BigDecimal length) {
        this.length = length;
    }

    public BigDecimal getWidth() {
        return width;
    }

    public void setWidth(BigDecimal width) {
        this.width = width;
    }

    public BigDecimal getWeight() {
        return weight;
    }

    public void setWeight(BigDecimal weight) {
        this.weight = weight;
    }

    public List<Image> getImages() {
        return images;
    }

    public void setImages(List<Image> images) {
        this.images = images;
    }

    public Brand getBrand() {
        return brand;
    }

    public void setBrand(Brand brand) {
        this.brand = brand;
    }

    public List<Sku> getSkus() {
        return skus;
    }

    public void setSkus(List<Sku> skus) {
        this.skus = skus;
    }

    public Integer getWarrantyTime() {
        return warrantyTime;
    }

    public void setWarrantyTime(Integer warrantyTime) {
        this.warrantyTime = warrantyTime;
    }

    public String getWarrantyText() {
        return warrantyText;
    }

    public void setWarrantyText(String warrantyText) {
        this.warrantyText = warrantyText;
    }

    public BigDecimal getPriceFactor() {
        return priceFactor;
    }

    public void setPriceFactor(BigDecimal priceFactor) {
        this.priceFactor = priceFactor;
    }

    public boolean isCalculatedPrice() {
        return calculatedPrice;
    }

    public void setCalculatedPrice(boolean calculatedPrice) {
        this.calculatedPrice = calculatedPrice;
    }

    public boolean isHasVariations() {
        return hasVariations;
    }

    public void setHasVariations(boolean hasVariations) {
        this.hasVariations = hasVariations;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
            .add("id", id)
            .add("title", title)
            .add("description", description)
            .add("model", model)
            .add("nbm", nbm)
            .add("youtubeVideoUrl", youtubeVideoUrl)
            .add("characteristics", characteristics)
            .add("origin", origin)
            .add("category", category)
            .add("height", height)
            .add("length", length)
            .add("width", width)
            .add("weight", weight)
            .add("images", images)
            .add("brand", brand)
            .add("skus", skus)
            .add("warrantyTime", warrantyTime)
            .add("warrantyText", warrantyText)
            .add("priceFactor", priceFactor)
            .add("calculatedPrice", calculatedPrice)
            .add("hasVariations", hasVariations)
            .toString();
    }
}
