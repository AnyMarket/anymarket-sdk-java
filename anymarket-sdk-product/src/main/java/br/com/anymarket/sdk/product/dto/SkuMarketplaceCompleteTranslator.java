package br.com.anymarket.sdk.product.dto;

import br.com.anymarket.sdk.variation.Variation;

import java.util.HashMap;
import java.util.Map;

public class SkuMarketplaceCompleteTranslator {

    public Sku toSku(SkuMarketplaceComplete skuMarketplaceComplete) {
        SkuResource skuResource = skuMarketplaceComplete.getSku();

        Sku sku = new Sku();
        sku.setId(skuResource.getId());
        sku.setPartnerId(skuResource.getPartnerId());
        sku.setStockAmount(skuResource.getAmount());
        sku.setEan(skuResource.getEan());
        sku.setTitle(skuResource.getTitle());
        sku.setPrice(skuResource.getPrice());
        sku.setSellPrice(skuResource.getSellPrice());
        sku.setAdditionalTime(skuResource.getAdditionalTime());
        sku.setVolumes(skuResource.getVolumes());
        if (skuResource.getVariations() != null && !skuResource.getVariations().isEmpty()) {
            Map<String, String> variations = new HashMap<>();

            for (Variation v : skuResource.getVariations()) {
                variations.put(v.getType().getName(), v.getValue());
            }

            sku.setVariations(variations);
        }

        return sku;
    }

    public SkuComplete toSkuComplete(SkuMarketplaceComplete skuMarketplaceComplete) {
        SkuResource skuResource = skuMarketplaceComplete.getSku();

        SkuComplete sku = new SkuComplete();
        sku.setId(skuResource.getId());
        sku.setPartnerId(skuResource.getPartnerId());
        sku.setStockAmount(skuResource.getAmount());
        sku.setEan(skuResource.getEan());
        sku.setTitle(skuResource.getTitle());
        sku.setPrice(skuResource.getPrice());
        sku.setAdditionalTime(skuResource.getAdditionalTime());
        sku.setVariations(skuResource.getVariations());
        sku.setVolumes(skuResource.getVolumes());

        return sku;
    }

    public SkuMarketPlace toSkuMarketplace(SkuMarketplaceComplete skuMarketplaceComplete) {
        SkuMarketPlace skuMarketPlace = SkuMarketPlace.builder()
                .withId(skuMarketplaceComplete.getId())
                .withIdInMarketplace(skuMarketplaceComplete.getIdInMarketplace())
                .withMarketPlace(skuMarketplaceComplete.getMarketPlace())
                .withIndex(skuMarketplaceComplete.getIndex())
                .withPublicationStatus(skuMarketplaceComplete.getPublicationStatus())
                .withMarketplaceStatus(skuMarketplaceComplete.getStatusInMarketplace())
                .withTransmissionStatus(skuMarketplaceComplete.getTransmissionStatus())
                .withErrorMsg(skuMarketplaceComplete.getErrorMsg())
                .withPrice(skuMarketplaceComplete.getPrice())
                .withPriceFactor(skuMarketplaceComplete.getPriceFactor())
                .withDiscountPrice(skuMarketplaceComplete.getDiscountPrice())
                .withSkuInMarketplace(skuMarketplaceComplete.getSkuInMarketplace())
                .withMarketplaceItemCode(skuMarketplaceComplete.getMarketplaceItemCode())
                .withFields(skuMarketplaceComplete.getFields())
                .withAvailableStock(skuMarketplaceComplete.getAvailableStock())
                .withAttributes(skuMarketplaceComplete.getAttributes())
                .withSku(toSku(skuMarketplaceComplete))
                .withIdAccount(skuMarketplaceComplete.getIdAccount())
                .withAccountName(skuMarketplaceComplete.getAccountName())
                .withSkuMarketPlaceMessage(skuMarketplaceComplete.getSkuMarketPlaceMessage())
                .withOrigin(skuMarketplaceComplete.getOrigin())
                .build();

        skuMarketPlace.setIndividualAvailableStocks(skuMarketplaceComplete.getIndividualAvailableStocks());
        return skuMarketPlace;
    }

}
