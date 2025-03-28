package br.com.anymarket.sdk.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class MapToStringsTest {

    @Test
    public void shouldMapToEmpty() {
        ObjectMapper objectMapper = new ObjectMapper();
        assertEmpty(MapToStrings.map(objectMapper, null));
        assertEmpty(MapToStrings.map(objectMapper, ""));
        assertEmpty(MapToStrings.map(objectMapper, "\"\""));
        assertEmpty(MapToStrings.map(objectMapper, "true"));
        assertEmpty(MapToStrings.map(objectMapper, "\"true\""));
        assertEmpty(MapToStrings.map(objectMapper, "false"));
        assertEmpty(MapToStrings.map(objectMapper, "\"false\""));
        assertEmpty(MapToStrings.map(objectMapper, "Response Body Unavailable"));
        assertEmpty(MapToStrings.map(objectMapper, "\"Response Body Unavailable\""));
        assertEmpty(MapToStrings.map(objectMapper, "\"[Response Body Unavailable]\""));
        assertEmpty(MapToStrings.map(objectMapper, "[]"));
        assertEmpty(MapToStrings.map(objectMapper, "\"[]\""));
        assertEmpty(MapToStrings.map(objectMapper, "[one]"));
        assertEmpty(MapToStrings.map(objectMapper, "\"[one]\""));
        assertEmpty(MapToStrings.map(objectMapper, "[one,two]"));
        assertEmpty(MapToStrings.map(objectMapper, "\"[one,two]\""));
        assertEmpty(MapToStrings.map(objectMapper, "[true]"));
        assertEmpty(MapToStrings.map(objectMapper, "\"[true]\""));
        assertEmpty(MapToStrings.map(objectMapper, "[false]"));
        assertEmpty(MapToStrings.map(objectMapper, "\"[false]\""));
    }

    private static void assertEmpty(String[] map) {
        assertEquals(0, map.length);
    }

    @Test
    public void shouldMapToValues() {
        ObjectMapper objectMapper = new ObjectMapper();

        String[] map = MapToStrings.map(objectMapper, "[\"one\"]");
        assertEquals(1, map.length);
        assertEquals("one", map[0]);

        map = MapToStrings.map(objectMapper, "\"[\"one\"]\"");
        assertEquals(1, map.length);
        assertEquals("one", map[0]);

        map = MapToStrings.map(objectMapper, "[\"one\",\"two\"]");
        assertEquals(2, map.length);
        assertEquals("one", map[0]);
        assertEquals("two", map[1]);

        map = MapToStrings.map(objectMapper, "\"[\"one\",\"two\"]\"");
        assertEquals(2, map.length);
        assertEquals("one", map[0]);
        assertEquals("two", map[1]);

        map = MapToStrings.map(objectMapper, "[\"Response Body Unavailable\"]");
        assertEquals(1, map.length);
        assertEquals("Response Body Unavailable", map[0]);

        map = MapToStrings.map(objectMapper, "\"[\"Response Body Unavailable\"]\"");
        assertEquals(1, map.length);
        assertEquals("Response Body Unavailable", map[0]);

    }

}
