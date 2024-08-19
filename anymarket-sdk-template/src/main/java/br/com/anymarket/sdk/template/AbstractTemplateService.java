package br.com.anymarket.sdk.template;

import br.com.anymarket.sdk.MarketPlace;
import br.com.anymarket.sdk.SDKConstants;
import br.com.anymarket.sdk.exception.HttpClientException;
import br.com.anymarket.sdk.exception.NotFoundException;
import br.com.anymarket.sdk.http.HttpService;
import br.com.anymarket.sdk.http.Response;
import br.com.anymarket.sdk.http.headers.IntegrationHeader;
import br.com.anymarket.sdk.template.dto.GenericTemplate;
import br.com.anymarket.sdk.util.SDKUrlEncoder;
import com.fasterxml.jackson.core.type.TypeReference;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.mashape.unirest.request.GetRequest;
import org.apache.http.HttpStatus;

import java.util.List;

import static br.com.anymarket.sdk.http.headers.AnymarketHeaderUtils.addModuleOriginHeader;
import static java.lang.String.format;

public abstract class AbstractTemplateService<T extends GenericTemplate> extends HttpService {

    private final String templateUri;
    public static final String SEPARATOR = "/";
    private final String apiEndPoint;
    private String moduleOrigin;

    public AbstractTemplateService(String templateUri, String apiEndPoint) {
        this.templateUri = templateUri;
        this.apiEndPoint = !Strings.isNullOrEmpty(apiEndPoint) ? apiEndPoint :
            SDKConstants.ANYMARKET_HOMOLOG_API_ENDPOINT;
    }

    public AbstractTemplateService(String templateUri, String apiEndPoint, String origin) {
        this.templateUri = templateUri;
        this.apiEndPoint = !Strings.isNullOrEmpty(apiEndPoint) ? apiEndPoint :
                SDKConstants.ANYMARKET_HOMOLOG_API_ENDPOINT;
        this.moduleOrigin = origin;
    }

    protected abstract Class<T> templateClazz();

    public T getTemplate(Long id, IntegrationHeader... headers) {
        GetRequest getRequest = get(apiEndPoint + templateUri + SEPARATOR + id, addModuleOriginHeader(headers, this.moduleOrigin));
        Response response = execute(getRequest);
        if (response.getStatus() == HttpStatus.SC_OK) {
            return response.to(templateClazz());
        }
        throw new NotFoundException(format("Template with id %s not found.", id));
    }

    public List<T> getTemplatesByMarketPlace(final MarketPlace marketPlace, IntegrationHeader... headers) {
        return getTemplateList(apiEndPoint.concat(templateUri).concat("/list/")
            .concat(SDKUrlEncoder.encodeParameterToUTF8(marketPlace.name())),
            addModuleOriginHeader(headers, this.moduleOrigin));
    }

    public String renderTemplate(Long templateId, Long skuMpId, IntegrationHeader... headers) {
        GetRequest getRequest = get(apiEndPoint + templateUri + "/render/" + templateId + SEPARATOR + skuMpId, addModuleOriginHeader(headers, this.moduleOrigin));
        Response response = execute(getRequest);
        if (response.getStatus() == HttpStatus.SC_OK) {
            return response.getMessage();
        }
        throw new HttpClientException(format("Could not render template with id %d for sku id %d", templateId, skuMpId));
    }


    private List<T> getTemplateList(final String url, IntegrationHeader... headers) {
        final List<T> templates = Lists.newArrayList();
        final GetRequest getRequest = get(url, addModuleOriginHeader(headers, this.moduleOrigin));
        final Response response = execute(getRequest);
        if (response.getStatus() == HttpStatus.SC_OK) {
            List<T> rootResponse = response.to(new TypeReference<List<T>>() {
            });
            templates.addAll(rootResponse);
        } else {
            throw new NotFoundException("Template not found.");
        }
        return templates;
    }

}
