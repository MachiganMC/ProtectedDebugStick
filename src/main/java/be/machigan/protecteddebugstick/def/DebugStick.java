package be.machigan.protecteddebugstick.def;

import be.machigan.protecteddebugstick.ProtectedDebugStick;
import be.machigan.protecteddebugstick.property.Property;
import be.machigan.protecteddebugstick.utils.Config;
import be.machigan.protecteddebugstick.utils.Tools;
import com.google.common.base.Preconditions;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.Serial;
import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

public abstract class DebugStick implements Serializable {
    @Nullable protected Property currentProperty;
    @Serial
    private static final long serialVersionUID = 2L;
    public static final NamespacedKey DEBUG_STICK_KEY = new NamespacedKey(ProtectedDebugStick.getInstance(), "debug-stick-key");
    public static final NamespacedKey INSPECTOR_KEY = new NamespacedKey(ProtectedDebugStick.getInstance(), "debug-stick-inspector");
    public static final List<String> ITEMS = Arrays.asList("basic", "infinity", "inspector");

    @Nullable
    public abstract Property getCurrentProperty();
    public abstract void setCurrentProperty(@Nullable Property property);

    public byte[] convertToByteArray() {
        return new byte[] {
                this.currentProperty == null ?
                        0 :
                        this.currentProperty.toByte()
        };
    }

    @NotNull
    public static ItemStack getBasicDebugStick(int durability) throws IllegalArgumentException {
        Preconditions.checkArgument(durability >= 0, "Durability can't below to 0");

        ItemStack debugStickClone = Config.Item.BASIC.getItemFromConfig().clone();
        ItemMeta debugStickCloneMeta = debugStickClone.getItemMeta();
        debugStickCloneMeta.getPersistentDataContainer().set(DEBUG_STICK_KEY, DebugStickDataType.INSTANCE, new DebugStickData(durability));

        List<String> lore = Config.Item.BASIC.getLore();
        lore.replaceAll(line -> line.replace("{durability}", Integer.toString(durability)));
        debugStickCloneMeta.setLore(lore);

        debugStickClone.setItemMeta(debugStickCloneMeta);
        return debugStickClone;
    }

    @NotNull
    public static ItemStack getInfiniteDebugStick() {
        ItemStack infinityDebugStick = Config.Item.INFINITY.getItemFromConfig();
        ItemMeta infinityDebugStickMeta = infinityDebugStick.getItemMeta();
        infinityDebugStickMeta.getPersistentDataContainer()
                .set(DEBUG_STICK_KEY, DebugStickDataType.INSTANCE, new DebugStickData(new InfiniteDebugStick()));

        infinityDebugStick.setItemMeta(infinityDebugStickMeta);
        return infinityDebugStick;
    }

    @NotNull
    public static ItemStack getInspector() {
        ItemStack inspector = Config.Item.INSPECTOR.getItemFromConfig();
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
        Preconditions.checkArgument(durability >= 0, "Durability cannot be below to 0");

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
        BasicDebugStick basicDebugStick = (BasicDebugStick) meta.getPersistentDataContainer()
                .get(DEBUG_STICK_KEY, DebugStickDataType.INSTANCE).getDebugStick();
        basicDebugStick.setDurability(current);
        meta.getPersistentDataContainer().set(DEBUG_STICK_KEY, DebugStickDataType.INSTANCE, new DebugStickData(basicDebugStick));

        List<String> lore = Config.getConfig().getStringList("Items.BasicDebugStick.Lore");
        int finalCurrent = current;
        lore.replaceAll(s -> Tools.replaceColor(s).replace("{durability}", Integer.toString(finalCurrent)));
        meta.setLore(lore);
        item.setItemMeta(meta);
        player.getInventory().addItem(clone);
        player.getInventory().setItemInMainHand(item);
    }

    public static boolean hasNotEnoughDurability(@NotNull ItemStack item, @NotNull Property property) throws IllegalArgumentException {
        Preconditions.checkArgument(isDebugStick(item), "Item to check has to be a debug stick");

        if (isInfinityDebugStick(item))
            return false;

        return getDurability(item) < property.getDurability();
    }

    public static int getDurability(@NotNull ItemStack item) throws IllegalArgumentException {
        ItemMeta itemMeta = item.getItemMeta();

        Preconditions.checkArgument(itemMeta != null, "Not a debug stick");
        Preconditions.checkArgument(isDebugStick(item), "Not a debug stick");

        DebugStick debugStick = item.getItemMeta().getPersistentDataContainer()
                .get(DEBUG_STICK_KEY, DebugStickDataType.INSTANCE).getDebugStick();
        return ((BasicDebugStick) debugStick).getDurability();
    }

    public static boolean willBreak(@NotNull ItemStack item) throws IllegalArgumentException {
        Preconditions.checkArgument(isDebugStick(item), "Not a debug stick");

        if (isInfinityDebugStick(item))
            return false;

        return getDurability(item) <= 0;
    }

    public static void changeCurrentProperty(@NotNull Property property, @NotNull ItemStack item) {
        Preconditions.checkArgument(DebugStick.isDebugStick(item), "Cannot change property on item that is not a debug stick");
        ItemMeta meta = item.getItemMeta();
        DebugStick debugStick = meta.getPersistentDataContainer()
                .get(DebugStick.DEBUG_STICK_KEY, DebugStickDataType.INSTANCE).getDebugStick();
        debugStick.setCurrentProperty(property);
        meta.getPersistentDataContainer()
                .set(DebugStick.DEBUG_STICK_KEY, DebugStickDataType.INSTANCE, new DebugStickData(debugStick));
        item.setItemMeta(meta);
    }
}