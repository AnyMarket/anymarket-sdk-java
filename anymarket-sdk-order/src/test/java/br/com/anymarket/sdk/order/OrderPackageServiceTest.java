package br.com.anymarket.sdk.order;

import br.com.anymarket.sdk.http.Response;
import br.com.anymarket.sdk.http.headers.IntegrationHeader;
import br.com.anymarket.sdk.http.restdsl.AnyMarketRestDSL;
import br.com.anymarket.sdk.http.restdsl.RestGetRequest;
import br.com.anymarket.sdk.http.restdsl.RestRequestWithBody;
import br.com.anymarket.sdk.order.dto.DimensionsPackage;
import br.com.anymarket.sdk.order.dto.ItemsPackage;
import br.com.anymarket.sdk.order.dto.Order;
import br.com.anymarket.sdk.order.dto.TrackingResource;
import br.com.anymarket.sdk.order.dto.packages.OrderPackage;
import br.com.anymarket.sdk.order.dto.packages.OrderPackageDeliverRequest;
import br.com.anymarket.sdk.order.dto.packages.OrderPackageInput;
import br.com.anymarket.sdk.order.dto.packages.OrderPackageInvoiceResource;
import br.com.anymarket.sdk.order.dto.packages.OrderPackageReason;
import br.com.anymarket.sdk.order.dto.packages.OrderPackageRequest;
import br.com.anymarket.sdk.order.dto.packages.OrderPackageUnit;
import com.fasterxml.jackson.core.type.TypeReference;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.mashape.unirest.request.HttpRequestWithBody;
import com.mashape.unirest.request.body.MultipartBody;
import org.apache.http.entity.ContentType;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.powermock.api.mockito.PowerMockito.mockStatic;

@RunWith(PowerMockRunner.class)
@PrepareForTest({AnyMarketRestDSL.class, Unirest.class})
public class OrderPackageServiceTest {

    private static final Long ORDER_ID = 123L;
    private static final String ENDPOINT = "http://test-endpoint.com";

    private OrderPackageService service;

    private Response mockDslResponse;
    private HttpRequestWithBody mockUnirestPost;

    @Before
    public void setUp() throws UnirestException {
        service = new OrderPackageService(ENDPOINT);

        RestRequestWithBody mockPostPutRequest = mock(RestRequestWithBody.class);
        RestGetRequest mockGetRequest = mock(RestGetRequest.class);
        mockDslResponse = mock(Response.class);
        mockUnirestPost = mock(HttpRequestWithBody.class);

        when(mockPostPutRequest.body(any())).thenReturn(mockPostPutRequest);
        when(mockPostPutRequest.headers(any(IntegrationHeader[].class))).thenReturn(mockPostPutRequest);
        when(mockPostPutRequest.routeParam(anyString(), anyString())).thenReturn(mockPostPutRequest);
        when(mockPostPutRequest.queryString(anyString(), anyString())).thenReturn(mockPostPutRequest);
        when(mockPostPutRequest.getResponse()).thenReturn(mockDslResponse);

        when(mockGetRequest.headers(any(IntegrationHeader[].class))).thenReturn(mockGetRequest);
        when(mockGetRequest.routeParam(anyString(), anyString())).thenReturn(mockGetRequest);
        when(mockGetRequest.getResponse()).thenReturn(mockDslResponse);

        mockStatic(AnyMarketRestDSL.class);
        when(AnyMarketRestDSL.post(anyString())).thenReturn(mockPostPutRequest);
        when(AnyMarketRestDSL.get(anyString())).thenReturn(mockGetRequest);
        when(AnyMarketRestDSL.put(anyString())).thenReturn(mockPostPutRequest);

        MultipartBody mockMultipartBody = mock(MultipartBody.class);
        when(mockUnirestPost.header(anyString(), anyString())).thenReturn(mockUnirestPost);
        when(mockUnirestPost.fields(any(java.util.Map.class))).thenReturn(mockMultipartBody);
        when(mockMultipartBody.field(anyString(), any(byte[].class), any(ContentType.class), anyString())).thenReturn(mockMultipartBody);

        mockStatic(Unirest.class);
        when(Unirest.post(anyString())).thenReturn(mockUnirestPost);
        HttpResponse<String> mockUnirestResponse = mock(HttpResponse.class);
        when(mockUnirestPost.asString()).thenReturn(mockUnirestResponse);
    }

    @Test
    public void constructor_with_valid_endpoint_should_create_service() {
        assertNotNull(new OrderPackageService(ENDPOINT));
    }

    @Test
    public void constructor_with_null_endpoint_should_use_homolog_default() {
        assertNotNull(new OrderPackageService(null));
    }

