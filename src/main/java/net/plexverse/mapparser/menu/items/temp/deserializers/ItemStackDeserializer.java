package net.plexverse.mapparser.menu.items.temp.deserializers;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import org.bukkit.inventory.ItemStack;

import java.io.IOException;
import java.io.Serial;

public final class ItemStackDeserializer extends StdDeserializer<ItemStack> {
    @Serial
    private static final long serialVersionUID = 6851665916212902994L;

    public ItemStackDeserializer() {
        super(ItemStack.class);
    }

    @Override
    public ItemStack deserialize(final JsonParser jsonParser, final DeserializationContext ctxt) throws IOException {
        final JsonNode node = jsonParser.getCodec().readTree(jsonParser);
        final byte[] nbtData = node.get("nbt").binaryValue();
        return ItemStack.deserializeBytes(nbtData);
    }
}