package be.machigan.protecteddebugstick.persistent;

import org.apache.commons.lang3.SerializationUtils;
import org.bukkit.persistence.PersistentDataAdapterContext;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;

public class LocationListDataType implements PersistentDataType<byte[], LocationList> {
    @NotNull
    @Override
    public Class<byte[]> getPrimitiveType() {
        return byte[].class;
    }

    @NotNull
    @Override
    public Class<LocationList> getComplexType() {
        return LocationList.class;
    }

    @Override
    public byte @NotNull [] toPrimitive(@NotNull LocationList locations, @NotNull PersistentDataAdapterContext persistentDataAdapterContext) {
        return SerializationUtils.serialize(locations);
    }

    @NotNull
    @Override
    public LocationList fromPrimitive(@NotNull byte[] bytes, @NotNull PersistentDataAdapterContext persistentDataAdapterContext) {
        try {
            InputStream is = new ByteArrayInputStream(bytes);
            ObjectInputStream o = new ObjectInputStream(is);
            return (LocationList) o.readObject();
        } catch (IOException | ClassNotFoundException e) {
            return new LocationList();
        }
    }
}
