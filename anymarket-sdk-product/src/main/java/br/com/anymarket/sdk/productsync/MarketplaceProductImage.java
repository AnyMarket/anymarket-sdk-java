package br.com.anymarket.sdk.productsync;

import br.com.anymarket.sdk.product.dto.Image;
import lombok.Getter;

import java.net.MalformedURLException;
import java.net.URL;

@Getter
public class MarketplaceProductImage extends Image {

    @lombok.Builder
    public MarketplaceProductImage(String url, String variation) {
        URL formattedUrl = toUrl(url);
        super.setUrl(formattedUrl);
        super.setVariation(variation);
    }

    private URL toUrl(String url) {
        try {
            return new URL(url);
        } catch (MalformedURLException e) {
            return null;
        }
    }

}
