package br.com.anymarket.sdk.i18any;

import java.util.HashSet;
import java.util.Set;

public class BulkTranslationResultMessage {

    private String tenantId;
    private Long publicationId;
    private Set<BulkTranslationResultErrorMessage> messages = new HashSet<>();

    public BulkTranslationResultMessage() {}

    public BulkTranslationResultMessage(
        String tenantId,
        Long publicationId,
        Set<BulkTranslationResultErrorMessage> messages
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

    public Set<BulkTranslationResultErrorMessage> getMessages() {
        return messages;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    public void setPublicationId(Long publicationId) {
        this.publicationId = publicationId;
    }

    public void setMessages(Set<BulkTranslationResultErrorMessage> messages) {
        this.messages = messages;
    }

    public static BulkTranslationResultMessageBuilder builder() {
        return new BulkTranslationResultMessageBuilder();
    }

    public static class BulkTranslationResultMessageBuilder {
        private String tenantId;
        private Long publicationId;
        private Set<BulkTranslationResultErrorMessage> messages = new HashSet<>();

        BulkTranslationResultMessageBuilder() {}

        public BulkTranslationResultMessageBuilder tenantId(String tenantId) {
            this.tenantId = tenantId;
            return this;
        }

        public BulkTranslationResultMessageBuilder publicationId(Long publicationId) {
            this.publicationId = publicationId;
            return this;
        }

        public BulkTranslationResultMessageBuilder messages(Set<BulkTranslationResultErrorMessage> messages) {
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

    public String toString() {
        return "BulkTranslationResultMessage(tenantId=" + this.getTenantId() + ", publicationId=" + this.getPublicationId() + ", messages=" + this.getMessages() + ")";
    }

}
