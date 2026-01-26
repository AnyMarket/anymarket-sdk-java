package br.com.anymarket.sdk.http;

import br.com.anymarket.sdk.dto.ErrorDTO;
import br.com.anymarket.sdk.exception.HttpClientException;
import br.com.anymarket.sdk.exception.HttpServerException;
import br.com.anymarket.sdk.exception.UnauthorizedException;
import br.com.anymarket.sdk.http.headers.IntegrationHeader;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.mashape.unirest.request.BaseRequest;
import com.mashape.unirest.request.GetRequest;
import com.mashape.unirest.request.HttpRequest;
import com.mashape.unirest.request.HttpRequestWithBody;
import com.mashape.unirest.request.body.RequestBodyEntity;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class HttpService {

    protected RequestBodyEntity put(String url, Object body, IntegrationHeader... headers) {
        HttpRequestWithBody put = Unirest.put(url);
        addHeaders(put, headers);

        String bodyJson = writeValueAsJson(body);
        return put.body(bodyJson);
    }

    protected GetRequest get(String url, IntegrationHeader... headers) {
        GetRequest unirest = Unirest.get(url);
        addHeaders(unirest, headers);

        return unirest;
    }

    protected RequestBodyEntity post(String url, Object body, IntegrationHeader... headers) {
        HttpRequestWithBody post = Unirest.post(url);
        addHeaders(post, headers);

        return post.body(writeValueAsJson(body));
    }

    protected HttpRequest postFile(String url, Map<String, Object> fields, IntegrationHeader... headers) {
        HttpRequestWithBody post = Unirest.post(url);
        addHeaders(post, headers);
        post.fields(fields);
        return post;
    }

    protected HttpRequestWithBody delete(String url, IntegrationHeader... headers) {
        HttpRequestWithBody delete = Unirest.delete(url);
        addHeaders(delete, headers);

        return delete;
    }

    public <T> T readValue(String value, TypeReference<T> valueType) {
        try {
            return Mapper.get().readValue(value, valueType);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    public String writeValueAsJson(Object value) {
        try {
            return Mapper.get().writeValueAsString(value);
        } catch (JsonProcessingException ex) {
            throw new RuntimeException(ex);
        }
    }

    private void addHeaders(HttpRequest request, IntegrationHeader... headers) {
        for (IntegrationHeader header : headers) {
            request.header(header.getKey(), header.getValue());
        }
    }

    protected Response execute(BaseRequest request) {
        try {
            HttpResponse<String> response = request.asString();
            checkGenericErrorToThrowGenericException(response);
            return new Response(response.getStatus(), response.getBody());
        } catch (UnirestException e) {
            throw getCouldNotConnectException();
        }
    }

    private HttpServerException getCouldNotConnectException() {
        return new HttpServerException(500, "Serviço indisponível no momento. Estamos trabalhando para restabelecer a conexão com a sua integração.");
    }

    private void checkGenericErrorToThrowGenericException(HttpResponse<String> response) {
        int statusCode = response.getStatus();
        if (statusCode >= 400 && statusCode != 404) {
            String message = response.getBody();
            String details = null;
            Map<String, Object> resources = null;
            try {
                ErrorDTO errorDTO = Mapper.get().readValue(response.getBody(), ErrorDTO.class);
                message = errorDTO.getMessage();
                details = errorDTO.getDetails();
                resources = errorDTO.getResources();
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
            if (statusCode == 500) {
                throw new HttpServerException(statusCode, message, details);
            } else if (statusCode == 502 || statusCode == 503 || statusCode == 505) {
                throw getCouldNotConnectException();
            } else if (statusCode == 401 || statusCode == 403) {
                throw new UnauthorizedException(message);
            }
            throw new HttpClientException(message, details, resources);
        }

    }

    public RequestBodyEntity patch(String url, Object body, IntegrationHeader... headers) {
        return Unirest.patch(url)
                .headers(toHeaderMap(headers))
                .body(writeValueAsJson(body));
    }

    protected Map<String, String> toHeaderMap(IntegrationHeader... headers) {
        Map<String, String> map = new HashMap<>();
        if (headers == null) return map;

        for (IntegrationHeader h : headers) {
            if (h != null && h.getKey() != null && h.getValue() != null) {
                map.put(h.getKey(), h.getValue());
            }
        }
        return map;
    }

}
