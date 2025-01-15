package br.com.anymarket.sdk.template.dto;

import br.com.anymarket.sdk.dto.ImageStatus;
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

    @JsonProperty("originalImage")
    private URL originalImageInS3URL;

    @JsonProperty("status")
    private ImageStatus status;

    @JsonProperty("standardWidth")
    private Integer standardWidth;

    @JsonProperty("standardHeight")
    private Integer standardHeight;

    @JsonProperty("originalWidth")
    private Integer originalWidth;

    @JsonProperty("originalHeight")
    private Integer originalHeight;

}