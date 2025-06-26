package br.com.anymarket.sdk.productsync.paging;

import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;

public class ProductSyncPageRequest {

    private static final int DEFAULT_OFFSET = 0;
    private static final int DEFAULT_PAGE_SIZE = 100;

    private int offset = DEFAULT_OFFSET;
    private int pageSize = DEFAULT_PAGE_SIZE;
    private Map<String, String> metadata = new HashMap<>();

    public ProductSyncPageRequest(int offset, int pageSize, Map<String, String> metadata) {
        this.offset = offset;
        this.pageSize = pageSize;
        this.metadata = metadata;
    }

    public ProductSyncPageRequest() { }

    public void concatValue(String key, String value) {
        String currentValue = metadata.get(key);
        if (StringUtils.isNotBlank(currentValue)) {
            metadata.put(key, currentValue + "," + value);
        }
    }

    public void addOrReplaceValue(String key, String value) {
        metadata.put(key, value);
    }

    public String getMetadataValue(String metadataKey) {
        return metadata.get(metadataKey);
    }

    public int getOffset() {
        return offset;
    }

    public int getPageSize() {
        return pageSize;
    }

    public Map<String, String> getMetadata() {
        return metadata;
    }

    public String toString() {
        return "ProductSyncPageRequest(offset="
            + this.offset
            + ", pageSize="
            + this.pageSize
            + ", metadata="
            + this.metadata
            + ")";
    }

}
