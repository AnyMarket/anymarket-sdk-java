package br.com.anymarket.sdk.productsync;

import br.com.anymarket.sdk.product.dto.ProductGender;
import br.com.anymarket.sdk.productsync.variation.MarketplaceProductVariation;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static java.util.Collections.emptyList;

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

    private List<MarketplaceProductCategory> categoryTree = new ArrayList<>();
    private List<MarketplaceProductAttribute> attributes = new ArrayList<>();
    private List<MarketplaceProductImage> images = new ArrayList<>();
    private List<MarketplaceProductVariation> variations = new ArrayList<>();

    @JsonIgnore
    public boolean hasMultipleVariations() {
        return hasVariations() && variations.size() > 1;
    }
    @JsonIgnore
    public boolean hasSingleVariation() {
        return hasVariations() && variations.size() == 1;
    }
    @JsonIgnore
    public MarketplaceProductVariation getFirstVariation() {
        if (hasVariations()) {
            return variations.stream()
                .findFirst()
                .orElse(null);
        }

        return null;
    }
    @JsonIgnore
    private boolean hasVariations() {
        return Objects.nonNull(variations) && !variations.isEmpty();
    }
    @JsonIgnore
    public List<String> getCategoryTreeNames() {
        return categoryTree.stream()
            .map(MarketplaceProductCategory::getName)
            .collect(Collectors.toList());
    }

    public MarketplaceProduct() {}

    public MarketplaceProduct(
        String marketplaceIdentifier,
        String sku,
        String title,
        String description,
        String warrantyDescription,
        Integer warrantyNumericValue,
        MarketplaceProductDimensions dimensions,
        String ean,
        String categoryId,
        String brandId,
        String brand,
        String model,
        ProductGender gender,
        BigDecimal stock,
        BigDecimal originalPrice,
        BigDecimal basePrice,
        BigDecimal price,
        MarketplaceProductPerformance performance,
        List<MarketplaceProductCategory> categoryTree,
        List<MarketplaceProductAttribute> attributes,
        List<MarketplaceProductImage> images,
        List<MarketplaceProductVariation> variations
    ) {
        this.marketplaceIdentifier = marketplaceIdentifier;
        this.sku = sku;
        this.title = title;
        this.description = description;
        this.warrantyDescription = warrantyDescription;
        this.warrantyNumericValue = warrantyNumericValue;
        this.dimensions = dimensions;
        this.ean = ean;
        this.categoryId = categoryId;
        this.brandId = brandId;
        this.brand = brand;
        this.model = model;
        this.gender = gender;
        this.stock = stock;
        this.originalPrice = originalPrice;
        this.basePrice = basePrice;
        this.price = price;
        this.performance = performance;
        this.categoryTree = categoryTree;
        this.attributes = attributes;
        this.images = images;
        this.variations = variations;
    }

    public String getMarketplaceIdentifier() {
        return marketplaceIdentifier;
    }

    @Override
    public String getSku() {
        return sku;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getWarrantyDescription() {
        return warrantyDescription;
    }

    public Integer getWarrantyNumericValue() {
        return warrantyNumericValue;
    }

    public MarketplaceProductDimensions getDimensions() {
        return dimensions;
    }

    public String getEan() {
        return ean;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public String getBrandId() {
        return brandId;
    }

    public String getBrand() {
        return brand;
    }

    public String getModel() {
        return model;
    }

    public ProductGender getGender() {
        return gender;
    }

    @Override
    public BigDecimal getStock() {
        return stock;
    }

    public BigDecimal getOriginalPrice() {
        return originalPrice;
    }

    public BigDecimal getBasePrice() {
        return basePrice;
    }

    @Override
    public BigDecimal getPrice() {
        return price;
    }

    public MarketplaceProductPerformance getPerformance() {
        return performance;
    }

    public List<MarketplaceProductCategory> getCategoryTree() {
        return categoryTree;
    }

    public List<MarketplaceProductAttribute> getAttributes() {
        return attributes;
    }

    public List<MarketplaceProductImage> getImages() {
        return images;
    }

    public List<MarketplaceProductVariation> getVariations() {
        return variations;
    }

    public static MarketplaceProductBuilder builder() {
        return new MarketplaceProductBuilder();
    }

    public static class MarketplaceProductBuilder {
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
        private List<MarketplaceProductCategory> categoryTree = new ArrayList<>();
        private List<MarketplaceProductAttribute> attributes = new ArrayList<>();
        private List<MarketplaceProductImage> images = new ArrayList<>();
        private List<MarketplaceProductVariation> variations = new ArrayList<>();

        MarketplaceProductBuilder() {}

        public MarketplaceProductBuilder marketplaceIdentifier(String marketplaceIdentifier) {
            this.marketplaceIdentifier = marketplaceIdentifier;
            return this;
        }

        public MarketplaceProductBuilder sku(String sku) {
            this.sku = sku;
            return this;
        }

        public MarketplaceProductBuilder title(String title) {
            this.title = title;
            return this;
        }

        public MarketplaceProductBuilder description(String description) {
            this.description = description;
            return this;
        }

        public MarketplaceProductBuilder warrantyDescription(String warrantyDescription) {
            this.warrantyDescription = warrantyDescription;
            return this;
        }

        public MarketplaceProductBuilder warrantyNumericValue(Integer warrantyNumericValue) {
            this.warrantyNumericValue = warrantyNumericValue;
            return this;
        }

        public MarketplaceProductBuilder dimensions(MarketplaceProductDimensions dimensions) {
            this.dimensions = dimensions;
            return this;
        }

        public MarketplaceProductBuilder ean(String ean) {
            this.ean = ean;
            return this;
        }

        public MarketplaceProductBuilder categoryId(String categoryId) {
            this.categoryId = categoryId;
            return this;
        }

        public MarketplaceProductBuilder brandId(String brandId) {
            this.brandId = brandId;
            return this;
        }

        public MarketplaceProductBuilder brand(String brand) {
            this.brand = brand;
            return this;
        }

        public MarketplaceProductBuilder model(String model) {
            this.model = model;
            return this;
        }

        public MarketplaceProductBuilder gender(ProductGender gender) {
            this.gender = gender;
            return this;
        }

        public MarketplaceProductBuilder stock(BigDecimal stock) {
            this.stock = stock;
            return this;
        }

        public MarketplaceProductBuilder originalPrice(BigDecimal originalPrice) {
            this.originalPrice = originalPrice;
            return this;
        }

        public MarketplaceProductBuilder basePrice(BigDecimal basePrice) {
            this.basePrice = basePrice;
            return this;
        }

        public MarketplaceProductBuilder price(BigDecimal price) {
            this.price = price;
            return this;
        }

        public MarketplaceProductBuilder performance(MarketplaceProductPerformance performance) {
            this.performance = performance;
            return this;
        }

        public MarketplaceProductBuilder categoryTree(List<MarketplaceProductCategory> categoryTree) {
            this.categoryTree = categoryTree;
            return this;
        }

        public MarketplaceProductBuilder attributes(List<MarketplaceProductAttribute> attributes) {
            this.attributes = attributes;
            return this;
        }

        public MarketplaceProductBuilder images(List<MarketplaceProductImage> images) {
            this.images = images;
            return this;
        }

        public MarketplaceProductBuilder variations(List<MarketplaceProductVariation> variations) {
            this.variations = variations;
            return this;
        }

        public MarketplaceProduct build() {
            return new MarketplaceProduct(
                this.marketplaceIdentifier,
                this.sku,
                this.title,
                this.description,
                this.warrantyDescription,
                this.warrantyNumericValue,
                this.dimensions,
                this.ean,
                this.categoryId,
                this.brandId,
                this.brand,
                this.model,
                this.gender,
                this.stock,
                this.originalPrice,
                this.basePrice,
                this.price,
                this.performance,
                getValues(this.categoryTree),
                getValues(this.attributes),
                getValues(this.images),
                getValues(this.variations)
            );
        }

        private <T> List<T> getValues(List<T> attributes) {
            return hasValues(attributes) ? attributes : emptyList();
        }

        private boolean hasValues(List<?> valueList) {
            return Objects.nonNull(valueList) && !valueList.isEmpty();
        }

    }

}
