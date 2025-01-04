package net.plexverse.mapparser.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;

@Getter
public class DataPointType {

    public static final DataPointType BORDER = new DataPointType("BORDER", "Border", false, false, Material.BARRIER);
    private static final Map<String, DataPointType> DATA_POINT_TYPES = new HashMap<>();

    private final String id;
    private final String menuName;
    private final boolean hasTeam;
    private final boolean changeYawPitch;
    private final Material material;

    private DataPointType(final String id, final String menuName, final boolean hasTeam, final boolean changeYawPitch, final Material material) {
        this.id = id;
        this.menuName = menuName;
        this.hasTeam = hasTeam;
        this.changeYawPitch = changeYawPitch;
        this.material = material;
    }

    public static DataPointType valueOf(final String name) {
        return DataPointType.getById(name.toUpperCase());
    }

    public String name() {
        return this.id.toUpperCase();
    }

    @JsonValue
    public String getName() {
        return this.id;
    }

    @JsonCreator
    public static DataPointType fromName(final String name) {
        return DataPointType.DATA_POINT_TYPES.get(name.toUpperCase());
    }

    /**
     * Loads data point types from the configuration file.
     */
    public static void loadDataPointTypes(final Plugin plugin) {
        final File configFile = new File(plugin.getDataFolder(), "datapointtypes.yml");

        // Generate default configuration if not present
        if (!configFile.exists()) {
            try {
                if (!plugin.getDataFolder().exists() && !plugin.getDataFolder().mkdirs()) {
                    plugin.getLogger().severe("Could not create plugin data folder!");
                    return;
                }
                final FileConfiguration config = YamlConfiguration.loadConfiguration(configFile);
                DataPointType.addDefaultValues(config);
                config.save(configFile);
                plugin.getLogger().info("Default datapointtypes.yml has been generated.");
            } catch (final IOException e) {
                plugin.getLogger().log(Level.SEVERE, "Could not generate default datapointtypes.yml", e);
                return;
            }
        }

        DataPointType.DATA_POINT_TYPES.put("BORDER", DataPointType.BORDER);

        // Load data point types from the configuration
        final FileConfiguration config = YamlConfiguration.loadConfiguration(configFile);
        for (final String key : config.getConfigurationSection("dataPointTypes").getKeys(false)) {
            final String menuName = config.getString("dataPointTypes." + key + ".menuName");
            final boolean hasTeam = config.getBoolean("dataPointTypes." + key + ".hasTeam");
            final boolean changeYawPitch = config.getBoolean("dataPointTypes." + key + ".changeYawPitch");
            final String materialName = config.getString("dataPointTypes." + key + ".material");
            Material material = Material.matchMaterial(materialName);

            if (material == null) {
                plugin.getLogger().warning("Invalid material for DataPointType: " + key + ", using default AIR.");
                material = Material.BARRIER;
            }

            final DataPointType dataPointType = new DataPointType(key.toUpperCase(), menuName, hasTeam, changeYawPitch, material);
            DataPointType.DATA_POINT_TYPES.put(key.toUpperCase(), dataPointType);
        }

        plugin.getLogger().info("Loaded " + DataPointType.DATA_POINT_TYPES.size() + " DataPointTypes from configuration.");
    }

    /**
     * Retrieves all DataPointTypes.
     */
    public static Collection<DataPointType> values() {
        return DataPointType.DATA_POINT_TYPES.values();
    }

    /**
     * Gets a DataPointType by its ID.
     */
    public static DataPointType getById(final String id) {
        return DataPointType.DATA_POINT_TYPES.get(id.toUpperCase());
    }

