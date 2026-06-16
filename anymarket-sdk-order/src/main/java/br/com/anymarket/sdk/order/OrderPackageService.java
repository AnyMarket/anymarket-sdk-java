package br.com.anymarket.sdk.order;

import br.com.anymarket.sdk.SDKConstants;
import br.com.anymarket.sdk.http.HttpService;
import br.com.anymarket.sdk.http.Mapper;
import br.com.anymarket.sdk.http.headers.AnymarketHeaderUtils;
import br.com.anymarket.sdk.http.headers.IntegrationHeader;
import br.com.anymarket.sdk.http.restdsl.AnyMarketRestDSL;
import br.com.anymarket.sdk.order.dto.ItemsPackage;
import br.com.anymarket.sdk.order.dto.Order;
import br.com.anymarket.sdk.order.dto.TrackingResource;
import br.com.anymarket.sdk.order.dto.packages.OrderPackage;
import br.com.anymarket.sdk.order.dto.packages.OrderPackageDeliverRequest;
import br.com.anymarket.sdk.order.dto.packages.OrderPackageInput;
import br.com.anymarket.sdk.order.dto.packages.OrderPackageInvoiceResource;
import br.com.anymarket.sdk.order.dto.packages.OrderPackageRequest;
import br.com.anymarket.sdk.order.dto.packages.OrderPackageSendRequest;
import br.com.anymarket.sdk.order.dto.packages.OrderPackageUnit;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.request.HttpRequestWithBody;
import com.mashape.unirest.request.body.MultipartBody;
import org.apache.http.entity.ContentType;

import java.util.List;
import java.util.Map;

public class OrderPackageService extends HttpService {

    private static final TypeReference<List<OrderPackageUnit>> LIST_TYPE_REFERENCE = new TypeReference<List<OrderPackageUnit>>() {
    };

    private static final String PARAM_ORDER_ID = "orderId";
    private static final String PARAM_PACKAGE_ID = "packageId";
    private static final String FIELD_FILE = "file";

    private final String apiEndPoint;
    private String moduleOrigin;

    public OrderPackageService(String apiEndPoint) {
        this.apiEndPoint = !Strings.isNullOrEmpty(apiEndPoint) ? apiEndPoint : SDKConstants.ANYMARKET_HOMOLOG_API_ENDPOINT;
    }

    public OrderPackageService(String apiEndPoint, String origin) {
        this.apiEndPoint = !Strings.isNullOrEmpty(apiEndPoint) ? apiEndPoint : SDKConstants.ANYMARKET_HOMOLOG_API_ENDPOINT;
        this.moduleOrigin = origin;
    }

    public OrderPackage createPackages(Long orderId, OrderPackageRequest packageRequest, IntegrationHeader... headers) {
        Preconditions.checkNotNull(orderId, "Erro ao criar pacote: orderId não informado");
        Preconditions.checkNotNull(packageRequest, "Erro ao criar pacote: packageRequest é obrigatório");
        validatePackages(packageRequest);
        return AnyMarketRestDSL.post(apiEndPoint.concat("/orders/{orderId}/packages"))
                .body(packageRequest)
                .headers(AnymarketHeaderUtils.addModuleOriginHeader(headers, this.moduleOrigin))
                .routeParam(PARAM_ORDER_ID, orderId.toString())
                .getResponse()
                .to(OrderPackage.class);
    }

    public List<OrderPackageUnit> getPackages(Long orderId, IntegrationHeader... headers) {
        Preconditions.checkNotNull(orderId, "Erro ao consultar pacotes: orderId não informado");
        return AnyMarketRestDSL.get(apiEndPoint.concat("/orders/{orderId}/packages"))
                .headers(AnymarketHeaderUtils.addModuleOriginHeader(headers, this.moduleOrigin))
                .routeParam(PARAM_ORDER_ID, orderId.toString())
                .getResponse()
                .to(LIST_TYPE_REFERENCE);
    }

