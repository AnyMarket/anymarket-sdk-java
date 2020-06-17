package br.com.anymarket.sdk.template.dto;

import br.com.anymarket.sdk.MarketPlace;
import br.com.anymarket.sdk.serializer.MarketPlaceProperty;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Template {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("name")
    private String name;

    @JsonProperty("content")
    private String content;

    @JsonProperty("templateType")
    private TemplateType templateType;

    @JsonProperty("plainTextContent")
    private String plainTextContent;

    @JsonProperty("includeDimensionAttributes")
    private boolean includeDimensionAttributes = true;

    @JsonProperty("manualImageSize")
    private boolean manualImageSize = false;

    @JsonProperty("manualImgAttributes")
    private String manualImgAttributes;

    @JsonProperty("isActive")
    private boolean isActive;

    @JsonProperty("removeDescriptionTableContent")
    private boolean removeDescriptionTableContent = false;

    @MarketPlaceProperty
    @JsonProperty("marketPlace")
    private MarketPlace marketPlace;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public TemplateType getTemplateType() {
        return templateType;
    }

    public void setTemplateType(TemplateType templateType) {
        this.templateType = templateType;
    }

    public String getPlainTextContent() {
        return plainTextContent;
    }

    public void setPlainTextContent(String plainTextContent) {
        this.plainTextContent = plainTextContent;
    }

    public boolean isIncludeDimensionAttributes() {
        return includeDimensionAttributes;
    }

    public void setIncludeDimensionAttributes(boolean includeDimensionAttributes) {
        this.includeDimensionAttributes = includeDimensionAttributes;
    }

    public boolean isManualImageSize() {
        return manualImageSize;
    }

    public void setManualImageSize(boolean manualImageSize) {
        this.manualImageSize = manualImageSize;
    }

    public String getManualImgAttributes() {
        return manualImgAttributes;
    }

    public void setManualImgAttributes(String manualImgAttributes) {
        this.manualImgAttributes = manualImgAttributes;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public boolean isRemoveDescriptionTableContent() {
        return removeDescriptionTableContent;
    }

    public void setRemoveDescriptionTableContent(boolean removeDescriptionTableContent) {
        this.removeDescriptionTableContent = removeDescriptionTableContent;
    }

    public MarketPlace getMarketPlace() {
        return marketPlace;
    }

    public void setMarketPlace(MarketPlace marketPlace) {
        this.marketPlace = marketPlace;
    }
}
