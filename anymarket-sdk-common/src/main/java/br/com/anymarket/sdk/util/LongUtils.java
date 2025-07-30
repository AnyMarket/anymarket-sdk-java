package br.com.anymarket.sdk.util;

import java.util.Optional;

/**
 * @deprecated Use br.com.anymarket.utils.LongUtils from anymarket-misc project
 */
@Deprecated
public final class LongUtils {

    public static Optional<Long> parse(String s) {
        try {
            return Optional.of(Long.parseLong(s));
        } catch (NumberFormatException ignore) {
            return Optional.empty();
        }
    }

}
