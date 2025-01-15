package br.com.anymarket.sdk.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class MapTo {

    private static final Logger LOG = LoggerFactory.getLogger(MapTo.class);
    private static final String FAIL_MAPPING_ERROR_MESSAGE = "Fail mapping object: %s";

    public static <T> List<T> map(ObjectMapper objectMapper, Class<T> type, List<T> invalidObjects) {
        if (Objects.isNull(invalidObjects)) {
            return Collections.emptyList();
        }

        for (int i = 0; i < invalidObjects.size(); i++) {
            Object invalidObject = invalidObjects.get(i);
            try {
                T object = map(objectMapper, type, invalidObject);
                invalidObjects.set(i, object);
            } catch (IOException e) {
                String error = String.format(FAIL_MAPPING_ERROR_MESSAGE, invalidObject);
                LOG.error(error, e);
            }
        }

        return invalidObjects;
    }

    private static <T> T map(ObjectMapper objectMapper, Class<T> type, Object invalidObject) throws IOException {
        String s = objectMapper.writeValueAsString(invalidObject);
        return objectMapper.readValue(s, type);
    }

}
