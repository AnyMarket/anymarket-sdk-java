package br.com.anymarket.sdk.order;

import br.com.anymarket.sdk.MarketPlace;
import br.com.anymarket.sdk.SDKConstants;
import br.com.anymarket.sdk.exception.HttpClientException;
import br.com.anymarket.sdk.exception.NotFoundException;
import br.com.anymarket.sdk.http.headers.IntegrationHeader;
import br.com.anymarket.sdk.order.dto.Order;
import br.com.anymarket.sdk.order.dto.OrderStatus;
import br.com.anymarket.sdk.order.dto.OrderTransmissionStatusResource;
import br.com.anymarket.sdk.order.filters.*;
import br.com.anymarket.sdk.paging.Page;
import br.com.anymarket.sdk.resource.Link;
import com.fasterxml.jackson.core.type.TypeReference;
import com.google.common.collect.Lists;

import java.util.List;
import java.util.Optional;

import static br.com.anymarket.sdk.http.headers.AnymarketHeaderUtils.addModuleOriginHeader;
import static br.com.anymarket.sdk.http.restdsl.AnyMarketRestDSL.*;
import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Strings.isNullOrEmpty;
import static java.lang.String.format;

public class OrderService {

    public static final TypeReference<Page<Order>> PAGED_TYPE_REFERENCE = new TypeReference<Page<Order>>() {
    };
    public static final String NEXT_PAGE = "next";
    private String apiEndPointForResource;
    private String moduleOrigin;

    public OrderService(String apiEndPoint) {
        this.apiEndPointForResource = !isNullOrEmpty(apiEndPoint) ? apiEndPoint :
            SDKConstants.ANYMARKET_HOMOLOG_API_ENDPOINT;
    }

    public OrderService(String apiEndPoint, String origin) {
        this.apiEndPointForResource = !isNullOrEmpty(apiEndPoint) ? apiEndPoint :
                SDKConstants.ANYMARKET_HOMOLOG_API_ENDPOINT;
        this.moduleOrigin = origin;
    }

    public Order getOrder(Long idOrder, IntegrationHeader... headers) {
        checkNotNull(idOrder, "Erro ao recuperar pedido: Id não informado");
        return get(apiEndPointForResource.concat("/orders/{id}"))
            .headers(addModuleOriginHeader(headers, this.moduleOrigin))
            .routeParam("id", idOrder.toString())
            .getResponse()
            .to(Order.class);
    }

    public OrderStatus getOrderStatusByMarketplaceId(String marketplaceId, MarketPlace marketplace, IntegrationHeader... headers) {
        checkNotNull(marketplaceId, "Erro ao recuperar pedido: Id não informado");
        return get(apiEndPointForResource.concat("/orders/{marketplace}/{id}/status"))
                .headers(addModuleOriginHeader(headers, this.moduleOrigin))
                .routeParam("marketplace", marketplace.name())
                .routeParam("id", marketplaceId)
                .getResponse()
                .to(OrderStatus.class);
    }

    public Order getOrderByPartnerID(String partnerId, IntegrationHeader... headers) {
        checkNotNull(partnerId, "Erro ao recuperar pedido: partnerId não informado");

        OrderPartnerIdFilter partnerIdFilter = new OrderPartnerIdFilter(partnerId);

        Page<Order> ordersPage = getOrders(Lists.<OrderFilter>newArrayList(partnerIdFilter), headers);
        if (ordersPage.getContent() == null || ordersPage.getContent().isEmpty()) {
            throw new NotFoundException(format("Não foi encontrado pedido com partnerId %s", partnerId));
        }

        if (ordersPage.getContent().size() > 1) {
            throw new HttpClientException(format("Foi encontrado mais de um pedido com partnerId %s", partnerId));
        }

        return ordersPage.getContent().get(0);
    }

    public Order getOrderByNumberInMarketPlace(String numberInMarketPlace, IntegrationHeader... headers) {
        checkNotNull(numberInMarketPlace, "Erro ao recuperar pedido: código do Marketplace não informado");

        OrderNumberInMarketPlaceFilter numberInMarketPlaceFilter = new OrderNumberInMarketPlaceFilter(numberInMarketPlace);

        Page<Order> ordersPage = getOrders(Lists.<OrderFilter>newArrayList(numberInMarketPlaceFilter), headers);
        if (ordersPage.getContent() == null || ordersPage.getContent().isEmpty()) {
            throw new NotFoundException(format("Não foi encontrado pedido com código do Marketplace %s", numberInMarketPlace));
        }

        if (ordersPage.getContent().size() > 1) {
            throw new HttpClientException(format("Foi encontrado mais de um pedido com código do Marketplace %s", numberInMarketPlace));
        }

        return ordersPage.getContent().get(0);
    }

