package br.com.anymarket.sdk.i18any;

import java.util.ArrayList;
import java.util.List;

public class BulkTranslationResultMessage {

    private String tenantId;
    private Long publicationId;
    private List<BulkTranslationResultErrorMessage> messages = new ArrayList<>();

    public BulkTranslationResultMessage() {}

    public BulkTranslationResultMessage(
        String tenantId,
        Long publicationId,
        List<BulkTranslationResultErrorMessage> messages
    ) {
        this.tenantId = tenantId;
        this.publicationId = publicationId;
        this.messages = messages;
    }

    public String getTenantId() {
        return this.tenantId;
    }

    public Long getPublicationId() {
        return this.publicationId;
    }

    public List<BulkTranslationResultErrorMessage> getMessages() {
        return messages;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    public void setPublicationId(Long publicationId) {
        this.publicationId = publicationId;
    }

    public void setMessages(List<BulkTranslationResultErrorMessage> messages) {
        this.messages = messages;
    }

    public static BulkTranslationResultMessageBuilder builder() {
        return new BulkTranslationResultMessageBuilder();
    }

    public static class BulkTranslationResultMessageBuilder {
        private String tenantId;
        private Long publicationId;
        private List<BulkTranslationResultErrorMessage> messages = new ArrayList<>();

        BulkTranslationResultMessageBuilder() {}

        public BulkTranslationResultMessageBuilder tenantId(String tenantId) {
            this.tenantId = tenantId;
            return this;
        }

        public BulkTranslationResultMessageBuilder publicationId(Long publicationId) {
            this.publicationId = publicationId;
            return this;
        }

        public BulkTranslationResultMessageBuilder messages(List<BulkTranslationResultErrorMessage> messages) {
            this.messages = messages;
            return this;
        }

        public BulkTranslationResultMessage build() {
            return new BulkTranslationResultMessage(
                this.tenantId,
                this.publicationId,
                this.messages
            );
        }

    }

}
