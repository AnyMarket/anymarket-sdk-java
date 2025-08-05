
package br.com.anymarket.sdk.i18any;

public class BulkTranslationErrorMessage {

    private Long transmissionErrorId;
    private String targetLanguage;
    private String origin;
    private String message;

    public BulkTranslationErrorMessage() {}

    public BulkTranslationErrorMessage(
        Long transmissionErrorId,
        String targetLanguage,
        String origin,
        String message
    ) {
        this.transmissionErrorId = transmissionErrorId;
        this.targetLanguage = targetLanguage;
        this.origin = origin;
        this.message = message;
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
        private Long transmissionErrorId;
        private String targetLanguage;
        private String origin;
        private String message;

        BulkTranslationErrorMessageBuilder() {}

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
            return new BulkTranslationErrorMessage(
                this.transmissionErrorId,
                this.targetLanguage,
                this.origin,
                this.message
            );
        }
    }

    public String toString() {
        return "BulkTranslationErrorMessage(transmissionErrorId=" + this.getTransmissionErrorId() + ", targetLanguage=" + this.getTargetLanguage() + ", origin=" + this.getOrigin() + ", message=" + this.getMessage() + ")";
    }

}
