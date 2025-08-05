package br.com.anymarket.sdk.i18any;

import java.util.ArrayList;
import java.util.List;

public class BulkTranslationMessage {

    private String tenantId;
    private Long publicationId;
    private String targetLanguage;
    private List<BulkTranslationErrorMessage> messages = new ArrayList<>();

    public BulkTranslationMessage() {}

    public BulkTranslationMessage(
        String tenantId,
        Long publicationId,
        String targetLanguage,
        List<BulkTranslationErrorMessage> messages
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

    public List<BulkTranslationErrorMessage> getMessages() {
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

    public void setMessages(List<BulkTranslationErrorMessage> messages) {
        this.messages = messages;
    }

    public static BulkTranslationMessageBuilder builder() {
        return new BulkTranslationMessageBuilder();
    }

    public static class BulkTranslationMessageBuilder {
        private String tenantId;
        private Long publicationId;
        private String targetLanguage;
        private List<BulkTranslationErrorMessage> messages;

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

        public BulkTranslationMessageBuilder messages(List<BulkTranslationErrorMessage> messages) {
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

}
