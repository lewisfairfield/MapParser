package net.plexverse.mapparser.menu.items.data.deserializers;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;

import java.io.IOException;
import java.io.Serial;

public final class NamespacedKeyDeserializer extends StdDeserializer<NamespacedKey> {
    @Serial
    private static final long serialVersionUID = -583783178305948648L;

    public NamespacedKeyDeserializer() {
        super(ItemStack.class);
    }

    @Override
    public NamespacedKey deserialize(final JsonParser jsonParser, final DeserializationContext ctxt)
            throws IOException {
        final JsonNode node = jsonParser.getCodec().readTree(jsonParser);
        final String namespace = node.get("namespace").asText();
        final String key = node.get("key").asText();
        return new NamespacedKey(namespace, key);
    }
}