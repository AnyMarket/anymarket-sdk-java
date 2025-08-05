
package br.com.anymarket.sdk.i18any;

public class BulkTranslationErrorMessage {

    private String tenantId;
    private Long publicationId;
    private Long transmissionErrorId;
    private String targetLanguage;
    private String origin;
    private String message;

    public BulkTranslationErrorMessage() {}

    public BulkTranslationErrorMessage(
        String tenantId,
        Long publicationId,
        Long transmissionErrorId,
        String targetLanguage,
        String origin,
        String message
    ) {
        this.tenantId = tenantId;
        this.publicationId = publicationId;
        this.transmissionErrorId = transmissionErrorId;
        this.targetLanguage = targetLanguage;
        this.origin = origin;
        this.message = message;
    }

    public String getTenantId() {
        return this.tenantId;
    }

    public Long getPublicationId() {
        return this.publicationId;
    }

    public Long getTransmissionErrorId() {
        return this.transmissionErrorId;
    }

    public String getTargetLanguage() {
        return this.targetLanguage;
    }

    public String getOrigin() {
        return this.origin;
    }

    public String getMessage() {
        return this.message;
    }

    public void setTenantId(final String tenantId) {
        this.tenantId = tenantId;
    }

    public void setPublicationId(final Long publicationId) {
        this.publicationId = publicationId;
    }

    public void setTransmissionErrorId(final Long transmissionErrorId) {
        this.transmissionErrorId = transmissionErrorId;
    }

    public void setTargetLanguage(final String targetLanguage) {
        this.targetLanguage = targetLanguage;
    }

    public void setOrigin(final String origin) {
        this.origin = origin;
    }

    public void setMessage(final String message) {
        this.message = message;
    }

    public static BulkTranslationErrorMessageBuilder builder() {
        return new BulkTranslationErrorMessageBuilder();
    }

    public static class BulkTranslationErrorMessageBuilder {
        private String tenantId;
        private Long publicationId;
        private Long transmissionErrorId;
        private String targetLanguage;
        private String origin;
        private String message;

        BulkTranslationErrorMessageBuilder() {}

        public BulkTranslationErrorMessageBuilder tenantId(final String tenantId) {
            this.tenantId = tenantId;
            return this;
        }

        public BulkTranslationErrorMessageBuilder publicationId(final Long publicationId) {
            this.publicationId = publicationId;
            return this;
        }

        public BulkTranslationErrorMessageBuilder transmissionErrorId(final Long transmissionErrorId) {
            this.transmissionErrorId = transmissionErrorId;
            return this;
        }

        public BulkTranslationErrorMessageBuilder targetLanguage(final String targetLanguage) {
            this.targetLanguage = targetLanguage;
            return this;
        }

        public BulkTranslationErrorMessageBuilder origin(final String origin) {
            this.origin = origin;
            return this;
        }

        public BulkTranslationErrorMessageBuilder message(final String message) {
            this.message = message;
            return this;
        }

        public BulkTranslationErrorMessage build() {
            return new BulkTranslationErrorMessage(this.tenantId, this.publicationId, this.transmissionErrorId, this.targetLanguage, this.origin, this.message);
        }

    }

}
