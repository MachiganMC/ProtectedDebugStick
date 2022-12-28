package be.machigan.protecteddebugstick.property;

import be.machigan.protecteddebugstick.ProtectedDebugStick;
import be.machigan.protecteddebugstick.def.DebugStick;
import be.machigan.protecteddebugstick.property.action.*;
import be.machigan.protecteddebugstick.utils.Config;
import be.machigan.protecteddebugstick.utils.Message;
import be.machigan.protecteddebugstick.utils.Permission;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.*;
import org.bukkit.block.data.type.*;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;
import org.jetbrains.annotations.NotNull;

public enum Property {

    ORIENTABLE(1, Permission.Property.ORIENTABLE, Orientable.class, new OrientableAction()),
    DIRECTIONAL(1, Permission.Property.DIRECTIONAL, Directional.class, new DirectionalAction()),
    ROTATABLE(1, Permission.Property.ROTATABLE, Rotatable.class, new RotatableAction()),
    SLAB(1, Permission.Property.SLAB, Slab.class, new SlabAction()),
    BISECTED(1, Permission.Property.BISECTED, Bisected.class, new BisectedAction()),
    SHAPE_STAIRS(1, Permission.Property.SHAPE_STAIRS, Stairs.class, new ShapeStairsAction()),
    SHAPE_RAIL(1, Permission.Property.SHAPE_RAIL, Rail.class, new ShapeRailAction()),
    PERSISTENT(3, Permission.Property.PERSISTENT, Leaves.class, new PersistentAction()),
    MULTIPLE_FACING(1, Permission.Property.MULTIPLE_FACING, MultipleFacing.class, new MultiplefacingAction()),
    LIGHTABLE(5, Permission.Property.LIGHTABLE, Lightable.class, new LightableAction()),
    REDSTONEWIRE(2, Permission.Property.REDSTONE_WIRE, RedstoneWire.class, new RedstoneWireAction()),
    WATER_LOGGED(3, Permission.Property.WATER_LOGGED, Waterlogged.class, new WaterLoggedAction()),
    POWERABLE(10, Permission.Property.POWERABLE, AnaloguePowerable.class, new PowerableAction()),
    AGEABLE(20, Permission.Property.AGEABLE, Ageable.class, new AgeableAction()),
    SAPLING(10, Permission.Property.SAPLING, Sapling.class, new SaplingAction()),
    BEEHIVE(5, Permission.Property.BEEHIVE, Beehive.class, new BeehiveAction()),
    WALL(1, Permission.Property.WALL, Wall.class, new WallAction()),
    SNOWABLE(3, Permission.Property.SNOWABLE, Snowable.class, new SnowableAction()),
    DISTANCE(1, Permission.Property.DISTANCE, Leaves.class, new DistanceAction()),
    LAYERS(1, Permission.Property.LAYERS, Snow.class, new LayersAction());


    private final int durability;
    @NotNull private final Permission.Property permission;
    @NotNull private final Class<? extends BlockData> dataClass;
    @NotNull private final PropertyAction action;


    Property(int durability, Permission.Property permission, @NotNull Class<? extends BlockData> dataClass, @NotNull PropertyAction action) {
        this.durability = Math.max(durability, 0);
        this.permission = permission;
        this.dataClass = dataClass;
        this.action = action;
    }


    public void edit(@NotNull Player player, @NotNull Block block, @NotNull BlockFace blockFace) {
        ItemStack item = player.getInventory().getItemInMainHand();
        if (!this.permission.has(player)) {
            Message.getMessage("OnUse.NoPerm.Property", player, false)
                    .replace(this)
                    .replace("{perm}", this.permission.toString())
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
        if (block.getMetadata(DebugStick.METADATA_NAME_FORCE_VALUE).isEmpty())
            block.setMetadata(DebugStick.METADATA_NAME_FORCE_VALUE, new FixedMetadataValue(ProtectedDebugStick.getInstance(), true));

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
    public Permission.Property getPermission() {
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
