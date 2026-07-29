package br.com.anymarket.sdk.order.dto;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;


import static org.junit.Assert.*;

public class OrderItemResourceTest {

    private final ObjectMapper objectMapper = new ObjectMapper();
    private OrderItemResource orderItemResource;

    @Before
    public void setUp() {
        orderItemResource = new OrderItemResource();
    }

    // ── Default collection initialization ──────────────────────────────────

    @Test
    public void should_default_discount_item_metadata_to_empty_list() {
        assertNotNull(orderItemResource.getDiscountItemMetadata());
        assertTrue(orderItemResource.getDiscountItemMetadata().isEmpty());
    }

    @Test
    public void should_default_shippings_to_empty_list() {
        assertNotNull(orderItemResource.getShippings());
        assertTrue(orderItemResource.getShippings().isEmpty());
    }

    @Test
    public void should_default_customizations_to_empty_list() {
        assertNotNull(orderItemResource.getCustomizations());
        assertTrue(orderItemResource.getCustomizations().isEmpty());
    }

    @Test
    public void should_default_stocks_to_empty_list() {
        assertNotNull(orderItemResource.getStocks());
        assertTrue(orderItemResource.getStocks().isEmpty());
    }

    // ── sku ────────────────────────────────────────────────────────────────

    @Test
    public void should_set_and_get_sku() {
        SimpleSkuResource sku = new SimpleSkuResource();
        sku.setId(1L);
        sku.setPartnerId("P-001");
        sku.setExternalId("EXT-001");
        sku.setTitle("SKU Title");

        orderItemResource.setSku(sku);

        assertSame(sku, orderItemResource.getSku());
        assertEquals(Long.valueOf(1L), orderItemResource.getSku().getId());
        assertEquals("P-001", orderItemResource.getSku().getPartnerId());
        assertEquals("EXT-001", orderItemResource.getSku().getExternalId());
        assertEquals("SKU Title", orderItemResource.getSku().getTitle());
    }

    @Test
    public void should_set_sku_to_null() {
        orderItemResource.setSku(null);
        assertNull(orderItemResource.getSku());
    }

    // ── skuKit ─────────────────────────────────────────────────────────────

    @Test
    public void should_set_and_get_sku_kit() {
        SimpleSkuKitResource skuKit = new SimpleSkuKitResource();
        skuKit.setId(10L);
        skuKit.setTitle("Kit Title");
        skuKit.setPartnerId("K-001");
        skuKit.setAmount(BigDecimal.valueOf(2));

        orderItemResource.setSkuKit(skuKit);

        assertSame(skuKit, orderItemResource.getSkuKit());
        assertEquals(Long.valueOf(10L), orderItemResource.getSkuKit().getId());
        assertEquals("Kit Title", orderItemResource.getSkuKit().getTitle());
        assertEquals("K-001", orderItemResource.getSkuKit().getPartnerId());
        assertEquals(BigDecimal.valueOf(2), orderItemResource.getSkuKit().getAmount());
    }

    @Test
    public void should_set_sku_kit_to_null() {
        orderItemResource.setSkuKit(null);
        assertNull(orderItemResource.getSkuKit());
    }

    // ── amount ─────────────────────────────────────────────────────────────

    @Test
    public void should_set_and_get_amount() {
        orderItemResource.setAmount(BigDecimal.valueOf(3.5));
        assertEquals(BigDecimal.valueOf(3.5), orderItemResource.getAmount());
    }

    @Test
    public void should_set_amount_to_null() {
        orderItemResource.setAmount(null);
        assertNull(orderItemResource.getAmount());
    }

    // ── unit ───────────────────────────────────────────────────────────────

    @Test
    public void should_set_and_get_unit() {
        orderItemResource.setUnit(BigDecimal.valueOf(10.0));
        assertEquals(BigDecimal.valueOf(10.0), orderItemResource.getUnit());
    }

    // ── gross ──────────────────────────────────────────────────────────────

    @Test
    public void should_set_and_get_gross() {
        orderItemResource.setGross(BigDecimal.valueOf(99.90));
        assertEquals(BigDecimal.valueOf(99.90), orderItemResource.getGross());
    }

    // ── total ──────────────────────────────────────────────────────────────