    @Test
    public void constructor_with_empty_endpoint_should_use_homolog_default() {
        assertNotNull(new OrderPackageService(""));
    }

    @Test
    public void constructor_with_origin_and_valid_endpoint_should_create_service() {
        assertNotNull(new OrderPackageService(ENDPOINT, "my-module"));
    }

    @Test
    public void constructor_with_origin_and_null_endpoint_should_use_homolog_default() {
        assertNotNull(new OrderPackageService(null, "my-module"));
    }

    @Test(expected = NullPointerException.class)
    public void create_packages_with_null_order_id_should_throw() {
        service.createPackages(null, buildValidPackageRequest());
    }

    @Test(expected = NullPointerException.class)
    public void create_packages_with_null_request_should_throw() {
        service.createPackages(ORDER_ID, null);
    }

    @Test(expected = NullPointerException.class)
    public void create_packages_with_null_packages_list_should_throw() {
        OrderPackageRequest request = OrderPackageRequest.builder()
                .packageReason(buildPackageReason())
                .packages(null)
                .build();
        service.createPackages(ORDER_ID, request);
    }

    @Test(expected = NullPointerException.class)
    public void create_packages_with_null_items_in_package_should_throw() {
        OrderPackageRequest request = OrderPackageRequest.builder()
                .packages(Collections.singletonList(
                        OrderPackageInput.builder().items(null).build()))
                .build();
        service.createPackages(ORDER_ID, request);
    }

    @Test(expected = IllegalArgumentException.class)
    public void create_packages_with_null_sku_should_throw() {
        service.createPackages(ORDER_ID, buildPackageRequestWith(
                ItemsPackage.builder().sku(null).quantity(BigDecimal.ONE).build()));
    }

    @Test(expected = IllegalArgumentException.class)
    public void create_packages_with_empty_sku_should_throw() {
        service.createPackages(ORDER_ID, buildPackageRequestWith(
                ItemsPackage.builder().sku("").quantity(BigDecimal.ONE).build()));
    }

    @Test(expected = NullPointerException.class)
    public void create_packages_with_null_quantity_should_throw() {
        service.createPackages(ORDER_ID, buildPackageRequestWith(
                ItemsPackage.builder().sku("SKU-001").quantity(null).build()));
    }

    @Test
    public void create_packages_should_post_to_packages_endpoint_and_return_result() {
        OrderPackage expected = new OrderPackage();
        expected.setOrderId(ORDER_ID);
        expected.setPackageId(10L);
        when(mockDslResponse.to(OrderPackage.class)).thenReturn(expected);

        OrderPackage result = service.createPackages(ORDER_ID, buildValidPackageRequest());

        assertNotNull(result);
        assertEquals(ORDER_ID, result.getOrderId());
        assertEquals(Long.valueOf(10L), result.getPackageId());
        PowerMockito.verifyStatic();
        AnyMarketRestDSL.post(ENDPOINT.concat("/orders/{orderId}/packages"));
    }

    @Test(expected = NullPointerException.class)
    public void get_packages_with_null_order_id_should_throw() {
        service.getPackages(null);
    }

    @Test
    public void get_packages_should_get_packages_endpoint_and_return_list() {
        List<OrderPackageUnit> expected = Collections.singletonList(new OrderPackageUnit());
        when(mockDslResponse.to(any(TypeReference.class))).thenReturn(expected);

        List<OrderPackageUnit> result = service.getPackages(ORDER_ID);

        assertNotNull(result);
        assertEquals(1, result.size());
        PowerMockito.verifyStatic();
        AnyMarketRestDSL.get(ENDPOINT.concat("/orders/{orderId}/packages"));
    }

    @Test(expected = NullPointerException.class)
    public void invoice_package_with_null_order_id_should_throw() {
        service.invoicePackage(null, new byte[0], buildValidInvoiceResource());
    }

    @Test
    public void invoice_package_with_null_xml_bytes_should_post_without_file_field() {
        service.invoicePackage(ORDER_ID, null, buildValidInvoiceResource());

        PowerMockito.verifyStatic();
        Unirest.post(ENDPOINT.concat("/orders/").concat(ORDER_ID.toString()).concat("/fiscalDocument"));
    }

    @Test(expected = NullPointerException.class)
    public void invoice_package_with_null_invoice_resource_should_throw() {
        service.invoicePackage(ORDER_ID, new byte[0], null);
    }

