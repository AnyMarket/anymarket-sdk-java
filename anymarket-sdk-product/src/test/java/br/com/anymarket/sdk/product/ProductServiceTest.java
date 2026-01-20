package br.com.anymarket.sdk.product;

import br.com.anymarket.sdk.exception.HttpClientException;
import br.com.anymarket.sdk.http.Response;
import br.com.anymarket.sdk.http.headers.IntegrationHeader;
import br.com.anymarket.sdk.http.headers.ModuleOriginHeader;
import br.com.anymarket.sdk.product.dto.Image;
import br.com.anymarket.sdk.product.dto.Product;
import com.mashape.unirest.request.HttpRequestWithBody;
import com.mashape.unirest.request.body.RequestBodyEntity;
import org.apache.http.HttpStatus;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class ProductServiceTest {

    @Spy
    @InjectMocks
    private ProductServiceFake service;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
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
        returnedProduct.setImagesForDelete(null);

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
        RequestBodyEntity mockedDeleteBodyEntity = mock(RequestBodyEntity.class);
        when(mockedDelete.body("[10]")).thenReturn(mockedDeleteBodyEntity);

        Response mockedDeleteResponse = mock(Response.class);
        when(mockedDeleteResponse.getStatus()).thenReturn(HttpStatus.SC_NO_CONTENT);
        doReturn(mockedDeleteResponse).when(service).execute(mockedDeleteBodyEntity);

        Product result = service.updateProductAndImages(inputProduct, new ModuleOriginHeader("ECOMMERCE"));

        assertNotNull(result);
        assertEquals(Long.valueOf(1), result.getId());
        assertNull(result.getImagesForDelete());
        assertEquals(1, result.getImages().size());
        assertEquals(Long.valueOf(99), result.getImages().get(0).getId());

        verify(service).put(anyString(), eq(inputProduct), any());
        verify(service).execute(mockedPut);
        verify(service).execute(mockedDeleteBodyEntity);
    }

    @Test
    public void should_update_product_and_create_and_update_image_success() {
        Product inputProduct = new Product();
        inputProduct.setId(1L);

        Product returnedProduct = new Product();
        returnedProduct.setId(1L);

        Image newImage = new Image();
        Image existingImage = new Image();
        existingImage.setId(20L);
        List<Image> existingImages = new ArrayList<>();
        existingImages.add(newImage);
        existingImages.add(existingImage);
        returnedProduct.setImages(existingImages);
        inputProduct.setImages(existingImages);

        Image imgToDelete = new Image();
        imgToDelete.setId(99L);
        returnedProduct.setImagesForDelete(Collections.singletonList(imgToDelete));

        RequestBodyEntity mockedPutProduct = mock(RequestBodyEntity.class);
        doReturn(mockedPutProduct).when(service).put(contains("/products/1"), eq(inputProduct), any());
        Response mockedProductResponse = mock(Response.class);
        when(mockedProductResponse.getStatus()).thenReturn(HttpStatus.SC_OK);
        when(mockedProductResponse.to(Product.class)).thenReturn(returnedProduct);
        doReturn(mockedProductResponse).when(service).execute(mockedPutProduct);

        RequestBodyEntity mockedPostImage = mock(RequestBodyEntity.class);
        doReturn(mockedPostImage).when(service).post(contains("/images/multi"), eq(Collections.singletonList(newImage)), any());
        Response mockedPostImageResponse = mock(Response.class);
        when(mockedPostImageResponse.getStatus()).thenReturn(HttpStatus.SC_OK);
        when(mockedPostImageResponse.getMessage()).thenReturn("{\"id\":21}");
        doReturn(mockedPostImageResponse).when(service).execute(mockedPostImage);

        RequestBodyEntity mockedPutImage = mock(RequestBodyEntity.class);
        doReturn(mockedPutImage).when(service).put(contains("/images/multi"), eq(Collections.singletonList(existingImage)), any());
        Response mockedPutImageResponse = mock(Response.class);
        when(mockedPutImageResponse.getStatus()).thenReturn(HttpStatus.SC_OK);
        when(mockedPutImageResponse.getMessage()).thenReturn("{\"id\":20}");
        doReturn(mockedPutImageResponse).when(service).execute(mockedPutImage);

        Product result = service.updateProductAndCreateAndUpdateImages(inputProduct, new ModuleOriginHeader("ECOMMERCE"));

        assertNotNull(result);
        assertEquals(Long.valueOf(1), result.getId());

        verify(service).put(contains("/products/1"), eq(inputProduct), any());
        verify(service).execute(mockedPutProduct);
        verify(service).post(contains("/images/multi"), eq(Collections.singletonList(newImage)), any());
        verify(service).execute(mockedPostImage);
        verify(service).put(contains("/images/multi"), eq(Collections.singletonList(existingImage)), any());
        verify(service).execute(mockedPutImage);
    }

    @Test(expected = HttpClientException.class)
    public void should_not_update_product_status_not_200() {
        Product inputProduct = new Product();
        inputProduct.setId(1L);

        RequestBodyEntity mockedPut = mock(RequestBodyEntity.class);
        doReturn(mockedPut).when(service).put(anyString(), eq(inputProduct), any());

        Response mockedResponse = mock(Response.class);
        when(mockedResponse.getStatus()).thenReturn(HttpStatus.SC_INTERNAL_SERVER_ERROR);
        when(mockedResponse.to(Product.class)).thenReturn(new Product());

        doReturn(mockedResponse).when(service).execute(mockedPut);

        Product result = service.updateProductAndImages(inputProduct, new ModuleOriginHeader("ECOMMERCE"));

        assertNotNull(result);
        verify(service).put(anyString(), eq(inputProduct), any());
        verify(service).execute(mockedPut);
        verify(service, never()).delete(anyString(), any());
    }

    @Test(expected = HttpClientException.class)
    public void should_not_add_image_if_put_returns_error() {
        Product inputProduct = new Product();
        inputProduct.setId(1L);

        Image imageWithId = new Image();
        imageWithId.setId(10L);
        Product returnedProduct = new Product();
        returnedProduct.setId(1L);
        List<Image> imagesWithId = Collections.singletonList(imageWithId);
        returnedProduct.setImages(imagesWithId);
        inputProduct.setImages(imagesWithId);

        RequestBodyEntity mockedProductPut = mock(RequestBodyEntity.class);
        doReturn(mockedProductPut).when(service).put(anyString(), eq(inputProduct), any());

        Response mockedResponse = mock(Response.class);
        when(mockedResponse.getStatus()).thenReturn(HttpStatus.SC_OK);
        when(mockedResponse.to(Product.class)).thenReturn(returnedProduct);

        doReturn(mockedResponse).when(service).execute(mockedProductPut);

        RequestBodyEntity mockedImagePut = mock(RequestBodyEntity.class);
        doReturn(mockedImagePut).when(service).put(contains("/images/multi"), eq(imagesWithId), any());

        Response imagePutResponse = mock(Response.class);
        when(imagePutResponse.getStatus()).thenReturn(HttpStatus.SC_INTERNAL_SERVER_ERROR);

        doReturn(imagePutResponse).when(service).execute(mockedImagePut);

        Product result = service.updateProductAndCreateAndUpdateImages(inputProduct, new ModuleOriginHeader("ECOMMERCE"));

        assertNotNull(result);
        verify(service).execute(mockedImagePut);
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
        protected Response execute(com.mashape.unirest.request.BaseRequest request) {
            return super.execute(request);
        }
    }

