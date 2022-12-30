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
import org.jetbrains.annotations.Nullable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class DebugStick implements Serializable {
    final private static long serialVersionUID = 2L;
    final public static NamespacedKey DEBUG_STICK_KEY = new NamespacedKey(ProtectedDebugStick.getInstance(), "debug-stick-key");
    final public static NamespacedKey INSPECTOR_KEY = new NamespacedKey(ProtectedDebugStick.getInstance(), "debug-stick-inspector");
    final public static String METADATA_NAME_FORCE_VALUE = "debug-stick-force-value";
    final public static List<String> ITEMS = new ArrayList<>(Arrays.asList("basic", "infinity", "inspector"));


    @NotNull
    public static ItemStack getBasicDebugStick(int durability) throws IllegalArgumentException {
        if (durability <= 0)
            throw new IllegalArgumentException("Durability can be equal or below to 0");

        ItemStack debugStickClone = Config.Item.BASIC.get().clone();
        ItemMeta debugStickCloneMeta = debugStickClone.getItemMeta();
        debugStickCloneMeta.getPersistentDataContainer().set(DEBUG_STICK_KEY, DebugStickDataType.INSTANCE, new DebugStickData(durability));

        List<String> lore = ProtectedDebugStick.getInstance().getConfig().getStringList("Items.BasicDebugStick.Lore");
        lore.replaceAll(line -> line = Tools.replaceColor(line).replace("{durability}", Integer.toString(durability)));
        debugStickCloneMeta.setLore(lore);

        debugStickClone.setItemMeta(debugStickCloneMeta);
        return debugStickClone;
    }

    @NotNull
    public static ItemStack getInfiniteDebugStick() {
        ItemStack infinityDebugStick = Config.Item.INFINITY.get();
        ItemMeta infinityDebugStickMeta = infinityDebugStick.getItemMeta();
        infinityDebugStickMeta.getPersistentDataContainer().set(DEBUG_STICK_KEY, DebugStickDataType.INSTANCE, new DebugStickData(new InfiniteDebugStick()));

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

        return itemMeta.getPersistentDataContainer().has(DEBUG_STICK_KEY, DebugStickDataType.INSTANCE);
    }

    public static boolean isBasicDebugStick(@NotNull ItemStack item) {
        if (!isDebugStick(item))
            return false;

        DebugStickData debugStickData =  item.getItemMeta().getPersistentDataContainer().get(DEBUG_STICK_KEY, DebugStickDataType.INSTANCE);
        return debugStickData.getDebugStick() instanceof BasicDebugStick;
    }

    public static boolean isInfinityDebugStick(@NotNull ItemStack item) {
        if (!isDebugStick(item))
            return false;

        DebugStickData debugStickData = item.getItemMeta().getPersistentDataContainer().get(DEBUG_STICK_KEY, DebugStickDataType.INSTANCE);
        return debugStickData.getDebugStick() instanceof InfiniteDebugStick;
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
            clone = getBasicDebugStick(getDurability(item));
            int amount = item.getAmount();
            clone.setAmount(amount - 1);
            item.setAmount(1);
        }

        ItemMeta meta = item.getItemMeta();
        BasicDebugStick basicDebugStick = (BasicDebugStick) meta.getPersistentDataContainer().get(DEBUG_STICK_KEY, DebugStickDataType.INSTANCE).getDebugStick();
        basicDebugStick.setDurability(current);
        meta.getPersistentDataContainer().set(DEBUG_STICK_KEY, DebugStickDataType.INSTANCE, new DebugStickData(basicDebugStick));

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

        if (isInfinityDebugStick(item))
            return false;

        return getDurability(item) < property.getDurability();
    }

    public static int getDurability(@NotNull ItemStack item) throws IllegalArgumentException {
        ItemMeta itemMeta = item.getItemMeta();
        if (itemMeta == null)
            throw new IllegalArgumentException("Not a debug stick");

        if (!isBasicDebugStick(item))
           throw new IllegalArgumentException("Not a basic debug stick");

        DebugStick debugStick = item.getItemMeta().getPersistentDataContainer().get(DEBUG_STICK_KEY, DebugStickDataType.INSTANCE).getDebugStick();
        return ((BasicDebugStick) debugStick).getDurability();
    }

    public static boolean willBreak(@NotNull ItemStack item) throws IllegalArgumentException {
        if (!isDebugStick(item))
            throw new IllegalArgumentException("Not a debug stick");

        if (isInfinityDebugStick(item))
            return false;

        return getDurability(item) <= 0;
    }

    @Nullable
    public abstract Property getCurrentProperty();
    public abstract void setCurrentProperty(@NotNull Property property);
}