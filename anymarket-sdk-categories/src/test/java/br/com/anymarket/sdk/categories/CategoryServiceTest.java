package br.com.anymarket.sdk.categories;

import br.com.anymarket.sdk.categories.dto.Category;
import br.com.anymarket.sdk.exception.NotFoundException;
import br.com.anymarket.sdk.http.Response;
import br.com.anymarket.sdk.http.headers.IntegrationHeader;
import br.com.anymarket.sdk.http.headers.ModuleOriginHeader;
import br.com.anymarket.sdk.paging.Page;
import br.com.anymarket.sdk.resource.Link;
import com.fasterxml.jackson.core.type.TypeReference;
import com.mashape.unirest.request.BaseRequest;
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
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

public class CategoryServiceTest {

    @Spy
    @InjectMocks
    private CategoryServiceFake service;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        service = spy(new CategoryServiceFake("http://hom-api.anymarket.com.br/v2", "ORIGIN"));
    }

    @Test
    public void should_update_category_success() {
        Category category = mock(Category.class);
        when(category.getId()).thenReturn(10L);

        RequestBodyEntity mockedPut = mock(RequestBodyEntity.class);
        doReturn(mockedPut).when(service).put(contains("/categories/10"), eq(category), any());

        Category returned = mock(Category.class);
        Response response = mock(Response.class);
        when(response.to(Category.class)).thenReturn(returned);
        doReturn(response).when(service).execute(any(BaseRequest.class));

        Category result = service.updateCategory(category, new ModuleOriginHeader("ECOMMERCE"));

        assertNotNull(result);
        verify(service).put(contains("/categories/10"), eq(category), any());
    }

    @Test
    public void should_insert_category_success() {
        Category category = mock(Category.class);

        RequestBodyEntity mockedPost = mock(RequestBodyEntity.class);
        doReturn(mockedPost).when(service).post(contains("/categories/"), eq(category), any());

        Category returned = mock(Category.class);
        Response response = mock(Response.class);
        when(response.to(Category.class)).thenReturn(returned);
        doReturn(response).when(service).execute(any(BaseRequest.class));

        Category result = service.insertCategory(category, new ModuleOriginHeader("ECOMMERCE"));

        assertNotNull(result);
        verify(service).post(contains("/categories/"), eq(category), any());
    }

    @Test
    public void should_delete_category_by_entity_success() {
        Category category = mock(Category.class);
        when(category.getId()).thenReturn(10L);

        HttpRequestWithBody mockedDelete = mock(HttpRequestWithBody.class);
        doReturn(mockedDelete).when(service).delete(contains("/categories/10"), any());

        Response response = mock(Response.class);
        when(response.getStatus()).thenReturn(HttpStatus.SC_NO_CONTENT);
        doReturn(response).when(service).execute(any(BaseRequest.class));

        service.deleteCategory(category, new ModuleOriginHeader("ECOMMERCE"));

        verify(service).delete(contains("/categories/10"), any());
    }

    @Test
    public void should_delete_category_by_id_success() {
        HttpRequestWithBody mockedDelete = mock(HttpRequestWithBody.class);
        doReturn(mockedDelete).when(service).delete(contains("/categories/10"), any());

        Response response = mock(Response.class);
        when(response.getStatus()).thenReturn(HttpStatus.SC_NO_CONTENT);
        doReturn(response).when(service).execute(any(BaseRequest.class));

        service.deleteCategory(10L, new ModuleOriginHeader("ECOMMERCE"));

        verify(service).execute(any(BaseRequest.class));
    }

    @Test(expected = NotFoundException.class)
    public void should_throw_not_found_when_delete_category_status_not_204() {
        HttpRequestWithBody mockedDelete = mock(HttpRequestWithBody.class);
        doReturn(mockedDelete).when(service).delete(anyString(), any());

        Response response = mock(Response.class);
        when(response.getStatus()).thenReturn(HttpStatus.SC_NOT_FOUND);
        doReturn(response).when(service).execute(any(BaseRequest.class));

        service.deleteCategory(10L, new ModuleOriginHeader("ECOMMERCE"));
    }

    @Test
    public void should_get_category_success() {
        GetRequest mockedGet = mock(GetRequest.class);
        doReturn(mockedGet).when(service).get(contains("/categories/10"), any());

        Category returned = mock(Category.class);
        Response response = mock(Response.class);
        when(response.getStatus()).thenReturn(HttpStatus.SC_OK);
        when(response.to(Category.class)).thenReturn(returned);
        doReturn(response).when(service).execute(any(BaseRequest.class));

        Category result = service.getCategory(10L, new ModuleOriginHeader("ECOMMERCE"));

        assertNotNull(result);
    }

    @Test(expected = NotFoundException.class)
    public void should_throw_not_found_when_get_category_status_not_200() {
        GetRequest mockedGet = mock(GetRequest.class);
        doReturn(mockedGet).when(service).get(anyString(), any());

        Response response = mock(Response.class);
        when(response.getStatus()).thenReturn(HttpStatus.SC_NOT_FOUND);
        doReturn(response).when(service).execute(any(BaseRequest.class));

        service.getCategory(10L, new ModuleOriginHeader("ECOMMERCE"));
    }

    @Test
    public void should_get_category_by_partner_id_returns_first_result() {
        GetRequest mockedGet = mock(GetRequest.class);
        doReturn(mockedGet).when(service).get(contains("partnerId="), any());

        Category cat = mock(Category.class);
        Page<Category> page = mock(Page.class);
        when(page.getContent()).thenReturn(Collections.singletonList(cat));

        Response response = mock(Response.class);
        when(response.getStatus()).thenReturn(HttpStatus.SC_OK);
        when(response.to(any(TypeReference.class))).thenReturn(page);
        doReturn(response).when(service).execute(any(BaseRequest.class));

        Category result = service.getCategoryByPartnerId("PARTNER-1", new ModuleOriginHeader("ECOMMERCE"));

        assertNotNull(result);
        assertSame(cat, result);
    }

    @Test
    public void should_return_null_when_get_category_by_partner_id_empty_list() {
        GetRequest mockedGet = mock(GetRequest.class);
        doReturn(mockedGet).when(service).get(anyString(), any());

        Page<Category> page = mock(Page.class);
        when(page.getContent()).thenReturn(Collections.emptyList());

        Response response = mock(Response.class);
        when(response.getStatus()).thenReturn(HttpStatus.SC_OK);
        when(response.to(any(TypeReference.class))).thenReturn(page);
        doReturn(response).when(service).execute(any(BaseRequest.class));

        Category result = service.getCategoryByPartnerId("PARTNER-1");

        assertNull(result);
    }

    @Test
    public void should_return_null_when_get_category_by_partner_id_status_not_200() {
        GetRequest mockedGet = mock(GetRequest.class);
        doReturn(mockedGet).when(service).get(anyString(), any());

        Response response = mock(Response.class);
        when(response.getStatus()).thenReturn(HttpStatus.SC_NOT_FOUND);
        doReturn(response).when(service).execute(any(BaseRequest.class));

        Category result = service.getCategoryByPartnerId("PARTNER-1");

        assertNull(result);
    }

    @Test
    public void should_find_category_by_partner_id_success() {
        GetRequest mockedGet = mock(GetRequest.class);
        doReturn(mockedGet).when(service).get(anyString(), any());

        Page<Category> emptyPage = mock(Page.class);
        when(emptyPage.getContent()).thenReturn(Collections.emptyList());
        when(emptyPage.getLinks()).thenReturn(Collections.emptyList());

        Response response = mock(Response.class);
        when(response.getStatus()).thenReturn(HttpStatus.SC_OK);
        when(response.to(any(TypeReference.class))).thenReturn(emptyPage);
        doReturn(response).when(service).execute(any(BaseRequest.class));

        List<Category> result = service.findCategoryByPartnerId("PARTNER-1");

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    public void should_get_all_categories_returns_empty_when_first_page_is_empty() {
        GetRequest mockedGet = mock(GetRequest.class);
        doReturn(mockedGet).when(service).get(anyString(), any());

        Page<Category> emptyPage = mock(Page.class);
        when(emptyPage.getContent()).thenReturn(Collections.emptyList());
        when(emptyPage.getLinks()).thenReturn(Collections.emptyList());

        Response response = mock(Response.class);
        when(response.getStatus()).thenReturn(HttpStatus.SC_OK);
        when(response.to(any(TypeReference.class))).thenReturn(emptyPage);
        doReturn(response).when(service).execute(any(BaseRequest.class));

        List<Category> result = service.getAllCategories(new ModuleOriginHeader("ECOMMERCE"));

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test(expected = NotFoundException.class)
    public void should_throw_not_found_when_get_all_categories_status_not_200() {
        GetRequest mockedGet = mock(GetRequest.class);
        doReturn(mockedGet).when(service).get(anyString(), any());

        Response response = mock(Response.class);
        when(response.getStatus()).thenReturn(HttpStatus.SC_INTERNAL_SERVER_ERROR);
        doReturn(response).when(service).execute(any(BaseRequest.class));

        service.getAllCategories(new ModuleOriginHeader("ECOMMERCE"));
    }

    @Test
    public void should_get_all_categories_with_root_and_no_children() {
        Category cat1 = mock(Category.class);
        when(cat1.getId()).thenReturn(1L);
        when(cat1.getChildren()).thenReturn(null);

        Page<Category> firstPage = mock(Page.class);
        when(firstPage.getContent()).thenReturn(Collections.singletonList(cat1));
        when(firstPage.getLinks()).thenReturn(Collections.emptyList());

        Response firstPageResponse = mock(Response.class);
        when(firstPageResponse.getStatus()).thenReturn(HttpStatus.SC_OK);
        when(firstPageResponse.to(any(TypeReference.class))).thenReturn(firstPage);

        Response catResponse = mock(Response.class);
        when(catResponse.getStatus()).thenReturn(HttpStatus.SC_OK);
        when(catResponse.to(Category.class)).thenReturn(cat1);

        Page<Category> emptyPage = mock(Page.class);
        when(emptyPage.getContent()).thenReturn(Collections.emptyList());
        when(emptyPage.getLinks()).thenReturn(Collections.emptyList());

        Response emptyPageResponse = mock(Response.class);
        when(emptyPageResponse.getStatus()).thenReturn(HttpStatus.SC_OK);
        when(emptyPageResponse.to(any(TypeReference.class))).thenReturn(emptyPage);

        GetRequest mockedGet = mock(GetRequest.class);
        doReturn(mockedGet).when(service).get(anyString(), any());

        doReturn(firstPageResponse)
                .doReturn(catResponse)
                .doReturn(emptyPageResponse)
                .when(service).execute(any(BaseRequest.class));

        List<Category> result = service.getAllCategories(new ModuleOriginHeader("ECOMMERCE"));

        assertNotNull(result);
        assertEquals(1, result.size());
    }

    @Test
    public void should_get_all_categories_including_children() {
        Category child1 = mock(Category.class);
        when(child1.getId()).thenReturn(2L);
        when(child1.getChildren()).thenReturn(null);

        Category cat1 = mock(Category.class);
        when(cat1.getId()).thenReturn(1L);
        when(cat1.getChildren()).thenReturn(Collections.singletonList(child1));

        Page<Category> firstPage = mock(Page.class);
        when(firstPage.getContent()).thenReturn(Collections.singletonList(cat1));
        when(firstPage.getLinks()).thenReturn(Collections.emptyList());

        Response firstPageResponse = mock(Response.class);
        when(firstPageResponse.getStatus()).thenReturn(HttpStatus.SC_OK);
        when(firstPageResponse.to(any(TypeReference.class))).thenReturn(firstPage);

        Response cat1Response = mock(Response.class);
        when(cat1Response.getStatus()).thenReturn(HttpStatus.SC_OK);
        when(cat1Response.to(Category.class)).thenReturn(cat1);

        Response child1Response = mock(Response.class);
        when(child1Response.getStatus()).thenReturn(HttpStatus.SC_OK);
        when(child1Response.to(Category.class)).thenReturn(child1);

        Page<Category> emptyPage = mock(Page.class);
        when(emptyPage.getContent()).thenReturn(Collections.emptyList());
        when(emptyPage.getLinks()).thenReturn(Collections.emptyList());

        Response emptyPageResponse = mock(Response.class);
        when(emptyPageResponse.getStatus()).thenReturn(HttpStatus.SC_OK);
        when(emptyPageResponse.to(any(TypeReference.class))).thenReturn(emptyPage);

        GetRequest mockedGet = mock(GetRequest.class);
        doReturn(mockedGet).when(service).get(anyString(), any());

        doReturn(firstPageResponse)
                .doReturn(cat1Response)
                .doReturn(child1Response)
                .doReturn(emptyPageResponse)
                .when(service).execute(any(BaseRequest.class));

        List<Category> result = service.getAllCategories(new ModuleOriginHeader("ECOMMERCE"));

        assertNotNull(result);
        assertEquals(2, result.size());
    }

    @Test
    public void should_get_all_categories_following_next_page_link() {
        Category cat1 = mock(Category.class);
        when(cat1.getId()).thenReturn(1L);
        when(cat1.getChildren()).thenReturn(null);

        Link nextLink = mock(Link.class);
        when(nextLink.getRel()).thenReturn("next");
        when(nextLink.getHref()).thenReturn("http://hom-api.anymarket.com.br/v2/categories/?offset=10");

        Page<Category> firstPage = mock(Page.class);
        when(firstPage.getContent()).thenReturn(Collections.singletonList(cat1));
        when(firstPage.getLinks()).thenReturn(Collections.singletonList(nextLink));

        Response firstPageResponse = mock(Response.class);
        when(firstPageResponse.getStatus()).thenReturn(HttpStatus.SC_OK);
        when(firstPageResponse.to(any(TypeReference.class))).thenReturn(firstPage);

        Response cat1Response = mock(Response.class);
        when(cat1Response.getStatus()).thenReturn(HttpStatus.SC_OK);
        when(cat1Response.to(Category.class)).thenReturn(cat1);

        Page<Category> secondPage = mock(Page.class);
        when(secondPage.getContent()).thenReturn(Collections.emptyList());
        when(secondPage.getLinks()).thenReturn(Collections.emptyList());

        Response secondPageResponse = mock(Response.class);
        when(secondPageResponse.getStatus()).thenReturn(HttpStatus.SC_OK);
        when(secondPageResponse.to(any(TypeReference.class))).thenReturn(secondPage);

        GetRequest mockedGet = mock(GetRequest.class);
        doReturn(mockedGet).when(service).get(anyString(), any());

        doReturn(firstPageResponse)
                .doReturn(cat1Response)
                .doReturn(secondPageResponse)
                .when(service).execute(any(BaseRequest.class));

        List<Category> result = service.getAllCategories(new ModuleOriginHeader("ECOMMERCE"));

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(service, atLeast(2)).get(anyString(), any());
    }

    @Test
    public void should_create_service_with_origin_and_use_default_endpoint_when_null() {
        CategoryServiceFake svc = spy(new CategoryServiceFake(null, "ECOMMERCE"));

        Category category = mock(Category.class);
        when(category.getId()).thenReturn(5L);

        RequestBodyEntity mockedPut = mock(RequestBodyEntity.class);
        doReturn(mockedPut).when(svc).put(anyString(), eq(category), any());

        Category returned = mock(Category.class);
        Response response = mock(Response.class);
        when(response.to(Category.class)).thenReturn(returned);
        doReturn(response).when(svc).execute(any(BaseRequest.class));

        Category result = svc.updateCategory(category, new ModuleOriginHeader("ECOMMERCE"));

        assertNotNull(result);
    }

    private static class CategoryServiceFake extends CategoryService {
        public CategoryServiceFake(String apiEndPoint, String origin) {
            super(apiEndPoint, origin);
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
        protected Response execute(BaseRequest request) {
            return super.execute(request);
        }
    }
}
