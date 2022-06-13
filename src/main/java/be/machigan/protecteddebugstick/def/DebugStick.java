package be.machigan.protecteddebugstick.def;

import be.machigan.protecteddebugstick.ProtectedDebugStick;
import be.machigan.protecteddebugstick.utils.Utils;
import me.ryanhamshire.GriefPrevention.GriefPrevention;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DebugStick implements Cloneable {
    public static ItemStack debugStick;
    public static ItemStack infinityDebugStick;
    public static ItemStack inspector;
    public static List<String> blacklist;
    final public static NamespacedKey DURABILITY_KEY = new NamespacedKey(ProtectedDebugStick.instance, "debug-stick-durability");
    final public static NamespacedKey INSPECTOR_KEY = new NamespacedKey(ProtectedDebugStick.instance, "inspector");
    final public static List<String> ITEMS = new ArrayList<>(Arrays.asList("basic", "infinity", "inspector"));

    static {
        init();
        Durability.init();
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    public static void init() {


        String path = "items.basicDebugStick.";
        Material m;
        blacklist = ProtectedDebugStick.config.getStringList("settings.blacklist");
        blacklist.forEach((String s) -> {
            if (Material.matchMaterial(s) == null) {
                Utils.log("The material \"" + s + "\" of the blacklist isn't a valid material.", Utils.LOG_WARNING);
            }
        });
        try {
            m = Material.matchMaterial(ProtectedDebugStick.config.getString(path + "material"));

            if (m == null) {
                Utils.log("Material for basic debug stick is incorrect. Setting to scute.", Utils.LOG_WARNING);
                m = Material.SCUTE;
            }
            if (m.equals(GriefPrevention.instance.config_claims_investigationTool)) {
                Utils.log("Warning, the item of basic debug stick is the same that the investigation tool of GriefPrevention. Some " +
                        "conflicts errors can appear", Utils.LOG_WARNING);
            }
            if (m.equals(GriefPrevention.instance.config_claims_modificationTool)) {
                Utils.log("Warning, the item of basic debug stick is the same that the claim tool of GriefPrevention. Some " +
                        "conflicts errors can appear", Utils.LOG_WARNING);
            }
        } catch (IllegalArgumentException ignored) {
            Utils.log("Path of material for basic debug stick : \"" + path + "material\" hasn't been found. Setting to scute", Utils.LOG_WARNING);
            m = Material.SCUTE;
        }

        debugStick = new ItemStack(m);
        ItemMeta dbMeta = debugStick.getItemMeta();
        if (dbMeta != null) {
            dbMeta.setDisplayName(Utils.configColor(path + "name"));
            List<String> lore;
            try {
                lore = ProtectedDebugStick.config.getStringList(path + "lore");
            } catch (NullPointerException ignored) {
                Utils.log("Path of lore of basic debug stick : \"" + path + "lore\" hasn't been found. Setting lore as empty", Utils.LOG_WARNING);
                lore = new ArrayList<>();
            }
            lore.replaceAll(s -> s = Utils.replaceColor(s));
            dbMeta.setLore(lore);

            try {
                for (String enchant : ProtectedDebugStick.config.getStringList(path + "enchants")) {
                    Enchantment e = Enchantment.getByKey(NamespacedKey.fromString("minecraft:" + enchant.toLowerCase()));
                    if (e == null) {
                        Utils.log("The enchant \"" + enchant + "\" from \"" + path + "enchants\" doesn't exist");
                        continue;
                    }
                    dbMeta.addEnchant(e, 1, true);
                }
            } catch (NullPointerException ignored) {
                Utils.log("Path to enchants list of basic debug stick : \"" + path + "enchants\" hasn't been found. Setting with no enchant.", Utils.LOG_WARNING);
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
                Utils.log("Item meta of basic debug stick hasn't been found. Disabling the plugin. THIS IS NOT NORMAL ! " +
                        "Look at the material, if the material is AIR, a item meta can't be found", Utils.LOG_SEVERE);
            }
            Bukkit.getServer().getPluginManager().disablePlugin(ProtectedDebugStick.instance);
        }

        path = "items.infinityDebugStick.";

        try {
            m = Material.matchMaterial(ProtectedDebugStick.config.getString(path + "material"));
            if (m == null) {
                m = Material.SCUTE;
                Utils.log("Material for infinity debug stick is invalid. Setting to scute.", Utils.LOG_WARNING);
            }
            if (m.equals(GriefPrevention.instance.config_claims_investigationTool)) {
                Utils.log("Warning, the item of infinity debug stick is the same that the investigation tool of GriefPrevention. Some " +
                        "conflicts errors can appear", Utils.LOG_WARNING);
            }
            if (m.equals(GriefPrevention.instance.config_claims_modificationTool)) {
                Utils.log("Warning, the item of infinity debug stick is the same that the claim tool of GriefPrevention. Some " +
                        "conflicts errors can appear", Utils.LOG_WARNING);
            }
        } catch (IllegalArgumentException ignored) {
            m = Material.SCUTE;
            Utils.log("Path of material of infinity debug stick : \"" + path + "material\" hasn't been found. Setting to scute.", Utils.LOG_WARNING);
        }
        infinityDebugStick = new ItemStack(m);

        ItemMeta ibMeta = infinityDebugStick.getItemMeta();
        if (ibMeta != null) {
            ibMeta.setDisplayName(Utils.configColor(path + "name"));

            List<String> lore;
            try {
                lore = ProtectedDebugStick.config.getStringList(path + "lore");
                lore.replaceAll(s -> s = Utils.replaceColor(s));
                ibMeta.setLore(lore);
            } catch (NullPointerException ignored) {
                Utils.log("The lore of infinity debug stick : \"" + path + "lore\" hasn't been found. Setting to empty.", Utils.LOG_WARNING);
                lore = new ArrayList<>();
            }
            ibMeta.setLore(lore);

            try {
                for (String enchant : ProtectedDebugStick.config.getStringList(path + "enchants")) {
                    Enchantment e = Enchantment.getByKey(NamespacedKey.fromString("minecraft:" + enchant));
                    if (e == null) {
                        Utils.log("The enchant " + enchant + " doesn't exist.");
                        continue;
                    }
                    ibMeta.addEnchant(e, 1, true);
                }
            } catch (NullPointerException ignored) {
                Utils.log("Path of enchant list of infinity debug stick : \"" + path  + "enchants \" hasn't been found. Setting with no enchants", Utils.LOG_WARNING);
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
                Utils.log("Item meta of infinty debug stick hasn't been found. Disabling the plugin. THIS IS NOT NORMAL ! " +
                        "Look at the material, if the material is AIR, a item meta can't be found", Utils.LOG_SEVERE);
            }
            Bukkit.getServer().getPluginManager().disablePlugin(ProtectedDebugStick.instance);
        }


        path = "items.inspector.";
        try {
            m = Material.matchMaterial(ProtectedDebugStick.config.getString(path + "material"));
            if (m == null) {
                m = Material.GOLD_INGOT;
                Utils.log("The material of the inspector is invalid. Setting to gold ingot.", Utils.LOG_WARNING);
            }
            if (m.equals(GriefPrevention.instance.config_claims_investigationTool)) {
                Utils.log("Warning, the item of inspector is the same that the investigation tool of GriefPrevention. Some " +
                        "conflicts errors can appear", Utils.LOG_WARNING);
            }
            if (m.equals(GriefPrevention.instance.config_claims_modificationTool)) {
                Utils.log("Warning, the item of inspector is the same that the claim tool of GriefPrevention. Some " +
                        "conflicts errors can appear", Utils.LOG_WARNING);
            }
        } catch (IllegalArgumentException ignored) {
            m = Material.GOLD_INGOT;
            Utils.log("The material of the inspector : \"" + path + "material\" hasn't been found. Setting to gold ingot.", Utils.LOG_WARNING);
        }
        inspector = new ItemStack(m);

        ItemMeta itemMeta = inspector.getItemMeta();
        if (itemMeta != null) {
            itemMeta.setDisplayName(Utils.configColor(path + "name"));

            List<String> lore;
            try {
                lore = ProtectedDebugStick.config.getStringList(path + "lore");
                lore.replaceAll(s -> s = Utils.replaceColor(s));
            } catch (NullPointerException ignored) {
                lore = new ArrayList<>();
                Utils.log("The lore of the inspector : \"" + path + "name\" hasn't been found. Setting with no lore.", Utils.LOG_WARNING);
            }
            itemMeta.setLore(lore);

            try {
                for (String enchant : ProtectedDebugStick.config.getStringList(path + "enchants")) {
                    Enchantment e = Enchantment.getByKey(NamespacedKey.fromString(enchant));
                    if (e == null) {
                        Utils.log("The enchant \"" + enchant + "\" doesn't exist.", Utils.LOG_WARNING);
                        continue;
                    }
                    itemMeta.addEnchant(e, 1, true);
                }
            } catch (NullPointerException ignored) {
                Utils.log("The enchant list of the inspector : \"" + path + "enchants\" hasn't been found. Setting with no enchant.", Utils.LOG_WARNING);
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
        lore.replaceAll(s -> s = Utils.replaceColor(s).replace("{durability}", Integer.toString(durability)));
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
            lore.replaceAll(s -> Utils.replaceColor(s).replace("{durability}", Integer.toString(finalCurrent)));
            itemMeta.setLore(lore);
            itemStack.setItemMeta(itemMeta);
            player.getInventory().addItem(itemStack);
            return;
        }
        itemMeta.getPersistentDataContainer().set(DURABILITY_KEY, PersistentDataType.INTEGER, current);
        List<String> lore = ProtectedDebugStick.config.getStringList("items.basicDebugStick.lore");
        int finalCurrent = current;
        lore.replaceAll(s -> Utils.replaceColor(s).replace("{durability}", Integer.toString(finalCurrent)));
        itemMeta.setLore(lore);
        item.setItemMeta(itemMeta);

    }

    public static boolean isPlayerHasDS(Player player) {
        if (player.getInventory().getItemInMainHand() == null) {
            return false;
        }
        ItemMeta itemMeta = player.getInventory().getItemInMainHand().getItemMeta();
        if (itemMeta == null) {
            return false;
        }

        return itemMeta.getPersistentDataContainer().has(DURABILITY_KEY, PersistentDataType.INTEGER);
    }

    public static boolean canUse(ItemStack item, int durability) {
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
        if (current == -1) {
            return true;
        }
        return (current >= durability);
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


}
