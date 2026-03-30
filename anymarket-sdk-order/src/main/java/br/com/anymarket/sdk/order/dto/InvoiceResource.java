package br.com.anymarket.sdk.order.dto;

import br.com.anymarket.sdk.serializer.SDKDateSerializer;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.google.common.base.MoreObjects;

import java.util.Date;

/**
 * Dados da nota vindo do ERP
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class InvoiceResource {

    @JsonProperty("accessKey")
    private String accessKey;

    @JsonProperty("series")
    private String series;

    @JsonProperty("number")
    private String number;

    @JsonProperty("date")
    @JsonSerialize(using = SDKDateSerializer.class)
    private Date date;

    @JsonProperty("linkNfe")
    private String linkNfe;

    @JsonProperty("cfop")
    private String cfop;

    @JsonProperty("companyStateTaxId")
    private String companyStateTaxId;

    @JsonProperty("invoiceLink")
    private String invoiceLink;

    @JsonProperty("fiscalMetadata")
    private FiscalMetadataResource fiscalMetadata;

    public String getAccessKey() {
        return accessKey;
    }

    public void setAccessKey(String accessKey) {
        this.accessKey = accessKey;
    }

    public String getSeries() {
        return series;
    }

    public void setSeries(String series) {
        this.series = series;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getLinkNfe() {
        return linkNfe;
    }

    public void setLinkNfe(String linkNfe) {
        this.linkNfe = linkNfe;
    }

    public String getCfop() {
        return cfop;
    }

    public void setCfop(String cfop) {
        this.cfop = cfop;
    }

    public String getCompanyStateTaxId() {
        return companyStateTaxId;
    }

    public void setCompanyStateTaxId(String companyStateTaxId) {
        this.companyStateTaxId = companyStateTaxId;
    }

    public String getInvoiceLink() {
        return invoiceLink;
    }

    public void setInvoiceLink(String invoiceLink) {
        this.invoiceLink = invoiceLink;
    }

    public FiscalMetadataResource getFiscalMetadata() {
        return fiscalMetadata;
    }

    public void setFiscalMetadata(FiscalMetadataResource fiscalMetadata) {
        this.fiscalMetadata = fiscalMetadata;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
            .add("accessKey", accessKey)
            .add("series", series)
            .add("number", number)
            .add("date", date)
            .add("linkNfe", linkNfe)
            .add("invoiceLink", invoiceLink)
            .add("cfop", cfop)
            .add("companyStateTaxId", companyStateTaxId)
            .add("fiscalMetadata", fiscalMetadata)
            .toString();
    }
}
