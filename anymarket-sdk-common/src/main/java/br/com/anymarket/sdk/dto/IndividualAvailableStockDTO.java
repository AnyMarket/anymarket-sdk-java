package br.com.anymarket.sdk.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Informações de estoque disponível
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class IndividualAvailableStockDTO implements Serializable {

    @JsonProperty("amount")
    private BigDecimal amount;

    @JsonProperty("availableAmount")
    private BigDecimal availableAmount;

    @JsonProperty("cost")
    private BigDecimal cost;

    @JsonProperty("totalReservation")
    private BigDecimal totalReservation;

    @JsonProperty("reservedPending")
    private BigDecimal reservedPending;

    @JsonProperty("reservedPaid")
    private BigDecimal reservedPaid;

    @JsonProperty("stockLocal")
    private String stockLocal;

    @JsonProperty("stockLocalId")
    private Long stockLocalId;

    @JsonProperty("additionalTime")
    private Integer additionalTime;

    @JsonProperty("totalReservationInAccount")
    private BigDecimal totalReservationInAccount;

    @JsonProperty("reservedPendingInAccount")
    private BigDecimal reservedPendingInAccount;

    @JsonProperty("reservedPaidInAccount")
    private BigDecimal reservedPaidInAccount;

    public BigDecimal getReservedPending() {
        return reservedPending;
    }

    public BigDecimal getReservedPaid() {
        return reservedPaid;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public BigDecimal getCost() {
        return cost;
    }

    public String getStockLocal() {
        return stockLocal;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public void setAvailableAmount(BigDecimal availableAmount) {
        this.availableAmount = availableAmount;
    }

    public void setCost(BigDecimal cost) {
        this.cost = cost;
    }

    public void setReservedPending(BigDecimal reservedPending) {
        this.reservedPending = reservedPending;
    }

    public void setReservedPaid(BigDecimal reservedPaid) {
        this.reservedPaid = reservedPaid;
    }

    public void setStockLocal(String stockLocal) {
        this.stockLocal = stockLocal;
    }

    public boolean hasStock() {
        return BigDecimal.ZERO.compareTo(this.availableAmount) < 0;
    }

    public BigDecimal getTotalReservation() {
        return totalReservation;
    }

    public void setTotalReservation(BigDecimal totalReservation) {
        this.totalReservation = totalReservation;
    }

    public BigDecimal getAvailableAmount() {
        if (availableAmount.compareTo(BigDecimal.ZERO) < 0) {
            return BigDecimal.ZERO;
        } else {
            return availableAmount;
        }
    }

    public Integer getAdditionalTime() {
        return additionalTime;
    }

    public void setAdditionalTime(final Integer additionalTime) {
        this.additionalTime = additionalTime;
    }

    public Long getStockLocalId() {
        return stockLocalId;
    }

    public void setStockLocalId(Long stockLocalId) {
        this.stockLocalId = stockLocalId;
    }

    public BigDecimal getTotalReservationInAccount() {
        return totalReservationInAccount;
    }

    public void setTotalReservationInAccount(BigDecimal totalReservationInAccount) {
        this.totalReservationInAccount = totalReservationInAccount;
    }

    public BigDecimal getReservedPendingInAccount() {
        return reservedPendingInAccount;
    }

    public void setReservedPendingInAccount(BigDecimal reservedPendingInAccount) {
        this.reservedPendingInAccount = reservedPendingInAccount;
    }

    public BigDecimal getReservedPaidInAccount() {
        return reservedPaidInAccount;
    }

    public void setReservedPaidInAccount(BigDecimal reservedPaidInAccount) {
        this.reservedPaidInAccount = reservedPaidInAccount;
    }
}
