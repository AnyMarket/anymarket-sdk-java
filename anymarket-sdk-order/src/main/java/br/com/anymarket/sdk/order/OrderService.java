package br.com.anymarket.sdk.order;

import br.com.anymarket.sdk.SDKConstants;
import br.com.anymarket.sdk.http.headers.IntegrationHeader;
import br.com.anymarket.sdk.order.dto.Order;
import br.com.anymarket.sdk.order.dto.OrderTransmissionStatusResource;
import br.com.anymarket.sdk.order.filters.OrderFilter;
import br.com.anymarket.sdk.paging.Page;
import br.com.anymarket.sdk.resource.Link;
import com.fasterxml.jackson.core.type.TypeReference;

import java.util.List;

import static br.com.anymarket.sdk.http.restdsl.AnyMarketRestDSL.*;
import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Strings.isNullOrEmpty;

public class OrderService extends OrderServiceAbstract<Order> {

    public static final TypeReference<Page<Order>> PAGED_TYPE_REFERENCE = new TypeReference<Page<Order>>() {
    };
    public static final String NEXT_PAGE = "next";
    private String apiEndPointForResource;

    public OrderService(String apiEndPoint) {
        this.apiEndPointForResource = !isNullOrEmpty(apiEndPoint) ? apiEndPoint :
            SDKConstants.ANYMARKET_HOMOLOG_API_ENDPOINT;
    }

    @Override
    public Order doGetOrder(Long idOrder, IntegrationHeader... headers) {
        return get(apiEndPointForResource.concat("/orders/{id}"))
                .headers(headers)
                .routeParam("id", idOrder.toString())
                .getResponse()
                .to(Order.class);
    }

    @Override
    public Page<Order> getOrders(List<OrderFilter> filters, IntegrationHeader... headers) {
        return get(apiEndPointForResource.concat("/orders"))
                .headers(headers)
                .filters(filters)
                .getResponse()
                .to(PAGED_TYPE_REFERENCE);
    }

    @Override
    public Page<Order> getNextPage(Page<Order> actualPagedOrders, IntegrationHeader... headers) {
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
        return new Page<Order>();
    }

    @Override
    public Order doCreateOrder(Order order, IntegrationHeader... headers) {
        order.setProductGross(null);
        return post(apiEndPointForResource.concat("/orders"))
                .body(order)
                .headers(headers)
                .getResponse()
                .to(Order.class);
    }

    @Override
    public Order doUpdateOrder(Order order, IntegrationHeader... headers) {
        checkNotNull(order.getId(), "Erro ao atualizar pedido: Id não informado");
        return put(apiEndPointForResource.concat("/orders/{id}"))
                .body(order)
                .headers(headers)
                .routeParam("id", order.getId().toString())
                .getResponse()
                .to(Order.class);
    }

    @Override
    public Order doUpdateOrder(Order order,  final String origin, IntegrationHeader... headers) {
        checkNotNull(order.getId(), "Erro ao atualizar pedido: Id não informado");
        return put(apiEndPointForResource.concat("/orders/{id}"))
                .body(order)
                .headers(headers)
                .routeParam("id", order.getId().toString())
                .queryString("origin", origin)
                .getResponse()
                .to(Order.class);
    }

    @Override
    public void doUpdatePartnerIdOrder(Order order, IntegrationHeader... headers) {
        checkNotNull(order.getId(), "Erro ao atualizar pedido: Id não informado");
        put(apiEndPointForResource.concat("/orders/partnerid/{id}"))
                .body(order)
                .headers(headers)
                .routeParam("id", order.getId().toString())
                .getResponse();
    }

    @Override
    public void doPutXMLNFe(Order order, String xml, IntegrationHeader... headers) {
        checkNotNull(order.getId(), "Erro ao atualizar pedido: Id não informado");
        put(apiEndPointForResource.concat("/orders/{id}/nfe"))
                .body(xml)
                .headers(headers)
                .routeParam("id", order.getId().toString())
                .getResponseXML();
    }

    public Order doUpdateTransmissionStatus(Long idOrder, OrderTransmissionStatusResource resource, IntegrationHeader... headers) {
        return put(apiEndPointForResource.concat("/orders/{id}/transmissionStatus"))
                .body(resource)
                .headers(headers)
                .routeParam("id", idOrder.toString())
                .getResponse()
                .to(Order.class);
    }

    public Order doUpdateShipmentTransmissionStatus(Long idOrder, Long shipmentIndex,
                                                       OrderTransmissionStatusResource resource,
                                                       IntegrationHeader... headers) {
        return put(apiEndPointForResource.concat("/orders/{orderId}/shipments/{shipmentIndex}/transmissionStatus"))
                .body(resource)
                .headers(headers)
                .routeParam("orderId", idOrder.toString())
                .routeParam("shipmentIndex", shipmentIndex.toString())
                .getResponse()
                .to(Order.class);
    }

}
