package br.com.anymarket.sdk.delegator;

import br.com.anymarket.sdk.MarketPlace;
import br.com.anymarket.sdk.SDKConstants;
import br.com.anymarket.sdk.http.HttpService;
import br.com.anymarket.sdk.http.headers.AnymarketHeaderUtils;
import br.com.anymarket.sdk.http.headers.IntegrationHeader;
import com.google.common.base.Strings;
import com.mashape.unirest.request.body.RequestBodyEntity;

import java.util.Objects;

/**
 * Created by marcio.scharam on 08/06/2016.
 */
public class DelegatorService extends HttpService {

    private static final String DELEGATES_URI = "/delegates";
    private static final String FORCE_SYNC_MARKUP = "/forceSyncMarkup/";
    private final String apiEndPoint;
    private String moduleOrigin;

    public DelegatorService(String apiEndPoint) {
        this.apiEndPoint = !Strings.isNullOrEmpty(apiEndPoint) ? apiEndPoint :
                SDKConstants.ANYMARKET_HOMOLOG_API_ENDPOINT;
    }

    public DelegatorService(String apiEndPoint, String origin) {
        this.apiEndPoint = !Strings.isNullOrEmpty(apiEndPoint) ? apiEndPoint :
                SDKConstants.ANYMARKET_HOMOLOG_API_ENDPOINT;
        this.moduleOrigin = origin;
    }

    public void addToForceSyncMarkupForMarketplace(MarketPlace marketPlace, Long idAccount, IntegrationHeader... headers) {
        String url = apiEndPoint.concat(DELEGATES_URI).concat(FORCE_SYNC_MARKUP)
                .concat(marketPlace.toString());
        if (idAccount != null) {
            url = url.concat("/").concat(idAccount.toString());
        }
        RequestBodyEntity put = put(url, null, AnymarketHeaderUtils.addModuleOriginHeader(headers, marketPlace.name()));
        execute(put);
    }

    public void addToForceSyncMarkupForMarketplace(MarketPlace marketPlace, IntegrationHeader... headers) {
        addToForceSyncMarkupForMarketplace(marketPlace, null, headers);
    }
}
