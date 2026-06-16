package br.com.anymarket.sdk.order.dto.packages;

import br.com.anymarket.sdk.order.dto.DimensionsPackage;
import br.com.anymarket.sdk.order.dto.ItemsPackage;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Collections;

import static org.junit.Assert.*;

public class OrderPackageInputTest {

    @Test
    public void should_set_and_get_all_fields() {
        DimensionsPackage dimensions = DimensionsPackage.builder()
            .length(new BigDecimal("30"))
            .width(new BigDecimal("20"))
            .height(new BigDecimal("15"))
            .weight(new BigDecimal("1.5"))
            .build();
        ItemsPackage item = ItemsPackage.builder().sku("SKU-001").quantity(BigDecimal.ONE).build();

        OrderPackageInput input = new OrderPackageInput();
        input.setDimensions(dimensions);
        input.setItems(Collections.singletonList(item));
        input.setExternalId("ext-001");

        assertEquals(dimensions, input.getDimensions());
        assertEquals(1, input.getItems().size());
        assertEquals("ext-001", input.getExternalId());
    }

    @Test
    public void builder_should_create_instance_with_all_fields() {
        DimensionsPackage dimensions = DimensionsPackage.builder().weight(new BigDecimal("2.0")).build();

        OrderPackageInput input = OrderPackageInput.builder()
            .dimensions(dimensions)
            .items(Collections.<ItemsPackage>emptyList())
            .externalId("ext-002")
            .build();

        assertEquals(dimensions, input.getDimensions());
        assertNotNull(input.getItems());
        assertEquals("ext-002", input.getExternalId());
    }

    @Test
    public void no_args_constructor_should_create_empty_instance() {
        assertNotNull(new OrderPackageInput());
    }
}
