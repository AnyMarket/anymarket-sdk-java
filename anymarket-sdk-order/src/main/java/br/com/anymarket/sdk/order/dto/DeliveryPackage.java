package br.com.anymarket.sdk.order.dto;

import br.com.anymarket.sdk.serializer.SDKDateSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DeliveryPackage {

    @JsonSerialize(using = SDKDateSerializer.class)
    private Date deliveredDate;
}
