package br.com.anymarket.sdk.callback;

import br.com.anymarket.sdk.exception.NotFoundException;
import br.com.anymarket.sdk.http.Response;
import br.com.anymarket.sdk.http.headers.IntegrationHeader;
import br.com.anymarket.sdk.http.headers.ModuleOriginHeader;
import br.com.anymarket.sdk.paging.Page;
import com.fasterxml.jackson.core.type.TypeReference;
import com.mashape.unirest.request.BaseRequest;
import com.mashape.unirest.request.GetRequest;
import com.mashape.unirest.request.HttpRequestWithBody;
import com.mashape.unirest.request.body.RequestBodyEntity;
import org.apache.http.HttpStatus;
import org.junit.Before;
import org.junit.Test;

import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

public class CallbackServiceTest {

    private static final String API_ENDPOINT = "http://hom-api.anymarket.com.br/v2";

    private CallbackServiceFake service;

    @Before
    public void setUp() {
        service = spy(new CallbackServiceFake(API_ENDPOINT, "ORIGIN"));
    }

    @Test
    public void should_get_all_callbacks_success() {
        Callback callback = mock(Callback.class);
        Page<Callback> page = mock(Page.class);
        when(page.getContent()).thenReturn(Collections.singletonList(callback));

        GetRequest mockedGet = mock(GetRequest.class);
        doReturn(mockedGet).when(service).get(contains("/callbacks/"), any());

        Response response = mock(Response.class);
        when(response.getStatus()).thenReturn(HttpStatus.SC_OK);
        when(response.to(any(TypeReference.class))).thenReturn(page);
        doReturn(response).when(service).execute(any(BaseRequest.class));

        List<Callback> result = service.getAllCallbacks(new ModuleOriginHeader("TEST"));

        assertNotNull(result);
        assertEquals(1, result.size());
    }

    @Test(expected = NotFoundException.class)
    public void should_throw_not_found_when_get_all_callbacks_status_not_200() {
        GetRequest mockedGet = mock(GetRequest.class);
        doReturn(mockedGet).when(service).get(anyString(), any());

        Response response = mock(Response.class);
        when(response.getStatus()).thenReturn(HttpStatus.SC_INTERNAL_SERVER_ERROR);
        doReturn(response).when(service).execute(any(BaseRequest.class));

        service.getAllCallbacks(new ModuleOriginHeader("TEST"));
    }

    @Test
    public void should_insert_callback_success() {
        Callback callback = mock(Callback.class);
        Callback returned = mock(Callback.class);

        RequestBodyEntity mockedPost = mock(RequestBodyEntity.class);
        doReturn(mockedPost).when(service).post(contains("/callbacks/"), eq(callback), any());

        Response response = mock(Response.class);
        when(response.to(Callback.class)).thenReturn(returned);
        doReturn(response).when(service).execute(any(BaseRequest.class));

        Callback result = service.insertCallback(callback, new ModuleOriginHeader("TEST"));

        assertNotNull(result);
        assertSame(returned, result);
    }

    @Test
    public void should_delete_callback_success() {
        HttpRequestWithBody mockedDelete = mock(HttpRequestWithBody.class);
        doReturn(mockedDelete).when(service).delete(contains("/callbacks/10"), any());

        Response response = mock(Response.class);
        when(response.getStatus()).thenReturn(HttpStatus.SC_NO_CONTENT);
        doReturn(response).when(service).execute(any(BaseRequest.class));

        service.deleteCallback(10L, new ModuleOriginHeader("TEST"));

        verify(service).execute(any(BaseRequest.class));
    }

    @Test(expected = NotFoundException.class)
    public void should_throw_not_found_when_delete_callback_status_not_204() {
        HttpRequestWithBody mockedDelete = mock(HttpRequestWithBody.class);
        doReturn(mockedDelete).when(service).delete(anyString(), any());

        Response response = mock(Response.class);
        when(response.getStatus()).thenReturn(HttpStatus.SC_NOT_FOUND);
        doReturn(response).when(service).execute(any(BaseRequest.class));

        service.deleteCallback(10L, new ModuleOriginHeader("TEST"));
    }

    @Test
    public void should_get_callback_success() {
        Callback returned = mock(Callback.class);

        GetRequest mockedGet = mock(GetRequest.class);
        doReturn(mockedGet).when(service).get(contains("/callbacks/10"), any());

        Response response = mock(Response.class);
        when(response.getStatus()).thenReturn(HttpStatus.SC_OK);
        when(response.to(Callback.class)).thenReturn(returned);
        doReturn(response).when(service).execute(any(BaseRequest.class));

        Callback result = service.getCallback(10L, new ModuleOriginHeader("TEST"));

        assertNotNull(result);
        assertSame(returned, result);
    }

    @Test(expected = NotFoundException.class)
    public void should_throw_not_found_when_get_callback_status_not_200() {
        GetRequest mockedGet = mock(GetRequest.class);
        doReturn(mockedGet).when(service).get(anyString(), any());

        Response response = mock(Response.class);
        when(response.getStatus()).thenReturn(HttpStatus.SC_NOT_FOUND);
        doReturn(response).when(service).execute(any(BaseRequest.class));

        service.getCallback(10L, new ModuleOriginHeader("TEST"));
    }

    @Test
    public void should_update_callback_success() {
        Callback callback = mock(Callback.class);
        when(callback.getId()).thenReturn(10L);
        Callback returned = mock(Callback.class);

        RequestBodyEntity mockedPut = mock(RequestBodyEntity.class);
        doReturn(mockedPut).when(service).put(contains("/callbacks/10"), eq(callback), any());

        Response response = mock(Response.class);
        when(response.to(Callback.class)).thenReturn(returned);
        doReturn(response).when(service).execute(any(BaseRequest.class));

        Callback result = service.updateCallback(callback, new ModuleOriginHeader("TEST"));

        assertNotNull(result);
        assertSame(returned, result);
    }

    @Test(expected = NotFoundException.class)
    public void should_throw_not_found_when_update_callback_id_null() {
        Callback callback = mock(Callback.class);
        when(callback.getId()).thenReturn(null);

        service.updateCallback(callback, new ModuleOriginHeader("TEST"));
    }

    static class CallbackServiceFake extends CallbackService {
        CallbackServiceFake(String apiEndPoint, String origin) {
            super(apiEndPoint, origin);
        }

        @Override
        protected GetRequest get(String url, IntegrationHeader... headers) {
            return super.get(url, headers);
        }

        @Override
        protected RequestBodyEntity post(String url, Object body, IntegrationHeader... headers) {
            return super.post(url, body, headers);
        }

        @Override
        protected RequestBodyEntity put(String url, Object body, IntegrationHeader... headers) {
            return super.put(url, body, headers);
        }

        @Override
        protected HttpRequestWithBody delete(String url, IntegrationHeader... headers) {
            return super.delete(url, headers);
        }

        @Override
        protected Response execute(BaseRequest request) {
            return super.execute(request);
        }
    }
}
