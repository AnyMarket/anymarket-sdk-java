package br.com.anymarket.sdk.order.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SimpleProductResource {
    @JsonProperty("id")
    private Long id;
    @JsonProperty("externalIdProduct")
    private String externalIdProduct;
    @JsonProperty("title")
    private String title;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getExternalIdProduct() {
        return externalIdProduct;
    }

    public void setExternalIdProduct(String externalIdProduct) {
        this.externalIdProduct = externalIdProduct;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
