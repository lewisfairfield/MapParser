package net.plexverse.mapparser.menu.items.temp.serializers;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import org.bukkit.Color;

import java.io.IOException;
import java.io.Serial;
import java.util.Map;

public final class ColorSerializer extends StdSerializer<Color> {
    @Serial
    private static final long serialVersionUID = 8898503800207345159L;

    public ColorSerializer() {
        super(Color.class);
    }

    @Override
    public void serialize(final Color value, final JsonGenerator gen, final SerializerProvider provider)
            throws IOException {
        final Map<String, Object> serialize = value.serialize();
        gen.writeObject(serialize);
    }
}