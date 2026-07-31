package br.com.anymarket.sdk.order.dto;

import com.fasterxml.jackson.annotation.JsonEnumDefaultValue;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Unit tests for {@link DiscountType}.
 *
 * <p>Covers the seven supported discount types, the stability of the two pre-existing values,
 * and the declaration of {@link DiscountType#PROMOTIONAL} as the unknown-value fallback.</p>
 */
public class DiscountTypeTest {

    /**
     * The seven names shared with the {@code anymarket-marketplace-sdk} enum of the same name.
     *
     * <p>Translation between the two SDKs goes through {@code name()}, so any divergence in
     * spelling breaks the order conversion. The list is pinned as a literal here — and
     * identically in the sibling SDK's test — instead of being derived from the enum, which
     * would make the assertion tautological.</p>
     */
    private static final List<String> EXPECTED_NAMES = Arrays.asList(
        "COUPON", "FREE_ITEM", "COIN", "PIX_DISCOUNT", "LOYALTY", "BUNDLE", "PROMOTIONAL");

    /**
     * Verifies that the enum declares exactly the seven expected values, by name — the parity
     * contract with the sibling SDK.
     */
    @Test
    public void should_declare_the_seven_supported_discount_types() {
        List<String> actualNames = new ArrayList<String>();
        for (DiscountType type : DiscountType.values()) {
            actualNames.add(type.name());
        }

        assertEquals(EXPECTED_NAMES.size(), actualNames.size());
        assertTrue("nomes divergentes entre as SDKs: " + actualNames,
            actualNames.containsAll(EXPECTED_NAMES));
    }

    /**
     * Verifies that the two pre-existing values keep their spelling and relative order, so
     * previously persisted data and ordinal-based consumers are unaffected.
     */
    @Test
    public void should_keep_pre_existing_values_unchanged() {
        assertEquals("COUPON", DiscountType.COUPON.name());
        assertEquals("FREE_ITEM", DiscountType.FREE_ITEM.name());
        assertTrue(DiscountType.COUPON.ordinal() < DiscountType.FREE_ITEM.ordinal());
    }

    /**
     * Verifies that every expected name resolves through {@link DiscountType#valueOf(String)}.
     */
    @Test
    public void should_resolve_every_expected_name_through_value_of() {
        for (String name : EXPECTED_NAMES) {
            assertEquals(name, DiscountType.valueOf(name).name());
        }
    }

    /**
     * Verifies that each value serializes to its own {@code name()}, the wire format already
     * exchanged with the core.
     *
     * @throws Exception if serialization fails
     */
    @Test
    public void should_serialize_each_value_as_its_name() throws Exception {
        ObjectMapper mapper = new ObjectMapper();

        for (String name : EXPECTED_NAMES) {
            assertEquals("\"" + name + "\"",
                mapper.writeValueAsString(DiscountType.valueOf(name)));
        }
    }

    /**
     * Verifies that {@link DiscountType#PROMOTIONAL} is the value annotated with
     * {@code @JsonEnumDefaultValue}, the target of the unknown-type fallback.
     *
     * <p>The deserialization itself is not exercised here: this module's {@code jackson-databind}
     * is 2.6.2 and {@code READ_UNKNOWN_ENUM_VALUES_USING_DEFAULT_VALUE} is {@code @since} 2.8.
     * The runtime behaviour is covered where the feature is actually enabled — in
     * {@code anymarket-marketplace-sdk} (jackson 2.9.4) and in the core, by
     * {@code JsonDiscountMetadataTypeTest} and {@code WebConfigObjectMapperTest}.</p>
     *
     * @throws Exception if the annotation cannot be read
     */
    @Test
    public void should_annotate_promotional_as_the_fallback_value() throws Exception {
        assertTrue("PROMOTIONAL precisa carregar @JsonEnumDefaultValue para o fallback funcionar",
            DiscountType.class.getField(DiscountType.PROMOTIONAL.name())
                .isAnnotationPresent(JsonEnumDefaultValue.class));
    }
}
