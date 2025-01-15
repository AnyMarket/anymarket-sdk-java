package br.com.anymarket.sdk.product.dto;

import br.com.anymarket.sdk.dto.ImageStatus;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.MoreObjects;

import java.net.URL;

public class Image {

    public Image() {
    }

    public Image(final URL url) {
        this.url = url;
    }

    public Image(final URL url, final String variation, final boolean main) {
        this.url = url;
        this.variation = variation;
        this.main = main;
    }

    @JsonProperty("id")
    private Long id;

    @JsonProperty("index")
    private int index;

    @JsonProperty("variation")
    private String variation;

    @JsonProperty("thumbnailUrl")
    private URL thumbnailUrl;

    @JsonProperty("standardUrl")
    private URL standardUrl;

    @JsonProperty("lowResolutionUrl")
    private URL lowResolutionUrl;

    @JsonProperty("originalImage")
    private URL originalImageInS3URL;

    @JsonProperty("url")
    private URL url;

    @JsonProperty("statusMessage")
    private String statusMessage;

    @JsonProperty("status")
    private ImageStatus status;

    @JsonProperty("main")
    private boolean main;

    @JsonProperty("standardWidth")
    private Integer standardWidth;

    @JsonProperty("standardHeight")
    private Integer standardHeight;

    @JsonProperty("originalWidth")
    private Integer originalWidth;

    @JsonProperty("originalHeight")
    private Integer originalHeight;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getVariation() {
        return variation;
    }

    public void setVariation(String variation) {
        this.variation = variation;
    }

    public URL getThumbnailUrl() {
        return thumbnailUrl;
    }

    public void setThumbnailUrl(URL thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }

    public URL getStandardUrl() {
        return standardUrl;
    }

    public void setStandardUrl(URL standardUrl) {
        this.standardUrl = standardUrl;
    }

    public URL getLowResolutionUrl() {
        return lowResolutionUrl;
    }

    public void setLowResolutionUrl(URL lowResolutionUrl) {
        this.lowResolutionUrl = lowResolutionUrl;
    }

    public URL getOriginalImageInS3URL() {
        return originalImageInS3URL;
    }

    public void setOriginalImageInS3URL(URL originalImageInS3URL) {
        this.originalImageInS3URL = originalImageInS3URL;
    }

    public URL getUrl() {
        return url;
    }

    public void setUrl(URL url) {
        this.url = url;
    }

    public String getStatusMessage() {
        return statusMessage;
    }

    public void setStatusMessage(String statusMessage) {
        this.statusMessage = statusMessage;
    }

    public ImageStatus getStatus() {
        return status;
    }

    public void setStatus(ImageStatus status) {
        this.status = status;
    }

    public boolean isMain() {
        return main;
    }

    public void setMain(boolean main) {
        this.main = main;
    }

    public Integer getStandardWidth() {
        return standardWidth;
    }

    public void setStandardWidth(Integer standardWidth) {
        this.standardWidth = standardWidth;
    }

    public Integer getStandardHeight() {
        return standardHeight;
    }

    public void setStandardHeight(Integer standardHeight) {
        this.standardHeight = standardHeight;
    }

    public Integer getOriginalWidth() {
        return originalWidth;
    }

    public void setOriginalWidth(Integer originalWidth) {
        this.originalWidth = originalWidth;
    }

    public Integer getOriginalHeight() {
        return originalHeight;
    }

    public void setOriginalHeight(Integer originalHeight) {
        this.originalHeight = originalHeight;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
            .add("id", id)
            .add("index", index)
            .add("variation", variation)
            .add("thumbnailUrl", thumbnailUrl)
            .add("standardUrl", standardUrl)
            .add("lowResolutionUrl", lowResolutionUrl)
            .add("originalImage", originalImageInS3URL)
            .add("url", url)
            .add("statusMessage", statusMessage)
            .add("status", status)
            .add("main", main)
            .add("standardWidth", standardWidth)
            .add("standardHeight", standardHeight)
            .add("originalWidth", originalWidth)
            .add("originalHeight", originalHeight)
            .toString();
    }
}
