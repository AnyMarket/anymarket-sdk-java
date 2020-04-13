package br.com.anymarket.sdk.order;

import br.com.anymarket.sdk.SDKConstants;
import br.com.anymarket.sdk.http.headers.IntegrationHeader;
import br.com.anymarket.sdk.order.dto.NewOrder;
import br.com.anymarket.sdk.order.dto.OrderTransmissionStatusResource;
import br.com.anymarket.sdk.order.filters.OrderFilter;
import br.com.anymarket.sdk.paging.Page;
import br.com.anymarket.sdk.resource.Link;
import com.fasterxml.jackson.core.type.TypeReference;

import java.util.List;

import static br.com.anymarket.sdk.http.restdsl.AnyMarketRestDSL.*;
import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Strings.isNullOrEmpty;

public class NewOrderService extends OrderServiceAbstract<NewOrder> {

    public static final TypeReference<Page<NewOrder>> PAGED_TYPE_REFERENCE = new TypeReference<Page<NewOrder>>() {};
    public static final String NEXT_PAGE = "next";
    private String apiEndPointForResource;

    public NewOrderService(String apiEndPoint) {
        this.apiEndPointForResource = !isNullOrEmpty(apiEndPoint) ? apiEndPoint :
                SDKConstants.ANYMARKET_HOMOLOG_API_ENDPOINT;
    }

    @Override
    public NewOrder doGetOrder(Long idOrder, IntegrationHeader... headers) {
        return get(apiEndPointForResource.concat("/orders/{id}"))
            .headers(headers)
            .routeParam("id", idOrder.toString())
            .getResponse()
            .to(NewOrder.class);
    }

    @Override
    public Page<NewOrder> getOrders(List<OrderFilter> filters, IntegrationHeader... headers) {
        return get(apiEndPointForResource.concat("/orders"))
            .headers(headers)
            .filters(filters)
            .getResponse()
            .to(PAGED_TYPE_REFERENCE);
    }

    @Override
    public Page<NewOrder> getNextPage(Page<NewOrder> actualPagedOrders, IntegrationHeader... headers) {
        String nextPageUrl = null;
        for (Link link : actualPagedOrders.getLinks()) {
            if (link.getRel().equals(NEXT_PAGE)) {
                nextPageUrl = link.getHref();
                break;
            }
        }
        if (nextPageUrl != null) {
            return get(nextPageUrl)
                .headers(headers)
                .getResponse()
                .to(PAGED_TYPE_REFERENCE);
        }
        return new Page<NewOrder>();
    }

    @Override
    public NewOrder doCreateOrder(NewOrder order, IntegrationHeader... headers) {
        order.setProductGross(null);
        return post(apiEndPointForResource.concat("/orders"))
            .body(order)
            .headers(headers)
            .getResponse()
            .to(NewOrder.class);
    }

    @Override
    public NewOrder doUpdateOrder(NewOrder order, IntegrationHeader... headers) {
        checkNotNull(order.getId(), "Erro ao atualizar pedido: Id não informado");
        return put(apiEndPointForResource.concat("/orders/{id}"))
            .body(order)
            .headers(headers)
            .routeParam("id", order.getId().toString())
            .getResponse()
            .to(NewOrder.class);
    }

    @Override
    public NewOrder doUpdateOrder(NewOrder order,  final String origin, IntegrationHeader... headers) {
        checkNotNull(order.getId(), "Erro ao atualizar pedido: Id não informado");
        return put(apiEndPointForResource.concat("/orders/{id}"))
            .body(order)
            .headers(headers)
            .routeParam("id", order.getId().toString())
            .queryString("origin", origin)
            .getResponse()
            .to(NewOrder.class);
    }

    @Override
    public void doUpdatePartnerIdOrder(NewOrder order, IntegrationHeader... headers) {
        checkNotNull(order.getId(), "Erro ao atualizar pedido: Id não informado");
        put(apiEndPointForResource.concat("/orders/partnerid/{id}"))
            .body(order)
            .headers(headers)
            .routeParam("id", order.getId().toString())
            .getResponse();
    }

    @Override
    public void doPutXMLNFe(NewOrder order, String xml, IntegrationHeader... headers) {
        checkNotNull(order.getId(), "Erro ao atualizar pedido: Id não informado");
        put(apiEndPointForResource.concat("/orders/{id}/nfe"))
            .body(xml)
            .headers(headers)
            .routeParam("id", order.getId().toString())
            .getResponseXML();
    }

    public NewOrder doUpdateTransmissionStatus(Long idOrder, OrderTransmissionStatusResource resource, IntegrationHeader... headers) {
        return put(apiEndPointForResource.concat("/orders/{id}/transmissionStatus"))
            .body(resource)
            .headers(headers)
            .routeParam("id", idOrder.toString())
            .getResponse()
            .to(NewOrder.class);
    }

    public NewOrder doUpdateShipmentTransmissionStatus(Long idOrder, Long shipmentIndex,
                                                  OrderTransmissionStatusResource resource,
                                                  IntegrationHeader... headers) {
        return put(apiEndPointForResource.concat("/orders/{orderId}/shipments/{shipmentIndex}/transmissionStatus"))
                .body(resource)
                .headers(headers)
                .routeParam("orderId", idOrder.toString())
                .routeParam("shipmentIndex", shipmentIndex.toString())
                .getResponse()
                .to(NewOrder.class);
    }

}
