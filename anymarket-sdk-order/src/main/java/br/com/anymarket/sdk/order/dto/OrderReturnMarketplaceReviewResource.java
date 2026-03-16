package br.com.anymarket.sdk.order.dto;

import br.com.anymarket.sdk.serializer.SDKDateSerializer;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.joda.time.DateTime;

import java.util.Date;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderReturnMarketplaceReviewResource {

    @JsonProperty("method")
    private String method;

    @JsonProperty("reviewStatus")
    private String reviewStatus;

    @JsonProperty("productCondition")
    private String productCondition;

    @JsonProperty("productDestination")
    private String productDestination;

    @JsonProperty("benefited")
    private String benefited;

    @JsonProperty("reasonId")
    private String reasonId;

    @JsonProperty("reviewedAt")
    @JsonSerialize(using = SDKDateSerializer.class)
    private Date reviewedAt;
}
