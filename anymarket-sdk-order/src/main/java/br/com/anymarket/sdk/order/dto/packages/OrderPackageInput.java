package br.com.anymarket.sdk.order.dto.packages;

import br.com.anymarket.sdk.order.dto.DimensionsPackage;
import br.com.anymarket.sdk.order.dto.ItemsPackage;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class OrderPackageInput {

    private DimensionsPackage dimensions;
    private List<ItemsPackage> items;
    private String externalId;
}
