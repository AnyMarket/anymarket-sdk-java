package br.com.anymarket.sdk.order.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PackageReasonMarketplace {

    private String reasonId;
    private String description;
}
