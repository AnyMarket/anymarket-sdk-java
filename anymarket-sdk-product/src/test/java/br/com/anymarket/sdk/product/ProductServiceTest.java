package br.com.anymarket.sdk.product;

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

import java.io.IOException;
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
    public void should_update_product_and_images_success() throws Exception {
        Product inputProduct = new Product();
        inputProduct.setId(1L);

        Product returnedProduct = new Product();
        returnedProduct.setId(1L);

        Image imgToDelete = new Image();
        imgToDelete.setId(10L);
        returnedProduct.setImagesForDelete(Collections.singletonList(imgToDelete));

        Image newImage = new Image();
        returnedProduct.setImages(Collections.singletonList(newImage));

        RequestBodyEntity mockedPut = mock(RequestBodyEntity.class);
        doReturn(mockedPut).when(service).put(anyString(), eq(inputProduct), any());

        Response mockedResponse = mock(Response.class);
        when(mockedResponse.getStatus()).thenReturn(HttpStatus.SC_OK);
        when(mockedResponse.to(Product.class)).thenReturn(returnedProduct);
        doReturn(mockedResponse).when(service).execute(mockedPut);

        HttpRequestWithBody mockedDelete = mock(HttpRequestWithBody.class);
        doReturn(mockedDelete).when(service).delete(contains("/images/10"), any());

        Response mockedDeleteResponse = mock(Response.class);
        when(mockedDeleteResponse.getStatus()).thenReturn(HttpStatus.SC_OK);
        doReturn(mockedDeleteResponse).when(service).execute(mockedDelete);

        doAnswer(invocation -> {
            List<Image> updatedImages = invocation.getArgument(1);
            Image uploaded = new Image();
            uploaded.setId(99L);
            updatedImages.add(uploaded);
            return null;
        }).when(service).sendProductImage(any(), anyList(), any(), any());

        Product result = service.updateProductAndImages(inputProduct, new ModuleOriginHeader("ECOMMERCE"));

        assertNotNull(result);
        assertEquals(Long.valueOf(1), result.getId());
        assertNull(result.getImagesForDelete());
        assertEquals(1, result.getImages().size());
        assertEquals(Long.valueOf(99), result.getImages().get(0).getId());

        verify(service).put(anyString(), eq(inputProduct), any());
        verify(service).execute(mockedPut);
        verify(service).execute(mockedDelete);
        verify(service).sendProductImage(eq(returnedProduct), anyList(), eq(newImage), any());
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

        Image imgToDelete = new Image();
        imgToDelete.setId(99L);
        returnedProduct.setImagesForDelete(Collections.singletonList(imgToDelete));

        RequestBodyEntity mockedPutProduct = mock(RequestBodyEntity.class);
        doReturn(mockedPutProduct).when(service).put(contains("/products/1"), eq(inputProduct), any());
        Response mockedProductResponse = mock(Response.class);
        when(mockedProductResponse.getStatus()).thenReturn(HttpStatus.SC_OK);
        when(mockedProductResponse.to(Product.class)).thenReturn(returnedProduct);
        doReturn(mockedProductResponse).when(service).execute(mockedPutProduct);

        doAnswer(invocation -> {
            List<Image> imageList = invocation.getArgument(1);
            Image img = new Image();
            img.setId(123L);
            imageList.add(img);
            return null;
        }).when(service).sendProductImage(eq(returnedProduct), anyList(), eq(newImage), any());

        RequestBodyEntity mockedPutImage = mock(RequestBodyEntity.class);
        doReturn(mockedPutImage).when(service).put(contains("/images/"), eq(existingImage), any());
        Response mockedPutImageResponse = mock(Response.class);
        when(mockedPutImageResponse.getStatus()).thenReturn(HttpStatus.SC_OK);
        when(mockedPutImageResponse.getMessage()).thenReturn("{\"id\":20}");
        doReturn(mockedPutImageResponse).when(service).execute(mockedPutImage);

        doNothing().when(service).imageForDelete(eq(returnedProduct), any());

        Product result = service.updateProductAndCreateAndUpdateImages(inputProduct, new ModuleOriginHeader("ECOMMERCE"));

        assertNotNull(result);
        assertEquals(Long.valueOf(1), result.getId());

        verify(service).put(contains("/products/1"), eq(inputProduct), any());
        verify(service).execute(mockedPutProduct);
        verify(service).sendProductImage(eq(returnedProduct), anyList(), eq(newImage), any());
        verify(service).put(contains("/images/"), eq(existingImage), any());
        verify(service).execute(mockedPutImage);
        verify(service).imageForDelete(eq(returnedProduct), any());
    }

    @Test
    public void should_send_image_success() {
        Product product = new Product();
        product.setId(123L);

        Image image = new Image();
        Image imageResponse = new Image();
        imageResponse.setId(999L);

        List<Image> imageList = new ArrayList<>();

        RequestBodyEntity mockedPost = mock(RequestBodyEntity.class);
        doReturn(mockedPost).when(service).post(contains("/images/"), eq(image), any());

        Response mockedResponse = mock(Response.class);
        when(mockedResponse.getStatus()).thenReturn(HttpStatus.SC_OK);
        when(mockedResponse.getMessage()).thenReturn("{\"id\":999}");

        doReturn(mockedResponse).when(service).execute(mockedPost);

        service.sendProductImage(product, imageList, image, new ModuleOriginHeader[]{new ModuleOriginHeader("ECOMMERCE")});

        assertEquals(1, imageList.size());
        assertEquals(Long.valueOf(999), imageList.get(0).getId());

        verify(service).post(contains("/images/"), eq(image), any());
        verify(service).execute(mockedPost);
    }

    @Test
    public void should_not_add_image_status_not_200() {
        Product product = new Product();
        product.setId(123L);

        Image image = new Image();
        List<Image> imageList = new ArrayList<>();

        RequestBodyEntity mockedPost = mock(RequestBodyEntity.class);
        doReturn(mockedPost).when(service).post(anyString(), eq(image), any());

        Response mockedResponse = mock(Response.class);
        when(mockedResponse.getStatus()).thenReturn(HttpStatus.SC_BAD_REQUEST);

        doReturn(mockedResponse).when(service).execute(mockedPost);

        service.sendProductImage(product, imageList, image, new ModuleOriginHeader[]{new ModuleOriginHeader("ECOMMERCE")});

        assertTrue(imageList.isEmpty());
        verify(service).post(anyString(), eq(image), any());
        verify(service).execute(mockedPost);
    }

    @Test
    public void should_not_update_product_status_not_200() throws Exception {
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
        verify(service, never()).sendProductImage(any(), anyList(), any(), any());
    }

    @Test
    public void should_not_update_product_nor_image_if_put_status_not_200() {
        Product inputProduct = new Product();
        inputProduct.setId(1L);

        RequestBodyEntity mockedPut = mock(RequestBodyEntity.class);
        doReturn(mockedPut).when(service).put(anyString(), eq(inputProduct), any());

        Response mockedPutResponse = mock(Response.class);
        when(mockedPutResponse.getStatus()).thenReturn(HttpStatus.SC_BAD_REQUEST);
        when(mockedPutResponse.to(Product.class)).thenReturn(new Product());

        doReturn(mockedPutResponse).when(service).execute(mockedPut);

        Product result = service.updateProductAndCreateAndUpdateImages(inputProduct, new ModuleOriginHeader("ECOMMERCE"));

        assertNotNull(result);
        verify(service).put(anyString(), eq(inputProduct), any());
        verify(service).execute(mockedPut);
        verify(service, never()).sendProductImage(any(), anyList(), any(), any());
    }

    @Test
    public void should_not_add_image_if_put_returns_error() {
        Product inputProduct = new Product();
        inputProduct.setId(1L);

        Image imageWithId = new Image();
        imageWithId.setId(10L);
        Product returnedProduct = new Product();
        returnedProduct.setId(1L);
        returnedProduct.setImages(Collections.singletonList(imageWithId));

        RequestBodyEntity mockedProductPut = mock(RequestBodyEntity.class);
        doReturn(mockedProductPut).when(service).put(anyString(), eq(inputProduct), any());

        Response mockedResponse = mock(Response.class);
        when(mockedResponse.getStatus()).thenReturn(HttpStatus.SC_OK);
        when(mockedResponse.to(Product.class)).thenReturn(returnedProduct);

        doReturn(mockedResponse).when(service).execute(mockedProductPut);

        RequestBodyEntity mockedImagePut = mock(RequestBodyEntity.class);
        doReturn(mockedImagePut).when(service).put(contains("/images/"), eq(imageWithId), any());

        Response imagePutResponse = mock(Response.class);
        when(imagePutResponse.getStatus()).thenReturn(HttpStatus.SC_INTERNAL_SERVER_ERROR);

        doReturn(imagePutResponse).when(service).execute(mockedImagePut);

        Product result = service.updateProductAndCreateAndUpdateImages(inputProduct, new ModuleOriginHeader("ECOMMERCE"));

        assertNotNull(result);
        verify(service).execute(mockedImagePut);
        verify(service, never()).sendProductImage(any(), anyList(), any(), any());
    }

    @Test(expected = RuntimeException.class)
    public void should_throw_exception_if_invalid_image_json() {
        Product inputProduct = new Product();
        inputProduct.setId(1L);

        Image imageWithId = new Image();
        imageWithId.setId(10L);
        Product returnedProduct = new Product();
        returnedProduct.setId(1L);
        returnedProduct.setImages(Collections.singletonList(imageWithId));

        RequestBodyEntity mockedProductPut = mock(RequestBodyEntity.class);
        doReturn(mockedProductPut).when(service).put(anyString(), eq(inputProduct), any());

        Response mockedResponse = mock(Response.class);
        when(mockedResponse.getStatus()).thenReturn(HttpStatus.SC_OK);
        when(mockedResponse.to(Product.class)).thenReturn(returnedProduct);

        doReturn(mockedResponse).when(service).execute(mockedProductPut);

        RequestBodyEntity mockedImagePut = mock(RequestBodyEntity.class);
        doReturn(mockedImagePut).when(service).put(contains("/images/"), eq(imageWithId), any());

        Response imagePutResponse = mock(Response.class);
        when(imagePutResponse.getStatus()).thenReturn(HttpStatus.SC_OK);
        when(imagePutResponse.getMessage()).thenReturn("invalid json");

        doReturn(imagePutResponse).when(service).execute(mockedImagePut);

        service.updateProductAndCreateAndUpdateImages(inputProduct, new ModuleOriginHeader("ECOMMERCE"));
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

        @Override
        protected void sendProductImage(Product resultProduct, List<Image> updatedImages, Image image, IntegrationHeader... headers) {
            super.sendProductImage(resultProduct, updatedImages, image, headers);
        }
    }
}