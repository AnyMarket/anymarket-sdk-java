package br.com.anymarket.sdk.order.dto;

import br.com.anymarket.sdk.serializer.SDKDateSerializer;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class OrderShipmentResource {
    @JsonProperty("id")
    private Long id;

    @JsonProperty("index")
    private Long index;

    @JsonProperty("status")
    private NewOrderStatus status;

    @JsonProperty("tracking")
    @JsonInclude(value = JsonInclude.Include.NON_NULL)
    private TrackingResource tracking;

    @JsonProperty("invoice")
    @JsonInclude(value = JsonInclude.Include.NON_NULL)
    private InvoiceResource invoice;

    @JsonProperty("marketPlaceStatus")
    private String marketPlaceStatus;

    @JsonProperty("marketPlaceId")
    private String marketPlaceId;

    @JsonProperty("partnerId")
    private String partnerId;

    @JsonProperty("transmissionStatus")
    private OrderTransmissionStatus transmissionStatus;

    @JsonProperty("cancelDate")
    @JsonSerialize(using = SDKDateSerializer.class)
    private Date cancelDate;

    @JsonProperty("cancellationCode")
    private OrderCancellationCode cancellationCode;

    @JsonProperty("marketPlaceStatusComplement")
    private String marketplaceStatusComplement;

    @JsonProperty("shipmentExceptionDate")
    @JsonSerialize(using = SDKDateSerializer.class)
    private Date shipmentExceptionDate;

    @JsonProperty("shipmentExceptionDescription")
    private String shipmentExceptionDescription;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("items")
    private List<OrderShipmentItemResource> items;

    @JsonProperty("cancelDetails")
    private String cancelationDetails;

    public OrderShipmentResource() {
        super();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIndex() {
        return index;
    }

    public void setIndex(Long index) {
        this.index = index;
    }

    public NewOrderStatus getStatus() {
        return status;
    }

    public void setStatus(NewOrderStatus status) {
        this.status = status;
    }

    public TrackingResource getTracking() {
        return tracking;
    }

    public void setTracking(TrackingResource tracking) {
        this.tracking = tracking;
    }

    public InvoiceResource getInvoice() {
        return invoice;
    }

    public void setInvoice(InvoiceResource invoice) {
        this.invoice = invoice;
    }

    public String getMarketPlaceStatus() {
        return marketPlaceStatus;
    }

    public void setMarketPlaceStatus(String marketPlaceStatus) {
        this.marketPlaceStatus = marketPlaceStatus;
    }

    public String getMarketPlaceId() {
        return marketPlaceId;
    }

    public void setMarketPlaceId(String marketPlaceId) {
        this.marketPlaceId = marketPlaceId;
    }

    public String getPartnerId() {
        return partnerId;
    }

    public void setPartnerId(String partnerId) {
        this.partnerId = partnerId;
    }

    public OrderTransmissionStatus getTransmissionStatus() {
        return transmissionStatus;
    }

    public void setTransmissionStatus(OrderTransmissionStatus transmissionStatus) {
        this.transmissionStatus = transmissionStatus;
    }

    public Date getCancelDate() {
        return cancelDate;
    }

    public void setCancelDate(Date cancelDate) {
        this.cancelDate = cancelDate;
    }

    public OrderCancellationCode getCancellationCode() {
        return cancellationCode;
    }

    public void setCancellationCode(OrderCancellationCode cancellationCode) {
        this.cancellationCode = cancellationCode;
    }

    public String getMarketplaceStatusComplement() {
        return marketplaceStatusComplement;
    }

    public void setMarketplaceStatusComplement(String marketplaceStatusComplement) {
        this.marketplaceStatusComplement = marketplaceStatusComplement;
    }

    public Date getShipmentExceptionDate() {
        return shipmentExceptionDate;
    }

    public void setShipmentExceptionDate(Date shipmentExceptionDate) {
        this.shipmentExceptionDate = shipmentExceptionDate;
    }

    public String getShipmentExceptionDescription() {
        return shipmentExceptionDescription;
    }

    public void setShipmentExceptionDescription(String shipmentExceptionDescription) {
        this.shipmentExceptionDescription = shipmentExceptionDescription;
    }

    public List<OrderShipmentItemResource> getItems() {
        return items;
    }

    public void setItems(List<OrderShipmentItemResource> items) {
        this.items = items;
    }

    public String getCancelationDetails() {
        return cancelationDetails;
    }

    public void setCancelationDetails(String cancelationDetails) {
        this.cancelationDetails = cancelationDetails;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("id", id)
                .add("index", index)
                .add("status", status)
                .add("tracking", tracking)
                .add("invoice", invoice)
                .add("marketPlaceStatus", marketPlaceStatus)
                .add("marketPlaceId", marketPlaceId)
                .add("partnerId", partnerId)
                .add("transmissionStatus", transmissionStatus)
                .add("cancelDate", cancelDate)
                .add("cancellationCode", cancellationCode)
                .add("marketplaceStatusComplement", marketplaceStatusComplement)
                .add("shipmentExceptionDate", shipmentExceptionDate)
                .add("shipmentExceptionDescription", shipmentExceptionDescription)
                .add("items", items)
                .add("cancelationDetails", cancelationDetails)
                .toString();
    }
}
