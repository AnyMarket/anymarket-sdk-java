package br.com.anymarket.sdk.util;

public final class LongUtils {

    public static Long parse(String s) {
        try {
            return Long.parseLong(s);
        } catch (NumberFormatException ignore) {
            return null;
        }
    }

}
