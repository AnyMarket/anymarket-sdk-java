package br.com.anymarket.sdk.order.dto;

import br.com.anymarket.sdk.serializer.SDKDateSerializer;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.google.common.base.MoreObjects;

import java.util.Date;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TrackingResource {

    @JsonProperty("url")
    private String url;

    @JsonProperty("number")
    private String number;

    @JsonProperty("carrier")
    private String carrier;

    @JsonProperty("carrierDocument")
    private String carrierDocument;

    @JsonProperty("date")
    @JsonSerialize(using = SDKDateSerializer.class)
    private Date date;

    @JsonProperty("estimateDate")
    @JsonSerialize(using = SDKDateSerializer.class)
    private Date estimateDate;

    @JsonProperty("shippedDate")
    @JsonSerialize(using = SDKDateSerializer.class)
    private Date shippedDate;

    @JsonProperty("deliveredDate")
    @JsonSerialize(using = SDKDateSerializer.class)
    private Date deliveredDate;

    @JsonProperty("deliveryMethodId")
    private Long deliveryMethodId;

    @JsonProperty("deliveryMethodName")
    private String deliveryMethodName;

    @JsonProperty("bufferingDate")
    @JsonSerialize(using = SDKDateSerializer.class)
    private Date bufferingDate;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getCarrier() {
        return carrier;
    }

    public void setCarrier(String carrier) {
        this.carrier = carrier;
    }

    public String getCarrierDocument() {
        return carrierDocument;
    }

    public void setCarrierDocument(String carrierDocument) {
        this.carrierDocument = carrierDocument;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Date getEstimateDate() {
        return estimateDate;
    }

    public void setEstimateDate(Date estimateDate) {
        this.estimateDate = estimateDate;
    }

    public Date getShippedDate() {
        return shippedDate;
    }

    public void setShippedDate(Date shippedDate) {
        this.shippedDate = shippedDate;
    }

    public Date getDeliveredDate() {
        return deliveredDate;
    }

    public void setDeliveredDate(Date deliveredDate) {
        this.deliveredDate = deliveredDate;
    }

    public Long getDeliveryMethodId() {
        return deliveryMethodId;
    }

    public void setDeliveryMethodId(Long deliveryMethodId) {
        this.deliveryMethodId = deliveryMethodId;
    }

    public String getDeliveryMethodName() {
        return deliveryMethodName;
    }

    public void setDeliveryMethodName(String deliveryMethodName) {
        this.deliveryMethodName = deliveryMethodName;
    }

    public Date getBufferingDate() {
        return bufferingDate;
    }

    public void setBufferingDate(Date bufferingDate) {
        this.bufferingDate = bufferingDate;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
            .add("url", url)
            .add("number", number)
            .add("carrier", carrier)
            .add("carrierDocument", carrierDocument)
            .add("date", date)
            .add("estimateDate", estimateDate)
            .add("shippedDate", shippedDate)
            .add("deliveredDate", deliveredDate)
            .add("bufferingDate", bufferingDate)
            .add("deliveryMethodId", deliveryMethodId)
            .add("deliveryMethodName", deliveryMethodName)
            .toString();
    }
}
