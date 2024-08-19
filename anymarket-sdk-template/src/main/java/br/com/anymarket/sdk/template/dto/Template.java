package br.com.anymarket.sdk.template.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Template extends GenericTemplate {

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

    @JsonProperty("removeDescriptionTableContent")
    private boolean removeDescriptionTableContent = false;

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

    public boolean isRemoveDescriptionTableContent() {
        return removeDescriptionTableContent;
    }

    public void setRemoveDescriptionTableContent(boolean removeDescriptionTableContent) {
        this.removeDescriptionTableContent = removeDescriptionTableContent;
    }

}
