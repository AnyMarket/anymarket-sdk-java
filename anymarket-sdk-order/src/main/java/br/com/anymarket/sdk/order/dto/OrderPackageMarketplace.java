package br.com.anymarket.sdk.order.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderPackageMarketplace implements Serializable {

    private Long orderId;
    private Long packageId;
    private EventPackage event;
    private String oi;
    private String orderIdInMarketplace;
    private PackageReasonMarketplace packageReasonMarketplace;
    private List<OrderPackageUnit> packages;
}
