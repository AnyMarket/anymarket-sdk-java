package br.com.anymarket.sdk.serializer;

import br.com.anymarket.sdk.MarketPlace;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;

import static com.google.common.base.Strings.isNullOrEmpty;

public class MarketplaceSerializer extends JsonSerializer<MarketPlace> {

    @Override
    public void serialize(MarketPlace marketPlace, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException, JsonProcessingException {
        if (marketPlace != null && !isNullOrEmpty(marketPlace.name())) {
            jsonGenerator.writeString(marketPlace.name());
        }
    }
}
