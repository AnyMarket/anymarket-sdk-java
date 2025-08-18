package br.com.anymarket.sdk.util;

import java.util.Objects;
import java.util.regex.Pattern;

public class Sanitizer {

    private Sanitizer() {
        // Utility class, no instantiation allowed
    }

    private static final Pattern PATTERN_ELEMENT_MATH_OR_ATTRIBUTE_ONERROR_COMPLETE = Pattern.compile(
            "(?i)</?math>|\\s?onerror=(\"[^\"]*\"|'[^']*'|[^>]*\\([^>]*\\)|[^>]*)"
    );
    private static final Pattern PATTERN_ELEMENT_SCRIPT = Pattern.compile(
            "(?i)<script[^>]*>(.|\\s)*</script>"
    );
    private static final Pattern PATTERN_ATTRIBUTE_ONERROR_INCOMPLETE = Pattern.compile("(?i)\\s?onerror=[^>]*");

    public static String removeNotAllowedHtmlElementsAndAttributes(String string, String newString) {
        if (Objects.nonNull(newString) && !newString.equals(string)) {
            newString = PATTERN_ELEMENT_MATH_OR_ATTRIBUTE_ONERROR_COMPLETE.matcher(newString).replaceAll("");
            newString = PATTERN_ATTRIBUTE_ONERROR_INCOMPLETE.matcher(newString).replaceAll("");
            newString = PATTERN_ELEMENT_SCRIPT.matcher(newString).replaceAll("");
        }
        return newString;
    }

}
