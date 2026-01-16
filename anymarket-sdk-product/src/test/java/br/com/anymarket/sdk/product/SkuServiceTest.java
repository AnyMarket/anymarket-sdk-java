package br.com.anymarket.sdk.product;

import br.com.anymarket.sdk.exception.HttpClientException;
import br.com.anymarket.sdk.exception.NotFoundException;
import br.com.anymarket.sdk.http.Response;
import br.com.anymarket.sdk.http.headers.IntegrationHeader;
import br.com.anymarket.sdk.http.headers.ModuleOriginHeader;
import br.com.anymarket.sdk.paging.Page;
import br.com.anymarket.sdk.product.dto.Sku;
import br.com.anymarket.sdk.product.dto.SkuResource;
import com.fasterxml.jackson.core.type.TypeReference;
import com.mashape.unirest.request.GetRequest;
import com.mashape.unirest.request.body.RequestBodyEntity;
import org.apache.http.HttpStatus;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

public class SkuServiceTest {

    @Spy
    @InjectMocks
    private SkuServiceFake service;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void should_insert_sku_success() {
        Sku sku = new Sku();
        Long productId = 10L;

        RequestBodyEntity mockedPost = mock(RequestBodyEntity.class);
        doReturn(mockedPost).when(service).post(contains("/products/10/skus/"), eq(sku), any());

        Sku returned = new Sku();
        returned.setId(1L);

        Response response = mock(Response.class);
        when(response.to(Sku.class)).thenReturn(returned);
        doReturn(response).when(service).execute(any(com.mashape.unirest.request.BaseRequest.class));

        Sku result = service.insertSku(sku, productId, new ModuleOriginHeader("ECOMMERCE"));

        assertNotNull(result);
        assertEquals(Long.valueOf(1L), result.getId());
    }

    @Test
    public void should_update_sku_success() {
        Sku sku = new Sku();
        sku.setId(7L);
        Long productId = 10L;

        RequestBodyEntity mockedPut = mock(RequestBodyEntity.class);
        doReturn(mockedPut).when(service)
                .put(contains("/products/10/skus//7"), eq(sku), any());

        Sku returned = new Sku();
        returned.setId(7L);

        Response response = mock(Response.class);
        when(response.to(Sku.class)).thenReturn(returned);
        doReturn(response).when(service).execute(any(com.mashape.unirest.request.BaseRequest.class));

        Sku result = service.updateSku(sku, productId, new ModuleOriginHeader("ECOMMERCE"));

        assertNotNull(result);
        assertEquals(Long.valueOf(7L), result.getId());
    }

    @Test
    public void should_insert_or_update_sku_insert_when_no_id() {
        Sku sku = new Sku();
        Long productId = 10L;

        RequestBodyEntity mockedPost = mock(RequestBodyEntity.class);
        doReturn(mockedPost).when(service).post(anyString(), eq(sku), any());

        Sku returned = new Sku();
        returned.setId(1L);

        Response response = mock(Response.class);
        when(response.to(Sku.class)).thenReturn(returned);
        doReturn(response).when(service).execute(any(com.mashape.unirest.request.BaseRequest.class));

        Sku result = service.insertOrUpdateSku(sku, productId, new ModuleOriginHeader("ECOMMERCE"));

        assertNotNull(result);
        assertEquals(Long.valueOf(1L), result.getId());
    }

    @Test
    public void should_insert_or_update_sku_update_when_has_id() {
        Sku sku = new Sku();
        sku.setId(7L);
        Long productId = 10L;

        RequestBodyEntity mockedPut = mock(RequestBodyEntity.class);
        doReturn(mockedPut).when(service).put(anyString(), eq(sku), any());

        Sku returned = new Sku();
        returned.setId(7L);

        Response response = mock(Response.class);
        when(response.to(Sku.class)).thenReturn(returned);
        doReturn(response).when(service).execute(any(com.mashape.unirest.request.BaseRequest.class));

        Sku result = service.insertOrUpdateSku(sku, productId, new ModuleOriginHeader("ECOMMERCE"));

        assertNotNull(result);
        assertEquals(Long.valueOf(7L), result.getId());
    }

    @Test
    public void should_get_all_skus_success() {
        Long productId = 10L;

        GetRequest mockedGet = mock(GetRequest.class);
        doReturn(mockedGet).when(service)
                .get(contains("/products/10/skus//"), any());

        List<Sku> returned = Arrays.asList(new Sku(), new Sku());

        Response response = mock(Response.class);
        when(response.getStatus()).thenReturn(HttpStatus.SC_OK);
        when(response.to(any(TypeReference.class))).thenReturn(returned);
        doReturn(response).when(service).execute(any(com.mashape.unirest.request.BaseRequest.class));

        List<Sku> result = service.getAllSkus(productId, new ModuleOriginHeader("ECOMMERCE"));

        assertEquals(2, result.size());
    }

