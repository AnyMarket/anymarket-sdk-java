package br.com.anymarket.sdk.order;

import br.com.anymarket.sdk.http.Response;
import br.com.anymarket.sdk.http.headers.IntegrationHeader;
import br.com.anymarket.sdk.http.headers.ModuleOriginHeader;
import br.com.anymarket.sdk.http.restdsl.AnyMarketRestDSL;
import br.com.anymarket.sdk.http.restdsl.RestRequestWithBody;
import br.com.anymarket.sdk.order.dto.OrderReturn;
import br.com.anymarket.sdk.order.dto.OrderReturnShippingResource;
import org.junit.Before;
import org.junit.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import java.util.Collections;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

public class OrderReturnServiceTest {

    private static final String TEST = "TEST";

    private static final String API_ENDPOINT = "http://hom-api.anymarket.com.br/v2";

    private OrderReturnService service;

    @Before
    public void setUp() {
        service = new OrderReturnService(API_ENDPOINT, "ORIGIN");
    }

    @Test
    public void should_save_order_return_success() {
        OrderReturn orderReturn = mock(OrderReturn.class);
        when(orderReturn.getItems()).thenReturn(Collections.emptyList());
        when(orderReturn.getShipping()).thenReturn(Collections.singletonList(mock(OrderReturnShippingResource.class)));
        OrderReturn returned = mock(OrderReturn.class);

        try (MockedStatic<AnyMarketRestDSL> dsl = Mockito.mockStatic(AnyMarketRestDSL.class)) {
            RestRequestWithBody mockPost = mock(RestRequestWithBody.class);
            dsl.when(() -> AnyMarketRestDSL.post(contains("/orders/{id}/returns"))).thenReturn(mockPost);
            when(mockPost.body(any())).thenReturn(mockPost);
            when(mockPost.headers(any(IntegrationHeader[].class))).thenReturn(mockPost);
            when(mockPost.routeParam(anyString(), anyString())).thenReturn(mockPost);

            Response response = mock(Response.class);
            when(response.to(OrderReturn.class)).thenReturn(returned);
            when(mockPost.getResponse()).thenReturn(response);

            OrderReturn result = service.saveOrderReturn(10L, orderReturn, new ModuleOriginHeader(TEST));

            assertNotNull(result);
            assertSame(returned, result);
        }
    }

    @Test(expected = NullPointerException.class)
    public void should_throw_null_pointer_when_save_order_return_id_null() {
        OrderReturn orderReturn = mock(OrderReturn.class);
        when(orderReturn.getItems()).thenReturn(Collections.emptyList());
        when(orderReturn.getShipping()).thenReturn(Collections.singletonList(mock(OrderReturnShippingResource.class)));

        service.saveOrderReturn(null, orderReturn, new ModuleOriginHeader(TEST));
    }

    @Test(expected = NullPointerException.class)
    public void should_throw_null_pointer_when_save_order_return_data_null() {
        service.saveOrderReturn(10L, null, new ModuleOriginHeader(TEST));
    }

    @Test(expected = NullPointerException.class)
    public void should_throw_null_pointer_when_save_order_return_items_null() {
        OrderReturn orderReturn = mock(OrderReturn.class);
        when(orderReturn.getItems()).thenReturn(null);

        service.saveOrderReturn(10L, orderReturn, new ModuleOriginHeader(TEST));
    }

    @Test
    public void should_update_order_return_success() {
        OrderReturn orderReturn = mock(OrderReturn.class);

        try (MockedStatic<AnyMarketRestDSL> dsl = Mockito.mockStatic(AnyMarketRestDSL.class)) {
            RestRequestWithBody mockPatch = mock(RestRequestWithBody.class);
            dsl.when(() -> AnyMarketRestDSL.patch(contains("/orders/returns/{returnId}"))).thenReturn(mockPatch);
            when(mockPatch.body(any())).thenReturn(mockPatch);
            when(mockPatch.headers(any(IntegrationHeader[].class))).thenReturn(mockPatch);
            when(mockPatch.routeParam(anyString(), anyString())).thenReturn(mockPatch);

            Response response = mock(Response.class);
            when(mockPatch.getResponse()).thenReturn(response);

            service.updateOrderReturn(5L, orderReturn, new ModuleOriginHeader(TEST));

            verify(mockPatch).getResponse();
        }
    }

    @Test(expected = NullPointerException.class)
    public void should_throw_null_pointer_when_update_order_return_id_null() {
        OrderReturn orderReturn = mock(OrderReturn.class);
        service.updateOrderReturn(null, orderReturn, new ModuleOriginHeader(TEST));
    }

    @Test(expected = NullPointerException.class)
    public void should_throw_null_pointer_when_update_order_return_data_null() {
        service.updateOrderReturn(5L, null, new ModuleOriginHeader(TEST));
    }
}
