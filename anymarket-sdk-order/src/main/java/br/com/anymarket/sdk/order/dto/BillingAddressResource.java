package br.com.anymarket.sdk.order.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class BillingAddressResource  extends AbstractAddressResource{

    @JsonProperty("shipmentUserName")
    private String shipmentUserName;

    @JsonProperty("shipmentUserDocument")
    private String shipmentUserDocument;

    @JsonProperty("shipmentUserDocumentType")
    private String shipmentUserDocumentType;

}
