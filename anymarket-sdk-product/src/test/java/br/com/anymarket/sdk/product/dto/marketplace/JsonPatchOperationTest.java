package br.com.anymarket.sdk.product.dto.marketplace;

import org.junit.Test;

import static org.junit.Assert.*;

public class JsonPatchOperationTest {

    @Test
    public void should_create_operation_with_constructor() {
        JsonPatchOperation op = new JsonPatchOperation("replace", "/title", "Novo");

        assertEquals("replace", op.getOp());
        assertEquals("/title", op.getPath());
        assertEquals("Novo", op.getValue());
    }

    @Test
    public void should_create_replace_operation_using_factory_method() {
        JsonPatchOperation op = JsonPatchOperation.replace("/price", 10);

        assertEquals("replace", op.getOp());
        assertEquals("/price", op.getPath());
        assertEquals(10, op.getValue());
    }

    @Test
    public void should_set_and_get_fields() {
        JsonPatchOperation op = new JsonPatchOperation();

        op.setOp("add");
        op.setPath("/stock");
        op.setValue(5);

        assertEquals("add", op.getOp());
        assertEquals("/stock", op.getPath());
        assertEquals(5, op.getValue());
    }
}
