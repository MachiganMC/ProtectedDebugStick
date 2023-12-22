package be.machigan.protecteddebugstick.utils;

import be.machigan.protecteddebugstick.ProtectedDebugStick;
import com.google.common.base.Preconditions;
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
import java.util.logging.Level;


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
        ProtectedDebugStick.generateFileIfNotExist("config.yml");

        configFile = YamlConfiguration.loadConfiguration(new File(ProtectedDebugStick.getInstance().getDataFolder(), "config.yml"));
        Item.reloadConfig();
        ProtectedDebugStick.PROPERTIES.forEach(property ->
                property.setDurability(configFile.getInt("Settings.Durability." + property.toString().toUpperCase().replace("-", "_"))));
        try {
            Item.BASIC.getItemFromConfig();
            Item.INFINITY.getItemFromConfig();
            Item.INSPECTOR.getItemFromConfig();
        } catch (IllegalStateException e) {
            throw new InvalidConfigurationException("Configuration of items not found");
        }
        Lang.reload();
        Recipe.reload();
    }

    @NotNull
    public static FileConfiguration getConfig() {
        return configFile;
    }

    public static void checkReload() throws InvalidConfigurationException {
        try {
            Config.reload();
        } catch (InvalidConfigurationException e) {
            if (Config.Item.BASIC.getConfigSection() == null)
                LogUtil.getLogger().severe("The configuration of BasicDebugStick cannot be found. Disabling the plugin");
            if (Config.Item.INFINITY.getConfigSection() == null)
                LogUtil.getLogger().severe("The configuration of InfinityDebugStick cannot be found. Disabling the plugin");
            if (Config.Item.INSPECTOR.getConfigSection() == null)
                LogUtil.getLogger().severe("The configuration of Inspector cannot be found. Disabling the plugin");

            throw new InvalidConfigurationException();
        }
    }


    /**
     * All configuration getters for the <i>Items</i> section
     */
    public enum Item {
        BASIC("BasicDebugStick"),
        INFINITY("InfinityDebugStick"),
        INSPECTOR("Inspector");

        private static final String PATH = "Items.";
        @Nullable private List<String> lore;

        @Nullable private final String configName;
        @Nullable private ConfigurationSection configurationSection;

        Item(@NotNull String configName) {
            this.configName = PATH + configName;
        }


        /**
         * @return The item with the meta like in the config <b>the item has not his named space key yet !</b>
         * @throws NullPointerException When the section of the items is {@code null}
         */
        @NotNull
        public ItemStack getItemFromConfig() throws IllegalStateException {
            Preconditions.checkState(this.configurationSection != null, "Unable to find configuration section of %s", configName);
            ItemStack item = new ItemStack(this.getMaterial());
            ItemMeta meta = item.getItemMeta();
            meta.setDisplayName(this.getName());
            meta.setLore(this.getLore());
            this.getEnchantments().forEach(enchantment -> meta.addEnchant(enchantment, 1, false));
            meta.addItemFlags(this.getItemFlags());
            meta.setUnbreakable(configurationSection.getBoolean("IsUnbreakable"));
            item.setItemMeta(meta);
            return item;
        }

        public static void reloadConfig() {
            for (Item item : Item.values())
                item.configurationSection = configFile.getConfigurationSection(item.configName);
        }

        @Nullable
        public ConfigurationSection getConfigSection() {
            return this.configurationSection;
        }

        @NotNull
        private Material getMaterial() {
            try {
                return Tools.getOrDefault(Material.matchMaterial(this.configurationSection.getString("Material")), Material.SCUTE);
            } catch (IllegalArgumentException e) {
                return Material.SCUTE;
            }
        }

        @Nullable
        private String getName() {
            final String[] name = {this.configurationSection.getString("Name")};
            Tools.doIfNotNull(name[0], () -> name[0] =  Tools.replaceColor(name[0]));
            return name[0];
        }

        @NotNull
        public List<String> getLore() {
            if (this.lore != null)
                return new ArrayList<>(lore);
            List<String> configLore = new ArrayList<>(this.configurationSection.getStringList("Lore"));
            configLore.replaceAll(Tools::replaceColor);
            this.lore = configLore;
            return configLore;
        }

        @NotNull
        private List<Enchantment> getEnchantments() {
            List<Enchantment> enchantments = new ArrayList<>();
            for (String enchantStr : this.configurationSection.getStringList("Enchants")) {
                Enchantment enchantment = Enchantment.getByKey(NamespacedKey.fromString("minecraft:" + enchantStr));
                Tools.doIfNotNull(enchantment, () -> enchantments.add(enchantment));
            }
            return enchantments;
        }

        @NotNull
        private ItemFlag[] getItemFlags() {
            Set<ItemFlag> flags = new HashSet<>();
            Tools.doIfTrue(configurationSection.getBoolean("HideEnchants"), () -> flags.add(ItemFlag.HIDE_ENCHANTS));
            Tools.doIfTrue(configurationSection.getBoolean("HideAttributes"), () -> flags.add(ItemFlag.HIDE_ATTRIBUTES));
            Tools.doIfTrue(configurationSection.getBoolean("HidePotionEffets"), () -> flags.add(ItemFlag.HIDE_POTION_EFFECTS));
            Tools.doIfTrue(configurationSection.getBoolean("HideDye"), () -> flags.add(ItemFlag.HIDE_DYE));
            Tools.doIfTrue(configurationSection.getBoolean("HidePlacedOn"), () -> flags.add(ItemFlag.HIDE_PLACED_ON));
            return flags.toArray(new ItemFlag[0]);
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
                Tools.doIfNotNull(material,() -> materials.add(material));
            }
            return materials;
        }

        @NotNull
        public static List<World> getWorlds() {
            List<World> worlds = new ArrayList<>();
            for (String worldStr : configFile.getStringList(PATH + "World")) {
                World world = Bukkit.getWorld(worldStr);
                Tools.doIfNotNull(world, () -> worlds.add(world));
            }
            return worlds;
        }

        private BlackList() {}
    }


    public static class Settings {
        @NotNull
        public static final String PATH = "Settings.";

        public static boolean hideNoPermProperty() {
            return configFile.getBoolean(PATH + "HideNoPermProperty");
        }

        private Settings() {}
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

        private Log() {}
    }


    public static class Recipe {
        private static final String PATH = "Recipes";

        public static void reload() {
            List<ShapedRecipe> recipes = Recipe.getValidRecipes();
            recipes.forEach(Bukkit::addRecipe);
            LogUtil.getLogger().log(Level.INFO, () -> recipes.size() + " recipes has been registered");
        }

        @NotNull
        private static List<ShapedRecipe> getValidRecipes() {
            Recipe.removeOldRecipes();
            ConfigurationSection configurationSection = Config.getConfig().getConfigurationSection(PATH);
            if (configurationSection == null)
                return Collections.emptyList();

            List<ShapedRecipe> recipes = new ArrayList<>();
            for (String key : configurationSection.getKeys(false)) {
                RecipeValidator validator = new RecipeValidator(configurationSection.getConfigurationSection(key), key);
                if (validator.getInvalidReason() == null) {
                    recipes.add(validator.toRecipe());
                } else {
                    LogUtil.getLogger().warning(validator.getInvalidReason());
                }
            }
            return recipes;
        }

        private static void removeOldRecipes() {
            Iterator<org.bukkit.inventory.Recipe> iterator = Bukkit.recipeIterator();
            while (iterator.hasNext()) {
                try {
                    ShapedRecipe recipe = (ShapedRecipe) iterator.next();
                    if (recipe.getKey().getNamespace().equalsIgnoreCase(ProtectedDebugStick.getInstance().getName()))
                        Bukkit.removeRecipe(recipe.getKey());
                } catch (ClassCastException ignored) {}
            }
        }

        private Recipe() {}
    }

    public static class Lang {
        private static FileConfiguration messageFile;
        private static final List<String> LANG_FILES = Arrays.asList("messages_en.yml", "messages_fr.yml");
        private static final File DEFAULT_LANG = new File(ProtectedDebugStick.getInstance().getDataFolder(), "lang/messages_en.yml");

        public static void reload() {
            generateLangFilesIfNotExist();
            String lang = getConfig().getString("Lang");
            if (lang == null)
                lang = "en";
            File file = strToLangFile("messages_" + lang + ".yml");
            if (!file.exists())
                file = DEFAULT_LANG;
            messageFile = YamlConfiguration.loadConfiguration(file);
        }

        private static void generateLangFilesIfNotExist() {
            LANG_FILES.stream()
                    .filter(lang -> !strToLangFile(lang).exists())
                    .forEach(lang -> ProtectedDebugStick.generateFileIfNotExist("lang/" + lang));
        }

        private static File strToLangFile(String lang) {
            return new File(ProtectedDebugStick.getInstance().getDataFolder(), "lang/" + lang);
        }

        public static FileConfiguration getMessageFile() {
            return messageFile != null ? messageFile : YamlConfiguration.loadConfiguration(DEFAULT_LANG);
        }

        private Lang() {}
    }
}
