package br.com.anymarket.sdk.order.dto;

import br.com.anymarket.sdk.MarketPlace;
import br.com.anymarket.sdk.serializer.SDKDateSerializer;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.google.common.collect.Lists;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class OrderReturn {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("orderId")
    private Long orderId;

    @JsonProperty("oi")
    private String oi;

    @JsonProperty("marketplace")
    private MarketPlace marketplace;

    @JsonProperty("marketplaceOrderId")
    private String marketplaceOrderId;

    @JsonProperty("marketplaceReturnId")
    private String marketplaceReturnId;

    @JsonProperty("marketplaceTypeId")
    private String marketplaceTypeId;

    @JsonProperty("accountId")
    private Long accountId;

    @JsonProperty("accountName")
    private String accountName;

    @JsonProperty("paymentStatus")
    private String paymentStatus;

    @JsonProperty("status")
    private String status;

    @JsonProperty("reason")
    private String reason;

    @JsonProperty("type")
    private ReturnType type;

    @JsonProperty("closedAt")
    @JsonSerialize(using = SDKDateSerializer.class)
    private Date closedAt;

    @JsonProperty("createdAt")
    @JsonSerialize(using = SDKDateSerializer.class)
    private Date createdAt;

    @JsonProperty("updatedAt")
    @JsonSerialize(using = SDKDateSerializer.class)
    private Date updatedAt;

    @JsonProperty("checkedAt")
    @JsonSerialize(using = SDKDateSerializer.class)
    private Date checkedAt;

    @JsonProperty("items")
    private List<OrderReturnItemResource> items = Lists.newArrayList();

    @JsonProperty("shipping")
    private List<OrderReturnShippingResource> shipping = Lists.newArrayList();

    @JsonProperty("history")
    private List<OrderReturnHistoryResource> history = Lists.newArrayList();

    @JsonProperty("conference")
    private OrderReturnConferenceResource conference;

    @JsonProperty("invoice")
    private OrderReturnInvoiceResource invoice;

    @JsonProperty("fulfillment")
    private Boolean fulfillment;
}
