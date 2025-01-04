package net.plexverse.mapparser.command;

import net.kyori.adventure.text.minimessage.MiniMessage;
import net.plexverse.mapparser.MapParser;
import net.plexverse.mapparser.mapsettings.MapSettingsManager;
import net.plexverse.mapparser.mapsettings.objects.MapMeta;
import net.plexverse.mapparser.parser.ParsingStrategy;
import net.plexverse.mapparser.parser.WorldParsingStrategy;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

public class ParseCommand implements CommandExecutor {
    private final MapParser plugin;
    private final MiniMessage miniMessage;

    public ParseCommand(final MapParser plugin) {
        this.plugin = plugin;
        this.miniMessage = MiniMessage.miniMessage();
    }

    @NotNull
    public static ParsingStrategy getParsingStrategy(final MapParser plugin, final Player player, final MapMeta mapMeta, final int radius) {
        // add any if statements here if you'd like a special parsing strategy
        return new WorldParsingStrategy(plugin, player, mapMeta.getGameType(), mapMeta.getMapName(), mapMeta.getAuthor(), radius, mapMeta.isLegacy());
    }

    @Override
    public boolean onCommand(final CommandSender sender, final Command command, final String label, final String[] args) {
        if (!(sender instanceof final Player player)) {
            sender.sendMessage(this.miniMessage.deserialize("<red>You need to be a player to execute this command!"));
            return true;
        }

        if (!player.hasPermission("command.parse.use")) {
            player.sendMessage(this.miniMessage.deserialize("<red>You do not have permission to parse the world!"));
            return true;
        }

        try {
            if (MapSettingsManager.getMapSettings(player.getWorld()).isEmpty()) {
                player.sendMessage(this.miniMessage.deserialize("<red>This map has not been setup (/mapsettings)"));
                return true;
            }
        } catch (final IOException e) {
            throw new RuntimeException(e);
        }

        if (args.length != 1) {
            player.sendMessage(this.miniMessage.deserialize("<red>Usage: <white>/parse <radiusInBlocks>"));
            player.sendMessage(this.miniMessage.deserialize("<red>Example: <white>/parse 300"));
            return true;
        }


        final int radius;
        try {
            radius = Integer.parseInt(args[0]);
        } catch (final NumberFormatException exception) {
            player.sendMessage(this.miniMessage.deserialize("<red>Invalid radius. (E.g. 50, 100, 150)"));
            return true;
        }
        final MapMeta mapMeta;
        try {
            mapMeta = MapSettingsManager.getMapSettings(player.getWorld()).get();
        } catch (final IOException e) {
            throw new RuntimeException(e);
        }

        player.sendMessage(MiniMessage.miniMessage().deserialize("<dark_purple><b>(0/8)</b> <white>Parsing map for datapoints"));

        final ParsingStrategy parsingStrategy = ParseCommand.getParsingStrategy(MapParser.getMapParser(), player, mapMeta, radius);

        parsingStrategy.parse(() -> player.sendMessage(MiniMessage.miniMessage().deserialize("<dark_purple><b>(8/8)</b> <white>Parsing has been completed!")), true);

        return true;
    }
}
