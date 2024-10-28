package br.com.anymarket.sdk.order.dto;

import br.com.anymarket.sdk.serializer.SDKDateSerializer;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.util.Date;

@JsonIgnoreProperties(ignoreUnknown = true)
public class OrderReturnShippingResource {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("marketplaceShippingId")
    private String marketplaceShippingId;

    @JsonProperty("status")
    private String status;

    @JsonProperty("subStatus")
    private String subStatus;

    @JsonProperty("trackingNumber")
    private String trackingNumber;

    @JsonProperty("estimatedDelivery")
    @JsonSerialize(using = SDKDateSerializer.class)
    private Date estimatedDelivery;

    @JsonProperty("transportServiceName")
    private String transportServiceName;

    @JsonProperty("senderName")
    private String senderName;

    @JsonProperty("type")
    private String type;

    @JsonProperty("addressLine")
    private String addressLine;

    @JsonProperty("streetName")
    private String streetName;

    @JsonProperty("streetNumber")
    private String streetNumber;

    @JsonProperty("comment")
    private String comment;

    @JsonProperty("zipCode")
    private String zipCode;

    @JsonProperty("city")
    private String city;

    @JsonProperty("state")
    private String state;

    @JsonProperty("country")
    private String country;

    @JsonProperty("neighborhood")
    private String neighborhood;

    @JsonProperty("municipality")
    private String municipality;

    @JsonProperty("destination")
    private String destination;

    @JsonProperty("createdAt")
    @JsonSerialize(using = SDKDateSerializer.class)
    private Date createdAt;
}
