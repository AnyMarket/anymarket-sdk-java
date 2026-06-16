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
public class OrderPackageDeliverRequest {

    private String packageId;
    @JsonSerialize(using = SDKDateSerializer.class)
    @JsonDeserialize(using = SDKDateDeserializer.class)
    private Date deliveredDate;
}
