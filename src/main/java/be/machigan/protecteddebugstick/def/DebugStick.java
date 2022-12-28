package be.machigan.protecteddebugstick.def;

import be.machigan.protecteddebugstick.ProtectedDebugStick;
import be.machigan.protecteddebugstick.property.Property;
import be.machigan.protecteddebugstick.utils.Config;
import be.machigan.protecteddebugstick.utils.Tools;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DebugStick {
    final public static NamespacedKey CURRENT_PROPERTY = new NamespacedKey(ProtectedDebugStick.getInstance(), "debug-stick-current-property");
    final public static NamespacedKey DURABILITY_KEY = new NamespacedKey(ProtectedDebugStick.getInstance(), "debug-stick-durability");
    final public static NamespacedKey INFINITY_DEBUG_STICK = new NamespacedKey(ProtectedDebugStick.getInstance(), "debug-stick-infinity");
    final public static NamespacedKey INSPECTOR_KEY = new NamespacedKey(ProtectedDebugStick.getInstance(), "debug-stick-inspector");
    final public static String METADATA_NAME_FORCE_VALUE = "debug-stick-force-value";
    final public static List<String> ITEMS = new ArrayList<>(Arrays.asList("basic", "infinity", "inspector"));


    @NotNull
    public static ItemStack getDebugStick(int durability) throws IllegalArgumentException {
        if (durability <= 0)
            throw new IllegalArgumentException("Durability can be equal or below to 0");

        ItemStack debugStickClone = Config.Item.BASIC.get().clone();
        ItemMeta debugStickCloneMeta = debugStickClone.getItemMeta();
        debugStickCloneMeta.getPersistentDataContainer().set(DURABILITY_KEY, PersistentDataType.INTEGER, durability);
        debugStickCloneMeta.getPersistentDataContainer().set(CURRENT_PROPERTY, PersistentDataType.STRING, "");

        List<String> lore = ProtectedDebugStick.getInstance().getConfig().getStringList("Items.BasicDebugStick.Lore");
        lore.replaceAll(line -> line = Tools.replaceColor(line).replace("{durability}", Integer.toString(durability)));
        debugStickCloneMeta.setLore(lore);

        debugStickClone.setItemMeta(debugStickCloneMeta);
        return debugStickClone;
    }

    @NotNull
    public static ItemStack getInfinityDebugStick() {
        ItemStack infinityDebugStick = Config.Item.INFINITY.get();
        ItemMeta infinityDebugStickMeta = infinityDebugStick.getItemMeta();
        infinityDebugStickMeta.getPersistentDataContainer().set(CURRENT_PROPERTY, PersistentDataType.STRING, "");
        infinityDebugStickMeta.getPersistentDataContainer().set(INFINITY_DEBUG_STICK, PersistentDataType.INTEGER, 1);
        infinityDebugStick.setItemMeta(infinityDebugStickMeta);
        return infinityDebugStick;
    }

    @NotNull
    public static ItemStack getInspector() {
        ItemStack inspector = Config.Item.INSPECTOR.get();
        ItemMeta inspectorMeta = inspector.getItemMeta();
        inspectorMeta.getPersistentDataContainer().set(INSPECTOR_KEY, PersistentDataType.INTEGER, 1);
        inspector.setItemMeta(inspectorMeta);
        return inspector;
    }

    public static boolean isDebugStick(@NotNull ItemStack item) {
        ItemMeta itemMeta = item.getItemMeta();
        if (itemMeta == null)
            return false;

        return itemMeta.getPersistentDataContainer().has(CURRENT_PROPERTY, PersistentDataType.STRING);
    }

    public static boolean isBasicDebugStick(@NotNull ItemStack item) {
        if (!isDebugStick(item))
            return false;

        return item.getItemMeta().getPersistentDataContainer().has(DURABILITY_KEY, PersistentDataType.INTEGER);
    }

    public static boolean isInfinityDebugStick(@NotNull ItemStack item) {
        if (!isDebugStick(item))
            return false;

        ItemMeta meta = item.getItemMeta();
        return meta.getPersistentDataContainer().has(INFINITY_DEBUG_STICK, PersistentDataType.INTEGER);
    }

    public static boolean isInspector(@NotNull ItemStack item) {
        ItemMeta itemMeta = item.getItemMeta();
        if (itemMeta == null)
            return false;

        return item.getItemMeta().getPersistentDataContainer().has(INSPECTOR_KEY, PersistentDataType.INTEGER);
    }

    public static void removeDurability(@NotNull Player player, int durability) {
        if (durability <= 0)
            throw new IllegalArgumentException("Cannot remove durability below or equal to 0");

        ItemStack item = player.getInventory().getItemInMainHand();

        if (!isBasicDebugStick(item))
            return;

        int current = getDurability(item);
        current -= durability;
        if (current < 0) {
            current = 0;
        }

        ItemStack clone = new ItemStack(Material.AIR);
        if (item.getAmount() > 1) {
            clone = getDebugStick(getDurability(item));
            int amount = item.getAmount();
            clone.setAmount(amount - 1);
            item.setAmount(1);
        }

        ItemMeta meta = item.getItemMeta();
        meta.getPersistentDataContainer().set(DURABILITY_KEY, PersistentDataType.INTEGER, current);
        List<String> lore = ProtectedDebugStick.getInstance().getConfig().getStringList("Items.BasicDebugStick.Lore");
        int finalCurrent = current;
        lore.replaceAll(s -> Tools.replaceColor(s).replace("{durability}", Integer.toString(finalCurrent)));
        meta.setLore(lore);
        item.setItemMeta(meta);
        player.getInventory().addItem(clone);
        player.getInventory().setItemInMainHand(item);
    }

    public static boolean hasNotEnoughDurability(@NotNull ItemStack item, @NotNull Property property) throws IllegalArgumentException {
        if (!isDebugStick(item))
            throw new IllegalArgumentException("Not a debug stick");

        ItemMeta meta = item.getItemMeta();

        if (isInfinityDebugStick(item))
            return false;

        int current = meta.getPersistentDataContainer().get(DURABILITY_KEY, PersistentDataType.INTEGER);
        return (current < property.getDurability());
    }

    public static int getDurability(@NotNull ItemStack item) throws IllegalArgumentException {
        ItemMeta itemMeta = item.getItemMeta();
        if (itemMeta == null)
            throw new IllegalArgumentException("Not a debug stick");

        if (!itemMeta.getPersistentDataContainer().has(DURABILITY_KEY, PersistentDataType.INTEGER))
            throw new IllegalArgumentException("Not a debug stick");

        return itemMeta.getPersistentDataContainer().get(DURABILITY_KEY, PersistentDataType.INTEGER);
    }

    public static boolean willBreak(@NotNull ItemStack item) throws IllegalArgumentException {
        if (!isDebugStick(item))
            throw new IllegalArgumentException("Not a debug stick");

        if (isInfinityDebugStick(item))
            return false;


        int current = item.getItemMeta().getPersistentDataContainer().get(DURABILITY_KEY, PersistentDataType.INTEGER);
        return current == 0;
    }
}