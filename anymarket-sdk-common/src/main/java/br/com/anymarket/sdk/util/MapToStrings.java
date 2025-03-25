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

        string = removeExternalQuotes(string);

        if (!isClosedByBrackets(string)) {
            return EMPTY_ARRAY_OF_STRINGS;
        }

        try {
            Object[] objects = objectMapper.readValue(string, Object[].class);
            return toStringsIfAllItemsAreStrings(objects);
        } catch (IOException e) {
            return EMPTY_ARRAY_OF_STRINGS;
        }
    }

    private static String[] toStringsIfAllItemsAreStrings(Object[] objects) {
        if (objects.length < 1) {
            return EMPTY_ARRAY_OF_STRINGS;
        }

        String[] strings = new String[objects.length];
        for (int i = 0; i < objects.length; i++) {
            Object object = objects[i];
            if (!(object instanceof String)) {
                return EMPTY_ARRAY_OF_STRINGS;
            }
            strings[i] = object.toString();
        }
        return strings;
    }

    private static String removeExternalQuotes(String string) {
        if (isClosedByQuotes(string)) {
            return string.substring(1, string.length() - 1);
        }
        return string;
    }

    private static boolean isClosedByQuotes(String string) {
        return (string.startsWith("\"") && string.endsWith("\""));
    }

    private static boolean isClosedByBrackets(String string) {
        return string.startsWith("[") && string.endsWith("]");
    }

}
