package net.plexverse.mapparser.command;

import net.kyori.adventure.text.minimessage.MiniMessage;
import net.plexverse.mapparser.MapParser;
import net.plexverse.mapparser.enums.DataPointType;
import net.plexverse.mapparser.mapsettings.MapSettingsManager;
import net.plexverse.mapparser.mapsettings.objects.MapMeta;
import net.plexverse.mapparser.parser.ParsingStrategy;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.IOException;

import static net.plexverse.mapparser.command.ParseCommand.getParsingStrategy;
import static net.plexverse.mapparser.menu.DataPointMenu.defineEntity;

public class WorldBorderCommand implements CommandExecutor {
    private final MiniMessage miniMessage;

    public WorldBorderCommand() {
        this.miniMessage = MiniMessage.miniMessage();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!(sender instanceof Player player)) {
            sender.sendMessage(this.miniMessage.deserialize("<red>You need to be a player to execute this command!"));
            return true;
        }

        if (!player.hasPermission("command.parse.use")) {
            player.sendMessage(this.miniMessage.deserialize("<red>You do not have permission to parse the world!"));
            return true;
        }

        if (args.length != 2) {
            player.sendMessage(this.miniMessage.deserialize("<red>Usage: <white>/worldborder <size> <y>"));
            player.sendMessage(this.miniMessage.deserialize("<red>Example: <white>/worldborder 100 50"));
            return true;
        }

        final int size = Integer.parseInt(args[0]);
        final int ylevel = Integer.parseInt(args[1]);

        final Location location1 = player.getLocation().clone().add(-((double) size / 2), -((double) ylevel / 2), -((double) size / 2));
        final Location location2 = player.getLocation().clone().add(((double) size / 2), ((double) ylevel / 2), ((double) size / 2));

        final MapMeta mapMeta;
        try {
            mapMeta = MapSettingsManager.getMapSettings(player.getWorld()).get();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        ParsingStrategy parsingStrategy = getParsingStrategy(MapParser.getMapParser(), player, mapMeta, size);
        parsingStrategy.parse(new BukkitRunnable() {
            @Override
            public void run() {
                final ArmorStand armorStand = player.getWorld().spawn(location1, ArmorStand.class);
                final ArmorStand armorStand2 = player.getWorld().spawn(location2, ArmorStand.class);
                armorStand.setGravity(false);
                armorStand2.setGravity(false);

                defineEntity(armorStand, player, DataPointType.BORDER, false);
                defineEntity(armorStand2, player, DataPointType.BORDER, false);

                System.out.println(armorStand.getLocation());
                System.out.println(armorStand2.getLocation());

            }
        }, false);

        return true;

    }
}
