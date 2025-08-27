package br.com.anymarket.sdk.util;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class SanitizerTest {

    @Test
    public void should_remove_not_allowed_html_elements_and_attributes() {
        String description =
                "<p><audio src=\"\"><math><style><img src onerror=alert(localStorage.getItem(\"token\"))></style></math></audio></p>" +
                "<p><audio src=\"\"><maTh><style><img src oneRror=\"alert(localStorage.getItem('token'))\"></style></Math></audio></p>" +
                "<p><audio src=\"\"><math><style><img src onerror='alert(localStorage.getItem(\"token\"))'></style></math></audio></p>" +
                "<p><audio src=\"\"><math><style><img src onError=alertlocalStorage.getItemtoken))></style></math></audio></p>" +
                "<script>window.location.href = \"https://fake-login.com\";</script>" +
                "<sCript>window.location.href = \"https://fake-login.com\";</scriPt>" +
                "<script>banana</SCRIPT>" +
                "<SCRIPT>banana</script>";
        description = Sanitizer.removeNotAllowedHtmlElementsAndAttributes(null, description);

        String expected =
                "<p><audio src=\"\"><style><img src></style></audio></p>" +
                "<p><audio src=\"\"><style><img src></style></audio></p>" +
                "<p><audio src=\"\"><style><img src></style></audio></p>" +
                "<p><audio src=\"\"><style><img src></style></audio></p>";
        assertEquals(expected, description);
    }

    @Test
    public void should_remove_not_allowed_html_elements_and_attributes_when_no_change_value() {
        String expected =
                "<p><audio src=\"\"><style><img src></style></audio></p>" +
                "<p><audio src=\"\"><style><img src></style></audio></p>" +
                "<p><audio src=\"\"><style><img src></style></audio></p>" +
                "<p><audio src=\"\"><style><img src></style></audio></p>";
        String result = Sanitizer.removeNotAllowedHtmlElementsAndAttributes(expected, expected);

        assertEquals(expected, result);
    }

    @Test
    public void should_remove_not_allowed_html_elements_and_attributes_value_is_null() {
        String result = Sanitizer.removeNotAllowedHtmlElementsAndAttributes("any", null);

        assertNull(result);
    }

}
