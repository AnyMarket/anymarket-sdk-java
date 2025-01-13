package br.com.anymarket.sdk.order.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class OrderReturnConferenceResource {

    @JsonProperty("reasonDetails")
    private String reasonDetails;

    @JsonProperty("status")
    private String status;

    @JsonProperty("observations")
    private String observations;
}
