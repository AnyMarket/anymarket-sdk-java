package br.com.anymarket.sdk.http.headers;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class AnymarketContentTypesTest {

    @Test
    public void should_have_json_patch_content_type() {
        assertEquals(
                "application/json-patch+json",
                AnymarketContentTypes.JSON_PATCH
        );
    }
}
