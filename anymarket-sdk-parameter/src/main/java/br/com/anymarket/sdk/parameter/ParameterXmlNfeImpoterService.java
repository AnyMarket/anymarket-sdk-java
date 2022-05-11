package br.com.anymarket.sdk.parameter;

import br.com.anymarket.sdk.SDKConstants;
import br.com.anymarket.sdk.http.headers.IntegrationHeader;
import br.com.anymarket.sdk.parameter.dto.ParameterXmlNfeImporter;

import static br.com.anymarket.sdk.http.headers.AnymarketHeaderUtils.addModuleOriginHeader;
import static br.com.anymarket.sdk.http.restdsl.AnyMarketRestDSL.get;
import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Strings.isNullOrEmpty;

public class ParameterXmlNfeImpoterService {

    private String apiEndpoint;
    private String moduleOrigin;

    public ParameterXmlNfeImpoterService(String apiEndPoint) {
        this.apiEndpoint = !isNullOrEmpty(apiEndPoint) ? apiEndPoint :
                SDKConstants.ANYMARKET_HOMOLOG_API_ENDPOINT;
    }

    public ParameterXmlNfeImpoterService(String apiEndPoint, String origin) {
        this.apiEndpoint = !isNullOrEmpty(apiEndPoint) ? apiEndPoint :
                SDKConstants.ANYMARKET_HOMOLOG_API_ENDPOINT;
        this.moduleOrigin = origin;
    }

    public ParameterXmlNfeImporter getParameter(String oi, IntegrationHeader... headers) {
            checkNotNull(oi, "Erro ao recuperar o parâmetro do xml NFe: OI não informado");
        return get(apiEndpoint.concat("/parameterXmlNfeImporter/{oi}"))
                .headers(addModuleOriginHeader(headers, this.moduleOrigin))
                .routeParam("oi", oi)
                .getResponse()
                .to(ParameterXmlNfeImporter.class);
    }
}
