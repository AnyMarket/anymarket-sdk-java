package br.com.anymarket.sdk.product;

import br.com.anymarket.sdk.MarketPlace;
import br.com.anymarket.sdk.SDKConstants;
import br.com.anymarket.sdk.exception.HttpServerException;
import br.com.anymarket.sdk.exception.NotFoundException;
import br.com.anymarket.sdk.http.HttpService;
import br.com.anymarket.sdk.http.Response;
import br.com.anymarket.sdk.http.headers.IntegrationHeader;
import br.com.anymarket.sdk.paging.Page;
import br.com.anymarket.sdk.product.dto.*;
import br.com.anymarket.sdk.resource.Link;
import com.fasterxml.jackson.core.type.TypeReference;
import com.google.common.collect.Lists;
import com.mashape.unirest.request.GetRequest;
import com.mashape.unirest.request.body.RequestBodyEntity;
import org.apache.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Objects;

import static br.com.anymarket.sdk.http.headers.AnymarketHeaderUtils.addModuleOriginHeader;
import static com.google.common.base.Strings.isNullOrEmpty;
import static java.lang.String.format;

public class SkuMarketPlaceService extends HttpService {

    private static final Logger LOG = LoggerFactory.getLogger(SkuMarketPlaceService.class);

    private static final String SKUMP_URI = "/skus/%s/marketplaces";
    private static final String SKUMP_UPDATE_PRICE_URI = "/skus/%s/updatePrice/%s";
    private static final String SKUMP_ALL_MARKETPLACE = "/skus/%s/all";
    public static final String NEXT = "next";
    private final String apiEndPoint;
    private String moduleOrigin;

    public SkuMarketPlaceService(final String apiEndPoint) {
        this.apiEndPoint = !isNullOrEmpty(apiEndPoint) ? apiEndPoint :
            SDKConstants.ANYMARKET_HOMOLOG_API_ENDPOINT;
    }

    public SkuMarketPlaceService(final String apiEndPoint, String origin) {
        this.apiEndPoint = !isNullOrEmpty(apiEndPoint) ? apiEndPoint :
                SDKConstants.ANYMARKET_HOMOLOG_API_ENDPOINT;
        this.moduleOrigin = origin;
    }

    public SkuMarketPlace insert(final SkuMarketPlace skuMp, Long idSku, IntegrationHeader... headers) {
        Objects.requireNonNull(skuMp, "Informe o SkuMarketPlace a ser persistido.");
        Objects.requireNonNull(idSku, "Informe o id do SKU");
        RequestBodyEntity post = post(getURLFormated(idSku), skuMp, addModuleOriginHeader(headers, this.moduleOrigin));
        Response response = execute(post);
        return response.to(SkuMarketPlace.class);
    }

    public SkuMarketPlace update(final SkuMarketPlace skuMp, Long idSku, Long idSkuMP, IntegrationHeader... headers) {
        return update(skuMp, idSku, idSkuMP, true, headers);
    }

    public SkuMarketPlace update(final SkuMarketPlace skuMp, Long idSku, Long idSkuMP, boolean syncChanges, IntegrationHeader... headers) {
        Objects.requireNonNull(skuMp, "Informe o SkuMarketPlace a ser atualizado.");
        Objects.requireNonNull(idSkuMP, "Informe o id do SkuMarketplace a ser atualizado.");
        Objects.requireNonNull(idSku, "Informe o id do SKU");

        if (skuMp.getPrice() != null && skuMp.getDiscountPrice() != null) {
            skuMp.getFields().put("HAS_DISCOUNT",
                Boolean.toString(skuMp.getPrice().compareTo(skuMp.getDiscountPrice()) != 0));
        }

        String url = getURLFormated(idSku).concat("/").concat(idSkuMP.toString()).concat("?syncChanges=" + syncChanges);
        RequestBodyEntity put = put(url, skuMp, addModuleOriginHeader(headers, this.moduleOrigin));
        Response response = execute(put);
        return response.to(SkuMarketPlace.class);
    }

    public SkuMarketPlace update(final SkuMarketPlace skuMp, Long idSku, IntegrationHeader... headers) {
        return update(skuMp, idSku, skuMp.getId(), addModuleOriginHeader(headers, this.moduleOrigin));
    }

    private String getURLFormated(final Long idSku) {
        return String.format(apiEndPoint.concat(SKUMP_URI), idSku.toString());
    }

