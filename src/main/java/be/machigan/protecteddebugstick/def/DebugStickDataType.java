package be.machigan.protecteddebugstick.def;

import org.apache.commons.lang3.SerializationUtils;
import org.bukkit.persistence.PersistentDataAdapterContext;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;

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
        return SerializationUtils.serialize(complex);
    }


    @NotNull
    @Override
    public DebugStickData fromPrimitive(@NotNull byte[] bytes, @NotNull PersistentDataAdapterContext context) {
        try {
            InputStream is = new ByteArrayInputStream(bytes);
            ObjectInputStream o = new ObjectInputStream(is);
            return (DebugStickData) o.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            throw new IllegalStateException("Unable to re-create the class");
        }
    }
}
