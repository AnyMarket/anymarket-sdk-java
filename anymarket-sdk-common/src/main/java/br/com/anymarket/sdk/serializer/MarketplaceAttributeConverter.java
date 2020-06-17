package br.com.anymarket.sdk.serializer;

import br.com.anymarket.sdk.MarketPlace;

import javax.persistence.AttributeConverter;

public class MarketplaceAttributeConverter implements AttributeConverter<MarketPlace, String> {

    @Override
    public String convertToDatabaseColumn(MarketPlace marketPlace) {
        return marketPlace.name();
    }

    @Override
    public MarketPlace convertToEntityAttribute(String marketplaceName) {
        return MarketPlace.valueOf(marketplaceName);
    }
}
