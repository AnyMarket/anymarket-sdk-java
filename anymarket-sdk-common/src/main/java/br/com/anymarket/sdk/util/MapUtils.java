package br.com.anymarket.sdk.util;

import java.util.Map;

public final class MapUtils {

    public static <K> void putAsStringIfNotNull(Map<K, String> map, K key, Object value) {
        if(value != null)
            map.put(key, value.toString());
    }

}
