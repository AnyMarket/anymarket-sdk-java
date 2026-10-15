package br.com.anymarket.sdk.profile;

import br.com.anymarket.sdk.exception.NotFoundException;
import br.com.anymarket.sdk.http.Response;
import br.com.anymarket.sdk.http.headers.IntegrationHeader;
import br.com.anymarket.sdk.http.headers.ModuleOriginHeader;
import br.com.anymarket.sdk.profile.dto.Language;
import br.com.anymarket.sdk.profile.dto.Seller;
import com.mashape.unirest.request.BaseRequest;
import com.mashape.unirest.request.GetRequest;
import org.apache.http.HttpStatus;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

public class SellerProfileServiceTest {

    private static final String API_ENDPOINT = "http://hom-api.anymarket.com.br/v2";

    private SellerProfileServiceFake service;

    @Before
    public void setUp() {
        service = spy(new SellerProfileServiceFake(API_ENDPOINT));
    }

    @Test
    public void should_get_profile_success() {
        Seller returned = mock(Seller.class);

        GetRequest mockedGet = mock(GetRequest.class);
        doReturn(mockedGet).when(service).get(contains("/seller/profile"), any());

        Response response = mock(Response.class);
        when(response.getStatus()).thenReturn(HttpStatus.SC_OK);
        when(response.to(Seller.class)).thenReturn(returned);
        doReturn(response).when(service).execute(any(BaseRequest.class));

        Seller result = service.getProfile(new ModuleOriginHeader("TEST"));

        assertNotNull(result);
        assertSame(returned, result);
    }

    @Test(expected = NotFoundException.class)
    public void should_throw_not_found_when_get_profile_status_not_200() {
        GetRequest mockedGet = mock(GetRequest.class);
        doReturn(mockedGet).when(service).get(anyString(), any());

        Response response = mock(Response.class);
        when(response.getStatus()).thenReturn(HttpStatus.SC_NOT_FOUND);
        doReturn(response).when(service).execute(any(BaseRequest.class));

        service.getProfile(new ModuleOriginHeader("TEST"));
    }

    @Test
    public void should_get_language_success() {
        Language returned = mock(Language.class);

        GetRequest mockedGet = mock(GetRequest.class);
        doReturn(mockedGet).when(service).get(contains("/seller/profile/language"), any());

        Response response = mock(Response.class);
        when(response.getStatus()).thenReturn(HttpStatus.SC_OK);
        when(response.to(Language.class)).thenReturn(returned);
        doReturn(response).when(service).execute(any(BaseRequest.class));

        Language result = service.getLanguage(new ModuleOriginHeader("TEST"));

        assertNotNull(result);
        assertSame(returned, result);
    }

    @Test
    public void should_return_default_language_when_get_language_status_not_200() {
        GetRequest mockedGet = mock(GetRequest.class);
        doReturn(mockedGet).when(service).get(anyString(), any());

        Response response = mock(Response.class);
        when(response.getStatus()).thenReturn(HttpStatus.SC_NOT_FOUND);
        doReturn(response).when(service).execute(any(BaseRequest.class));

        Language result = service.getLanguage(new ModuleOriginHeader("TEST"));

        assertNotNull(result);
        assertEquals("pt", result.getLanguage());
    }

    static class SellerProfileServiceFake extends SellerProfileService {
        SellerProfileServiceFake(String apiEndPoint) {
            super(apiEndPoint);
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
