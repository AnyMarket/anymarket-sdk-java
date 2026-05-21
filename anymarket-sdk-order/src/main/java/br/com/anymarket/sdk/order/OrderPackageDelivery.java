package br.com.anymarket.sdk.order;

import br.com.anymarket.sdk.serializer.SDKDateSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderPackageDelivery {

    @JsonSerialize(using = SDKDateSerializer.class)
    private Date deliveredDate;
}
