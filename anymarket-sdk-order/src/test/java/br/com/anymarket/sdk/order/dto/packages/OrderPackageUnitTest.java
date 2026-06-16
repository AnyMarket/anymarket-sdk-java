package br.com.anymarket.sdk.order.dto.packages;

import br.com.anymarket.sdk.order.dto.DimensionsPackage;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Date;
import java.util.UUID;

import static org.junit.Assert.*;

public class OrderPackageUnitTest {

    @Test
    public void should_set_and_get_all_fields() {
        UUID packageItemId = UUID.randomUUID();
        Date now = new Date();
        DimensionsPackage dimensions = DimensionsPackage.builder().weight(new BigDecimal("1.0")).build();
        OrderPackageItemResponse item = new OrderPackageItemResponse();

        OrderPackageUnit unit = new OrderPackageUnit();
        unit.setPackageItemId(packageItemId);
        unit.setExternalId("ext-001");
        unit.setCreatedAt(now);
        unit.setTrackingCode("TRACK-001");
        unit.setTrackingDate(now);
        unit.setDimensions(dimensions);
        unit.setReasonId("SPLIT");
        unit.setReasonDescription("Split de pacote");
        unit.setCreatedUnit(now);
        unit.setStatusMarketplace("APPROVED");
        unit.setStatus(OrderPackageStatus.CONFIRMED);
        unit.setItems(Collections.singletonList(item));

        assertEquals(packageItemId, unit.getPackageItemId());
        assertEquals("ext-001", unit.getExternalId());
        assertEquals(now, unit.getCreatedAt());
        assertEquals("TRACK-001", unit.getTrackingCode());
        assertEquals(now, unit.getTrackingDate());
        assertEquals(dimensions, unit.getDimensions());
        assertEquals("SPLIT", unit.getReasonId());
        assertEquals("Split de pacote", unit.getReasonDescription());
        assertEquals(now, unit.getCreatedUnit());
        assertEquals("APPROVED", unit.getStatusMarketplace());
        assertEquals(OrderPackageStatus.CONFIRMED, unit.getStatus());
        assertEquals(1, unit.getItems().size());
    }

    @Test
    public void builder_should_create_instance_with_all_fields() {
        UUID packageItemId = UUID.randomUUID();
        Date now = new Date();

        OrderPackageUnit unit = OrderPackageUnit.builder()
            .packageItemId(packageItemId)
            .externalId("ext-002")
            .createdAt(now)
            .trackingCode("TRACK-002")
            .trackingDate(now)
            .reasonId("CANCEL")
            .reasonDescription("Cancelamento")
            .createdUnit(now)
            .statusMarketplace("PENDING")
            .status(OrderPackageStatus.PENDING)
            .items(Collections.<OrderPackageItemResponse>emptyList())
            .build();

        assertEquals(packageItemId, unit.getPackageItemId());
        assertEquals("ext-002", unit.getExternalId());
        assertEquals(now, unit.getCreatedAt());
        assertEquals("TRACK-002", unit.getTrackingCode());
        assertEquals(now, unit.getTrackingDate());
        assertEquals("CANCEL", unit.getReasonId());
        assertEquals("Cancelamento", unit.getReasonDescription());
        assertEquals(now, unit.getCreatedUnit());
        assertEquals("PENDING", unit.getStatusMarketplace());
        assertEquals(OrderPackageStatus.PENDING, unit.getStatus());
        assertNotNull(unit.getItems());
    }

    @Test
    public void no_args_constructor_should_create_empty_instance() {
        assertNotNull(new OrderPackageUnit());
    }
}