    public SkuMarketPlace save(final SkuMarketPlace skuMp, Long idSku, IntegrationHeader... headers) {
        if (skuMp.getId() != null) {
            return update(skuMp, idSku, headers);
        } else {
            return insert(skuMp, idSku, headers);
        }
    }

    public SkuMarketPlace getSkuMarketPlace(Long idSku, Long idSkuMp, IntegrationHeader... headers) {
        return getSkuMarketPlace(idSku, idSkuMp, false, null, headers);
    }

    public SkuMarketPlace getSkuMarketPlaceWithSku(Long idSku, Long idSkuMp, IntegrationHeader... headers) {
        return getSkuMarketPlace(idSku, idSkuMp, false, "SKU_COMPLETE", headers);
    }

    public SkuMarketPlace getSkuMarketPlace(Long idSku, Long idSkuMp, boolean multiCd, IntegrationHeader... headers) {
        return getSkuMarketPlace(idSku, idSkuMp, multiCd, null, headers);
    }

    public SkuMarketPlace getSkuMarketPlace(Long idSku, Long idSkuMp, boolean multiCd, String skuMarketplaceReturnType, IntegrationHeader... headers) {
        Objects.requireNonNull(idSkuMp, "Informe o id do SkuMarketPlace.");
        Objects.requireNonNull(idSku, "Informe o id do SKU");
        skuMarketplaceReturnType = skuMarketplaceReturnType == null ? "" : "&returnType=" + skuMarketplaceReturnType;
        GetRequest getRequest = get(getURLFormated(idSku).concat("/").concat(idSkuMp.toString()).concat("?multiCd=" + multiCd).concat(skuMarketplaceReturnType), addModuleOriginHeader(headers, this.moduleOrigin));
        Response response = execute(getRequest);
        if (response.getStatus() == HttpStatus.SC_OK) {
            return response.to(SkuMarketPlace.class);
        }
        throw new NotFoundException(format("Sku with id %s not found.", idSkuMp));
    }

    public List<SkuMarketPlace> getAllSkuMps(Long idSku, IntegrationHeader... headers) {
        return getAllSkuMps(idSku, null, headers);
    }

    public List<SkuMarketPlace> getAllSkuMps(Long idSku, MarketPlace marketPlace, IntegrationHeader... headers) {
        final List<SkuMarketPlace> allSkuMps = Lists.newArrayList();
        String urlFormated = getURLFormated(idSku);
        final GetRequest getRequest = get(marketPlace == null ? urlFormated : urlFormated.concat("?marketplace=").concat(marketPlace.name()), addModuleOriginHeader(headers, this.moduleOrigin));
        final Response response = execute(getRequest);
        if (response.getStatus() == HttpStatus.SC_OK) {
            List<SkuMarketPlace> rootResponse = response.to(new TypeReference<List<SkuMarketPlace>>() {
            });
            allSkuMps.addAll(rootResponse);
        } else {
            throw new NotFoundException("SkuMps not found.");
        }
        return allSkuMps;
    }

    public SkuMarketplacePriceErrors updatePricesForSkuAndMarketplace(Long idSku, MarketPlace marketPlace, SkuMarketplacePriceResource prices, IntegrationHeader... headers) {
        Objects.requireNonNull(idSku, "Informe o id do SKU");
        Objects.requireNonNull(marketPlace, "Informe o Marketplace");

        String URL = String.format(apiEndPoint.concat(SKUMP_UPDATE_PRICE_URI), idSku, marketPlace);
        RequestBodyEntity post = post(URL, prices, addModuleOriginHeader(headers, this.moduleOrigin));
        Response response = execute(post);
        return response.to(SkuMarketplacePriceErrors.class);
    }

    public List<SkuMarketPlace> getSkuAndMarketplaceByMarketplaceAndPartnerId(MarketPlace marketPlace, PublicationStatus status, String partnerId, IntegrationHeader... headers) {
        Objects.requireNonNull(marketPlace, "Informe o Marketplace");
        Objects.requireNonNull(partnerId, "Informe o partnerId");


        final List<SkuMarketPlace> allSkuMps = Lists.newArrayList();
        String urlFormated = String.format(apiEndPoint.concat(SKUMP_ALL_MARKETPLACE), marketPlace);


        String finalUrl = urlFormated.concat("?skuPartnerId=").concat(partnerId);
        finalUrl = status == null ? finalUrl : urlFormated.concat("&status=").concat(status.toString());

        final GetRequest getRequest = get(finalUrl, addModuleOriginHeader(headers, this.moduleOrigin));
        final Response response = execute(getRequest);
        if (response.getStatus() == HttpStatus.SC_OK) {
            Page<SkuMarketPlace> rootResponse = response.to(new TypeReference<Page<SkuMarketPlace>>() {
            });
            allSkuMps.addAll(rootResponse.getContent());
        }
        return allSkuMps;
    }