    @Test
    public void should_set_and_get_total() {
        orderItemResource.setTotal(BigDecimal.valueOf(150.00));
        assertEquals(BigDecimal.valueOf(150.00), orderItemResource.getTotal());
    }

    // ── discount ───────────────────────────────────────────────────────────

    @Test
    public void should_set_and_get_discount() {
        orderItemResource.setDiscount(BigDecimal.valueOf(5.00));
        assertEquals(BigDecimal.valueOf(5.00), orderItemResource.getDiscount());
    }

    // ── discountItemMetadata ───────────────────────────────────────────────

    @Test
    public void should_set_and_get_discount_item_metadata() {
        DiscountItemMetadata meta = new DiscountItemMetadata();
        meta.setType(DiscountType.COUPON);
        meta.setDiscountProductSeller(BigDecimal.valueOf(3.00));
        meta.setDiscountProductMarketplace(BigDecimal.valueOf(1.50));

        orderItemResource.setDiscountItemMetadata(Collections.singletonList(meta));

        assertEquals(1, orderItemResource.getDiscountItemMetadata().size());
        assertEquals(DiscountType.COUPON, orderItemResource.getDiscountItemMetadata().get(0).getType());
        assertEquals(BigDecimal.valueOf(3.00), orderItemResource.getDiscountItemMetadata().get(0).getDiscountProductSeller());
        assertEquals(BigDecimal.valueOf(1.50), orderItemResource.getDiscountItemMetadata().get(0).getDiscountProductMarketplace());
    }

    @Test
    public void should_set_multiple_discount_item_metadata() {
        DiscountItemMetadata meta1 = new DiscountItemMetadata();
        meta1.setType(DiscountType.COUPON);
        DiscountItemMetadata meta2 = new DiscountItemMetadata();
        meta2.setType(DiscountType.FREE_ITEM);

        orderItemResource.setDiscountItemMetadata(Arrays.asList(meta1, meta2));

        assertEquals(2, orderItemResource.getDiscountItemMetadata().size());
    }

    // ── shippings ──────────────────────────────────────────────────────────

    @Test
    public void should_set_and_get_shippings() {
        OrderItemShippingResource shipping = new OrderItemShippingResource();
        shipping.setId(100L);
        shipping.setShippingtype("EXPRESS");
        shipping.setShippingCarrierNormalized("FEDEX");
        shipping.setShippingCarrierTypeNormalized("AIR");

        orderItemResource.setShippings(Collections.singletonList(shipping));

        assertEquals(1, orderItemResource.getShippings().size());
        assertEquals(Long.valueOf(100L), orderItemResource.getShippings().get(0).getId());
        assertEquals("EXPRESS", orderItemResource.getShippings().get(0).getShippingtype());
        assertEquals("FEDEX", orderItemResource.getShippings().get(0).getShippingCarrierNormalized());
        assertEquals("AIR", orderItemResource.getShippings().get(0).getShippingCarrierTypeNormalized());
    }

    // ── customizations ─────────────────────────────────────────────────────

    @Test
    public void should_set_and_get_customizations() {
        OrderItemCustomizationsResource custom = new OrderItemCustomizationsResource();
        custom.setCustomizationType("COLOR");
        custom.setCustomizationValue("RED");
        custom.setCustomizationQuantity(BigDecimal.valueOf(1));
        custom.setCustomizationPrice(BigDecimal.valueOf(9.99));

        orderItemResource.setCustomizations(Collections.singletonList(custom));

        assertEquals(1, orderItemResource.getCustomizations().size());
        assertEquals("COLOR", orderItemResource.getCustomizations().get(0).getCustomizationType());
        assertEquals("RED", orderItemResource.getCustomizations().get(0).getCustomizationValue());
        assertEquals(BigDecimal.valueOf(1), orderItemResource.getCustomizations().get(0).getCustomizationQuantity());
        assertEquals(BigDecimal.valueOf(9.99), orderItemResource.getCustomizations().get(0).getCustomizationPrice());
    }

    // ── marketPlaceId ──────────────────────────────────────────────────────

    @Test
    public void should_set_and_get_market_place_id() {
        orderItemResource.setMarketPlaceId("MKTPLACE_01");
        assertEquals("MKTPLACE_01", orderItemResource.getMarketPlaceId());
    }

