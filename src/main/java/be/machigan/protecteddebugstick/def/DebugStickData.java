package be.machigan.protecteddebugstick.def;

import org.jetbrains.annotations.NotNull;

import java.io.Serializable;

public class DebugStickData implements Serializable {
    private static final long serialVersionUID = 1L;
    @NotNull
    private final DebugStick debugStick;

    public DebugStickData(@NotNull DebugStick debugStick) {
        this.debugStick = debugStick;
    }

    public DebugStickData(int durability) {
        this.debugStick = new BasicDebugStick(durability);
    }

    @NotNull
    public DebugStick getDebugStick() {
        return this.debugStick;
    }
}
