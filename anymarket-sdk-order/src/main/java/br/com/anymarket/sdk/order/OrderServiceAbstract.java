package br.com.anymarket.sdk.order;

import br.com.anymarket.sdk.MarketPlace;
import br.com.anymarket.sdk.exception.HttpClientException;
import br.com.anymarket.sdk.exception.NotFoundException;
import br.com.anymarket.sdk.http.headers.IntegrationHeader;
import br.com.anymarket.sdk.order.dto.OrderTransmissionStatusResource;
import br.com.anymarket.sdk.order.filters.*;
import br.com.anymarket.sdk.paging.Page;
import com.google.common.collect.Lists;

import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;
import static java.lang.String.format;

public abstract class OrderServiceAbstract<T> {

    public T getOrder(Long idOrder, IntegrationHeader... headers) {
        checkNotNull(idOrder, "Erro ao recuperar pedido: Id não informado");
        return doGetOrder(idOrder, headers);
    }

    protected abstract T doGetOrder(Long idOrder, IntegrationHeader... headers);

    public T getOrderByPartnerID(String partnerId, IntegrationHeader... headers) {
        checkNotNull(partnerId, "Erro ao recuperar pedido: partnerId não informado");
        OrderPartnerIdFilter partnerIdFilter = new OrderPartnerIdFilter(partnerId);
        Page<T> ordersPage = getOrders(Lists.<OrderFilter>newArrayList(partnerIdFilter), headers);
        if (ordersPage.getContent() == null || ordersPage.getContent().isEmpty()) {
            throw new NotFoundException(format("Não foi encontrado pedido com partnerId %s", partnerId));
        }
        if (ordersPage.getContent().size() > 1) {
            throw new HttpClientException(format("Foi encontrado mais de um pedido com partnerId %s", partnerId));
        }
        return ordersPage.getContent().get(0);
    }

    public T getOrderByNumberInMarketPlace(String numberInMarketPlace, IntegrationHeader... headers) {
        checkNotNull(numberInMarketPlace, "Erro ao recuperar pedido: código do Marketplace não informado");
        OrderNumberInMarketPlaceFilter numberInMarketPlaceFilter = new OrderNumberInMarketPlaceFilter(numberInMarketPlace);

        Page<T> ordersPage = getOrders(Lists.<OrderFilter>newArrayList(numberInMarketPlaceFilter), headers);
        if (ordersPage.getContent() == null || ordersPage.getContent().isEmpty()) {
            throw new NotFoundException(format("Não foi encontrado pedido com código do Marketplace %s", numberInMarketPlace));
        }

        if (ordersPage.getContent().size() > 1) {
            throw new HttpClientException(format("Foi encontrado mais de um pedido com código do Marketplace %s", numberInMarketPlace));
        }
        return ordersPage.getContent().get(0);
    }

    public T getOrderByIdInMarketplace(String idInMarketplace, MarketPlace marketplace, IntegrationHeader... headers) {
        checkNotNull(idInMarketplace, "Erro ao recuperar pedido: idInMarketplace não informado");
        checkNotNull(marketplace, "Erro ao recuperar pedido: marketplace não informado");

        OrderMarketplaceFilter marketplaceFilter = new OrderMarketplaceFilter(marketplace);
        OrderMarketplaceIdFilter marketplaceIdFilter = new OrderMarketplaceIdFilter(idInMarketplace);

        Page<T> ordersPage = getOrders(Lists.newArrayList(marketplaceFilter, marketplaceIdFilter), headers);
        if (ordersPage.getContent() == null || ordersPage.getContent().isEmpty()) {
            throw new NotFoundException(format("Não foi encontrado pedido com idInMarketplace %s e marketplace %s",
                    idInMarketplace, marketplace.getDescription()));
        }

        if (ordersPage.getContent().size() > 1) {
            throw new HttpClientException(format("Foi encontrado mais de um pedido com idInMarketplace %s e marketplace %s",
                    idInMarketplace, marketplace.getDescription()));
        }

        return ordersPage.getContent().get(0);
    }

    protected abstract Page<T> getOrders(List<OrderFilter> filters, IntegrationHeader... headers);

    protected abstract Page<T> getNextPage(Page<T> actualPagedOrders, IntegrationHeader... headers);

    public T createOrder(T order, IntegrationHeader... headers) {
        checkNotNull(order, "Erro ao criar pedido: Dados não encontrados.");
        return doCreateOrder(order, headers);
    }

    protected abstract T doCreateOrder(T order, IntegrationHeader... headers);

    public T updateOrder(T order, IntegrationHeader... headers) {
        checkNotNull(order, "Erro ao atualizar pedido: Dados não encontrados.");
        return doUpdateOrder(order, headers);
    }

    protected abstract T doUpdateOrder(T order, IntegrationHeader... headers);

    public T updateOrder(T order, final String origin, IntegrationHeader... headers) {
        checkNotNull(order, "Erro ao atualizar pedido: Dados não encontrados.");
        return doUpdateOrder(order, origin, headers);
    }

    protected abstract T doUpdateOrder(T order, final String origin, IntegrationHeader... headers);


    public void updatePartnerIdOrder(T order, IntegrationHeader... headers) {
        checkNotNull(order, "Erro ao atualizar pedido: Dados não encontrados.");
        doUpdatePartnerIdOrder(order, headers);
    }

    protected abstract void doUpdatePartnerIdOrder(T order, IntegrationHeader... headers);

    public void putXMLNFe(T order, String xml, IntegrationHeader... headers) {
        checkNotNull(order, "Erro ao atualizar pedido: Dados não encontrados.");
        doPutXMLNFe(order, xml, headers);
    }

    protected abstract void doPutXMLNFe(T order, String xml, IntegrationHeader... headers);

    public T updateTransmissionStatus(Long idOrder, OrderTransmissionStatusResource resource, IntegrationHeader... headers) {
        checkNotNull(idOrder, "Erro ao atualizar pedido: Id não informado");
        checkNotNull(resource, "Erro ao atualizar pedido: Dados de TransmissionStatus não encontrados.");
        return doUpdateTransmissionStatus(idOrder, resource, headers);
    }

    protected abstract T doUpdateTransmissionStatus(Long idOrder, OrderTransmissionStatusResource resource, IntegrationHeader... headers);

    public T updateShipmentTransmissionStatus(Long idOrder, Long shipmentIndex, OrderTransmissionStatusResource resource,
                                              IntegrationHeader... headers) {
        checkNotNull(idOrder, "Erro ao atualizar pedido: Id não informado");
        checkNotNull(shipmentIndex, "Erro ao atualizar pedido: ShipmentIndex não informado");
        checkNotNull(resource, "Erro ao atualizar pedido: Dados de TransmissionStatus não encontrados.");
        return doUpdateShipmentTransmissionStatus(idOrder, shipmentIndex, resource, headers);
    }

    protected abstract T doUpdateShipmentTransmissionStatus(Long idOrder, Long shipmentIndex, OrderTransmissionStatusResource resource,
                                                            IntegrationHeader... headers);

}
