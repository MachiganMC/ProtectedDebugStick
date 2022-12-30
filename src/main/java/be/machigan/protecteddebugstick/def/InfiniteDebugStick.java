package be.machigan.protecteddebugstick.def;

import be.machigan.protecteddebugstick.property.Property;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.Serializable;

public class InfiniteDebugStick extends DebugStick implements Serializable {
    final private static long serialVersionUID = 3L;
    @Nullable private Property currentProperty;

    @Override
    public @Nullable Property getCurrentProperty() {
        return this.currentProperty;
    }

    @Override
    public void setCurrentProperty(@NotNull Property property) {
        this.currentProperty = property;
    }
}
