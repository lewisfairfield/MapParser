package net.plexverse.mapparser.menu.items.temp.serializers;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import org.bukkit.inventory.ItemStack;

import java.io.IOException;
import java.io.Serial;

public final class ItemStackSerializer extends StdSerializer<ItemStack> {
    @Serial
    private static final long serialVersionUID = -1268441068101569017L;

    public ItemStackSerializer() {
        super(ItemStack.class);
    }

    @Override
    public void serialize(final ItemStack value, final JsonGenerator gen, final SerializerProvider provider)
            throws IOException {
        gen.writeStartObject();
        gen.writeBinaryField("nbt", value.serializeAsBytes());
        gen.writeEndObject();
    }
}