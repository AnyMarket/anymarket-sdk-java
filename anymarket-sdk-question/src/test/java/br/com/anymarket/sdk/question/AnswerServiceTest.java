package br.com.anymarket.sdk.question;

import br.com.anymarket.sdk.exception.NotFoundException;
import br.com.anymarket.sdk.http.Response;
import br.com.anymarket.sdk.http.headers.IntegrationHeader;
import br.com.anymarket.sdk.http.headers.ModuleOriginHeader;
import br.com.anymarket.sdk.question.dto.Answer;
import com.mashape.unirest.request.BaseRequest;
import com.mashape.unirest.request.GetRequest;
import com.mashape.unirest.request.body.RequestBodyEntity;
import org.apache.http.HttpStatus;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

public class AnswerServiceTest {

    private static final String API_ENDPOINT = "http://hom-api.anymarket.com.br/v2";

    private AnswerServiceFake service;

    @Before
    public void setUp() {
        service = spy(new AnswerServiceFake(API_ENDPOINT));
    }

    @Test
    public void should_insert_answer_success() {
        Answer answer = mock(Answer.class);
        Answer returned = mock(Answer.class);

        RequestBodyEntity mockedPost = mock(RequestBodyEntity.class);
        doReturn(mockedPost).when(service).post(contains("/questions/5/answers/"), eq(answer), any());

        Response response = mock(Response.class);
        when(response.to(Answer.class)).thenReturn(returned);
        doReturn(response).when(service).execute(any(BaseRequest.class));

        Answer result = service.insertAnswer(answer, 5L, new ModuleOriginHeader("TEST"));

        assertNotNull(result);
        assertSame(returned, result);
        verify(service).post(contains("/questions/5/answers/"), eq(answer), any());
    }

    @Test(expected = NullPointerException.class)
    public void should_throw_null_pointer_when_answer_is_null() {
        service.insertAnswer(null, 5L, new ModuleOriginHeader("TEST"));
    }

    @Test(expected = NullPointerException.class)
    public void should_throw_null_pointer_when_question_id_is_null() {
        Answer answer = mock(Answer.class);
        service.insertAnswer(answer, null, new ModuleOriginHeader("TEST"));
    }

    @Test
    public void should_get_answer_success() {
        Answer returned = mock(Answer.class);

        GetRequest mockedGet = mock(GetRequest.class);
        doReturn(mockedGet).when(service).get(contains("/questions/5/answers/20"), any());

        Response response = mock(Response.class);
        when(response.getStatus()).thenReturn(HttpStatus.SC_OK);
        when(response.to(Answer.class)).thenReturn(returned);
        doReturn(response).when(service).execute(any(BaseRequest.class));

        Answer result = service.getAnswer(5L, 20L, new ModuleOriginHeader("TEST"));

        assertNotNull(result);
        assertSame(returned, result);
    }

    @Test(expected = NotFoundException.class)
    public void should_throw_not_found_when_get_answer_status_not_200() {
        GetRequest mockedGet = mock(GetRequest.class);
        doReturn(mockedGet).when(service).get(anyString(), any());

        Response response = mock(Response.class);
        when(response.getStatus()).thenReturn(HttpStatus.SC_NOT_FOUND);
        doReturn(response).when(service).execute(any(BaseRequest.class));

        service.getAnswer(5L, 20L, new ModuleOriginHeader("TEST"));
    }

    @Test(expected = NullPointerException.class)
    public void should_throw_null_pointer_when_get_answer_question_id_null() {
        service.getAnswer(null, 20L, new ModuleOriginHeader("TEST"));
    }

    @Test(expected = NullPointerException.class)
    public void should_throw_null_pointer_when_get_answer_id_null() {
        service.getAnswer(5L, null, new ModuleOriginHeader("TEST"));
    }

    static class AnswerServiceFake extends AnswerService {
        AnswerServiceFake(String apiEndPoint) {
            super(apiEndPoint);
        }

        @Override
        protected RequestBodyEntity post(String url, Object body, IntegrationHeader... headers) {
            return super.post(url, body, headers);
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
