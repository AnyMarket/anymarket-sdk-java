package br.com.anymarket.sdk.serializer;

import com.fasterxml.jackson.core.JsonParser;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class SDKDateDeserializerTest {

    private SDKDateDeserializer deserializer;
    private JsonParser parser;

    @Before
    public void setUp() {
        deserializer = new SDKDateDeserializer();
        parser = mock(JsonParser.class);
    }

    @Test
    public void deserialize_null_value_should_return_null() throws IOException {
        when(parser.getText()).thenReturn(null);
        assertNull(deserializer.deserialize(parser, null));
    }

    @Test
    public void deserialize_empty_value_should_return_null() throws IOException {
        when(parser.getText()).thenReturn("");
        assertNull(deserializer.deserialize(parser, null));
    }

    @Test
    public void deserialize_iso_with_millis_and_offset_should_return_date() throws IOException {
        when(parser.getText()).thenReturn("2024-01-15T10:30:45.123+03:00");
        assertNotNull(deserializer.deserialize(parser, null));
    }

    @Test
    public void deserialize_iso_with_offset_should_return_date() throws IOException {
        when(parser.getText()).thenReturn("2024-01-15T10:30:45+03:00");
        assertNotNull(deserializer.deserialize(parser, null));
    }

    @Test
    public void deserialize_iso_with_millis_and_z_should_return_date() throws IOException {
        when(parser.getText()).thenReturn("2024-01-15T10:30:45.123Z");
        assertNotNull(deserializer.deserialize(parser, null));
    }

    @Test
    public void deserialize_iso_with_z_should_return_date() throws IOException {
        when(parser.getText()).thenReturn("2024-01-15T10:30:45Z");
        assertNotNull(deserializer.deserialize(parser, null));
    }

    @Test(expected = IOException.class)
    public void deserialize_unparseable_date_should_throw_io_exception() throws IOException {
        when(parser.getText()).thenReturn("not-a-date");
        deserializer.deserialize(parser, null);
    }
}
