package br.com.anymarket.sdk.serializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SDKDateDeserializer extends JsonDeserializer<Date> {

    private static final String[] FORMATS = {
            "yyyy-MM-dd'T'HH:mm:ss.SSSXXX",
            "yyyy-MM-dd'T'HH:mm:ssXXX",
            "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'",
            "yyyy-MM-dd'T'HH:mm:ss'Z'"
    };

    @Override
    public Date deserialize(JsonParser parser, DeserializationContext ctxt) throws IOException {
        String value = parser.getText();
        if (value == null || value.isEmpty()) {
            return null;
        }
        for (String format : FORMATS) {
            try {
                return new SimpleDateFormat(format).parse(value);
            } catch (ParseException ignored) {
                // try next format
            }
        }
        throw new IOException("Unparseable date: \"" + value + "\"");
    }
}
