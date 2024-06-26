package br.com.anymarket.sdk.campaign;

import br.com.anymarket.sdk.SDKConstants;
import br.com.anymarket.sdk.campaign.dto.Campaign;
import br.com.anymarket.sdk.exception.NotFoundException;
import br.com.anymarket.sdk.http.HttpService;
import br.com.anymarket.sdk.http.Response;
import br.com.anymarket.sdk.http.headers.IntegrationHeader;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mashape.unirest.request.GetRequest;
import org.apache.http.HttpStatus;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Objects;

import static br.com.anymarket.sdk.http.headers.AnymarketHeaderUtils.addModuleOriginHeader;
import static com.google.common.base.Strings.isNullOrEmpty;
import static java.lang.String.format;

public class CampaignService extends HttpService {

    private static final String ACTIVE_CAMPAIGN_URI = "/campaigns/active/%s/%s";
    private static final String ERROR_CAMPAIGN_NOT_FOUND_FOR_MARKETPLACE = "Campanha com o id %s não foi encontrada para o marketplace %s.";
    private static final String CAMPAIGN_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";

    private final String apiEndPoint;
    private String moduleOrigin;

    public CampaignService(final String apiEndPoint) {
        this.apiEndPoint = !isNullOrEmpty(apiEndPoint) ? apiEndPoint :
                SDKConstants.ANYMARKET_HOMOLOG_API_ENDPOINT;
    }

    public CampaignService(final String apiEndPoint, String origin) {
        this(apiEndPoint);
        this.moduleOrigin = origin;
    }

    public Campaign getActiveCampaignById(Long idCampaign, String marketplace, IntegrationHeader... headers) throws IOException {
        Objects.requireNonNull(idCampaign, "Informe o id da campanha.");
        Objects.requireNonNull(marketplace, "Informe o marketplace da campanha");

        GetRequest request = get(getURLFormatted(idCampaign, marketplace).concat("/"),
                addModuleOriginHeader(headers, this.moduleOrigin));

        Response response = execute(request);
        if (response.getStatus() == HttpStatus.SC_OK) {
            return mapToCampaign(response);
        }

        throw new NotFoundException(format(ERROR_CAMPAIGN_NOT_FOUND_FOR_MARKETPLACE,
                idCampaign, marketplace));
    }

    private String getURLFormatted(Long idCampaign, String marketplace) {
        return String.format(apiEndPoint.concat(ACTIVE_CAMPAIGN_URI), idCampaign, marketplace);
    }

    private Campaign mapToCampaign(Response response) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setDateFormat(new SimpleDateFormat(CAMPAIGN_DATE_FORMAT));

        return mapper.readValue(response.getMessage(), Campaign.class);
    }

}
