package br.com.anymarket.sdk.i18any;

public class BulkTranslationResultMessage {

    private String tenantId;
    private Long publicationId;
    private Long transmissionErrorId;
    private String origin;
    private String internationalizedMessage;
    private String internationalizedReason;
    private String internationalizedActionToFix;
    private String internationalizedArticleLink;
    private TransmissionMessageCategory internationalizedErrorCategory;
    private Boolean isApproved;

    public BulkTranslationResultMessage() {}

    public BulkTranslationResultMessage(
        String tenantId,
        Long publicationId,
        Long transmissionErrorId,
        String origin,
        String internationalizedMessage,
        String internationalizedReason,
        String internationalizedActionToFix,
        String internationalizedArticleLink,
        TransmissionMessageCategory internationalizedErrorCategory,
        Boolean isApproved
    ) {
        this.tenantId = tenantId;
        this.publicationId = publicationId;
        this.transmissionErrorId = transmissionErrorId;
        this.origin = origin;
        this.internationalizedMessage = internationalizedMessage;
        this.internationalizedReason = internationalizedReason;
        this.internationalizedActionToFix = internationalizedActionToFix;
        this.internationalizedArticleLink = internationalizedArticleLink;
        this.internationalizedErrorCategory = internationalizedErrorCategory;
        this.isApproved = isApproved;
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

    public String getOrigin() {
        return this.origin;
    }

    public String getInternationalizedMessage() {
        return this.internationalizedMessage;
    }

    public String getInternationalizedReason() {
        return this.internationalizedReason;
    }

    public String getInternationalizedActionToFix() {
        return this.internationalizedActionToFix;
    }

    public String getInternationalizedArticleLink() {
        return this.internationalizedArticleLink;
    }

    public TransmissionMessageCategory getInternationalizedErrorCategory() {
        return this.internationalizedErrorCategory;
    }

    public Boolean getIsApproved() {
        return this.isApproved;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    public void setPublicationId(Long publicationId) {
        this.publicationId = publicationId;
    }

    public void setTransmissionErrorId(Long transmissionErrorId) {
        this.transmissionErrorId = transmissionErrorId;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public void setInternationalizedMessage(String internationalizedMessage) {
        this.internationalizedMessage = internationalizedMessage;
    }

    public void setInternationalizedReason(String internationalizedReason) {
        this.internationalizedReason = internationalizedReason;
    }

    public void setInternationalizedActionToFix(String internationalizedActionToFix) {
        this.internationalizedActionToFix = internationalizedActionToFix;
    }

    public void setInternationalizedArticleLink(String internationalizedArticleLink) {
        this.internationalizedArticleLink = internationalizedArticleLink;
    }

    public void setInternationalizedErrorCategory(TransmissionMessageCategory internationalizedErrorCategory) {
        this.internationalizedErrorCategory = internationalizedErrorCategory;
    }

    public void setIsApproved(Boolean isApproved) {
        this.isApproved = isApproved;
    }

    public static BulkTranslationResultMessageBuilder builder() {
        return new BulkTranslationResultMessageBuilder();
    }

    public static class BulkTranslationResultMessageBuilder {
        private String tenantId;
        private Long publicationId;
        private Long transmissionErrorId;
        private String origin;
        private String internationalizedMessage;
        private String internationalizedReason;
        private String internationalizedActionToFix;
        private String internationalizedArticleLink;
        private TransmissionMessageCategory internationalizedErrorCategory;
        private Boolean isApproved;

        BulkTranslationResultMessageBuilder() {}

        public BulkTranslationResultMessageBuilder tenantId(String tenantId) {
            this.tenantId = tenantId;
            return this;
        }

        public BulkTranslationResultMessageBuilder publicationId(Long publicationId) {
            this.publicationId = publicationId;
            return this;
        }

        public BulkTranslationResultMessageBuilder transmissionErrorId(Long transmissionErrorId) {
            this.transmissionErrorId = transmissionErrorId;
            return this;
        }

        public BulkTranslationResultMessageBuilder origin(String origin) {
            this.origin = origin;
            return this;
        }

        public BulkTranslationResultMessageBuilder internationalizedMessage(String internationalizedMessage) {
            this.internationalizedMessage = internationalizedMessage;
            return this;
        }

        public BulkTranslationResultMessageBuilder internationalizedReason(String internationalizedReason) {
            this.internationalizedReason = internationalizedReason;
            return this;
        }

        public BulkTranslationResultMessageBuilder internationalizedActionToFix(String internationalizedActionToFix) {
            this.internationalizedActionToFix = internationalizedActionToFix;
            return this;
        }

        public BulkTranslationResultMessageBuilder internationalizedArticleLink(String internationalizedArticleLink) {
            this.internationalizedArticleLink = internationalizedArticleLink;
            return this;
        }

        public BulkTranslationResultMessageBuilder internationalizedErrorCategory(TransmissionMessageCategory internationalizedErrorCategory) {
            this.internationalizedErrorCategory = internationalizedErrorCategory;
            return this;
        }

        public BulkTranslationResultMessageBuilder isApproved(Boolean isApproved) {
            this.isApproved = isApproved;
            return this;
        }

        public BulkTranslationResultMessage build() {
            return new BulkTranslationResultMessage(
                this.tenantId,
                this.publicationId,
                this.transmissionErrorId,
                this.origin,
                this.internationalizedMessage,
                this.internationalizedReason,
                this.internationalizedActionToFix,
                this.internationalizedArticleLink,
                this.internationalizedErrorCategory,
                this.isApproved
            );
        }

    }

}
