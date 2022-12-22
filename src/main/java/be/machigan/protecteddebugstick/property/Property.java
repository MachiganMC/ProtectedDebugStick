package be.machigan.protecteddebugstick.property;

import be.machigan.protecteddebugstick.ProtectedDebugStick;
import be.machigan.protecteddebugstick.def.DebugStick;
import be.machigan.protecteddebugstick.property.action.*;
import be.machigan.protecteddebugstick.utils.Message;
import be.machigan.protecteddebugstick.utils.Tools;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.*;
import org.bukkit.block.data.type.*;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public enum Property {

    ORIENTABLE(1, "orientable", Orientable.class, new OrientableAction()),
    DIRECTIONAL(1, "directional", Directional.class, new DirectionalAction()),
    ROTATABLE(1, "rotatable", Rotatable.class, new RotatableAction()),
    SLAB(1, "type", Slab.class, new SlabAction()),
    BISECTED(1, "bisected", Bisected.class, new BisectedAction()),
    SHAPE_STAIRS(1, "shape.stairs", Stairs.class, new ShapeStairsAction()),
    SHAPE_RAIL(1, "shape.rails", Rail.class, new ShapeRailAction()),
    PERSISTENT(3, "persistent", Persistent.class, new PersistentAction()),
    MULTIPLEFACING(1, "multiplefacing", MultipleFacing.class, new MultiplefacingAction(BlockFace.UP)),
    LIGHTABLE(5, "lightable", Lightable.class, new LightableAction()),
    REDSTONEWIRE(2, "redstonewire", RedstoneWire.class, new RedstoneWireAction()),
    WATERLOGGED(3, "waterlogged", Waterlogged.class, new WaterLoggedAction()),
    POWERABLE(10, "powerable", Powerable.class, new PowerableAction()),
    AGEABLE(20, "ageable", Ageable.class, new AgeableAction()),
    SAPLING(10, "sapling", Sapling.class, new SaplingAction()),
    BEEHIVE(5, "beehive", Beehive.class, new BeehiveAction()),
    WALL(1, "wall", Wall.class, new WallAction(BlockFace.UP)),
    SNOWABLE(3, "snowable", Snowable.class, new SnowableAction()),
    DISTANCE(1, "distance", Distance.class, new DistanceAction()),
    LAYERS(1, "layers", Snow.class, new LayersAction());


    private int durability;
    @NotNull private final String permission;
    @NotNull private final Class<? extends BlockData> dataClass;
    @NotNull private final PropertyAction action;
    @NotNull final public static Map<Class<? extends BlockData>, Property> CORRESPONDANCE;

    static {
        CORRESPONDANCE = new HashMap<>();
        for (Property property : Property.values())
            CORRESPONDANCE.put(property.dataClass, property);
    }

    Property(int durability, String permission, @NotNull Class<? extends BlockData> dataClass, @NotNull PropertyAction action) {
        this.durability = durability;
        this.permission = "pds.properties." + permission;
        this.dataClass = dataClass;
        this.action = action;
    }


    public void run(@NotNull Player player, @NotNull Block block) {
        if (!player.hasPermission(this.permission)) {
            Message.getMessage("OnUse.NoPerm.Property", player)
                    .replace("{property}", this.name())
                    .replace("{perm}", this.permission)
                    .send(player);
            return;
        }

        if (DebugStick.hasNotEnoughDurability(player.getInventory().getItemInMainHand(), this)) {
            Message.getMessage("OnUse.NotEnoughDurability", player)
                    .replace("{property}", this.name())
                    .replace("{need}", Integer.toString(this.durability))
                    .send(player);
            return;
        }

        if (DebugStick.blacklist.contains(block.getBlockData().getMaterial())) {
            Message.getMessage("OnUse.BlackListed", player)
                    .replace("{property}", this.name())
                    .replace("{block}", block.getBlockData().getMaterial().toString())
                    .send(player);
            return;
        }

        String value = this.action.modify(block.getBlockData(), block);
        DebugStick.afterUse(player, block, value, this);
    }


    public int getDurability() {
        return this.durability;
    }

    @NotNull
    public String getPermission() {
        return this.permission;
    }

    @NotNull
    public Class<? extends BlockData> getDataClass() {
        return this.dataClass;
    }

    @NotNull
    public PropertyAction getAction() {
        return action;
    }

    public static void init() {
        for (Property property : Property.values()) {
            settingFromConfig(property);
        }
    }

    private static void settingFromConfig(Property property) {
        String path = "Settings.Durability.";
        int durability = ProtectedDebugStick.config.getInt(path + property.name());
        if (durability < 0) {
            Tools.log("Cannot set the durability of " + property.name() + " below 0. Setting by default.", Tools.LOG_WARNING);
            return;
        }
        property.durability = durability;
    }
}
