package net.plexverse.mapparser.menu.items.temp;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.plexverse.mapparser.enums.GameType;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class KitCommand implements CommandExecutor {

    private static final ObjectMapper OBJECT_MAPPER;

    static {
        OBJECT_MAPPER = new ObjectMapper();
        OBJECT_MAPPER.registerModule(new MineplexJacksonModule());
    }

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {

        if (!(commandSender instanceof Player player)) {
            commandSender.sendMessage(MiniMessage.miniMessage().deserialize("<red>You need to be a player to execute this command!"));
            return true;
        }

        if (strings.length != 2) {
            player.sendMessage(MiniMessage.miniMessage().deserialize("<red>Usage: <white>/kitparse <gameType> <kitName>"));
            player.sendMessage(MiniMessage.miniMessage().deserialize("<red>Example: <white>/kitparse SKYWARS disrupter"));
            return true;
        }

        final GameType gameType = GameType.valueOf(strings[0].toUpperCase());
        final String kitName = strings[1];

        final String dirName = "kits/" + gameType.name() + "/" + kitName.toLowerCase(Locale.ROOT).replace(" ", "_") + ".json";
        final Map<Integer, ItemStack> items = new HashMap<>();
        for (int slot = 0; slot < player.getInventory().getSize(); slot++) {
            final ItemStack itemStack = player.getInventory().getItem(slot);
            if (itemStack == null || itemStack.getType() == Material.AIR) {
                continue;
            }

            items.put(slot, itemStack);
        }

        final File file = new File(Bukkit.getWorldContainer(), dirName);
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }

        try {
            OBJECT_MAPPER.writer().writeValue(file, items);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        player.sendMessage(MiniMessage.miniMessage().deserialize(
                "<green>You have created a kit named "
                        + kitName
                        + " for game " + gameType.getDisplayName()
                        + " It has been output to a file in the main directory "
                        + dirName));

        return true;
    }
}
