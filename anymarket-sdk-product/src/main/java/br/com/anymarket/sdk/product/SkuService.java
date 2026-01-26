package br.com.anymarket.sdk.product;

import br.com.anymarket.sdk.SDKConstants;
import br.com.anymarket.sdk.exception.HttpClientException;
import br.com.anymarket.sdk.exception.NotFoundException;
import br.com.anymarket.sdk.http.HttpService;
import br.com.anymarket.sdk.http.Response;
import br.com.anymarket.sdk.http.headers.AnymarketContentTypes;
import br.com.anymarket.sdk.http.headers.ContentTypeHeader;
import br.com.anymarket.sdk.http.headers.IntegrationHeader;
import br.com.anymarket.sdk.paging.Page;
import br.com.anymarket.sdk.product.dto.Sku;
import br.com.anymarket.sdk.product.dto.SkuResource;
import br.com.anymarket.sdk.product.dto.marketplace.JsonPatchOperation;
import br.com.anymarket.sdk.resource.Link;
import com.fasterxml.jackson.core.type.TypeReference;
import com.google.common.base.Supplier;
import com.mashape.unirest.request.GetRequest;
import com.mashape.unirest.request.body.RequestBodyEntity;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.http.HttpStatus;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static br.com.anymarket.sdk.http.headers.AnymarketHeaderUtils.addModuleOriginHeader;
import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Strings.isNullOrEmpty;
import static java.lang.String.format;

public class SkuService extends HttpService {

    private static final String PRODUCT_SKU_URI = "/products/%s/skus/";
    private static final String SKU_PATCH_URI = "/products/%s/skus/patch/%s";
    private static final String SKU_URI = "/skus/";
    private final String apiEndPoint;
    public static final String OFFSET = "offset";
    public static final String LIMIT = "limit";
    public static final String NEXT = "next";

    private String moduleOrigin;

    public SkuService(final String apiEndPoint) {
        this.apiEndPoint = !isNullOrEmpty(apiEndPoint) ? apiEndPoint :
                SDKConstants.ANYMARKET_HOMOLOG_API_ENDPOINT;
    }

    public SkuService(final String apiEndPoint, String origin) {
        this.apiEndPoint = !isNullOrEmpty(apiEndPoint) ? apiEndPoint :
                SDKConstants.ANYMARKET_HOMOLOG_API_ENDPOINT;
        this.moduleOrigin = origin;
    }

    private String getURLFormated(final Long idProduct) {
        return String.format(apiEndPoint.concat(PRODUCT_SKU_URI), idProduct.toString());
    }

    public Sku insertSku(final Sku sku, final Long idProduct, IntegrationHeader... headers) {
        checkNotNull(sku, "Informe o Sku a ser persistido.");
        checkNotNull(idProduct, "Informe o id do produto do Sku.");
        RequestBodyEntity post = post(getURLFormated(idProduct), sku, addModuleOriginHeader(headers, this.moduleOrigin));
        Response response = execute(post);
        return response.to(Sku.class);
    }

    public Sku updateSku(final Sku sku, final Long idProduct, IntegrationHeader... headers) {
        checkNotNull(sku, "Informe o Sku a ser atualizado.");
        checkNotNull(idProduct, "Informe o id do produto do Sku.");
        RequestBodyEntity put = put(getURLFormated(idProduct).concat("/").concat(sku.getId().toString()), sku, addModuleOriginHeader(headers, this.moduleOrigin));
        Response response = execute(put);
        return response.to(Sku.class);
    }

    public Sku insertOrUpdateSku(final Sku sku, final Long idProduct, IntegrationHeader... headers) {
        if (sku.getId() != null) {
            return updateSku(sku, idProduct, headers);
        } else {
            return insertSku(sku, idProduct, headers);
        }
    }

    public List<Sku> getAllSkus(final Long idProduct, IntegrationHeader... headers) {
        final GetRequest getRequest = get(getURLFormated(idProduct).concat("/"), addModuleOriginHeader(headers, this.moduleOrigin));
        final Response response = execute(getRequest);
        if (response.getStatus() == HttpStatus.SC_OK) {
            return response.to(new TypeReference<List<Sku>>() {
            });
        } else {
            throw new NotFoundException("Skus not found.");
        }
    }

//    public List<Sku> getAllSkus(final Long idProduct, IntegrationHeader... headers) {
//
//    }

    public Sku getSku(Long idSku, final Long idProduct, IntegrationHeader... headers) {
        return getSku(idSku, idProduct, Sku.class, headers);
    }

    public <T> T getSku(Long idSku, final Long idProduct, Class<T> clazz, IntegrationHeader... headers) {
        checkNotNull(idSku, "Informe o id do Sku.");
        checkNotNull(idProduct, "Informe o id do produto do Sku.");
        GetRequest getRequest = get(getURLFormated(idProduct).concat("/").concat(idSku.toString()), addModuleOriginHeader(headers, this.moduleOrigin));
        Response response = execute(getRequest);
        if (response.getStatus() == HttpStatus.SC_OK) {
            return response.to(clazz);
        }
        throw new NotFoundException(format("Sku with id %s and product id %s not found.", idSku, idProduct));
    }

    public Sku getSku(Long idSku, IntegrationHeader... headers) {
        return getSku(idSku, Sku.class, headers);
    }

    public <T> T getSku(Long idSku, Class<T> clazz, IntegrationHeader... headers) {
        return getSku(idSku, false, clazz, headers);
    }

