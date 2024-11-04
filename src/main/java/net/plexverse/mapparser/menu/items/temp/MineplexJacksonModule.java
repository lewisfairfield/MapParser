package net.plexverse.mapparser.menu.items.temp;

import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleDeserializers;
import com.fasterxml.jackson.databind.module.SimpleSerializers;
import net.kyori.adventure.text.format.TextColor;
import net.plexverse.mapparser.menu.items.temp.deserializers.*;
import net.plexverse.mapparser.menu.items.temp.serializers.*;
import org.bukkit.Color;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;

public class MineplexJacksonModule extends Module {

    /**
     * Method that returns a display that can be used by Jackson
     * for informational purposes, as well as in associating extensions with
     * module that provides them.
     */
    @Override
    public String getModuleName() {
        return "MineplexJackson";
    }

    /**
     * Method that returns version of this module. Can be used by Jackson for
     * informational purposes.
     */
    @Override
    public Version version() {
        return new Version(1, 0, 0, "", "com.mineplex.studio.jackson", "mineplex-jackson");
    }

    /**
     * Method called by {@link ObjectMapper} when module is registered.
     * It is called to let module register functionality it provides,
     * using callback methods passed-in context object exposes.
     */
    @Override
    public void setupModule(final SetupContext setupContext) {
        final SimpleSerializers serializers = new SimpleSerializers();
        serializers.addSerializer(ItemStack.class, new ItemStackSerializer());
        serializers.addSerializer(Enchantment.class, new EnchantmentSerializer());
        serializers.addSerializer(NamespacedKey.class, new NamespacedKeySerializer());
        serializers.addSerializer(TextColor.class, new TextColorSerializer());
        serializers.addSerializer(Color.class, new ColorSerializer());

        final SimpleDeserializers deserializers = new SimpleDeserializers();
        deserializers.addDeserializer(ItemStack.class, new ItemStackDeserializer());
        deserializers.addDeserializer(Enchantment.class, new EnchantmentDeserializer());
        deserializers.addDeserializer(NamespacedKey.class, new NamespacedKeyDeserializer());
        deserializers.addDeserializer(TextColor.class, new TextColorDeserializer());
        deserializers.addDeserializer(Color.class, new ColorDeserializer());

        setupContext.addSerializers(serializers);
        setupContext.addDeserializers(deserializers);
    }
}