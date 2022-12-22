package be.machigan.protecteddebugstick.def;

import be.machigan.protecteddebugstick.ProtectedDebugStick;
import be.machigan.protecteddebugstick.property.Property;
import be.machigan.protecteddebugstick.utils.Tools;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DebugStick implements Cloneable {
    public static ItemStack debugStick;
    public static ItemStack infinityDebugStick;
    public static ItemStack inspector;
    public static List<Material> blacklist = new ArrayList<>();
    final public static NamespacedKey DURABILITY_KEY = new NamespacedKey(ProtectedDebugStick.getInstance(), "debug-stick-durability");
    final public static NamespacedKey INSPECTOR_KEY = new NamespacedKey(ProtectedDebugStick.getInstance(), "inspector");
    final public static List<String> ITEMS = new ArrayList<>(Arrays.asList("basic", "infinity", "inspector"));

    static {
        init();
        Property.init();
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    public static void init() {
        String path = "items.basicDebugStick.";
        Material m;
        List<String> blacklistStr = ProtectedDebugStick.config.getStringList("settings.blacklist");
        blacklistStr.forEach((String s) -> {
            Material material = Material.matchMaterial(s);
            if (material == null) {
                Tools.log("The material \"" + s + "\" of the blacklist isn't a valid material.", Tools.LOG_WARNING);
            } else {
                blacklist.add(material);
            }
        });
        try {
            m = Material.matchMaterial(ProtectedDebugStick.config.getString(path + "material"));

            if (m == null) {
                Tools.log("Material for basic debug stick is incorrect. Setting to scute.", Tools.LOG_WARNING);
                m = Material.SCUTE;
            }
        } catch (IllegalArgumentException ignored) {
            Tools.log("Path of material for basic debug stick : \"" + path + "material\" hasn't been found. Setting to scute", Tools.LOG_WARNING);
            m = Material.SCUTE;
        }

        debugStick = new ItemStack(m);
        ItemMeta dbMeta = debugStick.getItemMeta();
        if (dbMeta != null) {
            dbMeta.setDisplayName(Tools.configColor(path + "name"));
            List<String> lore;
            try {
                lore = ProtectedDebugStick.config.getStringList(path + "lore");
            } catch (NullPointerException ignored) {
                Tools.log("Path of lore of basic debug stick : \"" + path + "lore\" hasn't been found. Setting lore as empty", Tools.LOG_WARNING);
                lore = new ArrayList<>();
            }
            lore.replaceAll(s -> s = Tools.replaceColor(s));
            dbMeta.setLore(lore);

            try {
                for (String enchant : ProtectedDebugStick.config.getStringList(path + "enchants")) {
                    Enchantment e = Enchantment.getByKey(NamespacedKey.fromString("minecraft:" + enchant.toLowerCase()));
                    if (e == null) {
                        Tools.log("The enchant \"" + enchant + "\" from \"" + path + "enchants\" doesn't exist");
                        continue;
                    }
                    dbMeta.addEnchant(e, 1, true);
                }
            } catch (NullPointerException ignored) {
                Tools.log("Path to enchants list of basic debug stick : \"" + path + "enchants\" hasn't been found. Setting with no enchant.", Tools.LOG_WARNING);
            }

            if (ProtectedDebugStick.config.getBoolean(path + "hideEnchants")) {
                dbMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            }
            if (ProtectedDebugStick.config.getBoolean(path + "hideAttributes")) {
                dbMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
            }
            if (ProtectedDebugStick.config.getBoolean(path + "hidePotionEffets")) {
                dbMeta.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
            }
            if (ProtectedDebugStick.config.getBoolean(path + "hideDye")) {
                dbMeta.addItemFlags(ItemFlag.HIDE_DYE);
            }
            if (ProtectedDebugStick.config.getBoolean(path + "hideUnbreakable")) {
                dbMeta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
            }
            if (ProtectedDebugStick.config.getBoolean(path + "hidePlacedOn")) {
                dbMeta.addItemFlags(ItemFlag.HIDE_PLACED_ON);
            }
            dbMeta.setUnbreakable(ProtectedDebugStick.config.getBoolean(path + "isUnBreakable"));

            debugStick.setItemMeta(dbMeta);
        } else {
            for (int i = 0; i < 3; i++) {
                Tools.log("Item meta of basic debug stick hasn't been found. Disabling the plugin. THIS IS NOT NORMAL ! " +
                        "Look at the material, if the material is AIR, a item meta can't be found", Tools.LOG_SEVERE);
            }
            Bukkit.getServer().getPluginManager().disablePlugin(ProtectedDebugStick.getInstance());
        }

        path = "items.infinityDebugStick.";

        try {
            m = Material.matchMaterial(ProtectedDebugStick.config.getString(path + "material"));
            if (m == null) {
                m = Material.SCUTE;
                Tools.log("Material for infinity debug stick is invalid. Setting to scute.", Tools.LOG_WARNING);
            }
        } catch (IllegalArgumentException ignored) {
            m = Material.SCUTE;
            Tools.log("Path of material of infinity debug stick : \"" + path + "material\" hasn't been found. Setting to scute.", Tools.LOG_WARNING);
        }
        infinityDebugStick = new ItemStack(m);

        ItemMeta ibMeta = infinityDebugStick.getItemMeta();
        if (ibMeta != null) {
            ibMeta.setDisplayName(Tools.configColor(path + "name"));

            List<String> lore;
            try {
                lore = ProtectedDebugStick.config.getStringList(path + "lore");
                lore.replaceAll(s -> s = Tools.replaceColor(s));
                ibMeta.setLore(lore);
            } catch (NullPointerException ignored) {
                Tools.log("The lore of infinity debug stick : \"" + path + "lore\" hasn't been found. Setting to empty.", Tools.LOG_WARNING);
                lore = new ArrayList<>();
            }
            ibMeta.setLore(lore);

            try {
                for (String enchant : ProtectedDebugStick.config.getStringList(path + "enchants")) {
                    Enchantment e = Enchantment.getByKey(NamespacedKey.fromString("minecraft:" + enchant));
                    if (e == null) {
                        Tools.log("The enchant " + enchant + " doesn't exist.");
                        continue;
                    }
                    ibMeta.addEnchant(e, 1, true);
                }
            } catch (NullPointerException ignored) {
                Tools.log("Path of enchant list of infinity debug stick : \"" + path  + "enchants \" hasn't been found. Setting with no enchants", Tools.LOG_WARNING);
            }

            if (ProtectedDebugStick.config.getBoolean(path + "hideEnchants")) {
                ibMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            }
            if (ProtectedDebugStick.config.getBoolean(path + "hideAttributes")) {
                ibMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
            }
            if (ProtectedDebugStick.config.getBoolean(path + "hidePotionEffets")) {
                ibMeta.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
            }
            if (ProtectedDebugStick.config.getBoolean(path + "hideDye")) {
                ibMeta.addItemFlags(ItemFlag.HIDE_DYE);
            }
            if (ProtectedDebugStick.config.getBoolean(path + "hideUnbreakable")) {
                ibMeta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
            }
            if (ProtectedDebugStick.config.getBoolean(path + "hidePlacedOn")) {
                ibMeta.addItemFlags(ItemFlag.HIDE_PLACED_ON);
            }
            ibMeta.setUnbreakable(ProtectedDebugStick.config.getBoolean(path + "isUnbreakable"));

            infinityDebugStick.setItemMeta(ibMeta);
        } else {
            for (int i = 0; i < 3; i++) {
                Tools.log("Item meta of infinty debug stick hasn't been found. Disabling the plugin. THIS IS NOT NORMAL ! " +
                        "Look at the material, if the material is AIR, a item meta can't be found", Tools.LOG_SEVERE);
            }
            Bukkit.getServer().getPluginManager().disablePlugin(ProtectedDebugStick.getInstance());
        }


        path = "items.inspector.";
        try {
            m = Material.matchMaterial(ProtectedDebugStick.config.getString(path + "material"));
            if (m == null) {
                m = Material.GOLD_INGOT;
                Tools.log("The material of the inspector is invalid. Setting to gold ingot.", Tools.LOG_WARNING);
            }
        } catch (IllegalArgumentException ignored) {
            m = Material.GOLD_INGOT;
            Tools.log("The material of the inspector : \"" + path + "material\" hasn't been found. Setting to gold ingot.", Tools.LOG_WARNING);
        }
        inspector = new ItemStack(m);

        ItemMeta itemMeta = inspector.getItemMeta();
        if (itemMeta != null) {
            itemMeta.setDisplayName(Tools.configColor(path + "name"));

            List<String> lore;
            try {
                lore = ProtectedDebugStick.config.getStringList(path + "lore");
                lore.replaceAll(s -> s = Tools.replaceColor(s));
            } catch (NullPointerException ignored) {
                lore = new ArrayList<>();
                Tools.log("The lore of the inspector : \"" + path + "name\" hasn't been found. Setting with no lore.", Tools.LOG_WARNING);
            }
            itemMeta.setLore(lore);

            try {
                for (String enchant : ProtectedDebugStick.config.getStringList(path + "enchants")) {
                    Enchantment e = Enchantment.getByKey(NamespacedKey.fromString(enchant));
                    if (e == null) {
                        Tools.log("The enchant \"" + enchant + "\" doesn't exist.", Tools.LOG_WARNING);
                        continue;
                    }
                    itemMeta.addEnchant(e, 1, true);
                }
            } catch (NullPointerException ignored) {
                Tools.log("The enchant list of the inspector : \"" + path + "enchants\" hasn't been found. Setting with no enchant.", Tools.LOG_WARNING);
            }

            if (ProtectedDebugStick.config.getBoolean(path + "hideEnchants")) {
                itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            }
            if (ProtectedDebugStick.config.getBoolean(path + "hideAttributes")) {
                itemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
            }
            if (ProtectedDebugStick.config.getBoolean(path + "hidePotionEffets")) {
                itemMeta.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
            }
            if (ProtectedDebugStick.config.getBoolean(path + "hideDye")) {
                itemMeta.addItemFlags(ItemFlag.HIDE_DYE);
            }
            if (ProtectedDebugStick.config.getBoolean(path + "hideUnbreakable")) {
                itemMeta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
            }
            if (ProtectedDebugStick.config.getBoolean(path + "hidePlacedOn")) {
                itemMeta.addItemFlags(ItemFlag.HIDE_PLACED_ON);
            }
            itemMeta.setUnbreakable(ProtectedDebugStick.config.getBoolean(path + "isUnbreakable"));
            itemMeta.getPersistentDataContainer().set(INSPECTOR_KEY, PersistentDataType.STRING, "inspector");

            inspector.setItemMeta(itemMeta);

        }
    }

    public static ItemStack getDebugStick(int durability) {
        ItemMeta itemMeta = debugStick.getItemMeta();
        itemMeta.getPersistentDataContainer().set(DURABILITY_KEY, PersistentDataType.INTEGER, durability);

        List<String> lore = ProtectedDebugStick.config.getStringList("items.basicDebugStick.lore");
        lore.replaceAll(s -> s = Tools.replaceColor(s).replace("{durability}", Integer.toString(durability)));
        itemMeta.setLore(lore);

        ItemStack i = debugStick.clone();
        i.setItemMeta(itemMeta);
        return i;
    }

    public static ItemStack getInfinityDebugStick() {
        ItemMeta itemMeta = infinityDebugStick.getItemMeta();
        itemMeta.getPersistentDataContainer().set(DURABILITY_KEY, PersistentDataType.INTEGER, -1);

        ItemStack i = infinityDebugStick.clone();
        i.setItemMeta(itemMeta);
        return i;
    }

    public static void removeDurability(Player player, int durability) {
        ItemStack item = player.getInventory().getItemInMainHand();
        ItemMeta itemMeta = item.getItemMeta();
        if (itemMeta == null) {
            return;
        }
        if (!itemMeta.getPersistentDataContainer().has(DURABILITY_KEY, PersistentDataType.INTEGER)) {
            return;
        }
        int current = itemMeta.getPersistentDataContainer().get(DURABILITY_KEY, PersistentDataType.INTEGER);
        if (current == -1) {
            return;
        }
        current -= durability;
        if (current < 0) {
            current = 0;
        }
        if (item.getAmount() > 1) {
            ItemStack itemStack = item.clone();
            item.setAmount(item.getAmount() -1);
            itemStack.setAmount(1);
            itemMeta.getPersistentDataContainer().set(DURABILITY_KEY, PersistentDataType.INTEGER, current);
            List<String> lore = ProtectedDebugStick.config.getStringList("items.basicDebugStick.lore");
            int finalCurrent = current;
            lore.replaceAll(s -> Tools.replaceColor(s).replace("{durability}", Integer.toString(finalCurrent)));
            itemMeta.setLore(lore);
            itemStack.setItemMeta(itemMeta);
            player.getInventory().addItem(itemStack);
            return;
        }
        itemMeta.getPersistentDataContainer().set(DURABILITY_KEY, PersistentDataType.INTEGER, current);
        List<String> lore = ProtectedDebugStick.config.getStringList("items.basicDebugStick.lore");
        int finalCurrent = current;
        lore.replaceAll(s -> Tools.replaceColor(s).replace("{durability}", Integer.toString(finalCurrent)));
        itemMeta.setLore(lore);
        item.setItemMeta(itemMeta);

    }

    public static boolean playerHasNotDS(Player player) {
        if (player.getInventory().getItemInMainHand() == null) {
            return true;
        }
        ItemMeta itemMeta = player.getInventory().getItemInMainHand().getItemMeta();
        if (itemMeta == null) {
            return true;
        }

        return !itemMeta.getPersistentDataContainer().has(DURABILITY_KEY, PersistentDataType.INTEGER);
    }

    public static boolean hasNotEnoughDurability(ItemStack item, Property durability) {
        if (item == null) {
            return true;
        }
        ItemMeta itemMeta = item.getItemMeta();
        if (itemMeta == null) {
            return true;
        }
        if (!itemMeta.getPersistentDataContainer().has(DURABILITY_KEY, PersistentDataType.INTEGER)) {
            return true;
        }
        int current = itemMeta.getPersistentDataContainer().get(DURABILITY_KEY, PersistentDataType.INTEGER);
        if (current == -1) {
            return false;
        }
        return (current < durability.getDurability());
    }

    public static int getDurability(ItemStack item) {
        if (item == null) {
            return 0;
        }
        ItemMeta itemMeta = item.getItemMeta();
        if (itemMeta == null) {
            return 0;
        }
        if (!itemMeta.getPersistentDataContainer().has(DURABILITY_KEY, PersistentDataType.INTEGER)) {
            return 0;
        }
        return itemMeta.getPersistentDataContainer().get(DURABILITY_KEY, PersistentDataType.INTEGER);
    }

    public static boolean willBreak(ItemStack item) {
        if (item == null) {
            return false;
        }
        ItemMeta itemMeta = item.getItemMeta();
        if (itemMeta == null) {
            return false;
        }
        if (!itemMeta.getPersistentDataContainer().has(DURABILITY_KEY, PersistentDataType.INTEGER)) {
            return false;
        }
        int current = itemMeta.getPersistentDataContainer().get(DURABILITY_KEY, PersistentDataType.INTEGER);
        return current == 0;
    }


    /**
     * Use this method after that the player uses the debug stick (after the applying of the data)
     * @param player The player who has use the debug stick
     * @param block The block who has been modified
     * @param property The property who has been changed
     * @param value The new value of the property
     */
    public static void afterUse(Player player, Block block, String value, Property property) {
        DebugStick.removeDurability(player, property.getDurability());
        try {
            player.sendMessage(Tools.replaceColor(ProtectedDebugStick.config.getString("messages.successChat")
                    .replace("{prefix}", ProtectedDebugStick.prefix)
                    .replace("{block}", block.getBlockData().getMaterial().toString())
                    .replace("{property}", property.name()).replace("{value}", value)
                    .replace("{durability}", Integer.toString(property.getDurability()))));
        } catch (NullPointerException ignored) {}
        try {

            player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(
                    Tools.replaceColor(ProtectedDebugStick.config.getString("messages.successHotbar"))
                            .replace("{prefix}", ProtectedDebugStick.prefix)
                            .replace("{block}", block.getBlockData().getMaterial().toString())
                            .replace("{property}", property.name()).replace("{value}", value)
                            .replace("{durability}", Integer.toString(property.getDurability()))));
        } catch (NullPointerException ignored) {}

        if (DebugStick.willBreak(player.getInventory().getItemInMainHand())) {
            player.getInventory().setItemInMainHand(null);
            player.sendMessage(Tools.configColor("messages.onBreak").replace("{prefix}", ProtectedDebugStick.prefix)
                    .replace("{player}", player.getName()));

        } else if (ProtectedDebugStick.config.getBoolean("settings.preventPlayerWhenBreaking.enable")) {
            int dsDurability = DebugStick.getDurability(player.getInventory().getItemInMainHand());
            String messageChat = null;
            TextComponent messageHotbar = null;
            try {
                messageChat = Tools.replaceColor(ProtectedDebugStick.config.getString("settings.preventPlayerWhenBreaking.messageChat")
                                .replace("{prefix}", ProtectedDebugStick.prefix)
                                .replace("{player}", player.getName())
                                .replace("{durability}", Integer.toString(dsDurability)))
                        .replace("{property}", property.name())
                        .replace("{value}", value).replace("{block}", block.getBlockData().getMaterial().toString());
            } catch (NullPointerException ignored){}
            try {
                messageHotbar =  new TextComponent(Tools.replaceColor(ProtectedDebugStick.config.getString(
                                "settings.preventPlayerWhenBreaking.messageHotbar"
                        )).replace("{player}", player.getName()).replace("{durability}", Integer.toString(dsDurability))
                        .replace("{property}", property.name()).replace("{value}", value)
                        .replace("{block}", block.getBlockData().getMaterial().toString()));
            } catch (NullPointerException ignored) {}


            if (dsDurability != -1) {
                if (!(ProtectedDebugStick.config.getBoolean("settings.preventPlayerWhenBreaking.sendOneTime")) &&
                        dsDurability <= ProtectedDebugStick.config.getInt("settings.preventPlayerWhenBreaking.durability")) {
                    if (messageChat != null) {
                        player.sendMessage(messageChat);
                    }
                    if (messageHotbar != null) {
                        player.spigot().sendMessage(ChatMessageType.ACTION_BAR, messageHotbar);
                    }
                } else if ((ProtectedDebugStick.config.getBoolean("settings.preventPlayerWhenBreaking.sendOneTime")) &&
                        (dsDurability + property.getDurability() > ProtectedDebugStick.config.getInt("settings.preventPlayerWhenBreaking.durability")) &&
                        (dsDurability <= ProtectedDebugStick.config.getInt("settings.preventPlayerWhenBreaking.durability"))) {
                    if (messageChat != null) {
                        player.sendMessage(messageChat);
                    }
                    if (messageHotbar != null) {
                        player.spigot().sendMessage(ChatMessageType.ACTION_BAR, messageHotbar);
                    }
                }

            }

        }

        if (ProtectedDebugStick.config.getBoolean("settings.logConsole")) {
            Tools.log("[LOG] " + player.getName() + " -> {" + property + "} = \"" + value + "\" at '" + block.getLocation().getBlockX() + " " +
                    block.getLocation().getBlockY() + " " + block.getLocation().getBlockZ() + "' [" + block.getBlockData().getMaterial() + "] in the world \"" +
                    block.getLocation().getWorld().getUID() + "\" [" + block.getLocation().getWorld().getName() + "]");
        }

        if (ProtectedDebugStick.config.getBoolean("settings.logFile")) {
            if (Files.notExists(Paths.get(ProtectedDebugStick.getInstance().getDataFolder() + "/logs"))) {
                if (!new File(ProtectedDebugStick.getInstance().getDataFolder(), "/logs").mkdir()) {
                    Tools.log("Error during the creation of the folder of the logs. Disabling the log file features", Tools.LOG_SEVERE);
                    ProtectedDebugStick.config.set("settings.logFile", false);
                    ProtectedDebugStick.getInstance().saveConfig();
                    return;
                }
            }

            File logFile = new File(ProtectedDebugStick.getInstance().getDataFolder(), "/logs/" + LocalDate.now() + ".txt");
            try {
                if (logFile.createNewFile()) {
                    Tools.log("Creating a new log file for today");
                }
                LocalDateTime now = LocalDateTime.now();
                FileWriter log = new FileWriter(logFile, true);
                log.write("[" + now.getHour() + ":" + now.getMinute() + ":" + now.getSecond() + "] " +
                        player.getName() + " -> {" + property + "} = \"" + value + "\" at '" + block.getLocation().getBlockX() + " " +
                        block.getLocation().getBlockY() + " " + block.getLocation().getBlockZ() + "' [" + block.getBlockData().getMaterial() + "] in the world \"" +
                        block.getLocation().getWorld().getUID() + "\" [" + block.getLocation().getWorld().getName() + "]\n");
                log.close();
            } catch (IOException e) {
                e.printStackTrace();
                Tools.log("Unable to write in the log file. Disabling the log file feature", Tools.LOG_SEVERE);
                ProtectedDebugStick.config.set("settings.logFile", false);
                ProtectedDebugStick.getInstance().saveConfig();
            }
        }
    }
}
