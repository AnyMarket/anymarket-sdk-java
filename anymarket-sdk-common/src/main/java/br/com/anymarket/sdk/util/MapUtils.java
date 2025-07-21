package br.com.anymarket.sdk.util;

import java.util.Map;

/**
 * @deprecated Use br.com.anymarket.utils.MapUtils from anymarket-misc project
 */
@Deprecated
public final class MapUtils {

    public static <K> void putAsStringIfNotNull(Map<K, String> map, K key, Object value) {
        if(value != null)
            map.put(key, value.toString());
    }

}
