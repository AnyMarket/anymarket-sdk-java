package br.com.anymarket.sdk.automaticmessage;

import br.com.anymarket.sdk.automaticmessage.dto.AutomaticMessage;
import br.com.anymarket.sdk.http.Response;
import br.com.anymarket.sdk.http.headers.IntegrationHeader;
import br.com.anymarket.sdk.http.headers.ModuleOriginHeader;
import com.mashape.unirest.request.BaseRequest;
import com.mashape.unirest.request.body.RequestBodyEntity;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

public class AutomaticMessageServiceTest {

    private static final String API_ENDPOINT = "http://hom-api.anymarket.com.br/v2";

    private AutomaticMessageServiceFake service;

    @Before
    public void setUp() {
        service = spy(new AutomaticMessageServiceFake(API_ENDPOINT));
    }

    @Test
    public void should_insert_automatic_message_success() {
        AutomaticMessage message = new AutomaticMessage(123L, "Hello");
        AutomaticMessage returned = mock(AutomaticMessage.class);

        RequestBodyEntity mockedPost = mock(RequestBodyEntity.class);
        doReturn(mockedPost).when(service).post(contains("/messages/order/123"), eq(message), any());

        Response response = mock(Response.class);
        when(response.to(AutomaticMessage.class)).thenReturn(returned);
        doReturn(response).when(service).execute(any(BaseRequest.class));

        AutomaticMessage result = service.insertAutomaticMessage(message, new ModuleOriginHeader("TEST"));

        assertNotNull(result);
        assertSame(returned, result);
        verify(service).post(contains("/messages/order/123"), eq(message), any());
    }

    @Test(expected = NullPointerException.class)
    public void should_throw_null_pointer_when_message_id_is_null() {
        AutomaticMessage message = new AutomaticMessage(null, "Hello");
        service.insertAutomaticMessage(message, new ModuleOriginHeader("TEST"));
    }

    @Test
    public void should_use_default_endpoint_when_null_given() {
        AutomaticMessageServiceFake svc = spy(new AutomaticMessageServiceFake(null));

        AutomaticMessage message = new AutomaticMessage(1L, "Test");
        AutomaticMessage returned = mock(AutomaticMessage.class);

        RequestBodyEntity mockedPost = mock(RequestBodyEntity.class);
        doReturn(mockedPost).when(svc).post(anyString(), eq(message), any());

        Response response = mock(Response.class);
        when(response.to(AutomaticMessage.class)).thenReturn(returned);
        doReturn(response).when(svc).execute(any(BaseRequest.class));

        AutomaticMessage result = svc.insertAutomaticMessage(message, new ModuleOriginHeader("TEST"));

        assertNotNull(result);
    }

    static class AutomaticMessageServiceFake extends AutomaticMessageService {
        AutomaticMessageServiceFake(String apiEndPoint) {
            super(apiEndPoint);
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
