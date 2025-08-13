
package br.com.anymarket.sdk.i18any;

public class BulkTranslationResultErrorMessage {

    private Long transmissionErrorId;
    private String origin;
    private String internationalizedMessage;
    private String internationalizedReason;
    private String internationalizedActionToFix;
    private String internationalizedArticleLink;
    private TransmissionMessageCategory internationalizedErrorCategory;
    private Boolean isApproved;

    public BulkTranslationResultErrorMessage() {}

    public BulkTranslationResultErrorMessage(
        Long transmissionErrorId,
        String origin,
        String internationalizedMessage,
        String internationalizedReason,
        String internationalizedActionToFix,
        String internationalizedArticleLink,
        TransmissionMessageCategory internationalizedErrorCategory,
        Boolean isApproved
    ) {
        this.transmissionErrorId = transmissionErrorId;
        this.origin = origin;
        this.internationalizedMessage = internationalizedMessage;
        this.internationalizedReason = internationalizedReason;
        this.internationalizedActionToFix = internationalizedActionToFix;
        this.internationalizedArticleLink = internationalizedArticleLink;
        this.internationalizedErrorCategory = internationalizedErrorCategory;
        this.isApproved = isApproved;
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

    public static BulkTranslationResultErrorMessage.BulkTranslationResultErrorMessageBuilder builder() {
        return new BulkTranslationResultErrorMessage.BulkTranslationResultErrorMessageBuilder();
    }

    public static class BulkTranslationResultErrorMessageBuilder {
        private Long transmissionErrorId;
        private String origin;
        private String internationalizedMessage;
        private String internationalizedReason;
        private String internationalizedActionToFix;
        private String internationalizedArticleLink;
        private TransmissionMessageCategory internationalizedErrorCategory;
        private Boolean isApproved;

        BulkTranslationResultErrorMessageBuilder() {}

        public BulkTranslationResultErrorMessage.BulkTranslationResultErrorMessageBuilder transmissionErrorId(Long transmissionErrorId) {
            this.transmissionErrorId = transmissionErrorId;
            return this;
        }

        public BulkTranslationResultErrorMessage.BulkTranslationResultErrorMessageBuilder origin(String origin) {
            this.origin = origin;
            return this;
        }

        public BulkTranslationResultErrorMessage.BulkTranslationResultErrorMessageBuilder internationalizedMessage(String internationalizedMessage) {
            this.internationalizedMessage = internationalizedMessage;
            return this;
        }

        public BulkTranslationResultErrorMessage.BulkTranslationResultErrorMessageBuilder internationalizedReason(String internationalizedReason) {
            this.internationalizedReason = internationalizedReason;
            return this;
        }

        public BulkTranslationResultErrorMessage.BulkTranslationResultErrorMessageBuilder internationalizedActionToFix(String internationalizedActionToFix) {
            this.internationalizedActionToFix = internationalizedActionToFix;
            return this;
        }

        public BulkTranslationResultErrorMessage.BulkTranslationResultErrorMessageBuilder internationalizedArticleLink(String internationalizedArticleLink) {
            this.internationalizedArticleLink = internationalizedArticleLink;
            return this;
        }

        public BulkTranslationResultErrorMessage.BulkTranslationResultErrorMessageBuilder internationalizedErrorCategory(TransmissionMessageCategory internationalizedErrorCategory) {
            this.internationalizedErrorCategory = internationalizedErrorCategory;
            return this;
        }

        public BulkTranslationResultErrorMessage.BulkTranslationResultErrorMessageBuilder isApproved(Boolean isApproved) {
            this.isApproved = isApproved;
            return this;
        }

        public BulkTranslationResultErrorMessage build() {
            return new BulkTranslationResultErrorMessage(
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

    @Override
    public String toString() {
        return String.format(
            "BulkTranslationResultErrorMessage(transmissionErrorId=%s, origin=%s, internationalizedMessage=%s, internationalizedReason=%s, internationalizedActionToFix=%s, internationalizedArticleLink=%s, internationalizedErrorCategory=%s, isApproved=%s)",
            transmissionErrorId,
            origin,
            internationalizedMessage,
            internationalizedReason,
            internationalizedActionToFix,
            internationalizedArticleLink,
            internationalizedErrorCategory,
            isApproved
        );
    }

}
