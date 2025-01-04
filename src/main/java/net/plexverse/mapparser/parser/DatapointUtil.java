package net.plexverse.mapparser.parser;

import net.plexverse.mapparser.constant.Keys;
import net.plexverse.mapparser.enums.DataPointType;
import net.plexverse.mapparser.objects.DataPointInfo;
import net.plexverse.mapparser.objects.Team;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.Locale;

public class DatapointUtil {

    public static void parseDataPoints(final Entity[] entities, final DataPointInfo dataPointInfo) {
        for (final Entity entity : entities) {
            final PersistentDataContainer persistentDataContainer = entity.getPersistentDataContainer();
            if (entity.getType() != EntityType.ARMOR_STAND || !persistentDataContainer.has(Keys.DATAPOINT_KEY)) {
                continue;
            }

            final String dataPointName = persistentDataContainer.get(Keys.DATAPOINT_KEY, PersistentDataType.STRING);
            final DataPointType dataPointType = DataPointType.valueOf(dataPointName.toUpperCase(Locale.ROOT));
            DatapointUtil.parse(dataPointType, dataPointInfo, entity, persistentDataContainer);
            entity.remove();
        }
    }

    public static void parse(final DataPointType dataPointType, final DataPointInfo dataPointInfo, final Entity entity, final PersistentDataContainer dataContainer) {
        final String teamName = dataContainer.get(Keys.TEAM_KEY, PersistentDataType.STRING);
        final Team team = teamName != null ? Team.getExistingOrCreate(teamName) : null;
        dataPointInfo.addDataType(dataPointType, team, entity.getX(), entity.getY(), entity.getZ(), entity.getYaw(), entity.getPitch());
    }

}
