package br.com.anymarket.sdk.marketplaces;

import br.com.anymarket.sdk.MarketPlace;
import br.com.anymarket.sdk.dto.MarketPlaceAccount;
import br.com.anymarket.sdk.exception.NotFoundException;
import br.com.anymarket.sdk.http.Response;
import br.com.anymarket.sdk.http.headers.IntegrationHeader;
import br.com.anymarket.sdk.http.headers.ModuleOriginHeader;
import br.com.anymarket.sdk.marketplaces.dto.MarketPlacesDTO;
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

public class MarketPlacesServiceTest {

    private static final String API_ENDPOINT = "http://hom-api.anymarket.com.br/v2";

    private MarketPlacesServiceFake service;

    @Before
    public void setUp() {
        service = spy(new MarketPlacesServiceFake(API_ENDPOINT, "ORIGIN"));
    }

    @Test
    public void should_get_market_places_success() {
        MarketPlacesDTO returned = mock(MarketPlacesDTO.class);

        GetRequest mockedGet = mock(GetRequest.class);
        doReturn(mockedGet).when(service).get(contains("/marketplaces?status=ACTIVE"), any());

        Response response = mock(Response.class);
        when(response.getStatus()).thenReturn(HttpStatus.SC_OK);
        when(response.to(MarketPlacesDTO.class)).thenReturn(returned);
        doReturn(response).when(service).execute(any(BaseRequest.class));

        MarketPlacesDTO result = service.getMarketPlaces("ACTIVE", new ModuleOriginHeader("TEST"));

        assertNotNull(result);
        assertSame(returned, result);
    }

    @Test(expected = NotFoundException.class)
    public void should_throw_not_found_when_get_market_places_status_not_200() {
        GetRequest mockedGet = mock(GetRequest.class);
        doReturn(mockedGet).when(service).get(anyString(), any());

        Response response = mock(Response.class);
        when(response.getStatus()).thenReturn(HttpStatus.SC_NOT_FOUND);
        doReturn(response).when(service).execute(any(BaseRequest.class));

        service.getMarketPlaces("ACTIVE", new ModuleOriginHeader("TEST"));
    }

    @Test
    public void should_get_active_accounts_success() {
        MarketPlaceAccount account = mock(MarketPlaceAccount.class);

        GetRequest mockedGet = mock(GetRequest.class);
        doReturn(mockedGet).when(service).get(contains("/marketplaces/B2W/accounts"), any());

        Response response = mock(Response.class);
        when(response.getStatus()).thenReturn(HttpStatus.SC_OK);
        when(response.to(any(TypeReference.class))).thenReturn(Collections.singletonList(account));
        doReturn(response).when(service).execute(any(BaseRequest.class));

        List<MarketPlaceAccount> result = service.getActiveAccounts(MarketPlace.B2W, new ModuleOriginHeader("TEST"));

        assertNotNull(result);
        assertEquals(1, result.size());
    }

    @Test(expected = NotFoundException.class)
    public void should_throw_not_found_when_get_active_accounts_status_not_200() {
        GetRequest mockedGet = mock(GetRequest.class);
        doReturn(mockedGet).when(service).get(anyString(), any());

        Response response = mock(Response.class);
        when(response.getStatus()).thenReturn(HttpStatus.SC_NOT_FOUND);
        doReturn(response).when(service).execute(any(BaseRequest.class));

        service.getActiveAccounts(MarketPlace.B2W, new ModuleOriginHeader("TEST"));
    }

    static class MarketPlacesServiceFake extends MarketPlacesService {
        MarketPlacesServiceFake(String apiEndPoint, String origin) {
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
