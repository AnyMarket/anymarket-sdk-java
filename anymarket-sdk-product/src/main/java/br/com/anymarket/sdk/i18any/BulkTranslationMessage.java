package br.com.anymarket.sdk.i18any;

import java.util.HashSet;
import java.util.Set;

public class BulkTranslationMessage {

    private String tenantId;
    private Long publicationId;
    private String targetLanguage;
    private Set<BulkTranslationErrorMessage> messages = new HashSet<>();

    public BulkTranslationMessage() {}

    public BulkTranslationMessage(
        String tenantId,
        Long publicationId,
        String targetLanguage,
        Set<BulkTranslationErrorMessage> messages
    ) {
        this.tenantId = tenantId;
        this.publicationId = publicationId;
        this.targetLanguage = targetLanguage;
        this.messages = messages;
    }

    public String getTenantId() {
        return this.tenantId;
    }

    public Long getPublicationId() {
        return this.publicationId;
    }

    public String getTargetLanguage() {
        return this.targetLanguage;
    }

    public Set<BulkTranslationErrorMessage> getMessages() {
        return this.messages;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    public void setPublicationId(Long publicationId) {
        this.publicationId = publicationId;
    }

    public void setTargetLanguage(String targetLanguage) {
        this.targetLanguage = targetLanguage;
    }

    public void setMessages(Set<BulkTranslationErrorMessage> messages) {
        this.messages = messages;
    }

    public static BulkTranslationMessageBuilder builder() {
        return new BulkTranslationMessageBuilder();
    }

    public static class BulkTranslationMessageBuilder {
        private String tenantId;
        private Long publicationId;
        private String targetLanguage;
        private Set<BulkTranslationErrorMessage> messages;

        BulkTranslationMessageBuilder() {}

        public BulkTranslationMessageBuilder tenantId(String tenantId) {
            this.tenantId = tenantId;
            return this;
        }

        public BulkTranslationMessageBuilder publicationId(Long publicationId) {
            this.publicationId = publicationId;
            return this;
        }

        public BulkTranslationMessageBuilder targetLanguage(String targetLanguage) {
            this.targetLanguage = targetLanguage;
            return this;
        }

        public BulkTranslationMessageBuilder messages(Set<BulkTranslationErrorMessage> messages) {
            this.messages = messages;
            return this;
        }

        public BulkTranslationMessage build() {
            return new BulkTranslationMessage(
                this.tenantId,
                this.publicationId,
                this.targetLanguage,
                this.messages
            );
        }
    }

    @Override
    public String toString() {
        return String.format(
            "BulkTranslationMessage(tenantId=%s, publicationId=%d, targetLanguage=%s, messages=%s)",
            tenantId,
            publicationId,
            targetLanguage,
            messages
        );
    }

}
