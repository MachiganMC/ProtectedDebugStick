package be.machigan.protecteddebugstick.persistent;

import be.machigan.protecteddebugstick.ProtectedDebugStick;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.NamespacedKey;

import java.util.*;
import java.util.function.Consumer;

public class ChunkDataHandler {
    private static final LocationListDataType LOCATION_LIST_DATA_TYPE = new LocationListDataType();
    private static final NamespacedKey KEY_LOCATION_EDITED_BLOCK = new NamespacedKey(
            ProtectedDebugStick.getInstance(),
            "location-edited-block"
    );
    private final static Map<Chunk, LocationList> CHUNK_CACHE = new HashMap<>();

    public static void load(Chunk chunk) {
        CHUNK_CACHE.put(
                chunk,
                Objects.requireNonNullElse(
                        chunk.getPersistentDataContainer().get(KEY_LOCATION_EDITED_BLOCK, LOCATION_LIST_DATA_TYPE),
                        new LocationList()
                )
        );
    }

    public static void unload(Chunk chunk) {
        CHUNK_CACHE.remove(chunk);
    }

    public static void save(Chunk chunk) {
        LocationList locationList = CHUNK_CACHE.get(chunk);
        if (locationList == null || locationList.isEmpty())
            chunk.getPersistentDataContainer().remove(KEY_LOCATION_EDITED_BLOCK);
        else
            chunk.getPersistentDataContainer().set(KEY_LOCATION_EDITED_BLOCK, LOCATION_LIST_DATA_TYPE, locationList);
    }

    public static void saveAll() {
        CHUNK_CACHE.keySet().forEach(ChunkDataHandler::save);
    }

    public static boolean hasData(Chunk chunk) {
        return !CHUNK_CACHE.get(chunk).isEmpty();
    }

    public static void clear(Chunk chunk) {
        nullSafeActionOnCache(chunk, HashSet::clear);
    }

    public static void addLocation(Location location) {
        nullSafeActionOnCache(location.getChunk(), locationList -> locationList.add(new LocationSerializable(location)));
    }

    public static void removeLocation(Location location) {
        nullSafeActionOnCache(location.getChunk(), locationList -> locationList.remove(new LocationSerializable(location)));
    }

    public static LocationList get(Chunk chunk) {
        return CHUNK_CACHE.getOrDefault(chunk, new LocationList());
    }

    private static void nullSafeActionOnCache(Chunk chunk, Consumer<LocationList> action) {
        action.accept(Optional
                .ofNullable(CHUNK_CACHE.get(chunk))
                .orElseGet(() -> {
                    LocationList locationList = new LocationList();
                    CHUNK_CACHE.put(chunk, locationList);
                    return locationList;
                })
        );
    }

    public static boolean isPresentAt(Location location) {
        LocationList locationList = CHUNK_CACHE.get(location.getChunk());
        if (locationList == null || locationList.isEmpty()) return false;
        return locationList.contains(new LocationSerializable(location));
    }
}
