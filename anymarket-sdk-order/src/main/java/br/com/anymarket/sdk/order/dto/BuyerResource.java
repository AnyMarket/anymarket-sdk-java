package br.com.anymarket.sdk.order.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class BuyerResource{

    @JsonProperty("id")
    private Long id;

    @JsonProperty("marketPlaceId")
    private String marketPlaceId;

    @JsonProperty("name")
    private String name;

    @JsonProperty("email")
    private String email;

    @JsonProperty("document")
    private String document;

    @JsonProperty("documentType")
    private String documentType;

    @JsonProperty("phone")
    private String phone;

    @JsonProperty("cellPhone")
    private String cellPhone;

    @JsonProperty("documentNumberNormalized")
    private String documentNumberNormalized;

    @JsonProperty("companyStateTaxId")
    private String companyStateTaxId;

    @JsonProperty("dateOfBirth")
    private String dateOfBirth;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDocument() {
        return document;
    }

    public void setDocument(String document) {
        this.document = document;
    }

    public String getDocumentType() {
        return documentType;
    }

    public void setDocumentType(String documentType) {
        this.documentType = documentType;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCellPhone() {
        return cellPhone;
    }

    public void setCellPhone(String cellPhone) {
        this.cellPhone = cellPhone;
    }

    public String getDocumentNumberNormalized() {
        return documentNumberNormalized;
    }

    public void setDocumentNumberNormalized(String documentNumberNormalized) {
        this.documentNumberNormalized = documentNumberNormalized;
    }

    public String getCompanyStateTaxId() {
        return companyStateTaxId;
    }

    public void setCompanyStateTaxId(String companyStateTaxId) {
        this.companyStateTaxId = companyStateTaxId;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }
}