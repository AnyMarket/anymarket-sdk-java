package br.com.anymarket.sdk.order.dto.packages;

import br.com.anymarket.sdk.serializer.SDKDateDeserializer;
import br.com.anymarket.sdk.serializer.SDKDateSerializer;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class OrderPackageInvoiceResource {

    private String packageId;
    @JsonSerialize(using = SDKDateSerializer.class)
    @JsonDeserialize(using = SDKDateDeserializer.class)
    private Date date;
    private String number;
    private String accessKey;
    private String series;
    private String invoiceLink;
    private String linkNfe;
    private String cfop;
    private String companyStateTaxId;
    private String extraDescription;
    private String operationType;
    private String emissionType;
    private String authorizationProtocol;
}
