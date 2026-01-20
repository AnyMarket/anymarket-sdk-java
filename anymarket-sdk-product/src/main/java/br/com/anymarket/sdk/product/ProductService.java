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

import java.util.*;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import static br.com.anymarket.sdk.http.headers.AnymarketHeaderUtils.addModuleOriginHeader;
import static com.google.common.base.Strings.isNullOrEmpty;
import static java.lang.String.format;

public class ProductService extends HttpService {

    public static final TypeReference<Page<Product>> PAGED_TYPE_REFERENCE = new TypeReference<Page<Product>>() {
    };
    private static final String PRODUCTS_URI = "/products";
    private static final String PRODUCT_PATCH_URI = "/products/patch/%s";
    private static final String PRODUCT_MERGE_PATCH_URI = "/products/%s";


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


    public Product mergePatchProduct(Product product, IntegrationHeader... headers) {
        Objects.requireNonNull(product, "Informe o produto a ser atualizado via merge patch.");
        Objects.requireNonNull(product.getId(), "Informe o id do produto.");

        Long productId = product.getId();

        Map<String, Object> body = buildProductMergePatchBody(product);

        if (body.isEmpty()) {
            throw new IllegalArgumentException("Nenhum campo para atualizar via merge patch (todos null/ignorados).");
        }

        String url = String.format(apiEndPoint.concat(PRODUCT_MERGE_PATCH_URI), productId.toString());

        IntegrationHeader[] withOrigin = addModuleOriginHeader(headers, this.moduleOrigin);

        IntegrationHeader[] finalHeaders = org.apache.commons.lang3.ArrayUtils.add(
                withOrigin,
                new ContentTypeHeader("application/merge-patch+json")
        );

        RequestBodyEntity patchReq = patch(url, body, finalHeaders);

        Response response = execute(patchReq, HttpStatus.SC_OK, () -> format("Failed to merge patch product - id %s", productId));

        return response.to(Product.class);
    }


    private Map<String, Object> buildProductMergePatchBody(Product product) {
        Map<String, Object> map = br.com.anymarket.sdk.http.Mapper.get()
                .convertValue(product, new com.fasterxml.jackson.core.type.TypeReference<Map<String, Object>>() {});

        map.remove("id");
        map.remove("skus");
        map.remove("images");
        map.remove("marketplaceImages");
        map.remove("imagesForDelete");
        map.remove("kitComponents");

        Object category = map.get("category");
        if (category instanceof Map) {
            Object id = ((Map) category).get("id");
            if (id != null) {
                Map<String, Object> cat = new HashMap<>();
                cat.put("id", id);
                map.put("category", cat);
            } else {
                map.remove("category");
            }
        }

        Object brand = map.get("brand");
        if (brand instanceof Map) {
            Map b = (Map) brand;
            Object id = b.get("id");
            Object name = b.get("name");
            Object partnerId = b.get("partnerId");

            boolean hasAny = id != null || name != null || partnerId != null;
            if (hasAny) {
                Map<String, Object> br = new HashMap<>();
                if (id != null) br.put("id", id);
                if (partnerId != null) br.put("partnerId", partnerId);
                if (name != null) br.put("name", name);
                map.put("brand", br);
            } else {
                map.remove("brand");
            }
        }

        Object nbm = map.get("nbm");
        if (nbm instanceof Map) {
            Object id = ((Map) nbm).get("id");
            if (id != null) {
                Map<String, Object> n = new HashMap<>();
                n.put("id", id);
                map.put("nbm", n);
            } else {
                map.remove("nbm");
            }
        }

        Object origin = map.get("origin");
        if (origin instanceof Map) {
            Object id = ((Map) origin).get("id");
            if (id != null) {
                Map<String, Object> o = new HashMap<>();
                o.put("id", id);
                map.put("origin", o);
            } else {
                map.remove("origin");
            }
        }

        Map<String, Object> cleaned = new LinkedHashMap<>();
        for (Map.Entry<String, Object> e : map.entrySet()) {
            if (e.getValue() != null) {
                cleaned.put(e.getKey(), e.getValue());
            }
        }

        return cleaned;
    }


    private void addReplace(List<JsonPatchOperation> ops, String path, Object value) {
        if (value != null) {
            ops.add(JsonPatchOperation.replace(path, value));
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
