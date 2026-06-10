
package br.com.anymarket.sdk.order.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import br.com.anymarket.sdk.serializer.SDKDateSerializer;
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
public class InvoicePackage {

    private String invoiceNumber;
    private String invoiceUrl;
    private String invoiceKey;
    @JsonSerialize(using = SDKDateSerializer.class)
    private Date invoiceDate;
}
