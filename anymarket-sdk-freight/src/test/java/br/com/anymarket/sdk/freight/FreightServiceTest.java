package br.com.anymarket.sdk.freight;

import br.com.anymarket.sdk.freight.dto.FreightRequest;
import br.com.anymarket.sdk.freight.dto.FreightResponse;
import br.com.anymarket.sdk.http.Response;
import br.com.anymarket.sdk.http.headers.IntegrationHeader;
import br.com.anymarket.sdk.http.headers.ModuleOriginHeader;
import com.mashape.unirest.request.BaseRequest;
import com.mashape.unirest.request.body.RequestBodyEntity;
import org.apache.http.HttpStatus;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

public class FreightServiceTest {

    private static final String API_ENDPOINT = "http://hom-api.anymarket.com.br/v2";

    private FreightServiceFake service;

    @Before
    public void setUp() {
        service = spy(new FreightServiceFake(API_ENDPOINT, "ORIGIN"));
    }

    @Test
    public void should_get_quotes_success() {
        FreightRequest request = mock(FreightRequest.class);
        FreightResponse returned = mock(FreightResponse.class);

        RequestBodyEntity mockedPost = mock(RequestBodyEntity.class);
        doReturn(mockedPost).when(service).post(contains("/freight/quotes"), eq(request), any());

        Response response = mock(Response.class);
        when(response.getStatus()).thenReturn(HttpStatus.SC_OK);
        when(response.to(FreightResponse.class)).thenReturn(returned);
        doReturn(response).when(service).execute(any(BaseRequest.class));

        FreightResponse result = service.getQuotes(request, new ModuleOriginHeader("TEST"));

        assertNotNull(result);
        assertSame(returned, result);
        verify(service).post(contains("/freight/quotes"), eq(request), any(IntegrationHeader[].class));
    }

    @Test(expected = RuntimeException.class)
    public void should_throw_runtime_exception_when_get_quotes_status_not_200() {
        FreightRequest request = mock(FreightRequest.class);

        RequestBodyEntity mockedPost = mock(RequestBodyEntity.class);
        doReturn(mockedPost).when(service).post(anyString(), any(), any());

        Response response = mock(Response.class);
        when(response.getStatus()).thenReturn(HttpStatus.SC_BAD_REQUEST);
        doReturn(response).when(service).execute(any(BaseRequest.class));

        service.getQuotes(request, new ModuleOriginHeader("TEST"));
    }

    @Test
    public void should_use_default_endpoint_when_null_given() {
        FreightServiceFake svc = spy(new FreightServiceFake(null, "ORIGIN"));

        FreightRequest request = mock(FreightRequest.class);
        FreightResponse returned = mock(FreightResponse.class);

        RequestBodyEntity mockedPost = mock(RequestBodyEntity.class);
        doReturn(mockedPost).when(svc).post(anyString(), any(), any());

        Response response = mock(Response.class);
        when(response.getStatus()).thenReturn(HttpStatus.SC_OK);
        when(response.to(FreightResponse.class)).thenReturn(returned);
        doReturn(response).when(svc).execute(any(BaseRequest.class));

        FreightResponse result = svc.getQuotes(request, new ModuleOriginHeader("TEST"));

        assertNotNull(result);
    }

    static class FreightServiceFake extends FreightService {
        FreightServiceFake(String apiEndPoint, String origin) {
            super(apiEndPoint, origin);
        }

        @Override
        protected RequestBodyEntity post(String url, Object body, IntegrationHeader... headers) {
            return super.post(url, body, headers);
        }

        @Override
        protected Response execute(BaseRequest request) {
            return super.execute(request);
        }
    }
}
