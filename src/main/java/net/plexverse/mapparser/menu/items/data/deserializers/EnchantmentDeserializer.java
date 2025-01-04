package net.plexverse.mapparser.menu.items.data.deserializers;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;

import java.io.IOException;
import java.io.Serial;

public final class EnchantmentDeserializer extends StdDeserializer<Enchantment> {

    @Serial
    private static final long serialVersionUID = -7313436494841711744L;

    public EnchantmentDeserializer() {
        super(ItemStack.class);
    }

    @Override
    public Enchantment deserialize(final JsonParser jsonParser, final DeserializationContext ctxt) throws IOException {
        final JsonNode node = jsonParser.getCodec().readTree(jsonParser);
        final NamespacedKey namespacedKey =
                ctxt.readValue(node.get("namespacedkey").traverse(jsonParser.getCodec()), NamespacedKey.class);
        return Enchantment.getByKey(namespacedKey);
    }
}