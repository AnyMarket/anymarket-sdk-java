package br.com.anymarket.sdk.stock;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyMap;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.contains;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.List;

import org.apache.http.HttpStatus;
import org.junit.Before;
import org.junit.Test;

import com.fasterxml.jackson.core.type.TypeReference;
import com.mashape.unirest.request.BaseRequest;
import com.mashape.unirest.request.GetRequest;
import com.mashape.unirest.request.body.RequestBodyEntity;

import br.com.anymarket.sdk.exception.NotFoundException;
import br.com.anymarket.sdk.http.Response;
import br.com.anymarket.sdk.http.headers.IntegrationHeader;
import br.com.anymarket.sdk.http.headers.ModuleOriginHeader;
import br.com.anymarket.sdk.stock.dto.Stock;
import br.com.anymarket.sdk.stock.dto.StockCollection;
import br.com.anymarket.sdk.stock.dto.StockLocal;
import br.com.anymarket.sdk.stock.dto.StockResult;

public class StockServiceTest {

    private static final String API_ENDPOINT = "http://hom-api.anymarket.com.br/v2";

    private StockServiceFake service;

    @Before
    public void setUp() {
        service = spy(new StockServiceFake(API_ENDPOINT, "ORIGIN"));
    }

    @Test
    public void should_insert_stock_single_success() {
        Stock stock = mock(Stock.class);

        RequestBodyEntity mockedPost = mock(RequestBodyEntity.class);
        doReturn(mockedPost).when(service).post(contains("/stocks"), any(), any());

        Response response = mock(Response.class);
        when(response.getStatus()).thenReturn(HttpStatus.SC_CREATED);
        doReturn(response).when(service).execute(any(BaseRequest.class));

        Response result = service.insertStock(stock, new ModuleOriginHeader("TEST"));

        assertNotNull(result);
        verify(service).execute(any(BaseRequest.class));
    }

    @Test
    public void should_insert_stock_collection_success() {
        StockCollection stockCollection = mock(StockCollection.class);
        when(stockCollection.getPathURI()).thenReturn("/stocks");
        when(stockCollection.getStocks()).thenReturn(Collections.emptyList());

        RequestBodyEntity mockedPost = mock(RequestBodyEntity.class);
        doReturn(mockedPost).when(service).post(contains("/stocks"), any(), any());

        Response response = mock(Response.class);
        when(response.getStatus()).thenReturn(HttpStatus.SC_CREATED);
        doReturn(response).when(service).execute(any(BaseRequest.class));

        Response result = service.insertStock(stockCollection, new ModuleOriginHeader("TEST"));

        assertNotNull(result);
    }

    @Test
    public void should_update_stock_single_success() {
        Stock stock = mock(Stock.class);

        RequestBodyEntity mockedPut = mock(RequestBodyEntity.class);
        doReturn(mockedPut).when(service).put(contains("/stocks"), any(), any());

        Response response = mock(Response.class);
        when(response.getStatus()).thenReturn(HttpStatus.SC_OK);
        doReturn(response).when(service).execute(any(BaseRequest.class));

        Response result = service.updateStock(stock, new ModuleOriginHeader("TEST"));

        assertNotNull(result);
        verify(service).execute(any(BaseRequest.class));
    }

    @Test
    public void should_get_stock_success() {
        StockResult returned = mock(StockResult.class);

        GetRequest mockedGet = mock(GetRequest.class);
        when(mockedGet.queryString(anyMap())).thenReturn(mockedGet);
        doReturn(mockedGet).when(service).get(contains("/stocks"), any());

        Response response = mock(Response.class);
        when(response.getStatus()).thenReturn(HttpStatus.SC_OK);
        when(response.to(any(TypeReference.class))).thenReturn(returned);
        doReturn(response).when(service).execute(any(BaseRequest.class));

        StockResult result = service.getStock("123", null, null, 0, 0, null, null, new ModuleOriginHeader("TEST"));

        assertNotNull(result);
    }

    @Test(expected = NotFoundException.class)
    public void should_throw_not_found_when_get_stock_status_not_200() {
        GetRequest mockedGet = mock(GetRequest.class);
        when(mockedGet.queryString(anyMap())).thenReturn(mockedGet);
        doReturn(mockedGet).when(service).get(anyString(), any());

        Response response = mock(Response.class);
        when(response.getStatus()).thenReturn(HttpStatus.SC_NOT_FOUND);
        doReturn(response).when(service).execute(any(BaseRequest.class));

        service.getStock(null, null, null, 0, 0, null, null, new ModuleOriginHeader("TEST"));
    }

    @Test
    public void should_get_all_stock_locals_success() {
        StockLocal local = mock(StockLocal.class);

        GetRequest mockedGet = mock(GetRequest.class);
        doReturn(mockedGet).when(service).get(contains("/stocks/locals"), any());

        Response response = mock(Response.class);
        when(response.getStatus()).thenReturn(HttpStatus.SC_OK);
        when(response.to(any(TypeReference.class))).thenReturn(Collections.singletonList(local));
        doReturn(response).when(service).execute(any(BaseRequest.class));

        List<StockLocal> result = service.getAllStockLocals(new ModuleOriginHeader("TEST"));

        assertNotNull(result);
        assertEquals(1, result.size());
    }

    @Test(expected = NotFoundException.class)
    public void should_throw_not_found_when_get_all_stock_locals_status_not_200() {
        GetRequest mockedGet = mock(GetRequest.class);
        doReturn(mockedGet).when(service).get(anyString(), any());

        Response response = mock(Response.class);
        when(response.getStatus()).thenReturn(HttpStatus.SC_INTERNAL_SERVER_ERROR);
        doReturn(response).when(service).execute(any(BaseRequest.class));

        service.getAllStockLocals(new ModuleOriginHeader("TEST"));
    }

    static class StockServiceFake extends StockService {
        StockServiceFake(String apiEndPoint, String origin) {
            super(apiEndPoint, origin);
        }

        @Override
        protected RequestBodyEntity post(String url, Object body, IntegrationHeader... headers) {
            return super.post(url, body, headers);
        }

        @Override
        protected RequestBodyEntity put(String url, Object body, IntegrationHeader... headers) {
            return super.put(url, body, headers);
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
