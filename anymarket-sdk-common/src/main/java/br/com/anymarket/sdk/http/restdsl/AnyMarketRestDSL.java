package br.com.anymarket.sdk.http.restdsl;

import com.mashape.unirest.http.Unirest;

public class AnyMarketRestDSL {

    private AnyMarketRestDSL() {
    }

    public static RestGetRequest get(String url) {
        return new RestGetRequest(Unirest.get(url));
    }

    public static RestRequestWithBody post(String url) {
        return new RestRequestWithBody(Unirest.post(url));
    }

    public static RestRequestWithBody put(String url) {
        return new RestRequestWithBody(Unirest.put(url));
    }

    public static RestRequestWithBody patch(String url) {
        return new RestRequestWithBody(Unirest.patch(url));
    }
}
