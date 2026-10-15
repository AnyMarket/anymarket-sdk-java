package br.com.anymarket.sdk.template;

import br.com.anymarket.sdk.MarketPlace;
import br.com.anymarket.sdk.exception.HttpClientException;
import br.com.anymarket.sdk.exception.NotFoundException;
import br.com.anymarket.sdk.http.Response;
import br.com.anymarket.sdk.http.headers.IntegrationHeader;
import br.com.anymarket.sdk.http.headers.ModuleOriginHeader;
import br.com.anymarket.sdk.template.dto.Template;
import com.fasterxml.jackson.core.type.TypeReference;
import com.mashape.unirest.request.BaseRequest;
import com.mashape.unirest.request.GetRequest;
import org.apache.http.HttpStatus;
import org.junit.Before;
import org.junit.Test;

import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

public class TemplateServiceTest {

    private static final String API_ENDPOINT = "http://hom-api.anymarket.com.br/v2";

    private TemplateServiceFake service;

    @Before
    public void setUp() {
        service = spy(new TemplateServiceFake(API_ENDPOINT, "ORIGIN"));
    }

    @Test
    public void should_get_template_success() {
        Template returned = mock(Template.class);

        GetRequest mockedGet = mock(GetRequest.class);
        doReturn(mockedGet).when(service).get(contains("/template/10"), any());

        Response response = mock(Response.class);
        when(response.getStatus()).thenReturn(HttpStatus.SC_OK);
        when(response.to(Template.class)).thenReturn(returned);
        doReturn(response).when(service).execute(any(BaseRequest.class));

        Template result = service.getTemplate(10L, new ModuleOriginHeader("TEST"));

        assertNotNull(result);
        assertSame(returned, result);
    }

    @Test(expected = NotFoundException.class)
    public void should_throw_not_found_when_get_template_status_not_200() {
        GetRequest mockedGet = mock(GetRequest.class);
        doReturn(mockedGet).when(service).get(anyString(), any());

        Response response = mock(Response.class);
        when(response.getStatus()).thenReturn(HttpStatus.SC_NOT_FOUND);
        doReturn(response).when(service).execute(any(BaseRequest.class));

        service.getTemplate(10L, new ModuleOriginHeader("TEST"));
    }

    @Test
    public void should_get_templates_by_marketplace_success() {
        Template template = mock(Template.class);

        GetRequest mockedGet = mock(GetRequest.class);
        doReturn(mockedGet).when(service).get(contains("/template/list/"), any());

        Response response = mock(Response.class);
        when(response.getStatus()).thenReturn(HttpStatus.SC_OK);
        when(response.to(any(TypeReference.class))).thenReturn(Collections.singletonList(template));
        doReturn(response).when(service).execute(any(BaseRequest.class));

        List<Template> result = service.getTemplatesByMarketPlace(MarketPlace.B2W, new ModuleOriginHeader("TEST"));

        assertNotNull(result);
        assertEquals(1, result.size());
    }

    @Test(expected = NotFoundException.class)
    public void should_throw_not_found_when_get_templates_by_marketplace_status_not_200() {
        GetRequest mockedGet = mock(GetRequest.class);
        doReturn(mockedGet).when(service).get(anyString(), any());

        Response response = mock(Response.class);
        when(response.getStatus()).thenReturn(HttpStatus.SC_NOT_FOUND);
        doReturn(response).when(service).execute(any(BaseRequest.class));

        service.getTemplatesByMarketPlace(MarketPlace.B2W, new ModuleOriginHeader("TEST"));
    }

    @Test
    public void should_render_template_success() {
        GetRequest mockedGet = mock(GetRequest.class);
        doReturn(mockedGet).when(service).get(contains("/template/render/1/2"), any());

        Response response = mock(Response.class);
        when(response.getStatus()).thenReturn(HttpStatus.SC_OK);
        when(response.getMessage()).thenReturn("<html>rendered</html>");
        doReturn(response).when(service).execute(any(BaseRequest.class));

        String result = service.renderTemplate(1L, 2L, new ModuleOriginHeader("TEST"));

        assertEquals("<html>rendered</html>", result);
    }

    @Test(expected = HttpClientException.class)
    public void should_throw_http_client_exception_when_render_template_status_not_200() {
        GetRequest mockedGet = mock(GetRequest.class);
        doReturn(mockedGet).when(service).get(anyString(), any());

        Response response = mock(Response.class);
        when(response.getStatus()).thenReturn(HttpStatus.SC_BAD_REQUEST);
        doReturn(response).when(service).execute(any(BaseRequest.class));

        service.renderTemplate(1L, 2L, new ModuleOriginHeader("TEST"));
    }

    static class TemplateServiceFake extends TemplateService {
        TemplateServiceFake(String apiEndPoint, String origin) {
            super(apiEndPoint, origin);
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
