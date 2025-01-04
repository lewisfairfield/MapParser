package net.plexverse.mapparser.mapsettings.objects;

import lombok.Data;
import net.plexverse.mapparser.enums.GameType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Data
public class MapMeta {

    private String author;
    private GameType gameType;
    private String mapName;
    private boolean legacy;

    public MapMeta() {
        this.author = "null";
        this.gameType = GameType.getAllGameTypes().getFirst();
        this.mapName = "Map Name";
        this.legacy = false;
    }

    public List<UUID> parseAuthorUuids() {
        if (this.author == null || this.author.equals("null")) {
            return new ArrayList<>();
        }
        return Arrays.stream(this.author.split(",")).map(UUID::fromString).toList();
    }

}
