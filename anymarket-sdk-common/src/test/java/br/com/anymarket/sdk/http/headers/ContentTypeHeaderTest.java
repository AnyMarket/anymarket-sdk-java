package br.com.anymarket.sdk.http.headers;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ContentTypeHeaderTest {

    @Test
    public void should_return_content_type_key() {
        ContentTypeHeader header = new ContentTypeHeader("application/json");

        assertEquals("Content-Type", header.getKey());
    }

    @Test
    public void should_return_value_passed_in_constructor() {
        ContentTypeHeader header = new ContentTypeHeader("application/json");

        assertEquals("application/json", header.getValue());
    }
}
