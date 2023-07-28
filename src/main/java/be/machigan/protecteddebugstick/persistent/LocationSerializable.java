package be.machigan.protecteddebugstick.persistent;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.jetbrains.annotations.NotNull;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

public class LocationSerializable implements Serializable {
    private final UUID worldUUID;
    private final double x;
    private final double y;
    private final double z;
    @Serial private static final long serialVersionUID = 12L;
    public LocationSerializable(@NotNull Location location) {
        this.worldUUID = location.getWorld().getUID();
        this.x = location.getX();
        this.y = location.getY();
        this.z = location.getZ();
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof LocationSerializable locationSerializable))return false;
        return (
                this.worldUUID.equals(locationSerializable.worldUUID)
                && this.x == locationSerializable.x
                && this.y == locationSerializable.y
                && this.z == locationSerializable.z
                );
    }

    @NotNull
    public Location toLocation() {
        return new Location(Bukkit.getWorld(this.worldUUID), this.x, this.y, this.z);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.worldUUID, this.x, this.y, this.x);
    }

    @Override
    public String toString() {
        return this.worldUUID + " " + this.x + " " + this.y + " " + this.z;
    }
}
