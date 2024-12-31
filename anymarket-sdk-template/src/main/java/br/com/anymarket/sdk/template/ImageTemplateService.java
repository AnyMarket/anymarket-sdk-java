package br.com.anymarket.sdk.template;

import br.com.anymarket.sdk.http.headers.IntegrationHeader;
import br.com.anymarket.sdk.template.dto.ImageTemplate;
import br.com.anymarket.sdk.template.dto.ImageTemplateImage;

import java.util.List;

public class ImageTemplateService extends AbstractTemplateService<ImageTemplate> {

    private static final String TEMPLATE_URI = "/imageTemplates";

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
        return getList(path, "Images", headers);
    }


}
