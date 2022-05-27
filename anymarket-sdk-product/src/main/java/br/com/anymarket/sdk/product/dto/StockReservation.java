package br.com.anymarket.sdk.product.dto;

import java.math.BigDecimal;

public class StockReservation {
    private BigDecimal reservedPending;
    private BigDecimal reservedPaid;
    private BigDecimal reservedInvoiced;

    public StockReservation(BigDecimal reservedPending, BigDecimal reservedPaid, BigDecimal reservedInvoiced) {
        this.reservedPending = reservedPending;
        this.reservedPaid = reservedPaid;
        this.reservedInvoiced = reservedInvoiced;
    }

    public StockReservation() {
    }

    public BigDecimal getReservedPending() {
        return reservedPending;
    }

    public void setReservedPending(BigDecimal reservedPending) {
        this.reservedPending = reservedPending;
    }

    public BigDecimal getReservedPaid() {
        return reservedPaid;
    }

    public void setReservedPaid(BigDecimal reservedPaid) {
        this.reservedPaid = reservedPaid;
    }

    public BigDecimal getReservedInvoiced() {
        return reservedInvoiced;
    }

    public void setReservedInvoiced(BigDecimal reservedInvoiced) {
        this.reservedInvoiced = reservedInvoiced;
    }
}
