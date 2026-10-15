package br.com.anymarket.sdk.question;

import br.com.anymarket.sdk.exception.NotFoundException;
import br.com.anymarket.sdk.http.Response;
import br.com.anymarket.sdk.http.headers.IntegrationHeader;
import br.com.anymarket.sdk.http.headers.ModuleOriginHeader;
import br.com.anymarket.sdk.question.dto.Question;
import com.mashape.unirest.request.BaseRequest;
import com.mashape.unirest.request.GetRequest;
import org.apache.http.HttpStatus;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

public class QuestionServiceTest {

    private static final String API_ENDPOINT = "http://hom-api.anymarket.com.br/v2";

    private QuestionServiceFake service;

    @Before
    public void setUp() {
        service = spy(new QuestionServiceFake(API_ENDPOINT));
    }

    @Test
    public void should_get_question_success() {
        Question returned = mock(Question.class);

        GetRequest mockedGet = mock(GetRequest.class);
        doReturn(mockedGet).when(service).get(contains("/questions/10"), any());

        Response response = mock(Response.class);
        when(response.getStatus()).thenReturn(HttpStatus.SC_OK);
        when(response.to(Question.class)).thenReturn(returned);
        doReturn(response).when(service).execute(any(BaseRequest.class));

        Question result = service.getQuestion(10L, new ModuleOriginHeader("TEST"));

        assertNotNull(result);
        assertSame(returned, result);
    }

    @Test(expected = NotFoundException.class)
    public void should_throw_not_found_when_get_question_status_not_200() {
        GetRequest mockedGet = mock(GetRequest.class);
        doReturn(mockedGet).when(service).get(anyString(), any());

        Response response = mock(Response.class);
        when(response.getStatus()).thenReturn(HttpStatus.SC_NOT_FOUND);
        doReturn(response).when(service).execute(any(BaseRequest.class));

        service.getQuestion(10L, new ModuleOriginHeader("TEST"));
    }

    @Test
    public void should_use_default_endpoint_when_null_given() {
        QuestionServiceFake svc = spy(new QuestionServiceFake(null));

        Question returned = mock(Question.class);
        GetRequest mockedGet = mock(GetRequest.class);
        doReturn(mockedGet).when(svc).get(anyString(), any());

        Response response = mock(Response.class);
        when(response.getStatus()).thenReturn(HttpStatus.SC_OK);
        when(response.to(Question.class)).thenReturn(returned);
        doReturn(response).when(svc).execute(any(BaseRequest.class));

        Question result = svc.getQuestion(1L);

        assertNotNull(result);
    }

    static class QuestionServiceFake extends QuestionService {
        QuestionServiceFake(String apiEndPoint) {
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
