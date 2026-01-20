package br.com.anymarket.sdk.product;

import br.com.anymarket.sdk.MarketPlace;
import br.com.anymarket.sdk.exception.HttpClientException;
import br.com.anymarket.sdk.exception.NotFoundException;
import br.com.anymarket.sdk.http.Response;
import br.com.anymarket.sdk.http.headers.ContentTypeHeader;
import br.com.anymarket.sdk.http.headers.IntegrationHeader;
import br.com.anymarket.sdk.http.headers.ModuleOriginHeader;
import br.com.anymarket.sdk.paging.Page;
import br.com.anymarket.sdk.product.dto.Image;
import br.com.anymarket.sdk.product.dto.Product;
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

import java.util.*;

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
    public void should_update_product_and_images_success() {
        Product inputProduct = new Product();
        inputProduct.setId(1L);

        Product returnedProduct = new Product();
        returnedProduct.setId(1L);

        Image imgToDelete = new Image();
        imgToDelete.setId(10L);
        inputProduct.setImagesForDelete(Collections.singletonList(imgToDelete));

        Image newImage = new Image();
        newImage.setId(99L);
        returnedProduct.setImages(Collections.singletonList(newImage));

        RequestBodyEntity mockedPut = mock(RequestBodyEntity.class);
        doReturn(mockedPut).when(service).put(anyString(), eq(inputProduct), any());

        Response mockedResponse = mock(Response.class);
        when(mockedResponse.getStatus()).thenReturn(HttpStatus.SC_OK);
        when(mockedResponse.to(Product.class)).thenReturn(returnedProduct);
        doReturn(mockedResponse).when(service).execute(mockedPut);

        HttpRequestWithBody mockedDelete = mock(HttpRequestWithBody.class);
        doReturn(mockedDelete).when(service).delete(contains("/images/multi"), any());

        RequestBodyEntity mockedDeleteBody = mock(RequestBodyEntity.class);
        when(mockedDelete.body("[10]")).thenReturn(mockedDeleteBody);

        Response deleteResponse = mock(Response.class);
        when(deleteResponse.getStatus()).thenReturn(HttpStatus.SC_NO_CONTENT);
        doReturn(deleteResponse).when(service).execute(mockedDeleteBody);

        Product result = service.updateProductAndImages(inputProduct, new ModuleOriginHeader("ECOMMERCE"));

        assertNotNull(result);
        assertEquals(Long.valueOf(1L), result.getId());
        assertEquals(1, result.getImages().size());
    }

    @Test
    public void should_patch_product_success() {
        Product input = new Product();
        input.setId(1L);
        input.setTitle("Produto teste");

        Product returned = new Product();
        returned.setId(1L);

        RequestBodyEntity mockedPatch = mock(RequestBodyEntity.class);

        doReturn(mockedPatch).when(service).patch(
                contains("/products/1"),
                any(Map.class),
                any(ModuleOriginHeader.class),
                any(ContentTypeHeader.class)
        );

        Response response = mock(Response.class);
        when(response.getStatus()).thenReturn(HttpStatus.SC_OK);
        when(response.to(Product.class)).thenReturn(returned);

        doReturn(response).when(service).execute(any());

        Product result = service.mergePatchProduct(input, new ModuleOriginHeader("ECOMMERCE"));

        assertNotNull(result);
        assertEquals(Long.valueOf(1L), result.getId());
    }

    @Test(expected = NullPointerException.class)
    public void should_throw_when_merge_patch_product_is_null() {
        service.mergePatchProduct(null, new ModuleOriginHeader("ECOMMERCE"));
    }

    @Test(expected = NullPointerException.class)
    public void should_throw_when_merge_patch_product_id_is_null() {
        Product input = new Product();
        service.mergePatchProduct(input, new ModuleOriginHeader("ECOMMERCE"));
    }

    @Test(expected = HttpClientException.class)
    public void should_throw_when_patch_returns_error_status() {
        Product input = new Product();
        input.setId(1L);
        input.setTitle("Produto teste");

        RequestBodyEntity mockedPatch = mock(RequestBodyEntity.class);
        doReturn(mockedPatch).when(service).patch(
                anyString(),
                any(Map.class),
                any(ModuleOriginHeader.class),
                any(ContentTypeHeader.class)
        );

        Response response = mock(Response.class);
        when(response.getStatus()).thenReturn(HttpStatus.SC_INTERNAL_SERVER_ERROR);
        when(response.getMessage()).thenReturn("error");
        doReturn(response).when(service).execute(any());

        service.mergePatchProduct(input, new ModuleOriginHeader("ECOMMERCE"));
    }

    @Test
    public void should_get_product_success() {
        GetRequest mockedGet = mock(GetRequest.class);
        doReturn(mockedGet).when(service).get(contains("/products/1"), any());

        Product returned = new Product();
        returned.setId(1L);

        Response response = mock(Response.class);
        when(response.getStatus()).thenReturn(HttpStatus.SC_OK);
        when(response.to(Product.class)).thenReturn(returned);

        doReturn(response).when(service).execute(mockedGet);

        Product result = service.getProduct(1L, new ModuleOriginHeader("ECOMMERCE"));

        assertNotNull(result);
        assertEquals(Long.valueOf(1L), result.getId());
    }

    @Test(expected = NotFoundException.class)
    public void should_throw_not_found_when_get_product_status_not_200() {
        GetRequest mockedGet = mock(GetRequest.class);
        doReturn(mockedGet).when(service).get(anyString(), any());

        Response response = mock(Response.class);
        when(response.getStatus()).thenReturn(HttpStatus.SC_NOT_FOUND);
        doReturn(response).when(service).execute(mockedGet);

        service.getProduct(1L, new ModuleOriginHeader("ECOMMERCE"));
    }

    @Test
    public void should_get_product_by_sku_returns_first() {
        Product p1 = new Product();
        p1.setId(1L);
        Product p2 = new Product();
        p2.setId(2L);

        doReturn(Arrays.asList(p1, p2)).when(service).getAllProducts(anyString(), any());

        Product result = service.getProductBySku("ABC", new ModuleOriginHeader("ECOMMERCE"));

        assertEquals(Long.valueOf(1L), result.getId());
    }

    @Test(expected = NotFoundException.class)
    public void should_throw_not_found_when_get_product_by_sku_empty() {
        doReturn(Collections.emptyList()).when(service).getAllProducts(anyString(), any());

        service.getProductBySku("ABC", new ModuleOriginHeader("ECOMMERCE"));
    }


    @Test
    public void should_find_by_oi_and_ids_in_client_success() {
        List<String> skus = Arrays.asList("A", "B");

        RequestBodyEntity mockedPost = mock(RequestBodyEntity.class);
        doReturn(mockedPost).when(service).post(contains("/byOiAndIdsInClient"), eq(skus), any());

        Response response = mock(Response.class);
        when(response.getStatus()).thenReturn(HttpStatus.SC_OK);
        when(response.to(any(TypeReference.class))).thenReturn(skus);

        doReturn(response).when(service).execute(mockedPost);

        List<String> result = service.findByOiAndIdsInClient(skus, new ModuleOriginHeader("ECOMMERCE"));

        assertEquals(2, result.size());
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
