package br.com.anymarket.sdk.variation;

import br.com.anymarket.sdk.exception.NotFoundException;
import br.com.anymarket.sdk.http.Response;
import br.com.anymarket.sdk.http.headers.IntegrationHeader;
import br.com.anymarket.sdk.http.headers.ModuleOriginHeader;
import br.com.anymarket.sdk.paging.Page;
import com.fasterxml.jackson.core.type.TypeReference;
import com.mashape.unirest.request.BaseRequest;
import com.mashape.unirest.request.GetRequest;
import com.mashape.unirest.request.body.RequestBodyEntity;
import org.apache.http.HttpStatus;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

public class VariationServiceTest {

    private static final String API_ENDPOINT = "http://hom-api.anymarket.com.br/v2";

    private VariationServiceFake service;

    @Before
    public void setUp() {
        service = spy(new VariationServiceFake(API_ENDPOINT, "ORIGIN"));
    }

    @Test
    public void should_insert_type_success() {
        VariationType type = mock(VariationType.class);
        VariationType returned = mock(VariationType.class);

        RequestBodyEntity mockedPost = mock(RequestBodyEntity.class);
        doReturn(mockedPost).when(service).post(contains("/variations"), eq(type), any());

        Response response = mock(Response.class);
        when(response.to(VariationType.class)).thenReturn(returned);
        doReturn(response).when(service).execute(any(BaseRequest.class));

        VariationType result = service.insertType(type, new ModuleOriginHeader("TEST"));

        assertNotNull(result);
        assertSame(returned, result);
    }

    @Test
    public void should_update_type_success() {
        VariationType type = mock(VariationType.class);
        when(type.getId()).thenReturn(10L);
        VariationType returned = mock(VariationType.class);

        RequestBodyEntity mockedPut = mock(RequestBodyEntity.class);
        doReturn(mockedPut).when(service).put(contains("/variations/10"), eq(type), any());

        Response response = mock(Response.class);
        when(response.to(VariationType.class)).thenReturn(returned);
        doReturn(response).when(service).execute(any(BaseRequest.class));

        VariationType result = service.updateType(type, new ModuleOriginHeader("TEST"));

        assertNotNull(result);
        assertSame(returned, result);
    }

    @Test
    public void should_insert_type_value_success() {
        VariationType type = mock(VariationType.class);
        when(type.getId()).thenReturn(10L);
        Variation value = mock(Variation.class);
        Variation returned = mock(Variation.class);

        RequestBodyEntity mockedPost = mock(RequestBodyEntity.class);
        doReturn(mockedPost).when(service).post(contains("/variations/10/values"), eq(value), any());

        Response response = mock(Response.class);
        when(response.to(Variation.class)).thenReturn(returned);
        doReturn(response).when(service).execute(any(BaseRequest.class));

        Variation result = service.insertTypeValue(type, value, new ModuleOriginHeader("TEST"));

        assertNotNull(result);
        assertSame(returned, result);
    }

    @Test
    public void should_get_type_success() {
        VariationType returned = mock(VariationType.class);

        GetRequest mockedGet = mock(GetRequest.class);
        doReturn(mockedGet).when(service).get(contains("/variations/5"), any());

        Response response = mock(Response.class);
        when(response.getStatus()).thenReturn(HttpStatus.SC_OK);
        when(response.to(VariationType.class)).thenReturn(returned);
        doReturn(response).when(service).execute(any(BaseRequest.class));

        VariationType result = service.getType(5L, new ModuleOriginHeader("TEST"));

        assertNotNull(result);
        assertSame(returned, result);
    }

    @Test(expected = NotFoundException.class)
    public void should_throw_not_found_when_get_type_status_not_200() {
        GetRequest mockedGet = mock(GetRequest.class);
        doReturn(mockedGet).when(service).get(anyString(), any());

        Response response = mock(Response.class);
        when(response.getStatus()).thenReturn(HttpStatus.SC_NOT_FOUND);
        doReturn(response).when(service).execute(any(BaseRequest.class));

        service.getType(5L, new ModuleOriginHeader("TEST"));
    }

    @Test
    public void should_get_all_types_success() {
        VariationType type = mock(VariationType.class);
        Page<VariationType> page = mock(Page.class);
        when(page.getContent()).thenReturn(Collections.singletonList(type));

        GetRequest mockedGet = mock(GetRequest.class);
        doReturn(mockedGet).when(service).get(contains("/variations"), any());

        Response response = mock(Response.class);
        when(response.getStatus()).thenReturn(HttpStatus.SC_OK);
        when(response.to(any(TypeReference.class))).thenReturn(page);
        doReturn(response).when(service).execute(any(BaseRequest.class));

        List<VariationType> result = service.getAllTypes(new ModuleOriginHeader("TEST"));

        assertNotNull(result);
        assertEquals(1, result.size());
    }

