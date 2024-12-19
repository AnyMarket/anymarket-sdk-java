package br.com.anymarket.sdk.order;

import br.com.anymarket.sdk.SDKConstants;
import br.com.anymarket.sdk.http.headers.IntegrationHeader;
import br.com.anymarket.sdk.order.dto.OrderReturn;

import static br.com.anymarket.sdk.http.headers.AnymarketHeaderUtils.addModuleOriginHeader;
import static br.com.anymarket.sdk.http.restdsl.AnyMarketRestDSL.patch;
import static br.com.anymarket.sdk.http.restdsl.AnyMarketRestDSL.post;
import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Strings.isNullOrEmpty;

public class OrderReturnService {
    private final String apiEndPointForResource;
    private String moduleOrigin;

    public OrderReturnService(String apiEndPoint) {
        this.apiEndPointForResource = !isNullOrEmpty(apiEndPoint) ? apiEndPoint :
                SDKConstants.ANYMARKET_HOMOLOG_API_ENDPOINT;
    }

    public OrderReturnService(String apiEndPoint, String origin) {
        this.apiEndPointForResource = !isNullOrEmpty(apiEndPoint) ? apiEndPoint :
                SDKConstants.ANYMARKET_HOMOLOG_API_ENDPOINT;
        this.moduleOrigin = origin;
    }

    public OrderReturn saveOrderReturn(Long idOrder, OrderReturn orderReturn, IntegrationHeader... headers) {
        checkNotNull(idOrder, "Erro ao criar devolução: Id do pedido não informado");
        checkNotNull(orderReturn, "Erro ao criar devolução: Dados da devolução não encontrados");
        checkNotNull(orderReturn.getItems(), "Erro ao criar devolução: Dados dos itens devem ser informados");
        checkNotNull(orderReturn.getShipping(), "Erro ao criar devolução: Dados de shipping devem ser informados");
        return post(apiEndPointForResource.concat("/orders/{id}/returns"))
                .body(orderReturn)
                .headers(addModuleOriginHeader(headers, this.moduleOrigin))
                .routeParam("id", idOrder.toString())
                .getResponse()
                .to(OrderReturn.class);
    }

    public OrderReturn updateOrderReturn(Long idOrderReturn, OrderReturn orderReturn, IntegrationHeader... headers) {
        checkNotNull(idOrderReturn, "Erro ao atualizar devolução: Id da devolução não informado");
        checkNotNull(orderReturn, "Erro ao atualizar devolução: Dados da devolução não informado");
        return patch(apiEndPointForResource.concat("/orders/returns/{returnId}"))
                .body(orderReturn)
                .headers(addModuleOriginHeader(headers, this.moduleOrigin))
                .routeParam("returnId", idOrderReturn.toString())
                .getResponse()
                .to(OrderReturn.class);
    }
}
