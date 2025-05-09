package br.com.anymarket.sdk.template;

import br.com.anymarket.sdk.http.headers.IntegrationHeader;
import br.com.anymarket.sdk.template.dto.ImageTemplate;
import br.com.anymarket.sdk.template.dto.ImageTemplateImage;
import br.com.anymarket.sdk.util.MapTo;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;

public class ImageTemplateService extends AbstractTemplateService<ImageTemplate> {

    private static final String TEMPLATE_URI = "/imageTemplates";
    private static final ObjectMapper MAPPER = new ObjectMapper();

    public ImageTemplateService(String apiEndPoint) {
        super(TEMPLATE_URI, apiEndPoint);
    }

    public ImageTemplateService(String apiEndPoint, String origin) {
        super(TEMPLATE_URI, apiEndPoint, origin);
    }

    @Override
    protected Class<ImageTemplate> templateClazz() {
        return ImageTemplate.class;
    }

    public List<ImageTemplateImage> getImages(Long  imageTemplateId, IntegrationHeader... headers) {
        String path = String.format("/%s/images", imageTemplateId);
        List<ImageTemplateImage> images = getList(path, "Images", headers);
        // An ArrayList of LinkedHashMap is returned from the getList. That's why this map is needed.
        Class<ImageTemplateImage> type = ImageTemplateImage.class;
        return MapTo.map(MAPPER, type, images);
    }

}