    @Test
    public void should_set_market_place_id_to_null() {
        orderItemResource.setMarketPlaceId(null);
        assertNull(orderItemResource.getMarketPlaceId());
    }

    // ── orderItemId ────────────────────────────────────────────────────────

    @Test
    public void should_set_and_get_order_item_id() {
        orderItemResource.setOrderItemId(42L);
        assertEquals(Long.valueOf(42L), orderItemResource.getOrderItemId());
    }

    @Test
    public void should_set_order_item_id_to_null() {
        orderItemResource.setOrderItemId(null);
        assertNull(orderItemResource.getOrderItemId());
    }

    // ── idInMarketPlace ────────────────────────────────────────────────────

    @Test
    public void should_set_and_get_id_in_market_place() {
        orderItemResource.setIdInMarketPlace("MKT-ID-999");
        assertEquals("MKT-ID-999", orderItemResource.getIdInMarketPlace());
    }

    // ── listingType ────────────────────────────────────────────────────────

    @Test
    public void should_set_and_get_listing_type() {
        orderItemResource.setListingType("GOLD_SPECIAL");
        assertEquals("GOLD_SPECIAL", orderItemResource.getListingType());
    }

    // ── stocks ─────────────────────────────────────────────────────────────

    @Test
    public void should_set_and_get_stocks() {
        OrderItemStockResource stock = new OrderItemStockResource();
        stock.setStockLocalId(7L);
        stock.setAmount(BigDecimal.valueOf(50));

        orderItemResource.setStocks(Collections.singletonList(stock));

        assertEquals(1, orderItemResource.getStocks().size());
        assertEquals(Long.valueOf(7L), orderItemResource.getStocks().get(0).getStockLocalId());
        assertEquals(BigDecimal.valueOf(50), orderItemResource.getStocks().get(0).getAmount());
    }

    @Test
    public void should_set_multiple_stocks() {
        OrderItemStockResource s1 = new OrderItemStockResource();
        s1.setStockLocalId(1L);
        OrderItemStockResource s2 = new OrderItemStockResource();
        s2.setStockLocalId(2L);

        orderItemResource.setStocks(Arrays.asList(s1, s2));

        assertEquals(2, orderItemResource.getStocks().size());
    }

    // ── officialStoreId ────────────────────────────────────────────────────

    @Test
    public void should_set_and_get_official_store_id() {
        orderItemResource.setOfficialStoreId("STORE-001");
        assertEquals("STORE-001", orderItemResource.getOfficialStoreId());
    }

    // ── officialStoreName ──────────────────────────────────────────────────

    @Test
    public void should_set_and_get_official_store_name() {
        orderItemResource.setOfficialStoreName("Official Brand Store");
        assertEquals("Official Brand Store", orderItemResource.getOfficialStoreName());
    }

    // ── product ────────────────────────────────────────────────────────────

    @Test
    public void should_set_and_get_product() {
        SimpleProductResource product = new SimpleProductResource();
        product.setId(55L);
        product.setExternalIdProduct("EXT-PROD-55");
        product.setTitle("Product Title");

        orderItemResource.setProduct(product);

        assertSame(product, orderItemResource.getProduct());
        assertEquals(Long.valueOf(55L), orderItemResource.getProduct().getId());
        assertEquals("EXT-PROD-55", orderItemResource.getProduct().getExternalIdProduct());
        assertEquals("Product Title", orderItemResource.getProduct().getTitle());
    }

    @Test
    public void should_set_product_to_null() {
        orderItemResource.setProduct(null);
        assertNull(orderItemResource.getProduct());
    }

    // ── toString ───────────────────────────────────────────────────────────

    @Test
    public void toString_should_contain_expected_field_names() {
        String result = orderItemResource.toString();
        assertTrue(result.contains("sku"));
        assertTrue(result.contains("amount"));
        assertTrue(result.contains("unit"));
        assertTrue(result.contains("gross"));
        assertTrue(result.contains("total"));
        assertTrue(result.contains("discount"));
        assertTrue(result.contains("discountItemMetadata"));
        assertTrue(result.contains("shippings"));
        assertTrue(result.contains("marketPlaceId"));
        assertTrue(result.contains("stocks"));
        assertTrue(result.contains("officialStoreId"));
        assertTrue(result.contains("officialStoreName"));
        assertTrue(result.contains("product"));
    }