    @Test(expected = NotFoundException.class)
    public void should_throw_not_found_when_get_all_types_status_not_200() {
        GetRequest mockedGet = mock(GetRequest.class);
        doReturn(mockedGet).when(service).get(anyString(), any());

        Response response = mock(Response.class);
        when(response.getStatus()).thenReturn(HttpStatus.SC_INTERNAL_SERVER_ERROR);
        doReturn(response).when(service).execute(any(BaseRequest.class));

        service.getAllTypes(new ModuleOriginHeader("TEST"));
    }

    @Test
    public void should_get_all_types_not_paged_success() {
        VariationType type = mock(VariationType.class);

        GetRequest mockedGet = mock(GetRequest.class);
        doReturn(mockedGet).when(service).get(contains("/variations/all"), any());

        Response response = mock(Response.class);
        when(response.getStatus()).thenReturn(HttpStatus.SC_OK);
        when(response.to(any(TypeReference.class))).thenReturn(Collections.singletonList(type));
        doReturn(response).when(service).execute(any(BaseRequest.class));

        List<VariationType> result = service.getAllTypesNotPaged(new ModuleOriginHeader("TEST"));

        assertNotNull(result);
        assertEquals(1, result.size());
    }

    @Test(expected = NotFoundException.class)
    public void should_throw_not_found_when_get_all_types_not_paged_status_not_200() {
        GetRequest mockedGet = mock(GetRequest.class);
        doReturn(mockedGet).when(service).get(anyString(), any());

        Response response = mock(Response.class);
        when(response.getStatus()).thenReturn(HttpStatus.SC_INTERNAL_SERVER_ERROR);
        doReturn(response).when(service).execute(any(BaseRequest.class));

        service.getAllTypesNotPaged(new ModuleOriginHeader("TEST"));
    }

    @Test
    public void should_insert_type_if_not_exists_returns_existing() {
        VariationType existing = mock(VariationType.class);
        Page<VariationType> page = mock(Page.class);
        when(page.getContent()).thenReturn(Collections.singletonList(existing));

        GetRequest mockedGet = mock(GetRequest.class);
        doReturn(mockedGet).when(service).get(anyString(), any());

        Response response = mock(Response.class);
        when(response.getStatus()).thenReturn(HttpStatus.SC_OK);
        when(response.to(any(TypeReference.class))).thenReturn(page);
        doReturn(response).when(service).execute(any(BaseRequest.class));

        VariationType result = service.insertTypeIfNotExists("Color", new ModuleOriginHeader("TEST"));

        assertSame(existing, result);
        verify(service, never()).post(anyString(), any(), any());
    }

    @Test
    public void should_insert_type_if_not_exists_inserts_when_not_found() {
        Page<VariationType> emptyPage = mock(Page.class);
        when(emptyPage.getContent()).thenReturn(Collections.emptyList());

        GetRequest mockedGet = mock(GetRequest.class);
        doReturn(mockedGet).when(service).get(anyString(), any());

        VariationType inserted = mock(VariationType.class);
        RequestBodyEntity mockedPost = mock(RequestBodyEntity.class);
        doReturn(mockedPost).when(service).post(anyString(), any(), any());

        Response searchResponse = mock(Response.class);
        when(searchResponse.getStatus()).thenReturn(HttpStatus.SC_OK);
        when(searchResponse.to(any(TypeReference.class))).thenReturn(emptyPage);

        Response insertResponse = mock(Response.class);
        when(insertResponse.to(VariationType.class)).thenReturn(inserted);

        doReturn(searchResponse).doReturn(insertResponse).when(service).execute(any(BaseRequest.class));

        VariationType result = service.insertTypeIfNotExists("Color", new ModuleOriginHeader("TEST"));

        assertSame(inserted, result);
    }

    @Test(expected = IllegalStateException.class)
    public void should_throw_illegal_state_when_multiple_types_found() {
        VariationType t1 = mock(VariationType.class);
        VariationType t2 = mock(VariationType.class);
        Page<VariationType> page = mock(Page.class);
        when(page.getContent()).thenReturn(Arrays.asList(t1, t2));

        GetRequest mockedGet = mock(GetRequest.class);
        doReturn(mockedGet).when(service).get(anyString(), any());

        Response response = mock(Response.class);
        when(response.getStatus()).thenReturn(HttpStatus.SC_OK);
        when(response.to(any(TypeReference.class))).thenReturn(page);
        doReturn(response).when(service).execute(any(BaseRequest.class));

        service.insertTypeIfNotExists("Color", new ModuleOriginHeader("TEST"));
    }

    static class VariationServiceFake extends VariationService {
        VariationServiceFake(String apiEndPoint, String origin) {
            super(apiEndPoint, origin);
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
        protected GetRequest get(String url, IntegrationHeader... headers) {
            return super.get(url, headers);
        }

        @Override
        protected Response execute(BaseRequest request) {
            return super.execute(request);
        }
    }
}
