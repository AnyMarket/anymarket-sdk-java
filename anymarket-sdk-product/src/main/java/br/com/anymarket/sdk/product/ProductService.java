package br.com.anymarket.sdk.product;

import br.com.anymarket.sdk.MarketPlace;
import br.com.anymarket.sdk.SDKConstants;
import br.com.anymarket.sdk.exception.HttpClientException;
import br.com.anymarket.sdk.exception.NotFoundException;
import br.com.anymarket.sdk.http.HttpService;
import br.com.anymarket.sdk.http.Response;
import br.com.anymarket.sdk.http.headers.AnymarketContentTypes;
import br.com.anymarket.sdk.http.headers.ContentTypeHeader;
import br.com.anymarket.sdk.http.headers.IntegrationHeader;
import br.com.anymarket.sdk.http.restdsl.AnyMarketRestDSL;
import br.com.anymarket.sdk.paging.Page;
import br.com.anymarket.sdk.product.dto.Image;
import br.com.anymarket.sdk.product.dto.Origin;
import br.com.anymarket.sdk.product.dto.Product;
import br.com.anymarket.sdk.product.dto.ProductComplete;
import br.com.anymarket.sdk.product.dto.marketplace.JsonPatchOperation;
import br.com.anymarket.sdk.product.filters.ProductFilter;
import br.com.anymarket.sdk.resource.Link;
import br.com.anymarket.sdk.util.SDKUrlEncoder;
import com.fasterxml.jackson.core.type.TypeReference;
import com.mashape.unirest.request.GetRequest;
import com.mashape.unirest.request.body.RequestBodyEntity;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.http.HttpStatus;

import java.lang.reflect.Method;
import java.util.*;
import java.util.function.*;
import java.util.stream.Collectors;

import static br.com.anymarket.sdk.http.headers.AnymarketHeaderUtils.addModuleOriginHeader;
import static com.google.common.base.Strings.isNullOrEmpty;
import static java.lang.String.format;

public class ProductService extends HttpService {

    public static final TypeReference<Page<Product>> PAGED_TYPE_REFERENCE = new TypeReference<Page<Product>>() {
    };
    private static final String PRODUCTS_URI = "/products";
    private static final String PRODUCT_PATCH_URI = "/products/patch/%s";

    private static final String IMAGES_MULTI = "/images/multi";
    private final String apiEndPoint;
    public static final String NEXT = "next";
    private String moduleOrigin;

    public ProductService(String apiEndPoint) {
        this.apiEndPoint = !isNullOrEmpty(apiEndPoint) ? apiEndPoint :
                SDKConstants.ANYMARKET_HOMOLOG_API_ENDPOINT;
    }

    public ProductService(String apiEndPoint, String origin) {
        this.apiEndPoint = !isNullOrEmpty(apiEndPoint) ? apiEndPoint :
                SDKConstants.ANYMARKET_HOMOLOG_API_ENDPOINT;
        this.moduleOrigin = origin;
    }

    public Product insertProduct(Product product, IntegrationHeader... headers) {
        RequestBodyEntity post = post(apiEndPoint.concat(PRODUCTS_URI), product, addModuleOriginHeader(headers, this.moduleOrigin));
        Response response = execute(post);
        return response.to(Product.class);
    }

    public Product updateProduct(Product product, IntegrationHeader... headers) {
        Long productId = product.getId();
        String url = apiEndPoint.concat(PRODUCTS_URI)
                .concat("/")
                .concat(productId.toString());
        RequestBodyEntity put = put(url, product, addModuleOriginHeader(headers, this.moduleOrigin));
        Response response = execute(put, HttpStatus.SC_OK, () -> format("Failed to update product - id %s", productId));
        return response.to(Product.class);
    }

    public Product updateProductAndImages(Product product, IntegrationHeader... headers) {
        insertImagesForInsert(product, headers);
        deleteImagesForDelete(product, headers);
        return updateProduct(product, headers);
    }

    public Product updateProductAndCreateAndUpdateImages(Product product, IntegrationHeader... headers) {
        deleteImagesForDelete(product, headers);
        insertImagesForInsert(product, headers);
        updateImagesForUpdate(product, headers);
        return updateProduct(product, headers);
    }