    @Test
    public void toString_should_contain_populated_values() {
        orderItemResource.setMarketPlaceId("ML");
        orderItemResource.setOfficialStoreName("Nike Store");
        orderItemResource.setAmount(BigDecimal.valueOf(2));

        String result = orderItemResource.toString();

        assertTrue(result.contains("ML"));
        assertTrue(result.contains("Nike Store"));
        assertTrue(result.contains("2"));
    }

    @Test
    public void toString_should_not_be_null_or_empty() {
        String result = orderItemResource.toString();
        assertNotNull(result);
        assertFalse(result.isEmpty());
    }

    // ── JSON serialization / deserialization ───────────────────────────────

    @Test
    public void should_roundtrip_through_json_with_all_scalar_fields() throws Exception {
        orderItemResource.setMarketPlaceId("MARKETPLACE_X");
        orderItemResource.setOrderItemId(99L);
        orderItemResource.setIdInMarketPlace("ID-IN-MKT");
        orderItemResource.setListingType("PREMIUM");
        orderItemResource.setOfficialStoreId("STORE-X");
        orderItemResource.setOfficialStoreName("Store X");
        orderItemResource.setAmount(BigDecimal.valueOf(5));
        orderItemResource.setUnit(BigDecimal.valueOf(20.00));
        orderItemResource.setGross(BigDecimal.valueOf(100.00));
        orderItemResource.setTotal(BigDecimal.valueOf(95.00));
        orderItemResource.setDiscount(BigDecimal.valueOf(5.00));

        String json = objectMapper.writeValueAsString(orderItemResource);
        OrderItemResource result = objectMapper.readValue(json, OrderItemResource.class);

        assertEquals("MARKETPLACE_X", result.getMarketPlaceId());
        assertEquals(Long.valueOf(99L), result.getOrderItemId());
        assertEquals("ID-IN-MKT", result.getIdInMarketPlace());
        assertEquals("PREMIUM", result.getListingType());
        assertEquals("STORE-X", result.getOfficialStoreId());
        assertEquals("Store X", result.getOfficialStoreName());
        assertEquals(BigDecimal.valueOf(5), result.getAmount());
        assertEquals(BigDecimal.valueOf(20.00), result.getUnit());
        assertEquals(BigDecimal.valueOf(100.00), result.getGross());
        assertEquals(BigDecimal.valueOf(95.00), result.getTotal());
        assertEquals(BigDecimal.valueOf(5.00), result.getDiscount());
    }

    @Test
    public void should_roundtrip_discount_item_metadata_through_json() throws Exception {
        DiscountItemMetadata meta = new DiscountItemMetadata();
        meta.setType(DiscountType.FREE_ITEM);
        meta.setDiscountProductMarketplace(BigDecimal.valueOf(4.00));

        orderItemResource.setDiscountItemMetadata(Collections.singletonList(meta));

        String json = objectMapper.writeValueAsString(orderItemResource);
        OrderItemResource result = objectMapper.readValue(json, OrderItemResource.class);

        assertEquals(1, result.getDiscountItemMetadata().size());
        assertEquals(DiscountType.FREE_ITEM, result.getDiscountItemMetadata().get(0).getType());
        assertEquals(BigDecimal.valueOf(4.00), result.getDiscountItemMetadata().get(0).getDiscountProductMarketplace());
    }

    @Test
    public void should_roundtrip_sku_through_json() throws Exception {
        SimpleSkuResource sku = new SimpleSkuResource();
        sku.setId(1L);
        sku.setPartnerId("P-001");
        sku.setExternalId("EXT-001");
        sku.setTitle("My SKU");
        orderItemResource.setSku(sku);

        String json = objectMapper.writeValueAsString(orderItemResource);
        OrderItemResource result = objectMapper.readValue(json, OrderItemResource.class);

        assertNotNull(result.getSku());
        assertEquals(Long.valueOf(1L), result.getSku().getId());
        assertEquals("P-001", result.getSku().getPartnerId());
        assertEquals("EXT-001", result.getSku().getExternalId());
        assertEquals("My SKU", result.getSku().getTitle());
    }

