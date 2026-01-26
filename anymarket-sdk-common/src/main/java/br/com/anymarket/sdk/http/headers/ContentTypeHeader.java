package br.com.anymarket.sdk.http.headers;

public class ContentTypeHeader implements IntegrationHeader {

    private final String value;

    public ContentTypeHeader(String value) {
        this.value = value;
    }

    @Override
    public String getKey() {
        return "Content-Type";
    }

    @Override
    public String getValue() {
        return value;
    }
}
