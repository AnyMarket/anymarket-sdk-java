package br.com.anymarket.sdk.order.dto.packages;

import br.com.anymarket.sdk.order.dto.DimensionsPackage;
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
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class OrderPackageUnit {

    private UUID packageItemId;
    private String externalId;
    @JsonSerialize(using = SDKDateSerializer.class)
    @JsonDeserialize(using = SDKDateDeserializer.class)
    private Date createdAt;
    private String trackingCode;
    @JsonSerialize(using = SDKDateSerializer.class)
    @JsonDeserialize(using = SDKDateDeserializer.class)
    private Date trackingDate;
    private DimensionsPackage dimensions;
    private String reasonId;
    private String reasonDescription;
    @JsonSerialize(using = SDKDateSerializer.class)
    @JsonDeserialize(using = SDKDateDeserializer.class)
    private Date createdUnit;
    private String statusMarketplace;
    private OrderPackageStatus status;
    private List<OrderPackageItemResponse> items;
}
