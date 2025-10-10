package br.com.anymarket.sdk.order.dto;

import br.com.anymarket.sdk.serializer.SDKDateSerializer;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.util.Date;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ShippingResource extends AbstractAddressResource{

    @JsonProperty("address")
    private String address;

    @JsonProperty("promisedShippingTime")
    @JsonSerialize(using = SDKDateSerializer.class)
    private Date promisedShippingTime;

    @JsonProperty("receiverName")
    private String receiverName;

    @JsonProperty("promisedDispatchTime")
    @JsonSerialize(using = SDKDateSerializer.class)
    private Date promisedDispatchTime;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Date getPromisedShippingTime() {
        return promisedShippingTime;
    }

    public void setPromisedShippingTime(Date promisedShippingTime) {
        this.promisedShippingTime = promisedShippingTime;
    }

    public String getReceiverName() {
        return receiverName;
    }

    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName;
    }

    public Date getPromisedDispatchTime() {
        return promisedDispatchTime;
    }

    public void setPromisedDispatchTime(Date promisedDispatchTime) {
        this.promisedDispatchTime = promisedDispatchTime;
    }
}
