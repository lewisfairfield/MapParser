package net.plexverse.mapparser.menu.items.data.serializers;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import org.bukkit.NamespacedKey;

import java.io.IOException;
import java.io.Serial;

public final class NamespacedKeySerializer extends StdSerializer<NamespacedKey> {
    @Serial
    private static final long serialVersionUID = 4926844308492356742L;

    public NamespacedKeySerializer() {
        super(NamespacedKey.class);
    }

    @Override
    public void serialize(final NamespacedKey value, final JsonGenerator gen, final SerializerProvider provider)
            throws IOException {
        gen.writeStartObject();
        gen.writeStringField("namespace", value.getNamespace());
        gen.writeStringField("key", value.getKey());
        gen.writeEndObject();
    }
}