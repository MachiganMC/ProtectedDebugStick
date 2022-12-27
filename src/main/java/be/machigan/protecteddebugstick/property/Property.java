package be.machigan.protecteddebugstick.property;

import be.machigan.protecteddebugstick.def.DebugStick;
import be.machigan.protecteddebugstick.property.action.*;
import be.machigan.protecteddebugstick.utils.Config;
import be.machigan.protecteddebugstick.utils.Message;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.*;
import org.bukkit.block.data.type.*;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
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
    PERSISTENT(3, "persistent", Leaves.class, new PersistentAction()),
    MULTIPLEFACING(1, "multiplefacing", MultipleFacing.class, new MultiplefacingAction()),
    LIGHTABLE(5, "lightable", Lightable.class, new LightableAction()),
    REDSTONEWIRE(2, "redstonewire", RedstoneWire.class, new RedstoneWireAction()),
    WATERLOGGED(3, "waterlogged", Waterlogged.class, new WaterLoggedAction()),
    POWERABLE(10, "powerable", AnaloguePowerable.class, new PowerableAction()),
    AGEABLE(20, "ageable", Ageable.class, new AgeableAction()),
    SAPLING(10, "sapling", Sapling.class, new SaplingAction()),
    BEEHIVE(5, "beehive", Beehive.class, new BeehiveAction()),
    WALL(1, "wall", Wall.class, new WallAction()),
    SNOWABLE(3, "snowable", Snowable.class, new SnowableAction()),
    DISTANCE(1, "distance", Leaves.class, new DistanceAction()),
    LAYERS(1, "layers", Snow.class, new LayersAction());


    private final int durability;
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
        this.durability = Math.max(durability, 0);
        this.permission = "pds.properties." + permission;
        this.dataClass = dataClass;
        this.action = action;
    }


    public void edit(@NotNull Player player, @NotNull Block block, @NotNull BlockFace blockFace) {
        ItemStack item = player.getInventory().getItemInMainHand();
        if (!player.hasPermission(this.permission)) {
            Message.getMessage("OnUse.NoPerm.Property", player, false)
                    .replace(this)
                    .replace("{perm}", this.permission)
                    .send(player);
            return;
        }

        if (DebugStick.isDebugStick(item) && DebugStick.hasNotEnoughDurability(item, this)) {
            Message.getMessage("OnUse.NotEnoughDurability", player, false)
                    .replace(this)
                    .replace("{need}", Integer.toString(this.durability))
                    .send(player);
            return;
        }

        this.action.modify(block.getBlockData(), block, blockFace);
        String value =  this.action.getValue(block.getBlockData(), blockFace);
        Message.getMessage("OnUse.Success", player, false)
                .replace(block)
                .replace(this)
                .replace("{value}", value)
                .send(player);

        if (DebugStick.isInfinityDebugStick(item))
            return;

        DebugStick.removeDurability(player, this.durability);
        if (DebugStick.willBreak(item)) {
            player.getInventory().setItemInMainHand(null);
            Message.getMessage("OnUse.Break", player, false)
                    .replace(block)
                    .replace(this)
                    .replace("{value}", value)
                    .send(player);
            return;
        }

        if (Config.PreventPlayerWhenBreaking.isEnable()) {
            int durability = DebugStick.getDurability(item);
            if (DebugStick.isInfinityDebugStick(item)) {
                Message message = Message.getMessage("OnUse.WarnBreakMessage", player, false)
                        .replace(block)
                        .replace(this)
                        .replace("{value}", value)
                        .replace("{durability}", Integer.toString(durability));

                if ((!Config.PreventPlayerWhenBreaking.mustSendOnce()) && durability <= Config.PreventPlayerWhenBreaking.getDurability()) {
                    message.send(player);
                } else if (Config.PreventPlayerWhenBreaking.mustSendOnce() &&
                        (durability + this.durability > Config.PreventPlayerWhenBreaking.getDurability()) &&
                        durability <= Config.PreventPlayerWhenBreaking.getDurability()) {
                    message.send(player);
                }
            }
        }
    }


    public int getDurability() {
        int durabilityConfig = Config.getConfig().getInt("Settings.Durability." + this.name());
        return durabilityConfig < 0 ? this.durability : durabilityConfig;
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
}
