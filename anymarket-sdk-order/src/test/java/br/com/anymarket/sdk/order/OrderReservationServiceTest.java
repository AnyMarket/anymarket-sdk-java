package br.com.anymarket.sdk.order;

import br.com.anymarket.sdk.MarketPlace;
import br.com.anymarket.sdk.http.Response;
import br.com.anymarket.sdk.http.headers.IntegrationHeader;
import br.com.anymarket.sdk.http.headers.ModuleOriginHeader;
import br.com.anymarket.sdk.http.restdsl.AnyMarketRestDSL;
import br.com.anymarket.sdk.http.restdsl.RestGetRequest;
import br.com.anymarket.sdk.http.restdsl.RestRequestWithBody;
import br.com.anymarket.sdk.order.dto.OrderReservation;
import org.junit.Before;
import org.junit.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

public class OrderReservationServiceTest {

    private static final String API_ENDPOINT = "http://hom-api.anymarket.com.br/v2";

    private OrderReservationService service;

    @Before
    public void setUp() {
        service = new OrderReservationService(API_ENDPOINT, "ORIGIN");
    }

    @Test
    public void should_get_order_reservation_success() {
        OrderReservation returned = mock(OrderReservation.class);

        try (MockedStatic<AnyMarketRestDSL> dsl = Mockito.mockStatic(AnyMarketRestDSL.class)) {
            RestGetRequest mockGet = mock(RestGetRequest.class);
            dsl.when(() -> AnyMarketRestDSL.get(contains("/orders/reservations"))).thenReturn(mockGet);
            when(mockGet.headers(any(IntegrationHeader[].class))).thenReturn(mockGet);
            when(mockGet.queryString(anyString(), anyString())).thenReturn(mockGet);

            Response response = mock(Response.class);
            when(response.to(OrderReservation.class)).thenReturn(returned);
            when(mockGet.getResponse()).thenReturn(response);

            OrderReservation result = service.getOrderReservation(10L, new ModuleOriginHeader("TEST"));

            assertNotNull(result);
            assertSame(returned, result);
        }
    }

    @Test(expected = NullPointerException.class)
    public void should_throw_null_pointer_when_get_order_reservation_id_null() {
        service.getOrderReservation(null, new ModuleOriginHeader("TEST"));
    }

    @Test
    public void should_get_order_reservation_by_id_in_marketplace_success() {
        OrderReservation returned = mock(OrderReservation.class);

        try (MockedStatic<AnyMarketRestDSL> dsl = Mockito.mockStatic(AnyMarketRestDSL.class)) {
            RestGetRequest mockGet = mock(RestGetRequest.class);
            dsl.when(() -> AnyMarketRestDSL.get(contains("/orders/reservations"))).thenReturn(mockGet);
            when(mockGet.headers(any(IntegrationHeader[].class))).thenReturn(mockGet);
            when(mockGet.queryString(anyString(), anyString())).thenReturn(mockGet);

            Response response = mock(Response.class);
            when(response.to(OrderReservation.class)).thenReturn(returned);
            when(mockGet.getResponse()).thenReturn(response);

            OrderReservation result = service.getOrderReservationByIdInMarketplace(
                    "MP-123", MarketPlace.B2W, new ModuleOriginHeader("TEST"));

            assertNotNull(result);
            assertSame(returned, result);
        }
    }

    @Test(expected = NullPointerException.class)
    public void should_throw_null_pointer_when_get_reservation_by_marketplace_id_null() {
        service.getOrderReservationByIdInMarketplace(null, MarketPlace.B2W, new ModuleOriginHeader("TEST"));
    }

    @Test(expected = NullPointerException.class)
    public void should_throw_null_pointer_when_get_reservation_by_marketplace_null() {
        service.getOrderReservationByIdInMarketplace("MP-123", null, new ModuleOriginHeader("TEST"));
    }

    @Test
    public void should_create_order_reservation_success() {
        OrderReservation reservation = mock(OrderReservation.class);
        OrderReservation returned = mock(OrderReservation.class);

        try (MockedStatic<AnyMarketRestDSL> dsl = Mockito.mockStatic(AnyMarketRestDSL.class)) {
            RestRequestWithBody mockPost = mock(RestRequestWithBody.class);
            dsl.when(() -> AnyMarketRestDSL.post(contains("/orders/reservations"))).thenReturn(mockPost);
            when(mockPost.body(any())).thenReturn(mockPost);
            when(mockPost.headers(any(IntegrationHeader[].class))).thenReturn(mockPost);

            Response response = mock(Response.class);
            when(response.to(OrderReservation.class)).thenReturn(returned);
            when(mockPost.getResponse()).thenReturn(response);

            OrderReservation result = service.createOrderReservation(reservation, new ModuleOriginHeader("TEST"));

            assertNotNull(result);
            assertSame(returned, result);
        }
    }

    @Test(expected = NullPointerException.class)
    public void should_throw_null_pointer_when_create_reservation_null() {
        service.createOrderReservation(null, new ModuleOriginHeader("TEST"));
    }

    @Test
    public void should_cancel_order_reservation_success() {
        try (MockedStatic<AnyMarketRestDSL> dsl = Mockito.mockStatic(AnyMarketRestDSL.class)) {
            RestRequestWithBody mockPut = mock(RestRequestWithBody.class);
            dsl.when(() -> AnyMarketRestDSL.put(contains("/orders/reservations/{id}/cancel"))).thenReturn(mockPut);
            when(mockPut.routeParam(anyString(), anyString())).thenReturn(mockPut);
            when(mockPut.headers(any(IntegrationHeader[].class))).thenReturn(mockPut);

            Response response = mock(Response.class);
            when(mockPut.getResponse()).thenReturn(response);

            service.cancelOrderReservation(10L, new ModuleOriginHeader("TEST"));

            verify(mockPut).getResponse();
        }
    }

    @Test(expected = NullPointerException.class)
    public void should_throw_null_pointer_when_cancel_reservation_id_null() {
        service.cancelOrderReservation(null, new ModuleOriginHeader("TEST"));
    }
}
