
package br.com.anymarket.sdk.order.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import br.com.anymarket.sdk.serializer.SDKDateSerializer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderPackageInvoice {

    private String invoiceNumber;
    private String invoiceUrl;
    private String invoiceKey;
    @JsonSerialize(using = SDKDateSerializer.class)
    private Date invoiceDate;
}
