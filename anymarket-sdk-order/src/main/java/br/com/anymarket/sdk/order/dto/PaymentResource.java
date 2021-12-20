package br.com.anymarket.sdk.order.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Dados do pagamento
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class PaymentResource {

    @JsonProperty("method")
    private String method;

    @JsonProperty("status")
    private String status;

    @JsonProperty("value")
    private BigDecimal value;

    @JsonProperty("installments")
    private Long installments;

    @JsonProperty("marketplaceId")
    private String marketplaceId;

    @JsonProperty("paymentMethodNormalized")
    private String paymentMethodNormalized;

    @JsonProperty("paymentDetailNormalized")
    private String paymentDetailNormalized;

    @JsonProperty("dueDate")
    private Date dueDate;

    @JsonProperty("cardOperator")
    private String cardOperator;

    @JsonProperty("orderAuthorizationCardCode")
    private String orderAuthorizationCardCode;

    public String getCardOperator() {
        return cardOperator;
    }

    public void setCardOperator(String cardOperator) {
        this.cardOperator = cardOperator;
    }

    public String getOrderAuthorizationCardCode() {
        return orderAuthorizationCardCode;
    }

    public void setOrderAuthorizationCardCode(String orderAuthorizationCardCode) {
        this.orderAuthorizationCardCode = orderAuthorizationCardCode;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }

    public String getMarketplaceId() {
        return marketplaceId;
    }

    public void setMarketplaceId(String marketplaceId) {
        this.marketplaceId = marketplaceId;
    }

    public Long getInstallments() {
        return installments;
    }

    public void setInstallments(Long installments) {
        this.installments = installments;
    }

    public String getPaymentMethodNormalized() {
        return paymentMethodNormalized;
    }

    public void setPaymentMethodNormalized(String paymentMethodNormalized) {
        this.paymentMethodNormalized = paymentMethodNormalized;
    }

    public String getPaymentDetailNormalized() {
        return paymentDetailNormalized;
    }

    public void setPaymentDetailNormalized(String paymentDetailNormalized) {
        this.paymentDetailNormalized = paymentDetailNormalized;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }
}