    private void insertImagesForInsert(Product product, IntegrationHeader[] headers) {
        doForMatchedImages(
                product.getImages(),
                image -> image.getId() == null,
                images -> insertImages(product.getId(), images, headers)
        );
    }

    private void updateImagesForUpdate(Product product, IntegrationHeader[] headers) {
        doForMatchedImages(
                product.getImages(),
                image -> image.getId() != null,
                images -> updateImages(product.getId(), images, headers)
        );
    }

    private void deleteImagesForDelete(Product product, IntegrationHeader[] headers) {
        doForMatchedImages(
                product.getImagesForDelete(),
                image -> image.getId() != null,
                images -> deleteImages(product.getId(), images, headers)
        );
    }

    private void doForMatchedImages(List<Image> images, Predicate<Image> imagePredicate, Consumer<List<Image>> action) {
        if (Objects.isNull(images)) {
            return;
        }
        List<Image> matchedImages = images.stream().filter(imagePredicate).collect(Collectors.toList());
        if (matchedImages.isEmpty()) {
            return;
        }
        action.accept(matchedImages);
    }

    private void insertImages(Long productId, List<Image> images, IntegrationHeader... headers) {
        String url = apiEndPoint.concat(PRODUCTS_URI)
                .concat("/")
                .concat(productId.toString())
                .concat(IMAGES_MULTI);
        RequestBodyEntity post = post(url, images, addModuleOriginHeader(headers, moduleOrigin));
        execute(post, HttpStatus.SC_OK, () -> format("Failed to insert images of product - id %s", productId));
    }

    private void updateImages(Long productId, List<Image> images, IntegrationHeader... headers) {
        String url = apiEndPoint.concat(PRODUCTS_URI)
                .concat("/")
                .concat(productId.toString())
                .concat(IMAGES_MULTI);
        RequestBodyEntity put = put(url, images, addModuleOriginHeader(headers, this.moduleOrigin));
        execute(put, HttpStatus.SC_OK, () -> format("Failed to update images of product - id %s", productId));
    }

    private void deleteImages(Long productId, List<Image> images, IntegrationHeader... headers) {
        String url = apiEndPoint.concat(PRODUCTS_URI)
                .concat("/")
                .concat(productId.toString())
                .concat(IMAGES_MULTI);
        List<Long> imageIds = images.stream().map(Image::getId).collect(Collectors.toList());
        RequestBodyEntity delete = delete(url, addModuleOriginHeader(headers, moduleOrigin))
                .body(writeValueAsJson(imageIds));
        execute(delete, HttpStatus.SC_NO_CONTENT, () -> format("Failed to delete images - ids %s", imageIds));
    }

    private Response execute(RequestBodyEntity request, int expectedHttpStatus, Supplier<String> failureMessagePrefixSupplier) {
        Response response = execute(request);
        if (response.getStatus() == expectedHttpStatus) {
            return response;
        }
        String failureMessagePrefix = failureMessagePrefixSupplier.get();
        throw new HttpClientException(format("%s. Cause: %s", failureMessagePrefix, response.getMessage()));
    }

    public Product getProduct(Long id, IntegrationHeader... headers) {
        return getProduct(id, Product.class, headers);
    }

    public <T> T getProduct(Long id, Class<T> clazz, IntegrationHeader... headers) {
        GetRequest getRequest = get(apiEndPoint.concat(PRODUCTS_URI).concat("/").concat(id.toString()), addModuleOriginHeader(headers, this.moduleOrigin));
        Response response = execute(getRequest);
        if (response.getStatus() == HttpStatus.SC_OK) {
            return response.to(clazz);
        }
        throw new NotFoundException(format("Product with id %s not found.", id));
    }

    public Product getProductBySku(final String sku, IntegrationHeader... headers) {
        final List<Product> products = getAllProducts(getUrlForProductsWithSku(sku), headers);
        if (!products.isEmpty()) {
            return products.get(0);
        }
        throw new NotFoundException(format("Product with partnerId %s not found.", sku));
    }

    public ProductComplete getProductCompleteBySku(final String sku, IntegrationHeader... headers) {
        final List<ProductComplete> products = getAllCompleteProducts(getUrlForProductsWithSku(sku), headers);
        if (!products.isEmpty()) {
            return products.get(0);
        }
        throw new NotFoundException(format("Product with partnerId %s not found.", sku));
    }

