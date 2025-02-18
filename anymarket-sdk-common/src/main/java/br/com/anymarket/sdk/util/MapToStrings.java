package br.com.anymarket.sdk.util;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

import static java.util.Objects.isNull;

public class MapToStrings {

    public static final String[] EMPTY_ARRAY_OF_STRINGS = new String[0];

    public static String[] map(ObjectMapper objectMapper, String string) {
        if (isNull(string)) {
            return EMPTY_ARRAY_OF_STRINGS;
        }

        try {
            return objectMapper.readValue(string, String[].class);
        } catch (IOException e) {
            return new String[]{string};
        }

    }

}
