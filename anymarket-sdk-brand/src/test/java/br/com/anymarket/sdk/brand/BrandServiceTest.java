package br.com.anymarket.sdk.brand;

import br.com.anymarket.sdk.brand.dto.Brand;
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

public class BrandServiceTest {

    private static final String API_ENDPOINT = "http://hom-api.anymarket.com.br/v2";

    private BrandServiceFake service;

    @Before
    public void setUp() {
        service = spy(new BrandServiceFake(API_ENDPOINT, "ORIGIN"));
    }

    @Test
    public void should_insert_brand_success() {
        Brand brand = mock(Brand.class);
        Brand returned = mock(Brand.class);

        RequestBodyEntity mockedPost = mock(RequestBodyEntity.class);
        doReturn(mockedPost).when(service).post(contains("/brands"), eq(brand), any());

        Response response = mock(Response.class);
        when(response.to(Brand.class)).thenReturn(returned);
        doReturn(response).when(service).execute(any(BaseRequest.class));

        Brand result = service.insertBrand(brand, new ModuleOriginHeader("TEST"));

        assertNotNull(result);
        assertSame(returned, result);
        verify(service).post(contains("/brands"), eq(brand), any(IntegrationHeader[].class));
    }

    @Test
    public void should_update_brand_success() {
        Brand brand = mock(Brand.class);
        when(brand.getId()).thenReturn(10L);
        Brand returned = mock(Brand.class);

        RequestBodyEntity mockedPut = mock(RequestBodyEntity.class);
        doReturn(mockedPut).when(service).put(contains("/brands/10"), eq(brand), any());

        Response response = mock(Response.class);
        when(response.to(Brand.class)).thenReturn(returned);
        doReturn(response).when(service).execute(any(BaseRequest.class));

        Brand result = service.updateBrand(brand, new ModuleOriginHeader("TEST"));

        assertNotNull(result);
        assertSame(returned, result);
        verify(service).put(contains("/brands/10"), eq(brand), any(IntegrationHeader[].class));
    }

    @Test
    public void should_get_brand_success() {
        Brand returned = mock(Brand.class);

        GetRequest mockedGet = mock(GetRequest.class);
        doReturn(mockedGet).when(service).get(contains("/brands/5"), any());

        Response response = mock(Response.class);
        when(response.getStatus()).thenReturn(HttpStatus.SC_OK);
        when(response.to(Brand.class)).thenReturn(returned);
        doReturn(response).when(service).execute(any(BaseRequest.class));

        Brand result = service.getBrand(5L, new ModuleOriginHeader("TEST"));

        assertNotNull(result);
        assertSame(returned, result);
    }

    @Test(expected = NotFoundException.class)
    public void should_throw_not_found_when_get_brand_status_not_200() {
        GetRequest mockedGet = mock(GetRequest.class);
        doReturn(mockedGet).when(service).get(anyString(), any());

        Response response = mock(Response.class);
        when(response.getStatus()).thenReturn(HttpStatus.SC_NOT_FOUND);
        doReturn(response).when(service).execute(any(BaseRequest.class));

        service.getBrand(5L, new ModuleOriginHeader("TEST"));
    }

    @Test
    public void should_get_all_brands_success() {
        Brand brand = mock(Brand.class);
        Page<Brand> page = mock(Page.class);
        when(page.getContent()).thenReturn(Collections.singletonList(brand));

        GetRequest mockedGet = mock(GetRequest.class);
        doReturn(mockedGet).when(service).get(contains("/brands"), any());

        Response response = mock(Response.class);
        when(response.getStatus()).thenReturn(HttpStatus.SC_OK);
        when(response.to(any(TypeReference.class))).thenReturn(page);
        doReturn(response).when(service).execute(any(BaseRequest.class));

        List<Brand> result = service.getAllBrands(new ModuleOriginHeader("TEST"));

        assertNotNull(result);
        assertEquals(1, result.size());
    }

    @Test(expected = NotFoundException.class)
    public void should_throw_not_found_when_get_all_brands_status_not_200() {
        GetRequest mockedGet = mock(GetRequest.class);
        doReturn(mockedGet).when(service).get(anyString(), any());

        Response response = mock(Response.class);
        when(response.getStatus()).thenReturn(HttpStatus.SC_INTERNAL_SERVER_ERROR);
        doReturn(response).when(service).execute(any(BaseRequest.class));

        service.getAllBrands(new ModuleOriginHeader("TEST"));
    }

    @Test
    public void should_insert_brand_if_not_exists_returns_existing_when_found() {
        Brand existing = mock(Brand.class);
        Page<Brand> page = mock(Page.class);
        when(page.getContent()).thenReturn(Collections.singletonList(existing));

        GetRequest mockedGet = mock(GetRequest.class);
        doReturn(mockedGet).when(service).get(anyString(), any());

        Response response = mock(Response.class);
        when(response.getStatus()).thenReturn(HttpStatus.SC_OK);
        when(response.to(any(TypeReference.class))).thenReturn(page);
        doReturn(response).when(service).execute(any(BaseRequest.class));

        Brand result = service.insertBrandIfNotExists("Adidas", new ModuleOriginHeader("TEST"));

        assertSame(existing, result);
        verify(service, never()).post(anyString(), any(), any());
    }

    @Test
    public void should_insert_brand_if_not_exists_inserts_when_not_found() {
        Page<Brand> emptyPage = mock(Page.class);
        when(emptyPage.getContent()).thenReturn(Collections.emptyList());

        GetRequest mockedGet = mock(GetRequest.class);
        doReturn(mockedGet).when(service).get(anyString(), any());

        Brand inserted = mock(Brand.class);
        RequestBodyEntity mockedPost = mock(RequestBodyEntity.class);
        doReturn(mockedPost).when(service).post(anyString(), any(), any());

        Response searchResponse = mock(Response.class);
        when(searchResponse.getStatus()).thenReturn(HttpStatus.SC_OK);
        when(searchResponse.to(any(TypeReference.class))).thenReturn(emptyPage);

        Response insertResponse = mock(Response.class);
        when(insertResponse.to(Brand.class)).thenReturn(inserted);

        doReturn(searchResponse).doReturn(insertResponse).when(service).execute(any(BaseRequest.class));

        Brand result = service.insertBrandIfNotExists("Adidas", new ModuleOriginHeader("TEST"));

        assertSame(inserted, result);
    }

    @Test(expected = IllegalStateException.class)
    public void should_throw_illegal_state_when_multiple_brands_found() {
        Brand b1 = mock(Brand.class);
        Brand b2 = mock(Brand.class);
        Page<Brand> page = mock(Page.class);
        when(page.getContent()).thenReturn(Arrays.asList(b1, b2));

        GetRequest mockedGet = mock(GetRequest.class);
        doReturn(mockedGet).when(service).get(anyString(), any());

        Response response = mock(Response.class);
        when(response.getStatus()).thenReturn(HttpStatus.SC_OK);
        when(response.to(any(TypeReference.class))).thenReturn(page);
        doReturn(response).when(service).execute(any(BaseRequest.class));

        service.insertBrandIfNotExists("Adidas", new ModuleOriginHeader("TEST"));
    }

    static class BrandServiceFake extends BrandService {
        BrandServiceFake(String apiEndPoint, String origin) {
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
