package be.machigan.protecteddebugstick.def;

import be.machigan.protecteddebugstick.property.Property;
import com.google.common.base.Preconditions;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.Serial;
import java.io.Serializable;

public class BasicDebugStick extends DebugStick implements Serializable {
    @Serial
    private static final long serialVersionUID = 4L;
    private int durability;
    @Nullable private Property currentProperty;

    /**
     * @param durability The durability that the basic debugstick will have
     * @throws IllegalArgumentException If the {@code durability} is below or equal to 0
     */
    public BasicDebugStick(int durability) throws IllegalArgumentException {
        Preconditions.checkArgument(durability > 0, "Durability below or equal to 0");
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
    public @Nullable Property getCurrentProperty() {
        return this.currentProperty;
    }

    @Override
    public void setCurrentProperty(@NotNull Property property) {
        this.currentProperty = property;
    }
}
