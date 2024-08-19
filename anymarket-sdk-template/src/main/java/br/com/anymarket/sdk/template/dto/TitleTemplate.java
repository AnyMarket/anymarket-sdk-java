package br.com.anymarket.sdk.template.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class TitleTemplate extends GenericTemplate {

    @JsonProperty("fields")
    private List<String> fields;

    public List<String> getFields() {
        return fields;
    }

    public void setFields(List<String> fields) {
        this.fields = fields;
    }

}
