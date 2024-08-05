package br.com.anymarket.sdk.template;

import br.com.anymarket.sdk.template.dto.TitleTemplate;

public class TitleTemplateService extends AbstractTemplateService<TitleTemplate> {

    private static final String TEMPLATE_URI = "/titleTemplates";

    public TitleTemplateService(String apiEndPoint) {
        super(TEMPLATE_URI, apiEndPoint);
    }

    public TitleTemplateService(String apiEndPoint, String origin) {
        super(TEMPLATE_URI, apiEndPoint, origin);
    }

    @Override
    protected Class<TitleTemplate> templateClazz() {
        return TitleTemplate.class;
    }

}
