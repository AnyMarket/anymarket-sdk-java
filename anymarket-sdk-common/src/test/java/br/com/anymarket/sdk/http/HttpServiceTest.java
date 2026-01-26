package br.com.anymarket.sdk.http;

import br.com.anymarket.sdk.exception.HttpClientException;
import br.com.anymarket.sdk.exception.HttpServerException;
import br.com.anymarket.sdk.exception.UnauthorizedException;
import br.com.anymarket.sdk.http.headers.IntegrationHeader;
import com.fasterxml.jackson.core.type.TypeReference;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.mashape.unirest.request.BaseRequest;
import com.mashape.unirest.request.body.RequestBodyEntity;
import org.apache.http.HttpStatus;
import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.Map;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class HttpServiceTest {

    private HttpService service;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        service = new HttpService();
    }

    @Test
    public void should_write_value_as_json() {
        String json = service.writeValueAsJson(Collections.singletonMap("key", "value"));

        assertEquals("{\"key\":\"value\"}", json);
    }

    @Test
    public void should_read_value_from_json() {
        String json = "{\"key\":\"value\"}";

        Map<String, String> result = service.readValue(
                json,
                new TypeReference<Map<String, String>>() {
                }
        );

        assertEquals("value", result.get("key"));
    }

    @Test
    public void should_create_header_map() {
        IntegrationHeader header = new IntegrationHeader() {
            @Override
            public String getKey() {
                return "X-Test";
            }

            @Override
            public String getValue() {
                return "123";
            }
        };

        Map<String, String> map = service.toHeaderMap(header);

        assertEquals(1, map.size());
        assertEquals("123", map.get("X-Test"));
    }

    @Test
    public void should_ignore_null_headers_in_header_map() {
        Map<String, String> map = service.toHeaderMap(null);

        assertNotNull(map);
        assertTrue(map.isEmpty());
    }

    @Test
    public void should_execute_success_request() throws Exception {
        BaseRequest request = mock(BaseRequest.class);
        HttpResponse<String> response = mock(HttpResponse.class);

        when(response.getStatus()).thenReturn(HttpStatus.SC_OK);
        when(response.getBody()).thenReturn("{\"ok\":true}");
        when(request.asString()).thenReturn(response);

        Response result = service.execute(request);

        assertNotNull(result);
        assertEquals(HttpStatus.SC_OK, result.getStatus());
    }

    @Test(expected = UnauthorizedException.class)
    public void should_throw_unauthorized_exception() throws Exception {
        BaseRequest request = mock(BaseRequest.class);
        HttpResponse<String> response = mock(HttpResponse.class);

        when(response.getStatus()).thenReturn(HttpStatus.SC_UNAUTHORIZED);
        when(response.getBody()).thenReturn("{\"message\":\"unauthorized\"}");
        when(request.asString()).thenReturn(response);

        service.execute(request);
    }

    @Test(expected = HttpClientException.class)
    public void should_throw_http_client_exception() throws Exception {
        BaseRequest request = mock(BaseRequest.class);
        HttpResponse<String> response = mock(HttpResponse.class);

        when(response.getStatus()).thenReturn(HttpStatus.SC_BAD_REQUEST);
        when(response.getBody()).thenReturn("{\"message\":\"bad request\"}");
        when(request.asString()).thenReturn(response);

        service.execute(request);
    }

    @Test(expected = HttpServerException.class)
    public void should_throw_http_server_exception_on_500() throws Exception {
        BaseRequest request = mock(BaseRequest.class);
        HttpResponse<String> response = mock(HttpResponse.class);

        when(response.getStatus()).thenReturn(HttpStatus.SC_INTERNAL_SERVER_ERROR);
        when(response.getBody()).thenReturn("{\"message\":\"error\"}");
        when(request.asString()).thenReturn(response);

        service.execute(request);
    }

    @Test(expected = HttpServerException.class)
    public void should_throw_server_unavailable_when_unirest_exception() throws Exception {
        BaseRequest request = mock(BaseRequest.class);
        when(request.asString()).thenThrow(new UnirestException("connection error"));

        service.execute(request);
    }

    @Test
    public void should_create_patch_request() {
        IntegrationHeader header = new IntegrationHeader() {
            @Override
            public String getKey() {
                return "Content-Type";
            }

            @Override
            public String getValue() {
                return "application/json";
            }
        };

        RequestBodyEntity entity = service.patch(
                "http://test",
                Collections.singletonMap("key", "value"),
                header
        );

        assertNotNull(entity);
    }
}