    @Test
    public void invoice_package_should_post_to_fiscal_document_endpoint() {
        service.invoicePackage(ORDER_ID, new byte[0], buildValidInvoiceResource());

        PowerMockito.verifyStatic();
        Unirest.post(ENDPOINT.concat("/orders/").concat(ORDER_ID.toString()).concat("/fiscalDocument"));
    }

    @Test
    public void invoice_package_with_module_origin_should_set_header_on_post_request() {
        OrderPackageService serviceWithOrigin = new OrderPackageService(ENDPOINT, "my-module");

        serviceWithOrigin.invoicePackage(ORDER_ID, new byte[0], buildValidInvoiceResource());

        PowerMockito.verifyStatic();
        Unirest.post(ENDPOINT.concat("/orders/").concat(ORDER_ID.toString()).concat("/fiscalDocument"));
    }

    @Test(expected = IllegalStateException.class)
    public void invoice_package_when_prepare_fails_should_throw_illegal_state() {
        when(mockUnirestPost.fields(any(java.util.Map.class))).thenThrow(new RuntimeException("unirest error"));

        service.invoicePackage(ORDER_ID, new byte[0], buildValidInvoiceResource());
    }

    @Test(expected = NullPointerException.class)
    public void send_package_with_null_order_id_should_throw() {
        service.sendPackage(null, "pkg-1", buildValidTrackingResource());
    }

    @Test(expected = NullPointerException.class)
    public void send_package_with_null_package_id_should_throw() {
        service.sendPackage(ORDER_ID, null, buildValidTrackingResource());
    }

    @Test(expected = NullPointerException.class)
    public void send_package_with_null_tracking_resource_should_throw() {
        service.sendPackage(ORDER_ID, "pkg-1", null);
    }

    @Test
    public void send_package_should_put_to_orders_endpoint_and_return_order() {
        Order expected = new Order();
        when(mockDslResponse.to(Order.class)).thenReturn(expected);

        Order result = service.sendPackage(ORDER_ID, "pkg-1", buildValidTrackingResource());

        assertNotNull(result);
        PowerMockito.verifyStatic();
        AnyMarketRestDSL.put(ENDPOINT.concat("/orders/{orderId}"));
    }

    @Test(expected = NullPointerException.class)
    public void deliver_package_with_null_order_id_should_throw() {
        service.deliverPackage(null, buildValidDeliverRequest());
    }

    @Test(expected = NullPointerException.class)
    public void deliver_package_with_null_deliver_request_should_throw() {
        service.deliverPackage(ORDER_ID, null);
    }

    @Test
    public void deliver_package_should_put_to_delivered_endpoint() {
        service.deliverPackage(ORDER_ID, buildValidDeliverRequest());

        PowerMockito.verifyStatic();
        AnyMarketRestDSL.put(ENDPOINT.concat("/orders/{orderId}/packages/delivered"));
    }

    private OrderPackageRequest buildValidPackageRequest() {
        ItemsPackage item = ItemsPackage.builder()
                .sku("SKU-001")
                .quantity(BigDecimal.ONE)
                .build();
        OrderPackageInput packageInput = OrderPackageInput.builder()
                .dimensions(DimensionsPackage.builder()
                        .length(new BigDecimal("30"))
                        .width(new BigDecimal("20"))
                        .height(new BigDecimal("15"))
                        .weight(new BigDecimal("1.5"))
                        .build())
                .items(Collections.singletonList(item))
                .externalId("ext-pkg-001")
                .build();
        return OrderPackageRequest.builder()
                .packageReason(buildPackageReason())
                .packages(Collections.singletonList(packageInput))
                .build();
    }

    private OrderPackageRequest buildPackageRequestWith(ItemsPackage item) {
        return OrderPackageRequest.builder()
                .packages(Collections.singletonList(
                        OrderPackageInput.builder()
                                .items(Collections.singletonList(item))
                                .build()))
                .build();
    }

    private OrderPackageInvoiceResource buildValidInvoiceResource() {
        return OrderPackageInvoiceResource.builder()
                .number("NF-001")
                .date(new Date())
                .packageId("pkg-1")
                .build();
    }

    private TrackingResource buildValidTrackingResource() {
        TrackingResource tracking = new TrackingResource();
        tracking.setNumber("TRACK-001");
        tracking.setCarrier("Correios");
        return tracking;
    }

    private OrderPackageDeliverRequest buildValidDeliverRequest() {
        return OrderPackageDeliverRequest.builder()
                .packageId("pkg-1")
                .deliveredDate(new Date())
                .build();
    }

    private OrderPackageReason buildPackageReason() {
        return OrderPackageReason.builder()
                .id("SPLIT")
                .description("Split de pacote")
                .build();
    }
}
