package br.com.anymarket.sdk.parameter;

import br.com.anymarket.sdk.SDKConstants;
import br.com.anymarket.sdk.http.headers.IntegrationHeader;
import br.com.anymarket.sdk.parameter.dto.ParameterValue;

import static br.com.anymarket.sdk.http.headers.AnymarketHeaderUtils.addModuleOriginHeader;
import static br.com.anymarket.sdk.http.restdsl.AnyMarketRestDSL.get;
import static com.google.common.base.Strings.isNullOrEmpty;

public class ParameterValueService {
    private String apiEndpoint;
    private String moduleOrigin;

    public ParameterValueService(String apiEndPoint) {
        this.apiEndpoint = !isNullOrEmpty(apiEndPoint) ? apiEndPoint :
                SDKConstants.ANYMARKET_HOMOLOG_API_ENDPOINT;
    }

    public ParameterValueService(String apiEndPoint, String origin) {
        this.apiEndpoint = !isNullOrEmpty(apiEndPoint) ? apiEndPoint :
                SDKConstants.ANYMARKET_HOMOLOG_API_ENDPOINT;
        this.moduleOrigin = origin;
    }

    public ParameterValue getParameter(String parameterName, IntegrationHeader... headers) {
        return get(apiEndpoint.concat("/parameters/{parameterName}"))
                .headers(addModuleOriginHeader(headers, this.moduleOrigin))
                .routeParam("parameterName", parameterName)
                .getResponse()
                .to(ParameterValue.class);
    }
}
