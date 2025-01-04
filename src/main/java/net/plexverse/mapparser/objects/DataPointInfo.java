package net.plexverse.mapparser.objects;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import lombok.Data;
import lombok.Getter;
import lombok.ToString;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.plexverse.mapparser.enums.DataPointType;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DataPointInfo {
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper()
            .enable(SerializationFeature.INDENT_OUTPUT)  // Pretty printing
            .enable(SerializationFeature.WRITE_EMPTY_JSON_ARRAYS);  // To handle empty lists like Gson's lenient handling

    private final Map<String, String> mapMeta;
    @Getter
    private final Map<String, List<WorldLocation>> dataPoints;

    public DataPointInfo() {
        this.mapMeta = new HashMap<>();
        this.dataPoints = new HashMap<>();
    }

    public void addMapMeta(final String key, final String value) {
        this.mapMeta.put(key, value);
    }

    public void addDataType(final DataPointType dataPointType, final Team team, final double x, final double y, final double z, final float yaw, final float pitch) {
        this.dataPoints.computeIfAbsent(dataPointType.name() + (team != null ? "_" + team.getId() : ""), ($) -> new ArrayList<>()).add(new WorldLocation(x, y, z, yaw, pitch));
    }

    public void export(final Player player, final File targetFile) {
        player.sendMessage(MiniMessage.miniMessage().deserialize("<dark_purple><b>(7/8)</b> <white>Saving info..."));

        // Save mapMeta.json
        try (final Writer writer = new OutputStreamWriter(new FileOutputStream(new File(targetFile, "mapMeta.json")), StandardCharsets.UTF_8)) {
            DataPointInfo.OBJECT_MAPPER.writeValue(writer, this.mapMeta);
        } catch (final IOException e) {
            throw new RuntimeException("Saving mapMeta.json", e);
        }

        // Save dataPoints.json
        try (final Writer writer = new OutputStreamWriter(new FileOutputStream(new File(targetFile, "dataPoints.json")), StandardCharsets.UTF_8)) {
            DataPointInfo.OBJECT_MAPPER.writeValue(writer, this.dataPoints);
        } catch (final IOException e) {
            throw new RuntimeException("Saving dataPoints.json", e);
        }
    }

    @Data
    @ToString
    public class WorldLocation {
        private final double x;
        private final double y;
        private final double z;
        private final float yaw;
        private final float pitch;

        public Location getLocation(final World world) {
            return new Location(world, this.x, this.y, this.z, this.yaw, this.pitch);
        }
    }
}
