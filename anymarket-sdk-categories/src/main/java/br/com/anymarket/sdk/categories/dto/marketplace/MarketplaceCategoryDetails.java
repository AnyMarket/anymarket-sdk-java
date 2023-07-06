package br.com.anymarket.sdk.categories.dto.marketplace;

import com.google.common.base.Objects;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static com.google.common.base.Objects.equal;
import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Detalhes da categoria vindas da integração com o MarketPlace
 */
public class MarketplaceCategoryDetails {

    private String codeInMarketPlace;
    private String name;
    private Boolean isReceivingItens;
    private Boolean variationsMandatory;
    private Boolean canBeSelected;
    private String completePath;
    private List<MarketplaceCategory> path = new ArrayList<MarketplaceCategory>();
    private List<MarketplaceCategory> children = new ArrayList<MarketplaceCategory>();

    protected MarketplaceCategoryDetails() {
    }

    public MarketplaceCategoryDetails(Builder builder) {
        this.codeInMarketPlace = builder.codeInMarketPlace;
        this.name = builder.name;
        this.completePath = builder.getCompletePath();
        this.path = builder.path;
        this.children = builder.children;
        this.isReceivingItens = builder.isReceivingItens;
        this.variationsMandatory = builder.variationsMandatory;
        this.canBeSelected = builder.canBeSelected;
    }

    public String getCodeInMarketPlace() {
        return codeInMarketPlace;
    }

    public String getName() {
        return name;
    }

    public String getCompletePath() {
        return completePath;
    }

    public void setCompletePath(String completePath) {
        this.completePath = completePath;
    }

    public List<MarketplaceCategory> getPath() {
        return path;
    }

    public List<MarketplaceCategory> getChildren() {
        return children;
    }

    public Boolean getIsReceivingItens() {
        return isReceivingItens;
    }

    public Boolean getVariationsMandatory() {
        return variationsMandatory;
    }

    public Boolean getCanBeSelected() {
        return canBeSelected;
    }

    public void setCodeInMarketPlace(String codeInMarketPlace) {
        this.codeInMarketPlace = codeInMarketPlace;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String codeInMarketPlace;
        private String name;
        private Boolean isReceivingItens = false;
        private Boolean variationsMandatory = false;
        private Boolean canBeSelected = false;
        private String completePath = "";
        private List<MarketplaceCategory> path;
        private List<MarketplaceCategory> children;

        public Builder withCodeInMarketPlace(String codeInMarketPlace) {
            this.codeInMarketPlace = codeInMarketPlace;
            return this;
        }

        public Builder withName(String name) {
            this.name = name;
            return this;
        }

        public Builder withCompletePath(String completePath) {
            this.completePath = completePath;
            return this;
        }

        public Builder withPath(List<MarketplaceCategory> path) {
            this.path = path;
            return this;
        }

        public Builder withChildren(List<MarketplaceCategory> children) {
            this.children = children;
            return this;
        }

        public Builder isReceivingItens() {
            this.isReceivingItens = true;
            return this;
        }

        public Builder variationsIsMandatory() {
            this.variationsMandatory = true;
            return this;
        }

        public Builder canBeSelected() {
            this.canBeSelected = true;
            return this;
        }

        public String getCompletePath() {
            if (StringUtils.isBlank(completePath)) {
                return path.stream()
                    .map(MarketplaceCategory::getName)
                    .collect(Collectors.joining("/"));
            }

            return completePath;
        }

        public MarketplaceCategoryDetails build() {
            checkNotNull(codeInMarketPlace, "Category code in marketplace must not be null");
            checkNotNull(name, "Category name in marketplace width must not be null");
            checkNotNull(isReceivingItens, "Category in marketplace is receiving itens must not be null");
            checkNotNull(variationsMandatory, "Category marketplace is variations mandatory must not be null");
            checkNotNull(canBeSelected, "Category marketplace can be used must not be null");
            checkNotNull(path, "Path of category in marketplace must not be null");
            checkNotNull(children, "Category childs in marketplace must not be null");

            return new MarketplaceCategoryDetails(this);
        }
    }

    public boolean equals(Object other) {
        if (other == null || !MarketplaceCategoryDetails.class.isInstance(other)) {
            return false;
        }

        if (this == other) {
            return true;
        }

        MarketplaceCategoryDetails that = MarketplaceCategoryDetails.class.cast(other);
        return equal(this.codeInMarketPlace, that.codeInMarketPlace) &&
            equal(this.name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(this.codeInMarketPlace, this.name, this.path, this.children, this.isReceivingItens, this.canBeSelected, this.variationsMandatory);
    }

}
