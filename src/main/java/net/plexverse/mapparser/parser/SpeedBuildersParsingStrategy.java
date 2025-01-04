package net.plexverse.mapparser.parser;

import net.kyori.adventure.text.minimessage.MiniMessage;
import net.plexverse.mapparser.MapParser;
import net.plexverse.mapparser.enums.GameType;
import net.plexverse.mapparser.objects.Cuboid;
import net.plexverse.mapparser.objects.DataPointInfo;
import org.bukkit.entity.Player;

import java.util.List;

/**
 * Example custom parsing strategy
 */
public class SpeedBuildersParsingStrategy extends WorldParsingStrategy {
    private static final int EXPECTED_BLOCK_COUNT = 567;

    public SpeedBuildersParsingStrategy(final MapParser plugin, final Player player, final GameType gameName, final String mapName, final String builder, final int radius, final boolean legacy) {
        super(plugin, player, gameName, mapName, builder, radius, legacy);
    }

    @Override
    public boolean validate(final Runnable runnable, final boolean ignoreBorder) {
        return super.validate(runnable, ignoreBorder) && this.validateBuildArea();
    }

    private boolean validateBuildArea() {
        for (final String k : this.dataPointInfo.getDataPoints().keySet()) {
            final List<DataPointInfo.WorldLocation> v = this.dataPointInfo.getDataPoints().get(k);
            if (k.contains("ISLAND_BUILD_BORDER")) {
                final Cuboid cuboid = new Cuboid(v.getFirst().getLocation(this.player.getWorld()), v.getLast().getLocation(this.player.getWorld()));
                if (cuboid.getTotalBlockSize() != SpeedBuildersParsingStrategy.EXPECTED_BLOCK_COUNT) {
                    this.player.sendMessage(MiniMessage.miniMessage().deserialize("<red><b>(!)</b> <white>" + k + " has " + cuboid.getTotalBlockSize() + " blocks, but needs " + SpeedBuildersParsingStrategy.EXPECTED_BLOCK_COUNT));
                    this.player.sendMessage(MiniMessage.miniMessage().deserialize("<red><b>(!)</b> <white> Expected: 6 high (server adds 1), 9x9 wide"));

                    return false;
                }
            }
        }
        return true;
    }


}
