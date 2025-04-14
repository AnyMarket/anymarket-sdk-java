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
public class OrderPackageUnit {

    private Long packageId;
    private String externalId;
    private List<ItemsPackage> items;
    private DimensionsPackage dimensions;
}
