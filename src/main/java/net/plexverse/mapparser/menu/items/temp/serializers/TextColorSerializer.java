package net.plexverse.mapparser.menu.items.temp.serializers;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import net.kyori.adventure.text.format.TextColor;

import java.io.IOException;
import java.io.Serial;

public final class TextColorSerializer extends StdSerializer<TextColor> {
    @Serial
    private static final long serialVersionUID = 4926844308492356742L;

    public TextColorSerializer() {
        super(TextColor.class);
    }

    @Override
    public void serialize(final TextColor value, final JsonGenerator gen, final SerializerProvider provider)
            throws IOException {
        gen.writeStartObject();
        gen.writeStringField("hex", value.asHexString());
        gen.writeEndObject();
    }
}