    @Test
    public void should_roundtrip_sku_kit_through_json() throws Exception {
        SimpleSkuKitResource skuKit = new SimpleSkuKitResource();
        skuKit.setId(10L);
        skuKit.setTitle("Kit");
        skuKit.setPartnerId("K-001");
        skuKit.setAmount(BigDecimal.valueOf(3));
        orderItemResource.setSkuKit(skuKit);

        String json = objectMapper.writeValueAsString(orderItemResource);
        OrderItemResource result = objectMapper.readValue(json, OrderItemResource.class);

        assertNotNull(result.getSkuKit());
        assertEquals(Long.valueOf(10L), result.getSkuKit().getId());
        assertEquals("Kit", result.getSkuKit().getTitle());
    }

    @Test
    public void should_roundtrip_product_through_json() throws Exception {
        SimpleProductResource product = new SimpleProductResource();
        product.setId(77L);
        product.setExternalIdProduct("EXT-77");
        product.setTitle("Product 77");
        orderItemResource.setProduct(product);

        String json = objectMapper.writeValueAsString(orderItemResource);
        OrderItemResource result = objectMapper.readValue(json, OrderItemResource.class);

        assertNotNull(result.getProduct());
        assertEquals(Long.valueOf(77L), result.getProduct().getId());
        assertEquals("EXT-77", result.getProduct().getExternalIdProduct());
        assertEquals("Product 77", result.getProduct().getTitle());
    }

    @Test
    public void should_roundtrip_stocks_through_json() throws Exception {
        OrderItemStockResource stock = new OrderItemStockResource();
        stock.setStockLocalId(3L);
        stock.setAmount(BigDecimal.valueOf(10));
        orderItemResource.setStocks(Collections.singletonList(stock));

        String json = objectMapper.writeValueAsString(orderItemResource);
        OrderItemResource result = objectMapper.readValue(json, OrderItemResource.class);

        assertEquals(1, result.getStocks().size());
        assertEquals(Long.valueOf(3L), result.getStocks().get(0).getStockLocalId());
    }

    @Test
    public void should_roundtrip_shippings_through_json() throws Exception {
        OrderItemShippingResource shipping = new OrderItemShippingResource();
        shipping.setId(200L);
        shipping.setShippingtype("STANDARD");
        orderItemResource.setShippings(Collections.singletonList(shipping));

        String json = objectMapper.writeValueAsString(orderItemResource);
        OrderItemResource result = objectMapper.readValue(json, OrderItemResource.class);

        assertEquals(1, result.getShippings().size());
        assertEquals(Long.valueOf(200L), result.getShippings().get(0).getId());
        assertEquals("STANDARD", result.getShippings().get(0).getShippingtype());
    }

    @Test
    public void should_roundtrip_customizations_through_json() throws Exception {
        OrderItemCustomizationsResource custom = new OrderItemCustomizationsResource();
        custom.setCustomizationType("SIZE");
        custom.setCustomizationValue("M");
        custom.setCustomizationQuantity(BigDecimal.ONE);
        custom.setCustomizationPrice(BigDecimal.valueOf(0));
        orderItemResource.setCustomizations(Collections.singletonList(custom));

        String json = objectMapper.writeValueAsString(orderItemResource);
        OrderItemResource result = objectMapper.readValue(json, OrderItemResource.class);

        assertEquals(1, result.getCustomizations().size());
        assertEquals("SIZE", result.getCustomizations().get(0).getCustomizationType());
        assertEquals("M", result.getCustomizations().get(0).getCustomizationValue());
    }

    @Test
    public void should_ignore_unknown_json_properties_during_deserialization() throws Exception {
        String json = "{\"unknownField\":\"ignored\",\"marketPlaceId\":\"ML\",\"orderItemId\":1}";
        OrderItemResource result = objectMapper.readValue(json, OrderItemResource.class);
        assertEquals("ML", result.getMarketPlaceId());
        assertEquals(Long.valueOf(1L), result.getOrderItemId());
    }

    @Test
    public void should_deserialize_empty_json_object_with_default_collections() throws Exception {
        String json = "{}";
        OrderItemResource result = objectMapper.readValue(json, OrderItemResource.class);
        assertNotNull(result.getDiscountItemMetadata());
        assertNotNull(result.getShippings());
        assertNotNull(result.getCustomizations());
        assertNotNull(result.getStocks());
    }
}
