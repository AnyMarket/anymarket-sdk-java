package br.com.anymarket.sdk.order;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.contains;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Collections;

import org.junit.Before;
import org.junit.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import br.com.anymarket.sdk.MarketPlace;
import br.com.anymarket.sdk.exception.NotFoundException;
import br.com.anymarket.sdk.http.Response;
import br.com.anymarket.sdk.http.headers.IntegrationHeader;
import br.com.anymarket.sdk.http.headers.ModuleOriginHeader;
import br.com.anymarket.sdk.http.restdsl.AnyMarketRestDSL;
import br.com.anymarket.sdk.http.restdsl.RestGetRequest;
import br.com.anymarket.sdk.http.restdsl.RestRequestWithBody;
import br.com.anymarket.sdk.order.dto.Order;
import br.com.anymarket.sdk.order.dto.OrderStatus;
import br.com.anymarket.sdk.order.dto.OrderTransmissionStatusResource;
import br.com.anymarket.sdk.paging.Page;

public class OrderServiceTest {

    private static final String API_ENDPOINT = "http://hom-api.anymarket.com.br/v2";

    private OrderService service;

    @Before
    public void setUp() {
        service = new OrderService(API_ENDPOINT, "ORIGIN");
    }

    @Test
    public void should_get_order_success() {
        Order order = mock(Order.class);

        try (MockedStatic<AnyMarketRestDSL> dsl = Mockito.mockStatic(AnyMarketRestDSL.class)) {
            RestGetRequest mockGet = mock(RestGetRequest.class);
            dsl.when(() -> AnyMarketRestDSL.get(contains("/orders/{id}"))).thenReturn(mockGet);
            when(mockGet.headers(any(IntegrationHeader[].class))).thenReturn(mockGet);
            when(mockGet.routeParam(anyString(), anyString())).thenReturn(mockGet);

            Response response = mock(Response.class);
            when(response.to(Order.class)).thenReturn(order);
            when(mockGet.getResponse()).thenReturn(response);

            Order result = service.getOrder(123L, new ModuleOriginHeader("TEST"));

            assertNotNull(result);
            assertSame(order, result);
        }
    }

    @Test(expected = NullPointerException.class)
    public void should_throw_null_pointer_when_get_order_id_null() {
        service.getOrder(null, new ModuleOriginHeader("TEST"));
    }

    @Test
    public void should_get_order_status_by_marketplace_id_success() {
        OrderStatus status = mock(OrderStatus.class);

        try (MockedStatic<AnyMarketRestDSL> dsl = Mockito.mockStatic(AnyMarketRestDSL.class)) {
            RestGetRequest mockGet = mock(RestGetRequest.class);
            dsl.when(() -> AnyMarketRestDSL.get(anyString())).thenReturn(mockGet);
            when(mockGet.headers(any(IntegrationHeader[].class))).thenReturn(mockGet);
            when(mockGet.routeParam(anyString(), anyString())).thenReturn(mockGet);

            Response response = mock(Response.class);
            when(response.to(OrderStatus.class)).thenReturn(status);
            when(mockGet.getResponse()).thenReturn(response);

            OrderStatus result = service.getOrderStatusByMarketplaceId("MP-123", MarketPlace.B2W, new ModuleOriginHeader("TEST"));

            assertNotNull(result);
            assertSame(status, result);
        }
    }

    @Test
    public void should_create_order_success() {
        Order order = mock(Order.class);
        Order returned = mock(Order.class);

        try (MockedStatic<AnyMarketRestDSL> dsl = Mockito.mockStatic(AnyMarketRestDSL.class)) {
            RestRequestWithBody mockPost = mock(RestRequestWithBody.class);
            dsl.when(() -> AnyMarketRestDSL.post(contains("/orders"))).thenReturn(mockPost);
            when(mockPost.body(any())).thenReturn(mockPost);
            when(mockPost.headers(any(IntegrationHeader[].class))).thenReturn(mockPost);

            Response response = mock(Response.class);
            when(response.to(Order.class)).thenReturn(returned);
            when(mockPost.getResponse()).thenReturn(response);

            Order result = service.createOrder(order, new ModuleOriginHeader("TEST"));

            assertNotNull(result);
            assertSame(returned, result);
        }
    }

    @Test(expected = NullPointerException.class)
    public void should_throw_null_pointer_when_create_order_null() {
        service.createOrder(null, new ModuleOriginHeader("TEST"));
    }

