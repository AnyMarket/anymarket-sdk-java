package br.com.anymarket.sdk.order.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderPackageMarketplace {

    private Long orderId;
    private String oi;
    private String orderIdInMarketplace;
    private PackageReason packageReason;
    private List<OrderPackage> packages;
    private String externalId;
}
