package br.com.anymarket.sdk.product;

import br.com.anymarket.sdk.SDKConstants;
import br.com.anymarket.sdk.exception.NotFoundException;
import br.com.anymarket.sdk.http.HttpService;
import br.com.anymarket.sdk.http.Response;
import br.com.anymarket.sdk.http.headers.IntegrationHeader;
import br.com.anymarket.sdk.paging.Page;
import br.com.anymarket.sdk.product.dto.Product;
import br.com.anymarket.sdk.product.dto.Sku;
import br.com.anymarket.sdk.resource.Link;
import com.fasterxml.jackson.core.type.TypeReference;
import com.mashape.unirest.request.GetRequest;
import com.mashape.unirest.request.body.RequestBodyEntity;
import org.apache.http.HttpStatus;

import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Strings.isNullOrEmpty;
import static java.lang.String.format;
import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

public class SkuService extends HttpService {

    private static final String PRODUCT_SKU_URI = "/products/%s/skus/";
    private static final String SKU_URI = "/skus/";
    private final String apiEndPoint;
    public static final String OFFSET = "offset";
    public static final String LIMIT = "limit";
    public static final String NEXT = "next";

    public SkuService(final String apiEndPoint) {
        this.apiEndPoint = !isNullOrEmpty(apiEndPoint) ? apiEndPoint :
            SDKConstants.ANYMARKET_HOMOLOG_API_ENDPOINT;
    }

    private String getURLFormated(final Long idProduct) {
        return String.format(apiEndPoint.concat(PRODUCT_SKU_URI), idProduct.toString());
    }

    public Sku insertSku(final Sku sku, final Long idProduct, IntegrationHeader... headers) {
        checkNotNull(sku, "Informe o Sku a ser persistido.");
        checkNotNull(idProduct, "Informe o id do produto do Sku.");
        RequestBodyEntity post = post(getURLFormated(idProduct), sku, headers);
        Response response = execute(post);
        return response.to(Sku.class);
    }

    public Sku updateSku(final Sku sku, final Long idProduct, IntegrationHeader... headers) {
        checkNotNull(sku, "Informe o Sku a ser atualizado.");
        checkNotNull(idProduct, "Informe o id do produto do Sku.");
        RequestBodyEntity put = put(getURLFormated(idProduct).concat("/").concat(sku.getId().toString()), sku, headers);
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
        final GetRequest getRequest = get(getURLFormated(idProduct).concat("/"), headers);
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
        GetRequest getRequest = get(getURLFormated(idProduct).concat("/").concat(idSku.toString()), headers);
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
        checkNotNull(idSku, "Informe o id do Sku.");
        GetRequest getRequest = get(this.apiEndPoint.concat("/skus/").concat(idSku.toString()), headers);
        Response response = execute(getRequest);
        if (response.getStatus() == HttpStatus.SC_OK) {
            return response.to(clazz);
        }
        throw new NotFoundException(format("Sku with id %s not found.", idSku));
    }

    public Page<Sku> getSkusPaged(IntegrationHeader... headers) {
        return getSkusPaged(null, null, headers);
    }

    public Page<Sku> getSkusPaged(Integer offset, Integer limit, IntegrationHeader... headers) {
        GetRequest request = get(apiEndPoint.concat(SKU_URI), headers);
        if (nonNull(offset)) {
            request.queryString(OFFSET, offset);
        }
        if (nonNull(limit)) {
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
            Response response = execute(get(nextPageUrl, headers));
            if (response.getStatus() == HttpStatus.SC_OK) {
                return response.to(new TypeReference<Page<Sku>>() {
                });
            }
        }
        return new Page<Sku>();
    }
}
