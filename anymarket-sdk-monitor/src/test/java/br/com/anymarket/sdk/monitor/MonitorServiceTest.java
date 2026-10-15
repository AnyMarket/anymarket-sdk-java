package br.com.anymarket.sdk.monitor;

import br.com.anymarket.sdk.http.Response;
import br.com.anymarket.sdk.http.headers.IntegrationHeader;
import br.com.anymarket.sdk.http.headers.ModuleOriginHeader;
import br.com.anymarket.sdk.http.restdsl.AnyMarketRestDSL;
import br.com.anymarket.sdk.http.restdsl.RestRequestWithBody;
import br.com.anymarket.sdk.monitor.dto.Monitor;
import org.junit.Before;
import org.junit.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

public class MonitorServiceTest {

    private static final String API_ENDPOINT = "http://hom-api.anymarket.com.br/v2";

    private MonitorService service;

    @Before
    public void setUp() {
        service = new MonitorService(API_ENDPOINT, "ORIGIN");
    }

    @Test
    public void should_insert_monitor_success() {
        Monitor monitor = mock(Monitor.class);

        try (MockedStatic<AnyMarketRestDSL> dsl = Mockito.mockStatic(AnyMarketRestDSL.class)) {
            RestRequestWithBody mockPost = mock(RestRequestWithBody.class);
            dsl.when(() -> AnyMarketRestDSL.post(contains("/monitorings"))).thenReturn(mockPost);
            when(mockPost.headers(any(IntegrationHeader[].class))).thenReturn(mockPost);
            when(mockPost.body(any())).thenReturn(mockPost);

            Response response = mock(Response.class);
            when(response.getStatus()).thenReturn(200);
            when(mockPost.getResponse()).thenReturn(response);

            Response result = service.insert(monitor, new ModuleOriginHeader("TEST"));

            assertNotNull(result);
            verify(mockPost).getResponse();
        }
    }

    @Test
    public void should_conclude_monitor_success() {
        Monitor monitor = mock(Monitor.class);

        try (MockedStatic<AnyMarketRestDSL> dsl = Mockito.mockStatic(AnyMarketRestDSL.class)) {
            RestRequestWithBody mockPost = mock(RestRequestWithBody.class);
            dsl.when(() -> AnyMarketRestDSL.post(contains("/monitorings/conclude"))).thenReturn(mockPost);
            when(mockPost.headers(any(IntegrationHeader[].class))).thenReturn(mockPost);
            when(mockPost.body(any())).thenReturn(mockPost);

            Response response = mock(Response.class);
            when(response.getStatus()).thenReturn(200);
            when(mockPost.getResponse()).thenReturn(response);

            Response result = service.conclude(monitor, new ModuleOriginHeader("TEST"));

            assertNotNull(result);
        }
    }

    @Test
    public void should_update_monitor_success() {
        Monitor monitor = mock(Monitor.class);
        when(monitor.getId()).thenReturn(99L);

        try (MockedStatic<AnyMarketRestDSL> dsl = Mockito.mockStatic(AnyMarketRestDSL.class)) {
            RestRequestWithBody mockPut = mock(RestRequestWithBody.class);
            dsl.when(() -> AnyMarketRestDSL.put(anyString())).thenReturn(mockPut);
            when(mockPut.headers(any(IntegrationHeader[].class))).thenReturn(mockPut);
            when(mockPut.routeParam(anyString(), anyString())).thenReturn(mockPut);
            when(mockPut.body(any())).thenReturn(mockPut);

            Response response = mock(Response.class);
            when(response.getStatus()).thenReturn(200);
            when(mockPut.getResponse()).thenReturn(response);

            Response result = service.update(monitor, new ModuleOriginHeader("TEST"));

            assertNotNull(result);
        }
    }

    @Test
    public void should_use_default_endpoint_when_null_given() {
        MonitorService svc = new MonitorService(null, "ORIGIN");

        Monitor monitor = mock(Monitor.class);

        try (MockedStatic<AnyMarketRestDSL> dsl = Mockito.mockStatic(AnyMarketRestDSL.class)) {
            RestRequestWithBody mockPost = mock(RestRequestWithBody.class);
            dsl.when(() -> AnyMarketRestDSL.post(anyString())).thenReturn(mockPost);
            when(mockPost.headers(any(IntegrationHeader[].class))).thenReturn(mockPost);
            when(mockPost.body(any())).thenReturn(mockPost);

            Response response = mock(Response.class);
            when(mockPost.getResponse()).thenReturn(response);

            Response result = svc.insert(monitor, new ModuleOriginHeader("TEST"));

            assertNotNull(result);
        }
    }
}
