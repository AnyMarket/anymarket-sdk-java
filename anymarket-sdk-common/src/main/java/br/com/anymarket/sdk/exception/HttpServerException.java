package br.com.anymarket.sdk.exception;

/**
 * Created by marcio.scharam on 18/03/2016.
 */
public class HttpServerException extends RuntimeException {

    public HttpServerException(String message) {
        super(message);
    }
}
