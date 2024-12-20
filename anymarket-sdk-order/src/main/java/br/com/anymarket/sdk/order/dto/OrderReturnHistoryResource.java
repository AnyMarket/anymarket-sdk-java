package br.com.anymarket.sdk.order.dto;

import br.com.anymarket.sdk.serializer.SDKDateSerializer;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Getter;
import lombok.Setter;
import org.joda.time.DateTime;

import java.util.Date;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class OrderReturnHistoryResource {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("returnStatus")
    private String returnStatus;

    @JsonProperty("shippingStatus")
    private String shippingStatus;

    @JsonProperty("shippingSubStatus")
    private String shippingSubStatus;

    @JsonProperty("createdAt")
    private DateTime createdAt;
}