    @Test
    public void should_update_order_success() {
        Order order = mock(Order.class);
        when(order.getId()).thenReturn(10L);
        Order returned = mock(Order.class);

        try (MockedStatic<AnyMarketRestDSL> dsl = Mockito.mockStatic(AnyMarketRestDSL.class)) {
            RestRequestWithBody mockPut = mock(RestRequestWithBody.class);
            dsl.when(() -> AnyMarketRestDSL.put(anyString())).thenReturn(mockPut);
            when(mockPut.body(any())).thenReturn(mockPut);
            when(mockPut.headers(any(IntegrationHeader[].class))).thenReturn(mockPut);
            when(mockPut.routeParam(anyString(), anyString())).thenReturn(mockPut);

            Response response = mock(Response.class);
            when(response.to(Order.class)).thenReturn(returned);
            when(mockPut.getResponse()).thenReturn(response);

            Order result = service.updateOrder(order, new ModuleOriginHeader("TEST"));

            assertNotNull(result);
            assertSame(returned, result);
        }
    }

    @Test
    public void should_get_orders_success() {
        Page<Order> page = mock(Page.class);
        when(page.getContent()).thenReturn(Collections.emptyList());

        try (MockedStatic<AnyMarketRestDSL> dsl = Mockito.mockStatic(AnyMarketRestDSL.class)) {
            RestGetRequest mockGet = mock(RestGetRequest.class);
            dsl.when(() -> AnyMarketRestDSL.get(contains("/orders"))).thenReturn(mockGet);
            when(mockGet.headers(any(IntegrationHeader[].class))).thenReturn(mockGet);
            when(mockGet.filters(anyList())).thenReturn(mockGet);

            Response response = mock(Response.class);
            when(response.to(OrderService.PAGED_TYPE_REFERENCE)).thenReturn(page);
            when(mockGet.getResponse()).thenReturn(response);

            Page<Order> result = service.getOrders(Collections.emptyList(), new ModuleOriginHeader("TEST"));

            assertNotNull(result);
        }
    }

    @Test
    public void should_get_order_by_partner_id_success() {
        Order order = mock(Order.class);
        Page<Order> page = mock(Page.class);
        when(page.getContent()).thenReturn(Collections.singletonList(order));

        try (MockedStatic<AnyMarketRestDSL> dsl = Mockito.mockStatic(AnyMarketRestDSL.class)) {
            RestGetRequest mockGet = mock(RestGetRequest.class);
            dsl.when(() -> AnyMarketRestDSL.get(anyString())).thenReturn(mockGet);
            when(mockGet.headers(any(IntegrationHeader[].class))).thenReturn(mockGet);
            when(mockGet.filters(anyList())).thenReturn(mockGet);

            Response response = mock(Response.class);
            when(response.to(OrderService.PAGED_TYPE_REFERENCE)).thenReturn(page);
            when(mockGet.getResponse()).thenReturn(response);

            Order result = service.getOrderByPartnerID("PARTNER-1", new ModuleOriginHeader("TEST"));

            assertNotNull(result);
            assertSame(order, result);
        }
    }

    @Test(expected = NotFoundException.class)
    public void should_throw_not_found_when_get_order_by_partner_id_empty() {
        Page<Order> page = mock(Page.class);
        when(page.getContent()).thenReturn(Collections.emptyList());

        try (MockedStatic<AnyMarketRestDSL> dsl = Mockito.mockStatic(AnyMarketRestDSL.class)) {
            RestGetRequest mockGet = mock(RestGetRequest.class);
            dsl.when(() -> AnyMarketRestDSL.get(anyString())).thenReturn(mockGet);
            when(mockGet.headers(any(IntegrationHeader[].class))).thenReturn(mockGet);
            when(mockGet.filters(anyList())).thenReturn(mockGet);

            Response response = mock(Response.class);
            when(response.to(OrderService.PAGED_TYPE_REFERENCE)).thenReturn(page);
            when(mockGet.getResponse()).thenReturn(response);

            service.getOrderByPartnerID("PARTNER-1", new ModuleOriginHeader("TEST"));
        }
    }

    @Test
    public void should_update_transmission_status_success() {
        OrderTransmissionStatusResource resource = mock(OrderTransmissionStatusResource.class);
        Order returned = mock(Order.class);

        try (MockedStatic<AnyMarketRestDSL> dsl = Mockito.mockStatic(AnyMarketRestDSL.class)) {
            RestRequestWithBody mockPut = mock(RestRequestWithBody.class);
            dsl.when(() -> AnyMarketRestDSL.put(contains("/orders/{id}/transmissionStatus"))).thenReturn(mockPut);
            when(mockPut.body(any())).thenReturn(mockPut);
            when(mockPut.headers(any(IntegrationHeader[].class))).thenReturn(mockPut);
            when(mockPut.routeParam(anyString(), anyString())).thenReturn(mockPut);

            Response response = mock(Response.class);
            when(response.to(Order.class)).thenReturn(returned);
            when(mockPut.getResponse()).thenReturn(response);

            Order result = service.updateTransmissionStatus(10L, resource, new ModuleOriginHeader("TEST"));

            assertNotNull(result);
            assertSame(returned, result);
        }
    }
}
