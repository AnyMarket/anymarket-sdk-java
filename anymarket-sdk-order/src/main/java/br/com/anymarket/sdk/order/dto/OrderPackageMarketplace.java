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
    private Long packageId;
    private String oi;
    private String orderIdInMarketplace;
    private PackageReasonMarketplace packageReasonMarketplace;
    private List<OrderPackageUnit> packages;
}
