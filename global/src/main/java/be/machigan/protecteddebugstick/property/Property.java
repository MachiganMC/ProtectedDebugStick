package be.machigan.protecteddebugstick.property;

import org.bukkit.block.data.BlockData;
import org.bukkit.configuration.file.FileConfiguration;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public class Property {
    private int durability = 1;
    private final PropertyPermission permission;
    private final String name;
    private final Class<? extends BlockData> dataClass;
    private final PropertyAction action;
    private final int ordinal;
    private static int currentOrdinal = 0;

    public Property(@Nullable String name, @NotNull String permission, @NotNull Class<? extends BlockData> dataClass, @NotNull PropertyAction action) {
        this.name = Objects.requireNonNullElse(name, permission);
        this.permission = new PropertyPermission(permission);
        this.dataClass = dataClass;
        this.action = action;
        this.ordinal = currentOrdinal++;
    }

    public Property(@NotNull String permission, @NotNull Class<? extends BlockData> dataClass, @NotNull PropertyAction action) {
        this(null, permission, dataClass, action);
    }

    public int getDurability() {
        return durability;
    }

    public PropertyPermission getPermission() {
        return permission;
    }

    public Class<? extends BlockData> getDataClass() {
        return dataClass;
    }

    public PropertyAction getAction() {
        return action;
    }

    public int getOrdinal() {
        return ordinal;
    }

    public void setDurability(int durability) {
        this.durability = Math.max(0, durability);
    }

    public void reloadDurabilityFromConfig(FileConfiguration config) {
        this.durability = config.getInt("Settings.Durability." + this.permission.toString().toUpperCase().replace("-", "_"));
    }

    public byte toByte() {
        return (byte) (this.ordinal - 128);
    }

    @Override
    public String toString() {
        return this.name;
    }
}