    /**
     * Adds default values to the configuration file.
     */
    private static void addDefaultValues(final FileConfiguration config) {
        config.createSection("dataPointTypes");

        // Example DataPointTypes
        config.set("dataPointTypes.SPAWNPOINT.menuName", "Spawnpoint");
        config.set("dataPointTypes.SPAWNPOINT.hasTeam", true);
        config.set("dataPointTypes.SPAWNPOINT.changeYawPitch", true);
        config.set("dataPointTypes.SPAWNPOINT.material", "RED_BED");

        config.set("dataPointTypes.CHEST.menuName", "Chest");
        config.set("dataPointTypes.CHEST.hasTeam", true);
        config.set("dataPointTypes.CHEST.changeYawPitch", true);
        config.set("dataPointTypes.CHEST.material", "CHEST");

        config.set("dataPointTypes.CHEST_MID.menuName", "Mid Chest");
        config.set("dataPointTypes.CHEST_MID.hasTeam", false);
        config.set("dataPointTypes.CHEST_MID.changeYawPitch", true);
        config.set("dataPointTypes.CHEST_MID.material", "ENDER_CHEST");

        config.set("dataPointTypes.HOLOGRAM.menuName", "Hologram");
        config.set("dataPointTypes.HOLOGRAM.hasTeam", true);
        config.set("dataPointTypes.HOLOGRAM.changeYawPitch", false);
        config.set("dataPointTypes.HOLOGRAM.material", "OAK_SIGN");

        config.set("dataPointTypes.WALLPOINT.menuName", "Wall Base");
        config.set("dataPointTypes.WALLPOINT.hasTeam", true);
        config.set("dataPointTypes.WALLPOINT.changeYawPitch", false);
        config.set("dataPointTypes.WALLPOINT.material", "GLASS");

        config.set("dataPointTypes.SPECTATOR_SPAWNPOINT.menuName", "Spectator Spawnpoint");
        config.set("dataPointTypes.SPECTATOR_SPAWNPOINT.hasTeam", false);
        config.set("dataPointTypes.SPECTATOR_SPAWNPOINT.changeYawPitch", true);
        config.set("dataPointTypes.SPECTATOR_SPAWNPOINT.material", "GREEN_BED");

        config.set("dataPointTypes.ISLAND_BORDER.menuName", "Island Border");
        config.set("dataPointTypes.ISLAND_BORDER.hasTeam", true);
        config.set("dataPointTypes.ISLAND_BORDER.changeYawPitch", true);
        config.set("dataPointTypes.ISLAND_BORDER.material", "BARRIER");

        config.set("dataPointTypes.CENTER.menuName", "Center");
        config.set("dataPointTypes.CENTER.hasTeam", false);
        config.set("dataPointTypes.CENTER.changeYawPitch", false);
        config.set("dataPointTypes.CENTER.material", "TOTEM_OF_UNDYING");

        config.set("dataPointTypes.ISLAND_BUILD_BORDER.menuName", "Island Build Border");
        config.set("dataPointTypes.ISLAND_BUILD_BORDER.hasTeam", true);
        config.set("dataPointTypes.ISLAND_BUILD_BORDER.changeYawPitch", false);
        config.set("dataPointTypes.ISLAND_BUILD_BORDER.material", "DIAMOND_PICKAXE");

        config.set("dataPointTypes.MINIBUILD.menuName", "Mini Build");
        config.set("dataPointTypes.MINIBUILD.hasTeam", false);
        config.set("dataPointTypes.MINIBUILD.changeYawPitch", false);
        config.set("dataPointTypes.MINIBUILD.material", "EMERALD_BLOCK");

        config.set("dataPointTypes.MOB.menuName", "Mob");
        config.set("dataPointTypes.MOB.hasTeam", false);
        config.set("dataPointTypes.MOB.changeYawPitch", true);
        config.set("dataPointTypes.MOB.material", "ZOMBIE_SPAWN_EGG");

        config.set("dataPointTypes.GAME_NPC.menuName", "Game (NPC)");
        config.set("dataPointTypes.GAME_NPC.hasTeam", true);
        config.set("dataPointTypes.GAME_NPC.changeYawPitch", true);
        config.set("dataPointTypes.GAME_NPC.material", "VILLAGER_SPAWN_EGG");

        config.set("dataPointTypes.STORE_NPC.menuName", "Store (NPC)");
        config.set("dataPointTypes.STORE_NPC.hasTeam", true);
        config.set("dataPointTypes.STORE_NPC.changeYawPitch", true);
        config.set("dataPointTypes.STORE_NPC.material", "EMERALD");

        config.set("dataPointTypes.INTERACT_NPC.menuName", "Interaction (NPC)");
        config.set("dataPointTypes.INTERACT_NPC.hasTeam", true);
        config.set("dataPointTypes.INTERACT_NPC.changeYawPitch", true);
        config.set("dataPointTypes.INTERACT_NPC.material", "WITHER_SKELETON_SKULL");

        config.set("dataPointTypes.INTERACTION.menuName", "Interaction");
        config.set("dataPointTypes.INTERACTION.hasTeam", true);
        config.set("dataPointTypes.INTERACTION.changeYawPitch", true);
        config.set("dataPointTypes.INTERACTION.material", "WITHER_SKELETON_SKULL");

        config.set("dataPointTypes.EVENT_BORDER.menuName", "Event Border");
        config.set("dataPointTypes.EVENT_BORDER.hasTeam", true);
        config.set("dataPointTypes.EVENT_BORDER.changeYawPitch", false);
        config.set("dataPointTypes.EVENT_BORDER.material", "LAVA_BUCKET");

        config.set("dataPointTypes.GAME_AREA.menuName", "Game Area Border");
        config.set("dataPointTypes.GAME_AREA.hasTeam", true);
        config.set("dataPointTypes.GAME_AREA.changeYawPitch", false);
        config.set("dataPointTypes.GAME_AREA.material", "BEACON");

        config.set("dataPointTypes.BRIDGE_POINT.menuName", "Bridge Point");
        config.set("dataPointTypes.BRIDGE_POINT.hasTeam", true);
        config.set("dataPointTypes.BRIDGE_POINT.changeYawPitch", false);
        config.set("dataPointTypes.BRIDGE_POINT.material", "OAK_LOG");

        config.set("dataPointTypes.BRIDGE_TYPE.menuName", "Bridge Type");
        config.set("dataPointTypes.BRIDGE_TYPE.hasTeam", true);
        config.set("dataPointTypes.BRIDGE_TYPE.changeYawPitch", false);
        config.set("dataPointTypes.BRIDGE_TYPE.material", "WATER_BUCKET");

        config.set("dataPointTypes.TREE.menuName", "Central Tree");
        config.set("dataPointTypes.TREE.hasTeam", false);
        config.set("dataPointTypes.TREE.changeYawPitch", false);
        config.set("dataPointTypes.TREE.material", "OAK_SAPLING");

        config.set("dataPointTypes.TEAM_GOAL.menuName", "Team Goal Point");
        config.set("dataPointTypes.TEAM_GOAL.hasTeam", true);
        config.set("dataPointTypes.TEAM_GOAL.changeYawPitch", false);
        config.set("dataPointTypes.TEAM_GOAL.material", "END_PORTAL_FRAME");
    }
}
