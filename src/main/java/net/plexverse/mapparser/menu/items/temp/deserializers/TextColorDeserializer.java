package net.plexverse.mapparser.menu.items.temp.deserializers;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import net.kyori.adventure.text.format.TextColor;

import java.io.IOException;
import java.io.Serial;

public final class TextColorDeserializer extends StdDeserializer<TextColor> {
    @Serial
    private static final long serialVersionUID = -6169873768314212402L;

    public TextColorDeserializer() {
        super(TextColor.class);
    }

    @Override
    public TextColor deserialize(final JsonParser jsonParser, final DeserializationContext ctxt) throws IOException {
        final JsonNode node = jsonParser.getCodec().readTree(jsonParser);

        final String hex = node.get("hex").asText();
        return TextColor.fromHexString(hex);
    }
}