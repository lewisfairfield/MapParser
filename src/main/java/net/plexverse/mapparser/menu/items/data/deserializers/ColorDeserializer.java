package net.plexverse.mapparser.menu.items.data.deserializers;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import org.bukkit.Color;

import java.io.IOException;
import java.io.Serial;
import java.util.Map;

public final class ColorDeserializer extends StdDeserializer<Color> {
    @Serial
    private static final long serialVersionUID = -3757462573458359216L;

    public ColorDeserializer() {
        super(Color.class);
    }

    @Override
    public Color deserialize(final JsonParser parser, final DeserializationContext ctxt) throws IOException {
        final JsonNode node = parser.getCodec().readTree(parser);
        final Map<String, Object> map = parser.getCodec().treeToValue(node, Map.class);
        return Color.deserialize(map);
    }
}