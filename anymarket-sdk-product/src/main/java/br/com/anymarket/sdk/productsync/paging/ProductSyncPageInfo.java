package br.com.anymarket.sdk.productsync.paging;

public class ProductSyncPageInfo {

    private static final int DEFAULT_OFFSET = 0;
    private static final int DEFAULT_PAGE_SIZE = 100;

    private int offset = DEFAULT_OFFSET;
    private int pageSize = DEFAULT_PAGE_SIZE;
    private int total = DEFAULT_PAGE_SIZE;

    public ProductSyncPageInfo(int offset, int pageSize, int total) {
        this.offset = offset;
        this.pageSize = pageSize;
        this.total = total;
    }

    public ProductSyncPageInfo() { }

    public int getOffset() {
        return offset;
    }

    public int getPageSize() {
        return pageSize;
    }

    public int getTotal() {
        return total;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private int offset;
        private int pageSize;
        private int total;

        Builder() { }

        public Builder offset(int offset) {
            this.offset = offset;
            return this;
        }

        public Builder pageSize(int pageSize) {
            this.pageSize = pageSize;
            return this;
        }

        public Builder total(int total) {
            this.total = total;
            return this;
        }

        public ProductSyncPageInfo build() {
            return new ProductSyncPageInfo(this.offset, this.pageSize, this.total);
        }

    }

    public String toString() {
        return "ProductSyncPageInfo(offset="
            + this.offset
            + ", pageSize="
            + this.pageSize
            + ", total="
            + this.total
            + ")";
    }

}
