package be.machigan.protecteddebugstick.utils;

import be.machigan.protecteddebugstick.ProtectedDebugStick;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


public final class Config {

    /**
     * The FileConfiguration where the plugin will search the config.
     * <br>
     * The plugin will not search in {@link ProtectedDebugStick#getConfig()}
     */
    @NotNull private static FileConfiguration config = ProtectedDebugStick.getInstance().getConfig();
    private Config() {}

    /**
     * Take the {@code config.yml} file in the plugin folder and use it for the plugin.
     * @throws InvalidConfigurationException When an error in the configuration prevent the plugin to work
     * properly
     */
    public static void reload() throws InvalidConfigurationException {
        config = YamlConfiguration.loadConfiguration(new File(ProtectedDebugStick.getInstance().getDataFolder(), "/config.yml"));

        try {
            Item.BASIC.get();
            Item.INFINITY.get();
            Item.INSPECTOR.get();
        } catch (NullPointerException e) {
            throw new InvalidConfigurationException("Configuration of items not found");
        }
    }

    @NotNull
    public static FileConfiguration getConfig() {
        return config;
    }


    /**
     * All configuration getters for the <i>Items</i> section
     */
    public enum Item {
        BASIC("BasicDebugStick"),
        INFINITY("InfinityDebugStick"),
        INSPECTOR("Inspector");

        final private static String PATH = "Items.";

        @Nullable final private String configName;

        Item(@NotNull String configName) {
            this.configName = PATH + configName;
        }


        /**
         * @return The item with the meta like in the config <b>the item has not his named space key yet !</b>
         * @throws NullPointerException When the section of the items is {@code null}
         */
        @NotNull
        public ItemStack get() throws NullPointerException {
            ConfigurationSection configurationSection = getConfig().getConfigurationSection(this.configName);
            if (configurationSection == null)
                throw new NullPointerException("Unable to find item description for " + this.name());

            Material material = Material.matchMaterial(configurationSection.getString("Material"));
            if (material == null)
                material = Material.SCUTE;

            ItemStack item = new ItemStack(material);
            ItemMeta meta = item.getItemMeta();

            String name = configurationSection.getString("Name");
            if (name != null)
                name = Tools.replaceColor(name);
            meta.setDisplayName(name);

            List<String> lore = configurationSection.getStringList("Lore");
            lore.replaceAll((String line) -> line = Tools.replaceColor(line));
            meta.setLore(lore);

            for (String enchantStr : configurationSection.getStringList("Enchants")) {
                Enchantment enchantment = Enchantment.getByKey(NamespacedKey.fromString("minecraft:" + enchantStr));
                if (enchantment != null)
                    meta.addEnchant(enchantment, 1, false);
            }

            if (configurationSection.getBoolean("HideEnchants"))
                meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);

            if (configurationSection.getBoolean("HideAttributes"))
                meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);

            if (configurationSection.getBoolean("HidePotionEffets"))
                meta.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);

            if (configurationSection.getBoolean("HideDye"))
                meta.addItemFlags(ItemFlag.HIDE_DYE);

            if (configurationSection.getBoolean("HidePlacedOn"))
                meta.addItemFlags(ItemFlag.HIDE_PLACED_ON);

            meta.setUnbreakable(configurationSection.getBoolean("IsUnbreakable"));

            item.setItemMeta(meta);
            return item;
        }

        @NotNull
        public ConfigurationSection getConfigSection() {
            return config.getConfigurationSection(this.configName);
        }
    }


    /**
     * All configuration getters for the <i>PreventPlayersWhenBreaking</i> section
     */
    public static class PreventPlayerWhenBreaking {
        private static final String PATH = "Settings.WarnPlayerWhenBreaking.";

        /**
         * @return {@code true} send a message to the player when his basic debug stick is about to break
         */
        public static boolean isEnable() {
            return config.getBoolean(PATH + "Enable");
        }

        /**
         * @return {@code true} send only one message to the player
         */
        public static boolean mustSendOnce() {
            return config.getBoolean(PATH + "SendOnce");
        }

        /**
         * @return The durability of the debug stick from which the plugin will warn the player
         */
        public static int getDurability() {
            return config.getInt(PATH + "Durability");
        }
    }


    /**
     * All configuration getters for the <i>BlackList</i> section
     */
    public static class BlackList {
        final private static String PATH = "Settings.BlackList.";

        @NotNull
        public static List<Material> getMaterials() {
            List<Material> materials = new ArrayList<>();
            for (String materialStr : config.getStringList(PATH + "Material")) {
                Material material = Material.matchMaterial(materialStr);
                if (material != null)
                    materials.add(material);
            }

            return materials;
        }

        @NotNull
        public static List<World> getWorlds() {
            List<World> worlds = new ArrayList<>();
            for (String worldStr : config.getStringList(PATH + "World")) {
                World world = Bukkit.getWorld(worldStr);
                if (world != null)
                    worlds.add(world);
            }

            return worlds;
        }
    }
}