//    @Test
//    public void should_patch_product_success() {
//        Product input = new Product();
//        input.setId(1L);
//        input.setTitle("Produto teste");
//
//        Product returned = new Product();
//        returned.setId(1L);
//
//        RequestBodyEntity mockedPatch = mock(RequestBodyEntity.class);
//        doReturn(mockedPatch).when(service).patch(contains("/products/patch/1"), any(), any());
//
//        Response response = mock(Response.class);
//        when(response.getStatus()).thenReturn(HttpStatus.SC_OK);
//        when(response.to(Product.class)).thenReturn(returned);
//        doReturn(response).when(service).execute(any(com.mashape.unirest.request.BaseRequest.class));
//
//        Product result = service.patchProduct(input, new ModuleOriginHeader("ECOMMERCE"));
//
//        assertNotNull(result);
//        assertEquals(Long.valueOf(1L), result.getId());
//
//        verify(service).patch(contains("/products/patch/1"), any(), any());
//        verify(service).execute(any(com.mashape.unirest.request.BaseRequest.class));
//    }
//
//    @Test(expected = HttpClientException.class)
//    public void should_throw_when_patch_returns_error_status() {
//        Product input = new Product();
//        input.setId(1L);
//        input.setTitle("Produto teste");
//
//        RequestBodyEntity mockedPatch = mock(RequestBodyEntity.class);
//        doReturn(mockedPatch).when(service).patch(anyString(), any(), any());
//
//        Response response = mock(Response.class);
//        when(response.getStatus()).thenReturn(HttpStatus.SC_INTERNAL_SERVER_ERROR);
//        when(response.getMessage()).thenReturn("error");
//        doReturn(response).when(service).execute(any(com.mashape.unirest.request.BaseRequest.class));
//
//        service.patchProduct(input, new ModuleOriginHeader("ECOMMERCE"));
//    }


}