    public SkuResource getSkuWithProduct(Long idSku, IntegrationHeader... headers) {
        return getSku(idSku, true, SkuResource.class, headers);
    }

    private <T> T getSku(Long idSku, boolean showProduct, Class<T> clazz, IntegrationHeader... headers) {
        checkNotNull(idSku, "Informe o id do Sku.");
        String param = showProduct ? "?showProduct=true" : "";
        GetRequest getRequest = get(this.apiEndPoint.concat("/skus/").concat(idSku.toString()).concat(param), addModuleOriginHeader(headers, this.moduleOrigin));
        Response response = execute(getRequest);
        if (response.getStatus() == HttpStatus.SC_OK) {
            return response.to(clazz);
        }
        throw new NotFoundException(format("Sku with id %s not found.", idSku));
    }

    public Sku getByPartnerId(String partnerId, boolean showProduct, IntegrationHeader... headers) {
        checkNotNull(partnerId, "Informe o idInClient");
        String param = showProduct ? "?showProduct=true" : "";
        GetRequest getRequest = get(this.apiEndPoint.concat("/skus/partnerId/").concat(partnerId).concat(param), addModuleOriginHeader(headers, this.moduleOrigin));
        Response response = execute(getRequest);
        if (response.getStatus() == HttpStatus.SC_OK) {
            return response.to(Sku.class);
        }
        throw new NotFoundException(format("Sku with idInClient %s not found.", partnerId));
    }

    public Page<Sku> getSkusPaged(IntegrationHeader... headers) {
        return getSkusPaged(null, null, headers);
    }

    public Page<Sku> getSkusPaged(Integer offset, Integer limit, IntegrationHeader... headers) {
        GetRequest request = get(apiEndPoint.concat(SKU_URI), addModuleOriginHeader(headers, this.moduleOrigin));
        if (offset != null) {
            request.queryString(OFFSET, offset);
        }
        if (limit != null) {
            request.queryString(LIMIT, limit);
        }
        Response response = execute(request);
        if (response.getStatus() == HttpStatus.SC_OK) {
            return response.to(new TypeReference<Page<Sku>>() {
            });
        } else {
            throw new NotFoundException("Skus not found.");
        }
    }

    public Page<Sku> getNextPageSkus(Page<Sku> actualPaged, IntegrationHeader... headers) {
        String nextPageUrl = null;
        for (Link link : actualPaged.getLinks()) {
            if (link.getRel().equals(NEXT)) {
                nextPageUrl = link.getHref();
                break;
            }
        }
        if (nextPageUrl != null) {
            Response response = execute(get(nextPageUrl, addModuleOriginHeader(headers, this.moduleOrigin)));
            if (response.getStatus() == HttpStatus.SC_OK) {
                return response.to(new TypeReference<Page<Sku>>() {
                });
            }
        }
        return new Page<Sku>();
    }

    private Response execute(RequestBodyEntity request, int expectedHttpStatus, Supplier<String> failureMessagePrefixSupplier) {
        Response response = execute(request);
        if (response.getStatus() == expectedHttpStatus) {
            return response;
        }
        String failureMessagePrefix = failureMessagePrefixSupplier.get();
        throw new HttpClientException(format("%s. Cause: %s", failureMessagePrefix, response.getMessage()));
    }

    public Sku patchSku(Long productId, Sku sku, IntegrationHeader... headers) {
        return patchSku(productId, sku, null, headers);
    }

    public Sku patchSku(Long productId, Sku sku, Boolean returnRelated, IntegrationHeader... headers) {
        checkNotNull(productId, "Informe o id do produto do Sku.");
        checkNotNull(sku, "Informe o Sku a ser atualizado via patch.");
        checkNotNull(sku.getId(), "Informe o id do Sku.");

        Long skuId = sku.getId();

        List<JsonPatchOperation> ops = buildSkuPatchOperations(sku);

        if (ops.isEmpty()) {
            throw new IllegalArgumentException("Nenhum campo para atualizar via patch (todos null ignorados).");
        }

        String url = String.format(apiEndPoint.concat(SKU_PATCH_URI), productId.toString(), skuId.toString());

        if (returnRelated != null) {
            url = url.concat("?returnRelated=").concat(returnRelated.toString());
        }

        IntegrationHeader[] withOrigin = addModuleOriginHeader(headers, this.moduleOrigin);

        IntegrationHeader[] finalHeaders = ArrayUtils.add(
                withOrigin,
                new ContentTypeHeader(AnymarketContentTypes.JSON_PATCH)
        );

        RequestBodyEntity patchReq = patch(url, ops, finalHeaders);

        Response response = execute(
                patchReq,
                HttpStatus.SC_OK,
                () -> format("Failed to patch sku - id %s (productId %s)", skuId, productId)
        );

        return response.to(Sku.class);
    }


    private List<JsonPatchOperation> buildSkuPatchOperations(Sku sku) {
        Map<String, Object> map = br.com.anymarket.sdk.http.Mapper.get()
                .convertValue(sku, new com.fasterxml.jackson.core.type.TypeReference<Map<String, Object>>() {
                });

        map.remove("id");
        map.remove("variations");

        List<JsonPatchOperation> ops = new ArrayList<>();
        for (Map.Entry<String, Object> e : map.entrySet()) {
            if (e.getValue() != null) {
                ops.add(JsonPatchOperation.replace("/" + e.getKey(), e.getValue()));
            }
        }
        return ops;
    }


}
