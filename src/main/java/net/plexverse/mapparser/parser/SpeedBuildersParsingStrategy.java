package net.plexverse.mapparser.parser;

import net.kyori.adventure.text.minimessage.MiniMessage;
import net.plexverse.mapparser.MapParser;
import net.plexverse.mapparser.enums.GameType;
import net.plexverse.mapparser.objects.Cuboid;
import net.plexverse.mapparser.objects.DataPointInfo;
import org.bukkit.entity.Player;

import java.util.List;


public class SpeedBuildersParsingStrategy extends WorldParsingStrategy {
    private static final int EXPECTED_BLOCK_COUNT = 567;

    public SpeedBuildersParsingStrategy(MapParser plugin, Player player, GameType gameName, String mapName, String builder, int radius, boolean legacy) {
        super(plugin, player, gameName, mapName, builder, radius, legacy);
    }

    @Override
    public boolean validate(Runnable runnable, boolean ignoreBorder) {
        return super.validate(runnable, ignoreBorder) && validateBuildArea();
    }

    private boolean validateBuildArea() {
        for (final String k : dataPointInfo.getDataPoints().keySet()) {
            final List<DataPointInfo.WorldLocation> v = dataPointInfo.getDataPoints().get(k);
            if (k.contains("ISLAND_BUILD_BORDER")) {
                final Cuboid cuboid = new Cuboid(v.getFirst().getLocation(player.getWorld()), v.getLast().getLocation(player.getWorld()));
                if (cuboid.getTotalBlockSize() != EXPECTED_BLOCK_COUNT) {
                    player.sendMessage(MiniMessage.miniMessage().deserialize("<red><b>(!)</b> <white>" + k + " has " + cuboid.getTotalBlockSize() + " blocks, but needs " + EXPECTED_BLOCK_COUNT));
                    player.sendMessage(MiniMessage.miniMessage().deserialize("<red><b>(!)</b> <white> Expected: 6 high (server adds 1), 9x9 wide"));

                    return false;
                }
            }
        }
        return true;
    }


}
