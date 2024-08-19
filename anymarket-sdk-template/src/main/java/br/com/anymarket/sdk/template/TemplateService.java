package br.com.anymarket.sdk.template;

import br.com.anymarket.sdk.template.dto.Template;

public class TemplateService extends AbstractTemplateService<Template> {

    private static final String TEMPLATE_URI = "/template";

    public TemplateService(String apiEndPoint) {
        super(TEMPLATE_URI, apiEndPoint);
    }

    public TemplateService(String apiEndPoint, String origin) {
        super(TEMPLATE_URI, apiEndPoint, origin);
    }

    @Override
    protected Class<Template> templateClazz() {
        return Template.class;
    }

}
