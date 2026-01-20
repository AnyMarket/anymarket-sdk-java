package br.com.anymarket.sdk.product;

import br.com.anymarket.sdk.MarketPlace;
import br.com.anymarket.sdk.exception.HttpClientException;
import br.com.anymarket.sdk.exception.NotFoundException;
import br.com.anymarket.sdk.http.Response;
import br.com.anymarket.sdk.http.headers.IntegrationHeader;
import br.com.anymarket.sdk.http.headers.ModuleOriginHeader;
import br.com.anymarket.sdk.paging.Page;
import br.com.anymarket.sdk.product.dto.Product;
import br.com.anymarket.sdk.product.dto.ProductComplete;
import com.fasterxml.jackson.core.type.TypeReference;
import com.mashape.unirest.request.GetRequest;
import com.mashape.unirest.request.HttpRequestWithBody;
import com.mashape.unirest.request.body.RequestBodyEntity;
import org.apache.http.HttpStatus;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class ProductServiceTest {

    @Spy
    @InjectMocks
    private ProductServiceFake service;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        service = spy(new ProductServiceFake("http://hom-api.anymarket.com.br/v2"));
    }

    @Test
    public void should_insert_product_success() {
        Product input = new Product();
        Product returned = new Product();
        returned.setId(1L);

        RequestBodyEntity mockedPost = mock(RequestBodyEntity.class);
        doReturn(mockedPost).when(service).post(anyString(), eq(input), any());

        Response response = mock(Response.class);
        when(response.to(Product.class)).thenReturn(returned);
        doReturn(response).when(service).execute(mockedPost);

        Product result = service.insertProduct(input, new ModuleOriginHeader("ECOMMERCE"));

        assertNotNull(result);
        assertEquals(Long.valueOf(1L), result.getId());
    }

    @Test
    public void should_update_product_success() {
        Product input = new Product();
        input.setId(1L);

        Product returned = new Product();
        returned.setId(1L);

        RequestBodyEntity mockedPut = mock(RequestBodyEntity.class);
        doReturn(mockedPut).when(service).put(contains("/products/1"), eq(input), any());

        Response response = mock(Response.class);
        when(response.getStatus()).thenReturn(HttpStatus.SC_OK);
        when(response.to(Product.class)).thenReturn(returned);
        doReturn(response).when(service).execute(mockedPut);

        Product result = service.updateProduct(input, new ModuleOriginHeader("ECOMMERCE"));

        assertNotNull(result);
        assertEquals(Long.valueOf(1L), result.getId());
    }

    @Test(expected = HttpClientException.class)
    public void should_throw_when_update_product_status_not_200() {
        Product input = new Product();
        input.setId(1L);

        RequestBodyEntity mockedPut = mock(RequestBodyEntity.class);
        doReturn(mockedPut).when(service).put(anyString(), eq(input), any());

        Response response = mock(Response.class);
        when(response.getStatus()).thenReturn(HttpStatus.SC_INTERNAL_SERVER_ERROR);
        when(response.getMessage()).thenReturn("error");
        doReturn(response).when(service).execute(mockedPut);

        service.updateProduct(input, new ModuleOriginHeader("ECOMMERCE"));
    }

    @Test
    public void should_get_all_products_success() {
        String url = "http://x";

        GetRequest mockedGet = mock(GetRequest.class);
        doReturn(mockedGet).when(service).get(eq(url), any());

        Product product = new Product();
        product.setId(1L);

        Page<Product> page = mock(Page.class);
        when(page.getContent()).thenReturn(Collections.singletonList(product));

        Response response = mock(Response.class);
        when(response.getStatus()).thenReturn(HttpStatus.SC_OK);
        when(response.to(any(TypeReference.class))).thenReturn(page);
        doReturn(response).when(service).execute(mockedGet);

        List<Product> result = service.getAllProducts(url, new ModuleOriginHeader("ECOMMERCE"));

        assertEquals(1, result.size());
        assertEquals(Long.valueOf(1L), result.get(0).getId());
    }

    @Test(expected = NotFoundException.class)
    public void should_throw_when_get_all_products_not_found() {
        GetRequest mockedGet = mock(GetRequest.class);
        doReturn(mockedGet).when(service).get(anyString(), any());

        Response response = mock(Response.class);
        when(response.getStatus()).thenReturn(HttpStatus.SC_NOT_FOUND);
        doReturn(response).when(service).execute(mockedGet);

        service.getAllProducts("http://x", new ModuleOriginHeader("ECOMMERCE"));
    }

    @Test
    public void should_get_all_complete_products_success() {
        GetRequest mockedGet = mock(GetRequest.class);
        doReturn(mockedGet).when(service).get(anyString(), any());

        ProductComplete pc = new ProductComplete();

        Page<ProductComplete> page = mock(Page.class);
        when(page.getContent()).thenReturn(Collections.singletonList(pc));

        Response response = mock(Response.class);
        when(response.getStatus()).thenReturn(HttpStatus.SC_OK);
        when(response.to(any(TypeReference.class))).thenReturn(page);
        doReturn(response).when(service).execute(mockedGet);

        List<ProductComplete> result = service.getAllCompleteProducts("http://x", new ModuleOriginHeader("ECOMMERCE"));

        assertEquals(1, result.size());
    }

    @Test
    public void should_get_next_page_product_when_no_next() {
        Page<Product> page = mock(Page.class);
        when(page.getLinks()).thenReturn(Collections.emptyList());

        Page<Product> result = service.getNextPageProduct(page, new ModuleOriginHeader("ECOMMERCE"));

        assertNotNull(result);
        assertTrue(result.getContent() == null || result.getContent().isEmpty());
    }

    @Test
    public void should_get_active_attributes_success() {
        GetRequest mockedGet = mock(GetRequest.class);
        doReturn(mockedGet).when(service).get(contains("/attributes/"), any());

        Map<String, String> attrs = new HashMap<>();
        attrs.put("a", "b");

        Response response = mock(Response.class);
        when(response.getStatus()).thenReturn(HttpStatus.SC_OK);
        when(response.to(HashMap.class)).thenReturn((HashMap) attrs);
        doReturn(response).when(service).execute(mockedGet);

        Map<String, String> result = service.getActiveAttributesByMarketPlace(
                1L, MarketPlace.AMAZON, new ModuleOriginHeader("ECOMMERCE"));

        assertEquals(1, result.size());
    }

    @Test(expected = NotFoundException.class)
    public void should_throw_when_get_active_attributes_not_found() {
        GetRequest mockedGet = mock(GetRequest.class);
        doReturn(mockedGet).when(service).get(anyString(), any());

        Response response = mock(Response.class);
        when(response.getStatus()).thenReturn(HttpStatus.SC_NOT_FOUND);
        doReturn(response).when(service).execute(mockedGet);

        service.getActiveAttributesByMarketPlace(
                1L, MarketPlace.AMAZON, new ModuleOriginHeader("ECOMMERCE"));
    }

    private static class ProductServiceFake extends ProductService {

        public ProductServiceFake(String apiEndPoint) {
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
        protected HttpRequestWithBody delete(String url, IntegrationHeader... headers) {
            return super.delete(url, headers);
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