    private String getUrlForProductsWithSku(String sku) {
        return apiEndPoint.concat(PRODUCTS_URI).concat("?sku=")
                .concat(SDKUrlEncoder.encodeParameterToUTF8(sku));
    }

    public List<Product> getAllProducts(final String url, IntegrationHeader... headers) {
        final List<Product> allProducts = new ArrayList<Product>();
        final GetRequest getRequest = get(url, addModuleOriginHeader(headers, this.moduleOrigin));
        final Response response = execute(getRequest);
        if (response.getStatus() == HttpStatus.SC_OK) {
            Page<Product> rootResponse = response.to(new TypeReference<Page<Product>>() {
            });
            allProducts.addAll(rootResponse.getContent());
        } else {
            throw new NotFoundException("Products not found.");
        }
        return allProducts;
    }

    public List<ProductComplete> getAllCompleteProducts(final String url, IntegrationHeader... headers) {
        final List<ProductComplete> allProducts = new ArrayList<ProductComplete>();
        final GetRequest getRequest = get(url, addModuleOriginHeader(headers, this.moduleOrigin));
        final Response response = execute(getRequest);
        if (response.getStatus() == HttpStatus.SC_OK) {
            Page<ProductComplete> rootResponse = response.to(new TypeReference<Page<ProductComplete>>() {
            });
            allProducts.addAll(rootResponse.getContent());
        } else {
            throw new NotFoundException("Products not found.");
        }
        return allProducts;
    }

    public Page<Product> getProductPaged(List<ProductFilter> filters, IntegrationHeader... headers) {
        return AnyMarketRestDSL.get(apiEndPoint.concat(PRODUCTS_URI))
                .headers(addModuleOriginHeader(headers, this.moduleOrigin))
                .filters(filters)
                .getResponse()
                .to(PAGED_TYPE_REFERENCE);
    }

    public Page<Product> getProductPaged(IntegrationHeader... headers) {
        Response response = execute(get(apiEndPoint.concat(PRODUCTS_URI), addModuleOriginHeader(headers, this.moduleOrigin)));
        if (response.getStatus() == HttpStatus.SC_OK) {
            return response.to(new TypeReference<Page<Product>>() {
            });
        } else {
            throw new NotFoundException("Products not found.");
        }
    }

