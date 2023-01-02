package be.machigan.protecteddebugstick.utils;

import be.machigan.protecteddebugstick.ProtectedDebugStick;
import be.machigan.protecteddebugstick.def.DebugStick;
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
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.util.*;
import java.util.stream.Collectors;


public final class Config {

    /**
     * The FileConfiguration where the plugin will search the config.
     * <br>
     * The plugin will not search in {@link ProtectedDebugStick#getConfig()}
     */
    @NotNull private static FileConfiguration configFile = ProtectedDebugStick.getInstance().getConfig();
    private Config() {}

    /**
     * Take the {@code config.yml} file in the plugin folder and use it for the plugin.
     * @throws InvalidConfigurationException When an error in the configuration prevent the plugin to work
     * properly
     */
    public static void reload() throws InvalidConfigurationException {
        File file = new File(ProtectedDebugStick.getInstance().getDataFolder(), "/config.yml");
        if (!file.exists())
            ProtectedDebugStick.getInstance().saveDefaultConfig();

        configFile = YamlConfiguration.loadConfiguration(new File(ProtectedDebugStick.getInstance().getDataFolder(), "/config.yml"));
        try {
            Item.BASIC.get();
            Item.INFINITY.get();
            Item.INSPECTOR.get();
        } catch (NullPointerException e) {
            throw new InvalidConfigurationException("Configuration of items not found");
        }

        Recipe.reload();
    }

    @NotNull
    public static FileConfiguration getConfig() {
        return configFile;
    }


    /**
     * All configuration getters for the <i>Items</i> section
     */
    public enum Item {
        BASIC("BasicDebugStick"),
        INFINITY("InfinityDebugStick"),
        INSPECTOR("Inspector");

        private static final String PATH = "Items.";

        @Nullable
        private final String configName;

        Item(@NotNull String configName) {
            this.configName = PATH + configName;
        }


        /**
         * @return The item with the meta like in the config <b>the item has not his named space key yet !</b>
         * @throws NullPointerException When the section of the items is {@code null}
         */
        @NotNull
        public ItemStack get() throws NullPointerException {
            ConfigurationSection configurationSection = Objects.requireNonNull(configFile.getConfigurationSection(this.configName),
                    "Unable to find item description for " + this.name().toLowerCase());

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
            return configFile.getConfigurationSection(this.configName);
        }
    }


    /**
     * All configuration getters for the <i>PreventPlayersWhenBreaking</i> section
     */
    public static class PreventPlayerWhenBreaking {
        private static final String PATH = "Settings.WarnPlayerWhenBreaking.";
        
        private PreventPlayerWhenBreaking() {}

        /**
         * @return {@code true} send a message to the player when his basic debug stick is about to break
         */
        public static boolean isEnable() {
            return configFile.getBoolean(PATH + "Enable");
        }

        /**
         * @return {@code true} send only one message to the player
         */
        public static boolean mustSendOnce() {
            return configFile.getBoolean(PATH + "SendOnce");
        }

        /**
         * @return The durability of the debug stick from which the plugin will warn the player
         */
        public static int getDurability() {
            return configFile.getInt(PATH + "Durability");
        }
    }


    /**
     * All configuration getters for the <i>BlackList</i> section
     */
    public static class BlackList {
        @NotNull
        private static final String PATH = "Settings.BlackList.";

        @NotNull
        public static List<Material> getMaterials() {
            List<Material> materials = new ArrayList<>();
            for (String materialStr : configFile.getStringList(PATH + "Material")) {
                Material material = Material.matchMaterial(materialStr);
                if (material != null)
                    materials.add(material);
            }

            return materials;
        }

        @NotNull
        public static List<World> getWorlds() {
            List<World> worlds = new ArrayList<>();
            for (String worldStr : configFile.getStringList(PATH + "World")) {
                World world = Bukkit.getWorld(worldStr);
                if (world != null)
                    worlds.add(world);
            }

            return worlds;
        }
    }


    public static class Settings {
        @NotNull
        public static final String PATH = "Settings.";

        public static boolean hideNoPermProperty() {
            return configFile.getBoolean(PATH + "HideNoPermProperty");
        }
    }


    public static class Log {
        @NotNull
        private static final String PATH = "Log.";

        public static boolean consoleEnable() {
            return configFile.getBoolean(PATH + "Console");
        }

        @Nullable
        public static String getFormat() {
            return configFile.getString(PATH + "Format");
        }
    }


    public static class Recipe {
        private static final String PATH = "Recipes";
        private static final List<String> POSSIBLE_FIELDS = Arrays.asList("1", "2", "3", "4", "5", "6", "7", "8", "9");


