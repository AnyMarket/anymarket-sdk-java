package br.com.anymarket.sdk.categories;

import br.com.anymarket.sdk.SDKConstants;
import br.com.anymarket.sdk.categories.dto.Category;
import br.com.anymarket.sdk.exception.NotFoundException;
import br.com.anymarket.sdk.http.HttpService;
import br.com.anymarket.sdk.http.Response;
import br.com.anymarket.sdk.http.headers.IntegrationHeader;
import br.com.anymarket.sdk.paging.Page;
import br.com.anymarket.sdk.resource.Link;
import br.com.anymarket.sdk.util.SDKUrlEncoder;
import com.fasterxml.jackson.core.type.TypeReference;
import com.mashape.unirest.request.GetRequest;
import com.mashape.unirest.request.HttpRequestWithBody;
import com.mashape.unirest.request.body.RequestBodyEntity;
import org.apache.http.HttpStatus;

import java.util.ArrayList;
import java.util.List;

import static br.com.anymarket.sdk.http.headers.AnymarketHeaderUtils.addModuleOriginHeader;
import static com.google.common.base.Strings.isNullOrEmpty;
import static java.lang.String.format;

/**
 * Created by marcio.scharam on 17/03/2016.
 */
public class CategoryService extends HttpService {

    private static final String NEXT = "next";
    private static final String CATEGORIES_URI = "/categories/";
    private String apiEndPoint;
    private String moduleOrigin;

    @Deprecated
    /**
     * Use the constructor with the origin parameter. Origin = the name of the marketplace, epr or platform
     */
    public CategoryService(String apiEndPoint) {
        this.apiEndPoint = !isNullOrEmpty(apiEndPoint) ? apiEndPoint :
            SDKConstants.ANYMARKET_HOMOLOG_API_ENDPOINT;
    }

    public CategoryService(String apiEndPoint, String origin) {
        this.apiEndPoint = !isNullOrEmpty(apiEndPoint) ? apiEndPoint :
                SDKConstants.ANYMARKET_HOMOLOG_API_ENDPOINT;
        this.moduleOrigin = origin;
    }

    public Category updateCategory(Category category, IntegrationHeader... headers) {
        RequestBodyEntity putRequest = put(apiEndPoint + CATEGORIES_URI + category.getId(), category, addModuleOriginHeader(headers, this.moduleOrigin));
        Response response = execute(putRequest);
        return response.to(Category.class);
    }

    public Category insertCategory(Category category, IntegrationHeader... headers) {
        RequestBodyEntity postRequest = post(apiEndPoint + CATEGORIES_URI, category, addModuleOriginHeader(headers, this.moduleOrigin));
        Response response = execute(postRequest);
        return response.to(Category.class);
    }

    public void deleteCategory(Category category, IntegrationHeader... headers) {
        deleteCategory(category.getId(), headers);
    }

    public void deleteCategory(Long id, IntegrationHeader... headers) {
        HttpRequestWithBody deleteRequest = delete(apiEndPoint + CATEGORIES_URI + id, addModuleOriginHeader(headers, this.moduleOrigin));
        Response response = execute(deleteRequest);
        if (response.getStatus() != HttpStatus.SC_NO_CONTENT) {
            throw new NotFoundException(format("Category with id %s not found.", id));
        }
    }


    public Category getCategory(Long id, IntegrationHeader... headers) {
        GetRequest getRequest = get(apiEndPoint + CATEGORIES_URI + id, addModuleOriginHeader(headers, this.moduleOrigin));
        Response response = execute(getRequest);
        if (response.getStatus() == HttpStatus.SC_OK) {
            return response.to(Category.class);
        }
        throw new NotFoundException(format("Category with id %s not found.", id));
    }

    public Category getCategoryByPartnerId(String partnerId, IntegrationHeader... headers) {
        String url = apiEndPoint.concat(CATEGORIES_URI).concat("?partnerId=").concat(SDKUrlEncoder.encodeParameterToUTF8(partnerId));
        Response response = execute(get(url, addModuleOriginHeader(headers, this.moduleOrigin)));
        if (response.getStatus() == HttpStatus.SC_OK) {
            List<Category> categoryPage = response.to(new TypeReference<Page<Category>>() {}).getContent();
            if (categoryPage != null && !categoryPage.isEmpty()) {
                return categoryPage.get(0);
            }
        }
        return null;
    }

    public List<Category> findCategoryByPartnerId(String partnerId, IntegrationHeader... headers) {
        String url = apiEndPoint.concat(CATEGORIES_URI).concat("?partnerId=").concat(SDKUrlEncoder.encodeParameterToUTF8(partnerId));
        return doGetAllCategories(url, headers);
    }

    public List<Category> getAllCategories(IntegrationHeader... headers) {
        String urlToGet = apiEndPoint + CATEGORIES_URI;
        return doGetAllCategories(urlToGet, headers);
    }

    private List<Category> doGetAllCategories(String urlToGet, IntegrationHeader[] headers) {
        boolean hasMoreElements;
        ArrayList<Category> allCategories = new ArrayList<Category>();


        do {
            GetRequest getRequest = get(urlToGet, addModuleOriginHeader(headers, this.moduleOrigin));
            Response response = execute(getRequest);
            if (response.getStatus() == HttpStatus.SC_OK) {
                Page<Category> rootResponse = response.to(new TypeReference<Page<Category>>() {
                });
                for (Category root : rootResponse.getContent()) {
                    List<Category> completeRootHierarchy = getCompleteCategory(root, headers);
                    allCategories.addAll(completeRootHierarchy);
                }
                for (Link link : rootResponse.getLinks()) {
                    if (link.getRel().equals(NEXT)) {
                        urlToGet = link.getHref();
                        break;
                    }
                }
                hasMoreElements = !rootResponse.getContent().isEmpty();
            } else {
                throw new NotFoundException("Categories not found.");
            }

        }
        while (hasMoreElements);

        return allCategories;
    }

    private List<Category> getCompleteCategory(Category category, IntegrationHeader... headers) {
        List<Category> completeCategories = new ArrayList<Category>();
        Category completeCategory = getCategory(category.getId(), headers);
        completeCategories.add(completeCategory);
        completeCategories.addAll(loadCompleteChildren(completeCategory, headers));
        return completeCategories;
    }

    private List<Category> loadCompleteChildren(Category parent, IntegrationHeader... headers) {
        List<Category> completeChildren = new ArrayList<Category>();
        if (parent.getChildren() != null) {
            for (Category child : parent.getChildren()) {
                completeChildren.addAll(getCompleteCategory(child, headers));
            }
        }
        return completeChildren;
    }

}
