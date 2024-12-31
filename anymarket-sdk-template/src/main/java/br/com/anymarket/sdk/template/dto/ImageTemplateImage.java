package br.com.anymarket.sdk.template.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.net.URL;

@Getter
@Setter
@EqualsAndHashCode
@JsonIgnoreProperties(ignoreUnknown = true)
public class ImageTemplateImage implements Serializable {

    @JsonProperty("standardUrl")
    private URL standardUrl;

}