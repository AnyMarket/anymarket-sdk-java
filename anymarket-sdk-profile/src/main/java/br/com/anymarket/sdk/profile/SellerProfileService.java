package br.com.anymarket.sdk.profile;

import br.com.anymarket.sdk.SDKConstants;
import br.com.anymarket.sdk.exception.NotFoundException;
import br.com.anymarket.sdk.http.HttpService;
import br.com.anymarket.sdk.http.Response;
import br.com.anymarket.sdk.http.headers.IntegrationHeader;
import br.com.anymarket.sdk.product.SkuMarketPlaceService;
import br.com.anymarket.sdk.profile.dto.Seller;
import com.google.common.base.Strings;
import com.mashape.unirest.request.GetRequest;
import org.apache.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SellerProfileService extends HttpService {

    private static final Logger LOG = LoggerFactory.getLogger(SkuMarketPlaceService.class);

    private static final String SELLER_PROFILE_URI = "/seller/profile";
    private final String apiEndPoint;

    public SellerProfileService(String apiEndPoint) {
        this.apiEndPoint = !Strings.isNullOrEmpty(apiEndPoint) ? apiEndPoint :
                SDKConstants.ANYMARKET_HOMOLOG_API_ENDPOINT;
    }

    public Seller getProfile(IntegrationHeader... headers) {
        GetRequest getRequest = get(apiEndPoint.concat(SELLER_PROFILE_URI), headers);
        Response response = execute(getRequest);
        if (response.getStatus() == HttpStatus.SC_OK) {
            return response.to(Seller.class);
        }
        throw new NotFoundException("Profile not found");
    }
}
