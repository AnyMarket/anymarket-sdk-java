package br.com.anymarket.sdk.order.dto;

import br.com.anymarket.sdk.MarketPlace;
import br.com.anymarket.sdk.serializer.MarketPlaceProperty;
import br.com.anymarket.sdk.serializer.SDKDateSerializer;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.google.common.base.MoreObjects;
import com.google.common.base.Strings;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Order {
    private final String FROZEN = "FROZEN";

    @JsonProperty("id")
    private Long id;

    @JsonProperty("marketPlaceId")
    private String marketPlaceId;

    @JsonProperty("marketPlaceNumber")
    private String marketPlaceNumber;

    @MarketPlaceProperty
    @JsonProperty("marketPlace")
    private MarketPlace marketPlace;

    @JsonProperty("partnerId")
    private String partnerId;

    @JsonProperty("createdAt")
    @JsonSerialize(using = SDKDateSerializer.class)
    private Date createdAt;

    @JsonProperty("paymentDate")
    @JsonSerialize(using = SDKDateSerializer.class)
    private Date paymentDate;

    @JsonProperty("cancelDate")
    @JsonSerialize(using = SDKDateSerializer.class)
    private Date cancelDate;

    @JsonProperty("cancellationCode")
    private OrderCancellationCode cancellationCode;

    @JsonProperty("transmissionStatus")
    private OrderTransmissionStatus transmissionStatus;

    @JsonProperty("status")
    private OrderStatus status;

    @JsonProperty("marketPlaceUrl")
    private String marketPlaceUrl;

    @JsonProperty("marketPlaceShipmentStatus")
    private String marketPlaceShipmentStatus;

    @JsonProperty("invoice")
    private InvoiceResource invoice;

    @JsonProperty("marketPlaceStatus")
    private String marketPlaceStatus;

    @JsonProperty("marketPlaceStatusComplement")
    private String marketPlaceStatusComplement;

    @JsonProperty("discount")
    private BigDecimal discount;

    @JsonProperty("freight")
    private BigDecimal freight;

    /**
     * @deprecated Use productNet to POST and productGross/productNet to GET
     */
    @JsonProperty("gross")
    @Deprecated
    private BigDecimal gross;

    @JsonProperty("productGross")
    private BigDecimal productGross;

    @JsonProperty("productNet")
    private BigDecimal productNet;

    @JsonProperty("total")
    private BigDecimal total;

    @JsonProperty("interestValue")
    private BigDecimal interestValue;

    @JsonProperty("shipping")
    private ShippingResource shipping;

    @JsonProperty("billingAddress")
    private BillingAddressResource billingAddress;

    @JsonProperty("buyer")
    private BuyerResource buyer;

    @JsonProperty("tracking")
    private TrackingResource tracking;

    @JsonProperty("payments")
    private List<PaymentResource> payments = new ArrayList<PaymentResource>();

    @JsonProperty("items")
    private List<OrderItemResource> items = new ArrayList<OrderItemResource>();

    @JsonProperty("deliveryStatus")
    private DeliveryStatus deliveryStatus;

    @JsonProperty("observation")
    private String observation;

    @JsonProperty("anymarketAddress")
    private AnymarketOrderAddress anymarketOrderAddress;

    @JsonProperty("idAccount")
    private Long idAccount;

    @JsonProperty("accountName")
    private String accountName;

    @JsonProperty("shipmentExceptionDate")
    @JsonSerialize(using = SDKDateSerializer.class)
    private Date shipmentExceptionDate;

    @JsonProperty("shipmentExceptionDescription")
    private String shipmentExceptionDescription;

    @JsonProperty("subChannel")
    private String subChannel;

    @JsonProperty("subChannelNormalized")
    private String subChannelNormalized;

    @JsonProperty("cancelDetails")
    private String cancelDetails;

    @JsonProperty("orderMessage")
    private OrderMessage orderMessage;

    @JsonProperty("fulfillment")
    private Boolean fulfillment;

    @JsonProperty("shippingId")
    private String shippingId;

    @JsonProperty("metadata")
    private Map<String, String> metadata;

    public boolean isFrozen() {
        return MarketPlace.NETSHOES.equals(marketPlace) && FROZEN.equalsIgnoreCase(Strings.nullToEmpty(marketPlaceStatus));
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMarketPlaceId() {
        return marketPlaceId;
    }

    public void setMarketPlaceId(String marketPlaceId) {
        this.marketPlaceId = marketPlaceId;
    }

    public MarketPlace getMarketPlace() {
        return marketPlace;
    }

    public void setMarketPlace(MarketPlace marketPlace) {
        this.marketPlace = marketPlace;
    }

    public String getPartnerId() {
        return partnerId;
    }

    public void setPartnerId(String partnerId) {
        this.partnerId = partnerId;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public OrderTransmissionStatus getTransmissionStatus() {
        return transmissionStatus;
    }

    public void setTransmissionStatus(OrderTransmissionStatus transmissionStatus) {
        this.transmissionStatus = transmissionStatus;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public String getMarketPlaceUrl() {
        return marketPlaceUrl;
    }

    public void setMarketPlaceUrl(String marketPlaceUrl) {
        this.marketPlaceUrl = marketPlaceUrl;
    }

    public String getMarketPlaceShipmentStatus() {
        return marketPlaceShipmentStatus;
    }

    public void setMarketPlaceShipmentStatus(String marketPlaceShipmentStatus) {
        this.marketPlaceShipmentStatus = marketPlaceShipmentStatus;
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

    public String getMarketPlaceStatusComplement() {
        return marketPlaceStatusComplement;
    }

    public void setMarketPlaceStatusComplement(String marketPlaceStatusComplement) {
        this.marketPlaceStatusComplement = marketPlaceStatusComplement;
    }

    public BigDecimal getDiscount() {
        return discount;
    }

    public void setDiscount(BigDecimal discount) {
        this.discount = discount;
    }

    public BigDecimal getFreight() {
        return freight;
    }

    public void setFreight(BigDecimal freight) {
        this.freight = freight;
    }

    /**
     * @deprecated Use productNet to POST and productGross/productNet to GET
     */
    @Deprecated
    public BigDecimal getGross() {
        return gross;
    }

    /**
     * @deprecated Use productNet to POST and productGross/productNet to GET
     */
    @Deprecated
    public void setGross(BigDecimal gross) {
        this.gross = gross;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public ShippingResource getShipping() {
        return shipping;
    }

    public void setShipping(ShippingResource shipping) {
        this.shipping = shipping;
    }

    public BillingAddressResource getBillingAddress() {
        return billingAddress;
    }

    public void setBillingAddress(BillingAddressResource billingAddress) {
        this.billingAddress = billingAddress;
    }

    public BuyerResource getBuyer() {
        return buyer;
    }

    public void setBuyer(BuyerResource buyer) {
        this.buyer = buyer;
    }

    public TrackingResource getTracking() {
        return tracking;
    }

    public void setTracking(TrackingResource tracking) {
        this.tracking = tracking;
    }

    public List<PaymentResource> getPayments() {
        return payments;
    }

    public void setPayments(List<PaymentResource> payments) {
        this.payments = payments;
    }

    public List<OrderItemResource> getItems() {
        return items;
    }

    public void setItems(List<OrderItemResource> items) {
        this.items = items;
    }

    public DeliveryStatus getDeliveryStatus() {
        return deliveryStatus;
    }

    public void setDeliveryStatus(DeliveryStatus deliveryStatus) {
        this.deliveryStatus = deliveryStatus;
    }

    public String getMarketPlaceNumber() {
        return marketPlaceNumber;
    }

    public void setMarketPlaceNumber(String marketPlaceNumber) {
        this.marketPlaceNumber = marketPlaceNumber;
    }

    public Date getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(Date paymentDate) {
        this.paymentDate = paymentDate;
    }

    public Date getCancelDate() {
        return cancelDate;
    }

    public void setCancelDate(Date cancelDate) {
        this.cancelDate = cancelDate;
    }

    public String getObservation() {
        return observation;
    }

    public void setObservation(String observation) {
        this.observation = observation;
    }

    public BigDecimal getInterestValue() {
        return interestValue;
    }

    public void setInterestValue(BigDecimal interestValue) {
        this.interestValue = interestValue;
    }

    public AnymarketOrderAddress getAnymarketOrderAddress() {
        return anymarketOrderAddress;
    }

    public void setAnymarketOrderAddress(AnymarketOrderAddress anymarketOrderAddress) {
        this.anymarketOrderAddress = anymarketOrderAddress;
    }

    public BigDecimal getProductGross() {
        return productGross;
    }

    public void setProductGross(BigDecimal productGross) {
        this.productGross = productGross;
    }

    public BigDecimal getProductNet() {
        return productNet;
    }

    public void setProductNet(BigDecimal productNet) {
        this.productNet = productNet;
    }

    public Long getIdAccount() {
        return idAccount;
    }

    public void setIdAccount(final Long idAccount) {
        this.idAccount = idAccount;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public OrderCancellationCode getCancellationCode() {
        return cancellationCode;
    }

    public void setCancellationCode(final OrderCancellationCode cancellationCode) {
        this.cancellationCode = cancellationCode;
    }

    public Date getShipmentExceptionDate() {
        return shipmentExceptionDate;
    }

    public void setShipmentExceptionDate(final Date shipmentExceptionDate) {
        this.shipmentExceptionDate = shipmentExceptionDate;
    }

    public String getShipmentExceptionDescription() {
        return shipmentExceptionDescription;
    }

    public void setShipmentExceptionDescription(final String shipmentExceptionDescription) {
        this.shipmentExceptionDescription = shipmentExceptionDescription;
    }

    public String getSubChannel() {
        return subChannel;
    }

    public void setSubChannel(String subChannel) {
        this.subChannel = subChannel;
    }

    public String getSubChannelNormalized() {
        return subChannelNormalized;
    }

    public void setSubChannelNormalized(String subChannelNormalized) {
        this.subChannelNormalized = subChannelNormalized;
    }

    public String getCancelDetails() {
        return cancelDetails;
    }

    public void setCancelDetails(String cancelDetails) {
        this.cancelDetails = cancelDetails;
    }

    public OrderMessage getOrderMessage() {
        return orderMessage;
    }

    public void setOrderMessage(final OrderMessage orderMessage) {
        this.orderMessage = orderMessage;
    }

    public Boolean getFulfillment() {
        return fulfillment;
    }

    public void setFulfillment(Boolean fulfillment) {
        this.fulfillment = fulfillment;
    }

    public String getShippingId() {
        return shippingId;
    }

    public void setShippingId(String shippingId) {
        this.shippingId = shippingId;
    }

    public Map<String, String> getMetadata() {
        return metadata;
    }

    public void setMetadata(Map<String, String> metadata) {
        this.metadata = metadata;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
            .add("id", id)
            .add("marketPlaceId", marketPlaceId)
            .add("marketPlaceNumber", marketPlaceNumber)
            .add("marketPlace", marketPlace)
            .add("createdAt", createdAt)
            .add("paymentDate", paymentDate)
            .add("transmissionStatus", transmissionStatus)
            .add("status", status)
            .add("marketPlaceUrl", marketPlaceUrl)
            .add("marketPlaceShipmentStatus", marketPlaceShipmentStatus)
            .add("invoice", invoice)
            .add("marketPlaceStatus", marketPlaceStatus)
            .add("marketPlaceStatusComplement", marketPlaceStatusComplement)
            .add("discount", discount)
            .add("freight", freight)
            .add("productNet", productNet)
            .add("productGross", productGross)
            .add("total", total)
            .add("interestValue", interestValue)
            .add("observation", observation)
            .add("tracking", tracking)
            .add("items", items)
            .add("anymarketAddress", anymarketOrderAddress)
            .add("subChannel", subChannel)
            .add("subChannelNormalized", subChannelNormalized)
            .add("orderMessage", orderMessage)
            .add("fulfillment", fulfillment)
            .add("metadata", metadata)
            .toString();
    }
}