    public Page<Product> getNextPageProduct(Page<Product> actualPaged, IntegrationHeader... headers) {
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
                return response.to(new TypeReference<Page<Product>>() {
                });
            }
        }
        return new Page<Product>();
    }

    public Map<String, String> getActiveAttributesByMarketPlace(Long idProduct, MarketPlace marketPlace, IntegrationHeader... headers) {
        String url = apiEndPoint.concat(PRODUCTS_URI).concat("/").concat(idProduct.toString()).concat("/attributes/").concat(marketPlace.name());
        GetRequest getRequest = get(url, addModuleOriginHeader(headers, this.moduleOrigin));
        Response response = execute(getRequest);
        if (response.getStatus() == HttpStatus.SC_OK) {
            return response.to(HashMap.class);
        }
        throw new NotFoundException(format("Product(id: %s) active attributes not found to this marketplace(%s).", idProduct, marketPlace.name()));
    }


    public Product patchProduct(Product product, IntegrationHeader... headers) {
        Objects.requireNonNull(product, "Informe o produto a ser atualizado via patch.");
        Objects.requireNonNull(product.getId(), "Informe o id do produto.");

        Long productId = product.getId();

        List<JsonPatchOperation> ops = buildProductPatchOperations(product);
        if (ops.isEmpty()) {
            throw new IllegalArgumentException("Nenhum campo para atualizar via patch (todos null/ignorados).");
        }

        String url = String.format(apiEndPoint.concat(PRODUCT_PATCH_URI), productId.toString());

        IntegrationHeader[] withOrigin = addModuleOriginHeader(headers, this.moduleOrigin);
        IntegrationHeader[] finalHeaders = ArrayUtils.add(withOrigin, new ContentTypeHeader(AnymarketContentTypes.JSON_PATCH));

        RequestBodyEntity patchReq = patch(url, ops, finalHeaders);

        Response response = execute(patchReq, HttpStatus.SC_OK, () -> format("Failed to patch product - id %s", productId));
        return response.to(Product.class);
    }

    private List<JsonPatchOperation> buildProductPatchOperations(Product product) {
        List<JsonPatchOperation> ops = new ArrayList<>();

        Map<String, Object> root = br.com.anymarket.sdk.http.Mapper.get()
                .convertValue(product, new com.fasterxml.jackson.core.type.TypeReference<Map<String, Object>>() {});

        root.remove("id");
        root.remove("skus");
        root.remove("images");
        root.remove("marketplaceImages");
        root.remove("kitComponents");

        Object characteristics = root.remove("characteristics");
        Object category = root.remove("category");
        Object brand = root.remove("brand");
        Object nbm = root.remove("nbm");
        Object origin = root.remove("origin");

        for (Map.Entry<String, Object> e : root.entrySet()) {
            Object v = e.getValue();
            if (v != null) {
                ops.add(JsonPatchOperation.replace("/" + e.getKey(), v));
            }
        }

        addNestedIdOps(ops, "category", category, Collections.singletonList("id"));

        addNestedIdOps(ops, "brand", brand, Arrays.asList("id", "partnerId", "name"));

        addNestedIdOps(ops, "nbm", nbm, Collections.singletonList("id"));

        addOriginOps(ops, origin);

        addCharacteristicsOps(ops, characteristics);

        return ops;
    }

    @SuppressWarnings("unchecked")
    private void addNestedIdOps(List<JsonPatchOperation> ops, String field, Object rawObj, List<String> allowedKeys) {
        if (rawObj == null) return;

        Map<String, Object> m;
        if (rawObj instanceof Map) {
            m = (Map<String, Object>) rawObj;
        } else {
            m = br.com.anymarket.sdk.http.Mapper.get()
                    .convertValue(rawObj, new com.fasterxml.jackson.core.type.TypeReference<Map<String, Object>>() {});
        }

        for (String k : allowedKeys) {
            Object v = m.get(k);
            if (v != null) {
                ops.add(JsonPatchOperation.replace("/" + field + "/" + k, v));
            }
        }
    }

    private void addOriginOps(List<JsonPatchOperation> ops, Object origin) {
        if (origin == null) return;

        if (origin instanceof Origin) {
            Integer id = ((Origin) origin).getId();
            if (id != null) {
                ops.add(JsonPatchOperation.replace("/origin/id", id));
            }
            return;
        }

        Map<String, Object> m = br.com.anymarket.sdk.http.Mapper.get()
                .convertValue(origin, new com.fasterxml.jackson.core.type.TypeReference<Map<String, Object>>() {});

        Object id = m.get("id");
        if (id != null) {
            ops.add(JsonPatchOperation.replace("/origin/id", id));
        }
    }

    private void addCharacteristicsOps(List<JsonPatchOperation> ops, Object characteristics) {
        if (characteristics == null) return;

        if (characteristics instanceof List) {
            List<?> list = (List<?>) characteristics;
            if (!list.isEmpty()) {
                ops.add(JsonPatchOperation.replace("/characteristics", list));
            }
            return;
        }

        List<?> list = br.com.anymarket.sdk.http.Mapper.get()
                .convertValue(characteristics, new com.fasterxml.jackson.core.type.TypeReference<List<Object>>() {});
        if (list != null && !list.isEmpty()) {
            ops.add(JsonPatchOperation.replace("/characteristics", list));
        }
    }



    public List<String> findByOiAndIdsInClient(List<String> skus, IntegrationHeader... headers) {
        List<String> results = new ArrayList<>();

        Objects.requireNonNull(skus, "Informe no minimo um sku");

        String url = apiEndPoint.concat(PRODUCTS_URI).concat("/").concat("byOiAndIdsInClient");

        final RequestBodyEntity postRequest = post(url, skus, addModuleOriginHeader(headers, this.moduleOrigin));
        final Response response = execute(postRequest);
        if (response.getStatus() == HttpStatus.SC_OK) {
            List<String> rootResponse = response.to(new TypeReference<List<String>>() {
            });
            results.addAll(rootResponse);
        }
        return results;
    }


}
