package br.com.anymarket.sdk.delegator;

import br.com.anymarket.sdk.MarketPlace;
import br.com.anymarket.sdk.exception.HttpClientException;
import br.com.anymarket.sdk.http.Response;
import com.mashape.unirest.request.BaseRequest;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class DelegatorServiceTest {

    private static final String API = "http://core.test/v2";

    private static class CapturingDelegatorService extends DelegatorService {

        private final int status;
        private String url;
        private String method;

        CapturingDelegatorService(int status) {
            super(API);
            this.status = status;
        }

        @Override
        protected Response execute(BaseRequest request) {
            url = request.getHttpRequest().getUrl();
            method = request.getHttpRequest().getHttpMethod().name();
            return new Response(status, "");
        }
    }

    @Test
    public void should_send_the_account_as_query_param_when_syncing_markup_by_account() {
        CapturingDelegatorService service = new CapturingDelegatorService(200);

        service.addToForceSyncMarkupForMarketplace(MarketPlace.GFG, 221L);

        assertEquals("http://core.test/v2/delegates/forceSyncMarkup/GFG?idAccount=221", service.url);
        assertEquals("PUT", service.method);
    }

    @Test
    public void should_not_send_the_param_when_syncing_markup_without_account() {
        CapturingDelegatorService service = new CapturingDelegatorService(200);

        service.addToForceSyncMarkupForMarketplace(MarketPlace.GFG);

        assertEquals("http://core.test/v2/delegates/forceSyncMarkup/GFG", service.url);
    }

    @Test
    public void should_throw_when_the_core_answers_not_found() {
        CapturingDelegatorService service = new CapturingDelegatorService(404);

        try {
            service.addToForceSyncMarkupForMarketplace(MarketPlace.GFG, 221L);
            fail("esperava HttpClientException");
        } catch (HttpClientException e) {
            assertTrue(e.getMessage().contains("/forceSyncMarkup/GFG"));
        }
    }
}