    public List<SkuMarketPlace> getSkuAndMarketplaceByMarketplace(MarketPlace marketPlace, PublicationStatus status, IntegrationHeader... headers) {
        Objects.requireNonNull(marketPlace, "Informe o Marketplace");

        final List<SkuMarketPlace> allSkuMps = Lists.newArrayList();
        String urlFormated = String.format(apiEndPoint.concat(SKUMP_ALL_MARKETPLACE), marketPlace);
        final GetRequest getRequest = get(status == null ? urlFormated : urlFormated.concat("?status=").concat(status.toString()), addModuleOriginHeader(headers, this.moduleOrigin));
        final Response response = execute(getRequest);
        if (response.getStatus() == HttpStatus.SC_OK) {
            Page<SkuMarketPlace> rootResponse = response.to(new TypeReference<Page<SkuMarketPlace>>() {
            });
            allSkuMps.addAll(rootResponse.getContent());
        }
        return allSkuMps;
    }

    public Page<SkuMarketPlace> getSkuAndMarketplaceByMarketplacePaged(MarketPlace marketPlace, PublicationStatus status, IntegrationHeader... headers) {
        Objects.requireNonNull(marketPlace, "Informe o Marketplace");

        String urlFormated = String.format(apiEndPoint.concat(SKUMP_ALL_MARKETPLACE), marketPlace);
        final GetRequest getRequest = get(status == null ? urlFormated : urlFormated.concat("?status=").concat(status.toString()), addModuleOriginHeader(headers, this.moduleOrigin));
        final Response response = execute(getRequest);
        if (response.getStatus() == HttpStatus.SC_OK) {
            return response.to(new TypeReference<Page<SkuMarketPlace>>() {
            });
        } else {
            throw new NotFoundException(String.format("SkuMarketplaces not found for marketplace %s.", marketPlace.toString()));
        }
    }

    public Page<SkuMarketPlace> getNextSkuAndMarketplaceByMarketplace(Page<SkuMarketPlace> actualPaged, IntegrationHeader... headers) {
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
                return response.to(new TypeReference<Page<SkuMarketPlace>>() {
                });
            }
        }
        return new Page<SkuMarketPlace>();
    }

    public SkuMarketplaceComplete getSkuMarketplaceCompleteById(Long idSkuMarketplace, IntegrationHeader... headers) {
        Objects.requireNonNull(idSkuMarketplace, "Informe o idSkuMarketplace");

        String endpoint = String.format("/skus/marketplaces/%s/complete", idSkuMarketplace);

        return getSkuMarketplaceComplete(idSkuMarketplace, endpoint, headers);
    }

    public SkuMarketplaceComplete getSkuMarketplaceCompleteByIdAndIdAccount(Long idSkuMarketplace, Long idAccount, IntegrationHeader... headers) {
        Objects.requireNonNull(idSkuMarketplace, "Informe o idSkuMarketplace");
        Objects.requireNonNull(idAccount, "Informe o idAccount");

        String endpoint = String.format("/skus/marketplaces/%s/complete/%s", idSkuMarketplace, idAccount);

        return getSkuMarketplaceComplete(idSkuMarketplace, endpoint, headers);
    }

    private SkuMarketplaceComplete getSkuMarketplaceComplete(Long idSkuMarketplace, String endpoint, IntegrationHeader[] headers) {
        GetRequest getRequest = get(apiEndPoint.concat(endpoint), addModuleOriginHeader(headers, this.moduleOrigin));

        try {
            LOG.info("Chamando endpoint {}", apiEndPoint.concat(endpoint));
            Response response = execute(getRequest);
            LOG.info("Response status {}", response.getStatus());

            if (response.getStatus() == HttpStatus.SC_OK) {
                return response.to(SkuMarketplaceComplete.class);
            } else {
                throw new NotFoundException(String.format("SkuMarketplace not found for id %s.", idSkuMarketplace));
            }
        } catch (HttpServerException e) {
            throw e;
        } catch (Exception e) {
            LOG.error("Ocorreu um erro ao chamar endpoint {}", this.apiEndPoint.concat(endpoint), e);
            throw new NotFoundException(String.format("SkuMarketplace not found for id %s.", idSkuMarketplace));
        }
    }

}
