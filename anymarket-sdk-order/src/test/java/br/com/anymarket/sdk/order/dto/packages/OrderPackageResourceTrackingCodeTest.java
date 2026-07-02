package br.com.anymarket.sdk.order.dto.packages;

import br.com.anymarket.sdk.order.dto.DimensionsPackage;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.UUID;

import static org.junit.Assert.*;

public class OrderPackageResourceTrackingCodeTest {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void builder_should_create_instance_with_all_fields() {
        UUID packageItemId = UUID.randomUUID();
        Date createdAt = new Date();
        Date trackingDate = new Date();
        DimensionsPackage dimensions = DimensionsPackage.builder().weight(new BigDecimal("1.5")).build();
        OrderPackageItem item = OrderPackageItem.builder().sku("SKU-001").build();
        OrderPackageResourceInvoice invoice = OrderPackageResourceInvoice.builder().numberNfe("NF-001").build();
        OrderPackageResourceTracking tracking = OrderPackageResourceTracking.builder().number("TRACK-001").build();
        OrderPackageResourceDelivery delivery = OrderPackageResourceDelivery.builder().packageId(UUID.randomUUID()).build();

        OrderPackageResourceTrackingCode code = OrderPackageResourceTrackingCode.builder()
            .id(42L)
            .packageItemId(packageItemId)
            .createdAt(createdAt)
            .reasonId("SPLIT")
            .reasonDescription("Split de pacote")
            .trackingCode("TC-001")
            .trackingDate(trackingDate)
            .statusMarketplace("APPROVED")
            .errorDescription("no error")
            .isPackageAcceptedByMarketplace(Boolean.TRUE)
            .status(OrderPackageStatus.CONFIRMED)
            .dimensions(dimensions)
            .items(Collections.singletonList(item))
            .externalId("EXT-001")
            .invoice(invoice)
            .tracking(tracking)
            .delivery(delivery)
            .build();

        assertEquals(Long.valueOf(42L), code.getId());
        assertEquals(packageItemId, code.getPackageItemId());
        assertEquals(createdAt, code.getCreatedAt());
        assertEquals("SPLIT", code.getReasonId());
        assertEquals("Split de pacote", code.getReasonDescription());
        assertEquals("TC-001", code.getTrackingCode());
        assertEquals(trackingDate, code.getTrackingDate());
        assertEquals("APPROVED", code.getStatusMarketplace());
        assertEquals("no error", code.getErrorDescription());
        assertEquals(Boolean.TRUE, code.getIsPackageAcceptedByMarketplace());
        assertEquals(OrderPackageStatus.CONFIRMED, code.getStatus());
        assertEquals(dimensions, code.getDimensions());
        assertEquals(1, code.getItems().size());
        assertEquals("EXT-001", code.getExternalId());
        assertEquals(invoice, code.getInvoice());
        assertEquals(tracking, code.getTracking());
        assertEquals(delivery, code.getDelivery());
    }

    @Test
    public void createdAt_should_round_trip_via_serialization() throws Exception {
        long epochSeconds = System.currentTimeMillis() / 1000;
        Date createdAt = new Date(epochSeconds * 1000);

        OrderPackageResourceTrackingCode code = OrderPackageResourceTrackingCode.builder()
            .createdAt(createdAt)
            .build();

        String json = objectMapper.writeValueAsString(code);
        OrderPackageResourceTrackingCode deserialized = objectMapper.readValue(json, OrderPackageResourceTrackingCode.class);

        String expectedFormatted = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX").format(createdAt);
        String actualFormatted = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX").format(deserialized.getCreatedAt());
        assertEquals(expectedFormatted, actualFormatted);
    }

    @Test
    public void trackingDate_should_round_trip_via_serialization() throws Exception {
        long epochSeconds = System.currentTimeMillis() / 1000;
        Date trackingDate = new Date(epochSeconds * 1000);

        OrderPackageResourceTrackingCode code = OrderPackageResourceTrackingCode.builder()
            .trackingDate(trackingDate)
            .build();

        String json = objectMapper.writeValueAsString(code);
        OrderPackageResourceTrackingCode deserialized = objectMapper.readValue(json, OrderPackageResourceTrackingCode.class);

        String expectedFormatted = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX").format(trackingDate);
        String actualFormatted = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX").format(deserialized.getTrackingDate());
        assertEquals(expectedFormatted, actualFormatted);
    }

    @Test
    public void should_ignore_unknown_properties_on_deserialization() throws Exception {
        String json = "{\"id\":1,\"unknownField\":\"ignored\",\"trackingCode\":\"TC-XYZ\"}";

        OrderPackageResourceTrackingCode code = objectMapper.readValue(json, OrderPackageResourceTrackingCode.class);

        assertEquals(Long.valueOf(1L), code.getId());
        assertEquals("TC-XYZ", code.getTrackingCode());
    }
}
