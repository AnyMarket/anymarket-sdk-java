package br.com.anymarket.sdk.product;

import br.com.anymarket.sdk.MarketPlace;
import br.com.anymarket.sdk.exception.HttpClientException;
import br.com.anymarket.sdk.exception.NotFoundException;
import br.com.anymarket.sdk.http.Response;
import br.com.anymarket.sdk.http.headers.ContentTypeHeader;
import br.com.anymarket.sdk.http.headers.IntegrationHeader;
import br.com.anymarket.sdk.http.headers.ModuleOriginHeader;
import br.com.anymarket.sdk.paging.Page;
import br.com.anymarket.sdk.product.dto.Product;
import br.com.anymarket.sdk.product.dto.ProductComplete;
import com.fasterxml.jackson.core.type.TypeReference;
import com.mashape.unirest.request.BaseRequest;
import com.mashape.unirest.request.GetRequest;
import com.mashape.unirest.request.HttpRequestWithBody;
import com.mashape.unirest.request.body.RequestBodyEntity;
import org.apache.http.HttpStatus;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import java.lang.reflect.Method;
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
    public void should_insert_product_success() {
        Product input = new Product();
        Product returned = new Product();
        returned.setId(1L);

        RequestBodyEntity mockedPost = mock(RequestBodyEntity.class);
        doReturn(mockedPost).when(service).post(anyString(), eq(input), any(IntegrationHeader.class));

        Response response = mock(Response.class);
        when(response.to(Product.class)).thenReturn(returned);
        doReturn(response).when(service).execute(any(BaseRequest.class));

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
        doReturn(mockedPut).when(service).put(contains("/products/1"), eq(input), any(IntegrationHeader.class));

        Response response = mock(Response.class);
        when(response.getStatus()).thenReturn(HttpStatus.SC_OK);
        when(response.to(Product.class)).thenReturn(returned);
        doReturn(response).when(service).execute(any(BaseRequest.class));

        Product result = service.updateProduct(input, new ModuleOriginHeader("ECOMMERCE"));

        assertNotNull(result);
        assertEquals(Long.valueOf(1L), result.getId());
    }

    @Test(expected = HttpClientException.class)
    public void should_throw_when_update_product_status_not_200() {
        Product input = new Product();
        input.setId(1L);

        RequestBodyEntity mockedPut = mock(RequestBodyEntity.class);
        doReturn(mockedPut).when(service).put(anyString(), eq(input), any(IntegrationHeader.class));

        Response response = mock(Response.class);
        when(response.getStatus()).thenReturn(HttpStatus.SC_INTERNAL_SERVER_ERROR);
        when(response.getMessage()).thenReturn("error");
        doReturn(response).when(service).execute(any(BaseRequest.class));

        service.updateProduct(input, new ModuleOriginHeader("ECOMMERCE"));
    }

    @Test
    public void should_get_all_products_success() {
        String url = "http://x";

        GetRequest mockedGet = mock(GetRequest.class);
        doReturn(mockedGet).when(service).get(eq(url), any(IntegrationHeader.class));

        Product product = new Product();
        product.setId(1L);

        Page<Product> page = mock(Page.class);
        when(page.getContent()).thenReturn(Collections.singletonList(product));

        Response response = mock(Response.class);
        when(response.getStatus()).thenReturn(HttpStatus.SC_OK);
        when(response.to(any(TypeReference.class))).thenReturn(page);
        doReturn(response).when(service).execute(any(BaseRequest.class));

        List<Product> result = service.getAllProducts(url, new ModuleOriginHeader("ECOMMERCE"));

        assertEquals(1, result.size());
        assertEquals(Long.valueOf(1L), result.get(0).getId());
    }

    @Test(expected = NotFoundException.class)
    public void should_throw_when_get_all_products_not_found() {
        GetRequest mockedGet = mock(GetRequest.class);
        doReturn(mockedGet).when(service).get(anyString(), any(IntegrationHeader.class));

        Response response = mock(Response.class);
        when(response.getStatus()).thenReturn(HttpStatus.SC_NOT_FOUND);
        doReturn(response).when(service).execute(any(BaseRequest.class));

        service.getAllProducts("http://x", new ModuleOriginHeader("ECOMMERCE"));
    }

    @Test
    public void should_get_all_complete_products_success() {
        GetRequest mockedGet = mock(GetRequest.class);
        doReturn(mockedGet).when(service).get(anyString(), any(IntegrationHeader.class));

        ProductComplete pc = new ProductComplete();

        Page<ProductComplete> page = mock(Page.class);
        when(page.getContent()).thenReturn(Collections.singletonList(pc));

        Response response = mock(Response.class);
        when(response.getStatus()).thenReturn(HttpStatus.SC_OK);
        when(response.to(any(TypeReference.class))).thenReturn(page);
        doReturn(response).when(service).execute(any(BaseRequest.class));

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
        doReturn(mockedGet).when(service).get(contains("/attributes/"), any(IntegrationHeader.class));

        Map<String, String> attrs = new HashMap<>();
        attrs.put("a", "b");

        Response response = mock(Response.class);
        when(response.getStatus()).thenReturn(HttpStatus.SC_OK);
        when(response.to(HashMap.class)).thenReturn((HashMap) attrs);
        doReturn(response).when(service).execute(any(BaseRequest.class));

        Map<String, String> result = service.getActiveAttributesByMarketPlace(
                1L, MarketPlace.AMAZON, new ModuleOriginHeader("ECOMMERCE"));

        assertEquals(1, result.size());
    }

    @Test(expected = NotFoundException.class)
    public void should_throw_when_get_active_attributes_not_found() {
        GetRequest mockedGet = mock(GetRequest.class);
        doReturn(mockedGet).when(service).get(anyString(), any(IntegrationHeader.class));

        Response response = mock(Response.class);
        when(response.getStatus()).thenReturn(HttpStatus.SC_NOT_FOUND);
        doReturn(response).when(service).execute(any(BaseRequest.class));

        service.getActiveAttributesByMarketPlace(
                1L, MarketPlace.AMAZON, new ModuleOriginHeader("ECOMMERCE"));
    }

    @Test
    public void should_merge_patch_product_success() {
        Product input = new Product();
        input.setId(1L);
        input.setTitle("Produto teste");

        Product returned = new Product();
        returned.setId(1L);

        RequestBodyEntity mockedPatch = mock(RequestBodyEntity.class);
        doReturn(mockedPatch).when(service).patch(anyString(), any(), any(IntegrationHeader.class), any(IntegrationHeader.class));

        Response response = mock(Response.class);
        when(response.getStatus()).thenReturn(HttpStatus.SC_OK);
        when(response.to(Product.class)).thenReturn(returned);
        doReturn(response).when(service).execute(any(BaseRequest.class));

        Product result = service.mergePatchProduct(input, new ModuleOriginHeader("ECOMMERCE"));

        assertNotNull(result);
        assertEquals(Long.valueOf(1L), result.getId());

        ArgumentCaptor<String> urlCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<Object> bodyCaptor = ArgumentCaptor.forClass(Object.class);
        ArgumentCaptor<IntegrationHeader> h1 = ArgumentCaptor.forClass(IntegrationHeader.class);
        ArgumentCaptor<IntegrationHeader> h2 = ArgumentCaptor.forClass(IntegrationHeader.class);

        verify(service).patch(urlCaptor.capture(), bodyCaptor.capture(), h1.capture(), h2.capture());

        assertTrue(urlCaptor.getValue().contains("/products/1"));
        assertTrue(bodyCaptor.getValue() instanceof Map);
        assertTrue((h1.getValue() instanceof ModuleOriginHeader) || (h2.getValue() instanceof ModuleOriginHeader));
        assertTrue((h1.getValue() instanceof ContentTypeHeader) || (h2.getValue() instanceof ContentTypeHeader));
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
    public void should_throw_when_merge_patch_returns_error_status() {
        Product input = new Product();
        input.setId(1L);
        input.setTitle("Produto teste");

        RequestBodyEntity mockedPatch = mock(RequestBodyEntity.class);
        doReturn(mockedPatch).when(service).patch(anyString(), any(), any(IntegrationHeader.class), any(IntegrationHeader.class));

        Response response = mock(Response.class);
        when(response.getStatus()).thenReturn(HttpStatus.SC_INTERNAL_SERVER_ERROR);
        when(response.getMessage()).thenReturn("error");
        doReturn(response).when(service).execute(any(BaseRequest.class));

        service.mergePatchProduct(input, new ModuleOriginHeader("ECOMMERCE"));
    }

    @Test
    public void should_find_by_oi_and_ids_in_client_success() {
        List<String> skus = Arrays.asList("A", "B");

        RequestBodyEntity mockedPost = mock(RequestBodyEntity.class);
        doReturn(mockedPost).when(service).post(contains("/byOiAndIdsInClient"), eq(skus), any(IntegrationHeader.class));

        Response response = mock(Response.class);
        when(response.getStatus()).thenReturn(HttpStatus.SC_OK);
        when(response.to(any(TypeReference.class))).thenReturn(skus);
        doReturn(response).when(service).execute(any(BaseRequest.class));

        List<String> result = service.findByOiAndIdsInClient(skus, new ModuleOriginHeader("ECOMMERCE"));

        assertNotNull(result);
        assertEquals(2, result.size());
    }

    @Test
    public void should_find_by_oi_and_ids_in_client_when_status_not_ok() {
        List<String> skus = Arrays.asList("A", "B");

        RequestBodyEntity mockedPost = mock(RequestBodyEntity.class);
        doReturn(mockedPost).when(service).post(contains("/byOiAndIdsInClient"), eq(skus), any(IntegrationHeader.class));

        Response response = mock(Response.class);
        when(response.getStatus()).thenReturn(HttpStatus.SC_INTERNAL_SERVER_ERROR);
        doReturn(response).when(service).execute(any(BaseRequest.class));

        List<String> result = service.findByOiAndIdsInClient(skus, new ModuleOriginHeader("ECOMMERCE"));

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test(expected = NullPointerException.class)
    public void should_throw_when_find_by_oi_and_ids_in_client_null_list() {
        service.findByOiAndIdsInClient(null, new ModuleOriginHeader("ECOMMERCE"));
    }

    @Test
    public void should_remove_ignored_fields() throws Exception {
        Map<String, Object> map = new HashMap<>();
        map.put("id", 1L);
        map.put("skus", new ArrayList<>());
        map.put("images", new ArrayList<>());
        map.put("marketplaceImages", new ArrayList<>());
        map.put("imagesForDelete", new ArrayList<>());
        map.put("kitComponents", new ArrayList<>());
        map.put("title", "ok");

        Method m = ProductService.class.getDeclaredMethod("removeIgnoredFields", Map.class);
        m.setAccessible(true);
        m.invoke(service, map);

        assertFalse(map.containsKey("id"));
        assertFalse(map.containsKey("skus"));
        assertFalse(map.containsKey("images"));
        assertFalse(map.containsKey("marketplaceImages"));
        assertFalse(map.containsKey("imagesForDelete"));
        assertFalse(map.containsKey("kitComponents"));
        assertTrue(map.containsKey("title"));
    }

    @Test
    public void should_normalize_id_only_object_keep_only_id() throws Exception {
        Map<String, Object> map = new HashMap<>();
        Map<String, Object> nested = new HashMap<>();
        nested.put("id", 10L);
        nested.put("name", "x");
        map.put("category", nested);

        Method m = ProductService.class.getDeclaredMethod("normalizeIdOnlyObject", Map.class, String.class);
        m.setAccessible(true);
        m.invoke(service, map, "category");

        Object v = map.get("category");
        assertTrue(v instanceof Map);
        Map<?, ?> out = (Map<?, ?>) v;
        assertEquals(1, out.size());
        assertEquals(10L, out.get("id"));
    }

    @Test
    public void should_normalize_id_only_object_remove_when_id_null() throws Exception {
        Map<String, Object> map = new HashMap<>();
        Map<String, Object> nested = new HashMap<>();
        nested.put("id", null);
        map.put("origin", nested);

        Method m = ProductService.class.getDeclaredMethod("normalizeIdOnlyObject", Map.class, String.class);
        m.setAccessible(true);
        m.invoke(service, map, "origin");

        assertFalse(map.containsKey("origin"));
    }

    @Test
    public void should_normalize_brand_remove_when_empty() throws Exception {
        Map<String, Object> map = new HashMap<>();
        Map<String, Object> nested = new HashMap<>();
        nested.put("id", null);
        nested.put("name", null);
        nested.put("partnerId", null);
        map.put("brand", nested);

        Method m = ProductService.class.getDeclaredMethod("normalizeBrand", Map.class);
        m.setAccessible(true);
        m.invoke(service, map);

        assertFalse(map.containsKey("brand"));
    }

    @Test
    public void should_normalize_brand_keep_present_fields() throws Exception {
        Map<String, Object> map = new HashMap<>();
        Map<String, Object> nested = new HashMap<>();
        nested.put("id", 2L);
        nested.put("name", null);
        nested.put("partnerId", "P");
        map.put("brand", nested);

        Method m = ProductService.class.getDeclaredMethod("normalizeBrand", Map.class);
        m.setAccessible(true);
        m.invoke(service, map);

        Object v = map.get("brand");
        assertTrue(v instanceof Map);
        Map<?, ?> out = (Map<?, ?>) v;
        assertEquals(2, out.size());
        assertEquals(2L, out.get("id"));
        assertEquals("P", out.get("partnerId"));
        assertFalse(out.containsKey("name"));
    }

    @Test
    public void should_as_map_return_empty_when_not_map() throws Exception {
        Method m = ProductService.class.getDeclaredMethod("asMap", Object.class);
        m.setAccessible(true);

        Object r = m.invoke(service, "x");
        assertTrue(r instanceof Optional);
        assertFalse(((Optional<?>) r).isPresent());
    }

    @Test
    public void should_remove_null_values() throws Exception {
        Map<String, Object> map = new LinkedHashMap<>();
        map.put("a", null);
        map.put("b", 1);
        map.put("c", null);

        Method m = ProductService.class.getDeclaredMethod("removeNullValues", Map.class);
        m.setAccessible(true);

        Object r = m.invoke(service, map);
        assertTrue(r instanceof Map);
        Map<?, ?> out = (Map<?, ?>) r;
        assertEquals(1, out.size());
        assertEquals(1, out.get("b"));
        assertFalse(out.containsKey("a"));
        assertFalse(out.containsKey("c"));
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
        public RequestBodyEntity patch(String url, Object body, IntegrationHeader... headers) {
            return super.patch(url, body, headers);
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
        protected Response execute(BaseRequest request) {
            throw new AssertionError("execute must be stubbed in tests");
        }
    }
}
