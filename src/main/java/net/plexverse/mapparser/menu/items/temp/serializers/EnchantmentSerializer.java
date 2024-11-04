package net.plexverse.mapparser.menu.items.temp.serializers;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import org.bukkit.enchantments.Enchantment;

import java.io.IOException;
import java.io.Serial;

public final class EnchantmentSerializer extends StdSerializer<Enchantment> {

    @Serial
    private static final long serialVersionUID = 1986449013692256493L;

    public EnchantmentSerializer() {
        super(Enchantment.class);
    }

    @Override
    public void serialize(final Enchantment value, final JsonGenerator gen, final SerializerProvider provider)
            throws IOException {
        gen.writeStartObject();
        provider.defaultSerializeField("namespacedkey", value.getKey(), gen);
        gen.writeEndObject();
    }
}