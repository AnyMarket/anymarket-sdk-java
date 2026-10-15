package br.com.anymarket.sdk.delegator;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.contains;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.apache.http.HttpStatus;
import org.junit.Before;
import org.junit.Test;

import com.mashape.unirest.request.BaseRequest;
import com.mashape.unirest.request.body.RequestBodyEntity;

import br.com.anymarket.sdk.MarketPlace;
import br.com.anymarket.sdk.http.Response;
import br.com.anymarket.sdk.http.headers.IntegrationHeader;
import br.com.anymarket.sdk.http.headers.ModuleOriginHeader;

public class DelegatorServiceTest {

    private static final String API_ENDPOINT = "http://hom-api.anymarket.com.br/v2";

    private DelegatorServiceFake service;

    @Before
    public void setUp() {
        service = spy(new DelegatorServiceFake(API_ENDPOINT, "ORIGIN"));
    }

    @Test
    public void should_add_to_force_sync_markup_with_account_success() {
        RequestBodyEntity mockedPut = mock(RequestBodyEntity.class);
        String substring = "/delegates/forceSyncMarkup/B2W?idAccount=42";
        doReturn(mockedPut).when(service).put(contains(substring), any(), any());

        Response response = mock(Response.class);
        when(response.getStatus()).thenReturn(HttpStatus.SC_OK);
        doReturn(response).when(service).execute(any(BaseRequest.class));

        service.addToForceSyncMarkupForMarketplace(MarketPlace.B2W, 42L, new ModuleOriginHeader("TEST"));

        verify(service).put(contains(substring), any(), any(IntegrationHeader[].class));
    }

    @Test
    public void should_add_to_force_sync_markup_without_account_success() {
        RequestBodyEntity mockedPut = mock(RequestBodyEntity.class);
        doReturn(mockedPut).when(service).put(contains("/delegates/forceSyncMarkup/B2W"), any(), any());

        Response response = mock(Response.class);
        when(response.getStatus()).thenReturn(HttpStatus.SC_OK);
        doReturn(response).when(service).execute(any(BaseRequest.class));

        service.addToForceSyncMarkupForMarketplace(MarketPlace.B2W, new ModuleOriginHeader("TEST"));

        verify(service).execute(any(BaseRequest.class));
    }

    static class DelegatorServiceFake extends DelegatorService {
        DelegatorServiceFake(String apiEndPoint, String origin) {
            super(apiEndPoint, origin);
        }

        @Override
        protected RequestBodyEntity put(String url, Object body, IntegrationHeader... headers) {
            return super.put(url, body, headers);
        }

        @Override
        protected Response execute(BaseRequest request) {
            return super.execute(request);
        }
    }
}
