package br.com.anymarket.sdk.productsync;

import br.com.anymarket.sdk.product.dto.ProductGender;
import br.com.anymarket.sdk.productsync.variation.MarketplaceProductVariation;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MarketplaceProduct implements ProductWithMandatoryInfo {

    private String marketplaceIdentifier;
    private String sku;
    private String title;
    private String description;
    private String warrantyDescription;
    private Integer warrantyNumericValue;
    private MarketplaceProductDimensions dimensions;
    private String ean;
    private String categoryId;
    private String brandId;
    private String brand;
    private String model;
    private ProductGender gender;
    private BigDecimal stock;
    private BigDecimal originalPrice;
    private BigDecimal basePrice;
    private BigDecimal price;
    private MarketplaceProductPerformance performance;

    @lombok.Builder.Default
    private List<MarketplaceProductCategory> categoryTree = new ArrayList<>();
    @lombok.Builder.Default
    private List<MarketplaceProductAttribute> attributes = new ArrayList<>();
    @lombok.Builder.Default
    private List<MarketplaceProductImage> images = new ArrayList<>();
    @lombok.Builder.Default
    private List<MarketplaceProductVariation> variations = new ArrayList<>();

    public boolean hasMultipleVariations() {
        return hasVariations() && variations.size() > 1;
    }

    public boolean hasSingleVariation() {
        return hasVariations() && variations.size() == 1;
    }

    public MarketplaceProductVariation getFirstVariation() {
        if (hasVariations()) {
            return variations.stream()
                .findFirst()
                .orElse(null);
        }

        return null;
    }

    private boolean hasVariations() {
        return Objects.nonNull(variations) && !variations.isEmpty();
    }

    public List<String> getCategoryTreeNames() {
        return categoryTree.stream()
            .map(MarketplaceProductCategory::getName)
            .collect(Collectors.toList());
    }

}
