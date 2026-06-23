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
public class OrderPackageResourceTrackingCode {

    private Long id;
    private UUID packageItemId;
    @JsonSerialize(using = SDKDateSerializer.class)
    @JsonDeserialize(using = SDKDateDeserializer.class)
    private Date createdAt;
    private String reasonId;
    private String reasonDescription;
    private String trackingCode;
    @JsonSerialize(using = SDKDateSerializer.class)
    @JsonDeserialize(using = SDKDateDeserializer.class)
    private Date trackingDate;
    private String statusMarketplace;
    private String errorDescription;
    private Boolean isPackageAcceptedByMarketplace;
    private OrderPackageStatus status;
    private DimensionsPackage dimensions;
    private List<OrderPackageItem> items;
    private String externalId;
    private OrderPackageResourceInvoice invoice;
    private OrderPackageResourceTracking tracking;
    private OrderPackageResourceDelivery delivery;
}
