package br.com.anymarket.sdk.util;

import java.util.Optional;

public final class LongUtils {

    public static Optional<Long> parse(String s) {
        try {
            return Optional.of(Long.parseLong(s));
        } catch (NumberFormatException ignore) {
            return Optional.empty();
        }
    }

}
