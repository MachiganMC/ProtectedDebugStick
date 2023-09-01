package be.machigan.protecteddebugstick.def;

import be.machigan.protecteddebugstick.property.Property;
import org.jetbrains.annotations.Nullable;

import java.io.Serial;
import java.io.Serializable;

public class InfiniteDebugStick extends DebugStick implements Serializable {
    @Serial
    private static final long serialVersionUID = 3L;

    @Override
    public @Nullable Property getCurrentProperty() {
        return this.currentProperty;
    }

    @Override
    public void setCurrentProperty(@Nullable Property property) {
        this.currentProperty = property;
    }
}
