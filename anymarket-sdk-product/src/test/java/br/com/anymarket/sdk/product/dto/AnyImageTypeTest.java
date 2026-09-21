package br.com.anymarket.sdk.product.dto;

import org.junit.Test;

import static org.junit.Assert.*;

public class AnyImageTypeTest {

    @Test
    public void should_have_only_model_image_value() {
        AnyImageType[] values = AnyImageType.values();

        assertEquals(1, values.length);
        assertEquals(AnyImageType.MODEL_IMAGE, values[0]);
    }

    @Test
    public void should_resolve_value_of_model_image() {
        assertEquals(AnyImageType.MODEL_IMAGE, AnyImageType.valueOf("MODEL_IMAGE"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void should_throw_exception_for_unknown_value() {
        AnyImageType.valueOf("UNKNOWN");
    }
}