    @Deprecated
    public Order getOrderByIdInMarketplace(String idInMarketplace, MarketPlace marketplace, IntegrationHeader... headers) {
        checkNotNull(idInMarketplace, "Erro ao recuperar pedido: idInMarketplace não informado");
        checkNotNull(idInMarketplace, "Erro ao recuperar pedido: marketplace não informado");

        OrderMarketplaceFilter marketplaceFilter = new OrderMarketplaceFilter(marketplace);
        OrderMarketplaceIdFilter marketplaceIdFilter = new OrderMarketplaceIdFilter(idInMarketplace);

        Page<Order> ordersPage = getOrders(Lists.newArrayList(marketplaceFilter, marketplaceIdFilter), headers);
        if (ordersPage.getContent() == null || ordersPage.getContent().isEmpty()) {
            throw new NotFoundException(format("Não foi encontrado pedido com idInMarketplace %s e marketplace %s", idInMarketplace, marketplace.getDescription()));
        }

        if (ordersPage.getContent().size() > 1) {
            throw new HttpClientException(format("Foi encontrado mais de um pedido com idInMarketplace %s e marketplace %s", idInMarketplace, marketplace.getDescription()));
        }

        return ordersPage.getContent().get(0);
    }

    public Order getOrderBy(String idInMarketplace, MarketPlace marketplace, IntegrationHeader... headers) {
        checkNotNull(idInMarketplace, "Erro ao recuperar pedido: idInMarketplace não informado");
        checkNotNull(idInMarketplace, "Erro ao recuperar pedido: marketplace não informado");

        Order order = get(apiEndPointForResource.concat("/orders/{marketPlace}/{idInMarketPlace}"))
                .headers(addModuleOriginHeader(headers, this.moduleOrigin))
                .routeParam("marketPlace", marketplace.toString())
                .routeParam("idInMarketPlace", idInMarketplace)
                .getResponse()
                .to(Order.class);

        if (order == null) {
            throw new NotFoundException(format("Não foi encontrado pedido com idInMarketplace %s e marketplace %s", idInMarketplace, marketplace.getDescription()));
        }

        return order;
    }

    public Page<Order> getOrders(List<OrderFilter> filters, IntegrationHeader... headers) {
        return get(apiEndPointForResource.concat("/orders"))
            .headers(addModuleOriginHeader(headers, this.moduleOrigin))
            .filters(filters)
            .getResponse()
            .to(PAGED_TYPE_REFERENCE);
    }

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
                .headers(addModuleOriginHeader(headers, this.moduleOrigin))
                .getResponse()
                .to(PAGED_TYPE_REFERENCE);
        }
        return new Page<Order>();
    }

    public Order createOrder(Order order, IntegrationHeader... headers) {
        checkNotNull(order, "Erro ao criar pedido: Dados não encontrados.");

        order.setProductGross(null);
        return post(apiEndPointForResource.concat("/orders"))
            .body(order)
            .headers(addModuleOriginHeader(headers, this.moduleOrigin))
            .getResponse()
            .to(Order.class);
    }

    public Order updateOrder(Order order, IntegrationHeader... headers) {
        checkNotNull(order, "Erro ao atualizar pedido: Dados não encontrados.");
        checkNotNull(order.getId(), "Erro ao atualizar pedido: Id não informado");
        return put(apiEndPointForResource.concat("/orders/{id}"))
            .body(order)
            .headers(addModuleOriginHeader(headers, this.moduleOrigin))
            .routeParam("id", order.getId().toString())
            .getResponse()
            .to(Order.class);
    }

    public Order updateOrder(Order order,  final String origin, IntegrationHeader... headers) {
        checkNotNull(order, "Erro ao atualizar pedido: Dados não encontrados.");
        checkNotNull(order.getId(), "Erro ao atualizar pedido: Id não informado");
        return put(apiEndPointForResource.concat("/orders/{id}"))
            .body(order)
            .headers(addModuleOriginHeader(headers, this.moduleOrigin))
            .routeParam("id", order.getId().toString())
            .queryString("origin", origin)
            .getResponse()
            .to(Order.class);
    }

    public void updatePartnerIdOrder(Order order, IntegrationHeader... headers) {
        checkNotNull(order, "Erro ao atualizar pedido: Dados não encontrados.");
        checkNotNull(order.getId(), "Erro ao atualizar pedido: Id não informado");
        put(apiEndPointForResource.concat("/orders/partnerid/{id}"))
            .body(order)
            .headers(addModuleOriginHeader(headers, this.moduleOrigin))
            .routeParam("id", order.getId().toString())
            .getResponse();
    }

    public void putXMLNFe(Order order, String xml, IntegrationHeader... headers) {
        checkNotNull(order, "Erro ao atualizar pedido: Dados não encontrados.");
        checkNotNull(order.getId(), "Erro ao atualizar pedido: Id não informado");
        put(apiEndPointForResource.concat("/orders/{id}/nfe"))
            .body(xml)
            .headers(addModuleOriginHeader(headers, this.moduleOrigin))
            .routeParam("id", order.getId().toString())
            .getResponseXML();
    }

    public Order updateTransmissionStatus(Long idOrder, OrderTransmissionStatusResource resource, IntegrationHeader... headers) {
        checkNotNull(idOrder, "Erro ao atualizar pedido: Id não informado");
        checkNotNull(resource, "Erro ao atualizar pedido: Dados de TransmissionStatus não encontrados.");
        return put(apiEndPointForResource.concat("/orders/{id}/transmissionStatus"))
            .body(resource)
            .headers(addModuleOriginHeader(headers, this.moduleOrigin))
            .routeParam("id", idOrder.toString())
            .getResponse()
            .to(Order.class);
    }
}
