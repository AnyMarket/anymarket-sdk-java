package br.com.anymarket.sdk.order.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.joda.time.DateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderReturnMarketplaceReviewResource {

    private String method;

    private String reviewStatus;

    private String productCondition;

    private String productDestination;

    private String benefited;

    private String reasonId;

    private DateTime reviewedAt;
}
