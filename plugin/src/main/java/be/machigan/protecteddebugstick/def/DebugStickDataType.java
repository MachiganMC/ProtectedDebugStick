package be.machigan.protecteddebugstick.def;

import org.apache.commons.lang3.SerializationException;
import org.bukkit.persistence.PersistentDataAdapterContext;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;

public class DebugStickDataType implements PersistentDataType<byte[], DebugStickData> {
    public static final DebugStickDataType INSTANCE = new DebugStickDataType();

    @NotNull
    @Override
    public Class<byte[]> getPrimitiveType() {
        return byte[].class;
    }

    @NotNull
    @Override
    public Class<DebugStickData> getComplexType() {
        return DebugStickData.class;
    }

    @NotNull
    @Override
    public byte[] toPrimitive(@NotNull DebugStickData complex, @NotNull PersistentDataAdapterContext context) {
        return complex.getDebugStick().convertToByteArray();
    }


    @NotNull
    @Override
    public DebugStickData fromPrimitive(@NotNull byte[] bytes, @NotNull PersistentDataAdapterContext context) throws SerializationException {
        try {
            return DebugStickData.fromByteArray(bytes);
        } catch (Exception e) {
            throw new SerializationException("Unable to re-create the debug stick. It might be an old version replace it !");
        }
    }
}
