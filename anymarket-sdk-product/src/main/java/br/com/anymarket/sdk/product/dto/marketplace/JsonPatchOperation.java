package br.com.anymarket.sdk.product.dto.marketplace;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class JsonPatchOperation {

    @JsonProperty("op")
    private String op;

    @JsonProperty("path")
    private String path;

    @JsonProperty("value")
    private Object value;

    public JsonPatchOperation() {
    }

    public JsonPatchOperation(String op, String path, Object value) {
        this.op = op;
        this.path = path;
        this.value = value;
    }

    public static JsonPatchOperation replace(String path, Object value) {
        return new JsonPatchOperation("replace", path, value);
    }

    public String getOp() {
        return op;
    }

    public void setOp(String op) {
        this.op = op;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }
}
