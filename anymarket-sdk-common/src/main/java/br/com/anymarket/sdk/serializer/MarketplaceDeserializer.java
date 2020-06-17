package br.com.anymarket.sdk.serializer;

import br.com.anymarket.sdk.MarketPlace;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;

import java.io.IOException;

public class MarketplaceDeserializer extends JsonDeserializer<MarketPlace> {

    @Override
    public MarketPlace deserialize(JsonParser jsonParser, DeserializationContext ctxt) throws IOException {
        ObjectCodec oc = jsonParser.getCodec();
        JsonNode node = oc.readTree(jsonParser);
        if (node.isTextual()) {
            return MarketPlace.valueOf(node.asText());
        }
        return null;
    }
}
