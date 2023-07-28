package be.machigan.protecteddebugstick.persistent;

import be.machigan.protecteddebugstick.ProtectedDebugStick;
import org.apache.commons.lang.SerializationUtils;
import org.bukkit.Chunk;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Block;
import org.bukkit.persistence.PersistentDataAdapterContext;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;

public class LocationListDataType implements PersistentDataType<byte[], LocationList> {
    private static final LocationListDataType INSTANCE = new LocationListDataType();
    private static final NamespacedKey KEY_LOCATION_EDITED_BLOCK = new NamespacedKey(ProtectedDebugStick.getInstance(), "location-edited-block");

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

    @NotNull
    @Override
    public byte[] toPrimitive(@NotNull LocationList locations, @NotNull PersistentDataAdapterContext persistentDataAdapterContext) {
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

    public static void addNewBlock(@NotNull Block block) {
        PersistentDataContainer container = block.getChunk().getPersistentDataContainer();
        LocationList locations;
        if (container.has(KEY_LOCATION_EDITED_BLOCK, INSTANCE)) {
            locations = container.get(KEY_LOCATION_EDITED_BLOCK, INSTANCE);
        } else {
            locations = new LocationList();
        }
        locations.add(new LocationSerializable(block.getLocation()));
        container.set(KEY_LOCATION_EDITED_BLOCK, INSTANCE, locations);
    }

    public static void removeBlock(@NotNull Block block) {
        PersistentDataContainer container = block.getChunk().getPersistentDataContainer();
        if (container.has(KEY_LOCATION_EDITED_BLOCK, INSTANCE)) {
            LocationList locations = container.get(KEY_LOCATION_EDITED_BLOCK, INSTANCE);
            locations.remove(new LocationSerializable(block.getLocation()));
            container.set(KEY_LOCATION_EDITED_BLOCK, INSTANCE, locations);
        }
    }

    public static boolean isPresent(@NotNull Block block) {
        PersistentDataContainer container = block.getChunk().getPersistentDataContainer();
        if (!container.has(KEY_LOCATION_EDITED_BLOCK, INSTANCE))return false;
        LocationList locations = container.get(KEY_LOCATION_EDITED_BLOCK, INSTANCE);
        return locations.contains(new LocationSerializable(block.getLocation()));
    }

    public static boolean chunkHasLocation(@NotNull Chunk chunk) {
        return chunk.getPersistentDataContainer().has(KEY_LOCATION_EDITED_BLOCK, INSTANCE);
    }

    @NotNull
    public static LocationList getChunkLocations(@NotNull Chunk chunk) {
        if (!chunkHasLocation(chunk))
            throw new IllegalStateException("Chunk has no locations");
        return chunk.getPersistentDataContainer().get(KEY_LOCATION_EDITED_BLOCK, INSTANCE);
    }

    public static void clearLocations(@NotNull Chunk chunk) {
        chunk.getPersistentDataContainer().set(KEY_LOCATION_EDITED_BLOCK, INSTANCE, new LocationList());
    }

    private LocationListDataType() {}
}
