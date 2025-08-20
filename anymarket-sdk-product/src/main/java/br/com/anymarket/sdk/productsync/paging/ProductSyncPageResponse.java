package br.com.anymarket.sdk.productsync.paging;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProductSyncPageResponse<T> {

    private ProductSyncPageInfo page;
    private List<T> content = new ArrayList<>();
    private final Map<String, String> metadata = new HashMap<>();

    public ProductSyncPageResponse(List<T> content, int offset, int pageSize, int total) {
        this.content = content;
        this.page = ProductSyncPageInfo.builder()
            .offset(offset)
            .pageSize(pageSize)
            .total(total)
            .build();
    }

    public ProductSyncPageResponse(List<T> content, ProductSyncPageInfo page) {
        this.content = content;
        this.page = page;
    }

    public ProductSyncPageResponse() { }

    public String getMetadataValue(String key) {
        return metadata.get(key);
    }

    public void putMetadataValue(String key, String value) {
        metadata.put(key, value);
    }

    public ProductSyncPageInfo getPage() {
        return page;
    }

    public List<T> getContent() {
        return content;
    }

    public Map<String, String> getMetadata() {
        return metadata;
    }

    public static <T> Builder<T> builder() {
        return new Builder<T>();
    }

    public static class Builder<T> {
        private List<T> content;
        private int offset;
        private int pageSize;
        private int total;

        Builder() { }

        public Builder<T> content(List<T> content) {
            this.content = content;
            return this;
        }

        public Builder<T> offset(int offset) {
            this.offset = offset;
            return this;
        }

        public Builder<T> pageSize(int pageSize) {
            this.pageSize = pageSize;
            return this;
        }

        public Builder<T> total(int total) {
            this.total = total;
            return this;
        }

        public ProductSyncPageResponse<T> build() {
            return new ProductSyncPageResponse<T>(this.content, this.offset, this.pageSize, this.total);
        }
    }

    public String toString() {
        return "ProductSyncPageResponse(page="
            + this.page
            + ", content="
            + this.content
            + ", metadata="
            + this.metadata
            + ")";
    }

}
