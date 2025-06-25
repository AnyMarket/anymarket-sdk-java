package br.com.anymarket.sdk.productsync;

import br.com.anymarket.sdk.product.dto.Image;

import java.net.MalformedURLException;
import java.net.URL;

public class MarketplaceProductImage extends Image {

    public MarketplaceProductImage(String url, String variation) {
        URL formattedUrl = this.toUrl(url);
        super.setUrl(formattedUrl);
        super.setVariation(variation);
    }

    private URL toUrl(String url) {
        try {
            return new URL(url);
        } catch (MalformedURLException var3) {
            return null;
        }
    }

    public static MarketplaceProductImageBuilder builder() {
        return new MarketplaceProductImageBuilder();
    }

    public static class MarketplaceProductImageBuilder {

        private String url;
        private String variation;

        MarketplaceProductImageBuilder() {}

        public MarketplaceProductImageBuilder url(final String url) {
            this.url = url;
            return this;
        }

        public MarketplaceProductImageBuilder variation(final String variation) {
            this.variation = variation;
            return this;
        }

        public MarketplaceProductImage build() {
            return new MarketplaceProductImage(this.url, this.variation);
        }

    }

}