    @Test(expected = NotFoundException.class)
    public void should_throw_not_found_when_get_all_skus_status_not_200() {
        Long productId = 10L;

        GetRequest mockedGet = mock(GetRequest.class);
        doReturn(mockedGet).when(service).get(anyString(), any());

        Response response = mock(Response.class);
        when(response.getStatus()).thenReturn(HttpStatus.SC_NOT_FOUND);
        doReturn(response).when(service).execute(any(com.mashape.unirest.request.BaseRequest.class));

        service.getAllSkus(productId, new ModuleOriginHeader("ECOMMERCE"));
    }

    @Test
    public void should_get_sku_by_product_success() {
        Long productId = 10L;
        Long skuId = 7L;

        GetRequest mockedGet = mock(GetRequest.class);
        doReturn(mockedGet).when(service)
                .get(contains("/products/10/skus//7"), any());

        Sku returned = new Sku();
        returned.setId(7L);

        Response response = mock(Response.class);
        when(response.getStatus()).thenReturn(HttpStatus.SC_OK);
        when(response.to(Sku.class)).thenReturn(returned);
        doReturn(response).when(service).execute(any(com.mashape.unirest.request.BaseRequest.class));

        Sku result = service.getSku(skuId, productId, new ModuleOriginHeader("ECOMMERCE"));

        assertEquals(Long.valueOf(7L), result.getId());
    }

    @Test
    public void should_get_sku_success() {
        Long skuId = 7L;

        GetRequest mockedGet = mock(GetRequest.class);
        doReturn(mockedGet).when(service).get(contains("/skus/7"), any());

        Sku returned = new Sku();
        returned.setId(7L);

        Response response = mock(Response.class);
        when(response.getStatus()).thenReturn(HttpStatus.SC_OK);
        when(response.to(Sku.class)).thenReturn(returned);
        doReturn(response).when(service).execute(any(com.mashape.unirest.request.BaseRequest.class));

        Sku result = service.getSku(skuId, new ModuleOriginHeader("ECOMMERCE"));

        assertEquals(Long.valueOf(7L), result.getId());
    }

    @Test
    public void should_get_sku_with_product_success() {
        Long skuId = 7L;

        GetRequest mockedGet = mock(GetRequest.class);
        doReturn(mockedGet).when(service)
                .get(contains("/skus/7?showProduct=true"), any());

        SkuResource returned = new SkuResource();

        Response response = mock(Response.class);
        when(response.getStatus()).thenReturn(HttpStatus.SC_OK);
        when(response.to(SkuResource.class)).thenReturn(returned);
        doReturn(response).when(service).execute(any(com.mashape.unirest.request.BaseRequest.class));

        SkuResource result = service.getSkuWithProduct(skuId, new ModuleOriginHeader("ECOMMERCE"));

        assertNotNull(result);
    }

    @Test
    public void should_patch_sku_success() {
        Long productId = 10L;

        Sku sku = new Sku();
        sku.setId(7L);
        sku.setTitle("Novo titulo");

        RequestBodyEntity mockedPatch = mock(RequestBodyEntity.class);
        doReturn(mockedPatch).when(service)
                .patch(contains("/products/10/skus/patch/7"), any(), any());

        Sku returned = new Sku();
        returned.setId(7L);

        Response response = mock(Response.class);
        when(response.getStatus()).thenReturn(HttpStatus.SC_OK);
        when(response.to(Sku.class)).thenReturn(returned);
        doReturn(response).when(service).execute(any(com.mashape.unirest.request.BaseRequest.class));

        Sku result = service.patchSku(productId, sku, new ModuleOriginHeader("ECOMMERCE"));

        assertEquals(Long.valueOf(7L), result.getId());
    }

    @Test(expected = HttpClientException.class)
    public void should_throw_http_client_exception_when_patch_sku_status_not_200() {
        Long productId = 10L;

        Sku sku = new Sku();
        sku.setId(7L);
        sku.setTitle("Novo titulo");

        RequestBodyEntity mockedPatch = mock(RequestBodyEntity.class);
        doReturn(mockedPatch).when(service).patch(anyString(), any(), any());

        Response response = mock(Response.class);
        when(response.getStatus()).thenReturn(HttpStatus.SC_INTERNAL_SERVER_ERROR);
        when(response.getMessage()).thenReturn("error");
        doReturn(response).when(service).execute(any(com.mashape.unirest.request.BaseRequest.class));

        service.patchSku(productId, sku, new ModuleOriginHeader("ECOMMERCE"));
    }

    private static class SkuServiceFake extends SkuService {
        public SkuServiceFake(String apiEndPoint) {
            super(apiEndPoint);
        }

        @Override
        protected RequestBodyEntity put(String url, Object body, IntegrationHeader... headers) {
            return super.put(url, body, headers);
        }

        @Override
        protected RequestBodyEntity post(String url, Object body, IntegrationHeader... headers) {
            return super.post(url, body, headers);
        }

        @Override
        public RequestBodyEntity patch(String url, Object body, IntegrationHeader... headers) {
            return super.patch(url, body, headers);
        }

        @Override
        protected GetRequest get(String url, IntegrationHeader... headers) {
            return super.get(url, headers);
        }

        @Override
        protected Response execute(com.mashape.unirest.request.BaseRequest request) {
            return super.execute(request);
        }
    }
}
