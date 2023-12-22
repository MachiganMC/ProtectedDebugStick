package be.machigan.protecteddebugstick.def;

import be.machigan.protecteddebugstick.ProtectedDebugStick;
import be.machigan.protecteddebugstick.property.Property;
import org.apache.commons.lang3.ArrayUtils;
import org.jetbrains.annotations.NotNull;

import java.io.Serial;
import java.io.Serializable;
import java.nio.ByteBuffer;

public class DebugStickData implements Serializable {
    @Serial private static final long serialVersionUID = 1L;
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

    public static DebugStickData fromByteArray(byte[] bytes) {
        DebugStick debugStick;
        if (bytes.length == 1) {
            debugStick = new InfiniteDebugStick();
        } else {
            debugStick = new BasicDebugStick(ByteBuffer.wrap(ArrayUtils.subarray(bytes, 1, bytes.length)).getInt());
        }
        debugStick.setCurrentProperty(propertyFromByte(bytes[0]));
        return new DebugStickData(debugStick);
    }

    private static Property propertyFromByte(byte b) {
        return ProtectedDebugStick.PROPERTIES.stream()
                .filter(p -> p.getOrdinal() == b + 128)
                .findFirst()
                .orElse(ProtectedDebugStick.PROPERTIES.get(0));
    }
}
