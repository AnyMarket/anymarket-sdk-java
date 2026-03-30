package br.com.anymarket.sdk.order.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class FiscalMetadataResource {

    @JsonProperty("operationType")
    private String operationType;

    @JsonProperty("emissionType")
    private String emissionType;

    @JsonProperty("authorizationProtocol")
    private String authorizationProtocol;

    public String getOperationType() {
        return operationType;
    }

    public void setOperationType(String operationType) {
        this.operationType = operationType;
    }

    public String getEmissionType() {
        return emissionType;
    }

    public void setEmissionType(String emissionType) {
        this.emissionType = emissionType;
    }

    public String getAuthorizationProtocol() {
        return authorizationProtocol;
    }

    public void setAuthorizationProtocol(String authorizationProtocol) {
        this.authorizationProtocol = authorizationProtocol;
    }
}
