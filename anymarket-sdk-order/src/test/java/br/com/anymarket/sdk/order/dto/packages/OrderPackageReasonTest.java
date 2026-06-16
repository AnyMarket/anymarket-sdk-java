package br.com.anymarket.sdk.order.dto.packages;

import org.junit.Test;

import static org.junit.Assert.*;

public class OrderPackageReasonTest {

    @Test
    public void should_set_and_get_all_fields() {
        OrderPackageReason reason = new OrderPackageReason();
        reason.setId("SPLIT");
        reason.setDescription("Split de pacote");

        assertEquals("SPLIT", reason.getId());
        assertEquals("Split de pacote", reason.getDescription());
    }

    @Test
    public void builder_should_create_instance_with_all_fields() {
        OrderPackageReason reason = OrderPackageReason.builder()
            .id("CANCEL")
            .description("Cancelamento de pacote")
            .build();

        assertEquals("CANCEL", reason.getId());
        assertEquals("Cancelamento de pacote", reason.getDescription());
    }

    @Test
    public void no_args_constructor_should_create_empty_instance() {
        assertNotNull(new OrderPackageReason());
    }
}
