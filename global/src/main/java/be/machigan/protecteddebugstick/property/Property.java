package be.machigan.protecteddebugstick.property;

import org.bukkit.block.data.BlockData;
import org.jetbrains.annotations.NotNull;

import java.io.Serial;
import java.io.Serializable;

public class Property implements Serializable {
    private int durability = 1;
    private final PropertyPermission permission;
    private final Class<? extends BlockData> dataClass;
    private final PropertyAction action;
    private final int ordinal;
    private static int currentOrdinal = 0;
    @Serial
    private static final long serialVersionUID = 3L;

    public Property(@NotNull String permission, @NotNull Class<? extends BlockData> dataClass, @NotNull PropertyAction action) {
        this.permission = new PropertyPermission(permission);
        this.dataClass = dataClass;
        this.action = action;
        this.ordinal = currentOrdinal;
        currentOrdinal++;
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

    public byte toByte() {
        return (byte) (this.ordinal - 128);
    }

    @Override
    public String toString() {
        return this.permission.toString();
    }
}