    public void invoicePackage(Long orderId, byte[] xmlBytes, OrderPackageInvoiceResource invoiceResource, IntegrationHeader... headers) {
        Preconditions.checkNotNull(orderId, "Erro ao faturar pacote: orderId não informado");
        Preconditions.checkNotNull(invoiceResource, "Erro ao faturar pacote: invoiceResource é obrigatório");
        execute(prepareInvoice(orderId, xmlBytes, invoiceResource, headers));
    }

    public Order sendPackage(Long orderId, String packageId, TrackingResource trackingResource, IntegrationHeader... headers) {
        Preconditions.checkNotNull(orderId, "Erro ao enviar pacote: orderId não informado");
        Preconditions.checkNotNull(packageId, "Erro ao enviar pacote: packageId não informado");
        Preconditions.checkNotNull(trackingResource, "Erro ao enviar pacote: trackingResource é obrigatório");
        OrderPackageSendRequest sendRequest = new OrderPackageSendRequest(trackingResource);
        return AnyMarketRestDSL.put(apiEndPoint.concat("/orders/{orderId}"))
                .body(sendRequest)
                .headers(AnymarketHeaderUtils.addModuleOriginHeader(headers, this.moduleOrigin))
                .routeParam(PARAM_ORDER_ID, orderId.toString())
                .queryString(PARAM_PACKAGE_ID, packageId)
                .getResponse()
                .to(Order.class);
    }

    public void deliverPackage(Long orderId, OrderPackageDeliverRequest deliverRequest, IntegrationHeader... headers) {
        Preconditions.checkNotNull(orderId, "Erro ao entregar pacote: orderId não informado");
        Preconditions.checkNotNull(deliverRequest, "Erro ao entregar pacote: deliverRequest é obrigatório");
        AnyMarketRestDSL.put(apiEndPoint.concat("/orders/{orderId}/packages/delivered"))
                .body(deliverRequest)
                .headers(AnymarketHeaderUtils.addModuleOriginHeader(headers, this.moduleOrigin))
                .routeParam(PARAM_ORDER_ID, orderId.toString())
                .getResponse();
    }

    private void validatePackages(OrderPackageRequest request) {
        Preconditions.checkNotNull(request.getPackages(), "Erro ao criar pacote: packages é obrigatório");
        for (OrderPackageInput packageInput : request.getPackages()) {
            Preconditions.checkNotNull(packageInput.getItems(), "Erro ao criar pacote: packages.items é obrigatório");
            for (ItemsPackage item : packageInput.getItems()) {
                Preconditions.checkArgument(!Strings.isNullOrEmpty(item.getSku()), "Erro ao criar pacote: packages.items.sku é obrigatório");
                Preconditions.checkNotNull(item.getQuantity(), "Erro ao criar pacote: packages.items.quantity é obrigatório");
            }
        }
    }

    private HttpRequestWithBody prepareInvoice(Long orderId, byte[] xmlBytes, OrderPackageInvoiceResource invoiceResource,
                                               IntegrationHeader[] headers) {
        try {
            ObjectMapper mapper = Mapper.get();
            HttpRequestWithBody post = Unirest.post(apiEndPoint.concat("/orders/").concat(orderId.toString()).concat("/fiscalDocument"));
            for (IntegrationHeader header : AnymarketHeaderUtils.addModuleOriginHeader(headers, this.moduleOrigin)) {
                post.header(header.getKey(), header.getValue());
            }
            String json = mapper.writeValueAsString(invoiceResource);
            Map<String, Object> fields = mapper.readValue(json, new TypeReference<Map<String, Object>>() {
            });
            MultipartBody multipartBody = post.fields(fields);
            if (xmlBytes != null) {
                multipartBody.field(FIELD_FILE, xmlBytes, ContentType.create("application/xml"), invoiceResource.getPackageId() + ".xml");
            }
            return post;
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }
}
