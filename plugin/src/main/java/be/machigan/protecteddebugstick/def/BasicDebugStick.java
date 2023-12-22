package be.machigan.protecteddebugstick.def;

import be.machigan.protecteddebugstick.property.Property;
import be.machigan.protecteddebugstick.utils.Tools;
import com.google.common.base.Preconditions;
import org.apache.commons.lang3.ArrayUtils;
import org.jetbrains.annotations.Nullable;

import java.io.Serial;
import java.io.Serializable;

public class BasicDebugStick extends DebugStick implements Serializable {
    @Serial
    private static final long serialVersionUID = 4L;
    private int durability;

    /**
     * @param durability The durability that the basic debugstick will have
     * @throws IllegalArgumentException If the {@code durability} is below or equal to 0
     */
    public BasicDebugStick(int durability) throws IllegalArgumentException {
        Preconditions.checkArgument(durability >= 0, "Durability below to 0");
        this.durability = durability;
    }

    /**
     * @param durability The durability that the debugstick will lose
     * @throws IllegalArgumentException If the {@code durability} is negative
     */
    public void setDurability(int durability) throws IllegalArgumentException {
        Preconditions.checkArgument(durability >= 0, "Cannot set negative durability");
        this.durability = durability;
    }

    public int getDurability() {
        return durability;
    }

    @Override
    public byte[] convertToByteArray() {
        return ArrayUtils.addAll(super.convertToByteArray(), Tools.intToByteArray(this.durability));
    }

    @Override
    public @Nullable Property getCurrentProperty() {
        return this.currentProperty;
    }

    @Override
    public void setCurrentProperty(@Nullable Property property) {
        this.currentProperty = property;
    }
}
