package br.com.anymarket.sdk.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Informações de estoque disponível para a conta
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class AvailableStockForAccountDTO implements Serializable {

    @JsonProperty("idAccount")
    private Long idAccount;

    @JsonProperty("availableStock")
    private AvailableStockDTO availableStock;

    public Long getIdAccount() {
        return idAccount;
    }

    public void setIdAccount(Long idAccount) {
        this.idAccount = idAccount;
    }

    public AvailableStockDTO getAvailableStock() {
        return availableStock;
    }

    public void setAvailableStock(AvailableStockDTO availableStock) {
        this.availableStock = availableStock;
    }
}