        public static void reload() {
            Set<String> recipesName;
            ConfigurationSection configurationSection;
            try {
                configurationSection = Config.getConfig().getConfigurationSection(PATH);
                recipesName = configurationSection.getKeys(false);
            } catch (NullPointerException e) {
                return;
            }

            Set<String> removed = new HashSet<>();
            for (String key : recipesName) {

                try {
                    Item itemType = Item.valueOf(configurationSection.getString(key + ".Item").toUpperCase());
                    if (itemType == null) {
                        throw new NullPointerException();
                    }
                } catch (NullPointerException e) {
                    removed.add(key);
                    LogUtil.getLogger().warning("Item type for " + key + " not found or invalid");
                    continue;
                }


                Set<String> fields;
                try {
                    fields = configurationSection.getConfigurationSection(key + ".Craft").getKeys(false);
                } catch (NullPointerException ignored) {
                    LogUtil.getLogger().warning("Recipes \"" + key + "\" has no slot. Ignoring the recipe");
                    removed.add(key);
                    continue;
                }
                fields = fields.stream().filter(POSSIBLE_FIELDS::contains).collect(Collectors.toSet());
                if (fields.isEmpty())
                    removed.add(key);
            }
            recipesName.removeAll(removed);


            List<ShapedRecipe> recipes = new ArrayList<>();
            for (String key : recipesName) {
                NamespacedKey namespacedKey = new NamespacedKey(ProtectedDebugStick.getInstance(), key);

                Item itemType = Item.valueOf(configurationSection.getString(key + ".Item").toUpperCase());
                if (itemType == null)
                    continue;

                ItemStack ds;
                switch (itemType) {
                    case BASIC:
                        int durability = configurationSection.getInt(key + ".Durability");
                        if (durability <= 0) {
                            LogUtil.getLogger().info("The craft \" + key + \" for a basic debug stick has a invalid durability (\" + durability + \")");
                            continue;
                        }
                        ds = DebugStick.getBasicDebugStick(durability);
                        break;
                    case INFINITY:
                        ds = DebugStick.getInfiniteDebugStick();
                        break;
                    case INSPECTOR:
                        ds = DebugStick.getInspector();
                        break;
                    default:
                        continue;
                }

                Set<String> shapeSet;
                try {
                    shapeSet = configurationSection.getConfigurationSection(key + ".Craft").getKeys(false);
                } catch (NullPointerException e) {
                    LogUtil.getLogger().warning("Recipes \"" + key + "\" has no slot. Ignoring the recipe");
                    continue;
                }

                ShapedRecipe recipe = new ShapedRecipe(namespacedKey, ds);

                recipe.shape(
                        (shapeSet.contains("1") ? "1" : " ") + (shapeSet.contains("2") ? "2" : " ") + (shapeSet.contains("3") ? "3" : " "),
                        (shapeSet.contains("4") ? "4" : " ") + (shapeSet.contains("5") ? "5" : " ") + (shapeSet.contains("6") ? "6" : " "),
                        (shapeSet.contains("7") ? "7" : " ") + (shapeSet.contains("8") ? "8" : " ") + (shapeSet.contains("9") ? "9" : " ")
                );

                for (int i = 1; i <= 9; i++) {
                    try {
                        Material m = Material.matchMaterial(configurationSection.getString(key + ".Craft." + i));
                        if (m != null) {
                            recipe.setIngredient(Integer.toString(i).toCharArray()[0], m);
                        } else {
                            recipe.setIngredient(Integer.toString(i).toCharArray()[0], Material.BARRIER);
                            LogUtil.getLogger().warning("The material \"" +
                                    configurationSection.getString(key + ".Craft." + i) +
                                    "\" doesn't" + " exist from the recipe \"" +
                                    key + "\" (slot N°" + i + ") ! This slot has been replaces by a barrier block");
                        }
                    } catch (IllegalArgumentException ignored) {
                        // no material for key i
                    }
                }

                recipes.add(recipe);
            }

            Iterator<org.bukkit.inventory.Recipe> iterator = Bukkit.recipeIterator();
            while (iterator.hasNext()) {
                try {
                    org.bukkit.inventory.ShapedRecipe recipe = (ShapedRecipe) iterator.next();
                    if (recipe.getKey().getNamespace().equalsIgnoreCase(ProtectedDebugStick.getInstance().getName()))
                        Bukkit.removeRecipe(recipe.getKey());
                } catch (ClassCastException ignored) {}
            }

            for (ShapedRecipe recipe : recipes)
                Bukkit.addRecipe(recipe);

            LogUtil.getLogger().info(recipes.size() + " recipes has been registered");
        }
    }
}
