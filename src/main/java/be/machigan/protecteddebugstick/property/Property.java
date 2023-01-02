package be.machigan.protecteddebugstick.property;

import be.machigan.protecteddebugstick.ProtectedDebugStick;
import be.machigan.protecteddebugstick.def.DebugStick;
import be.machigan.protecteddebugstick.property.action.*;
import be.machigan.protecteddebugstick.utils.Config;
import be.machigan.protecteddebugstick.utils.LogUtil;
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

import java.io.Serializable;

public enum Property implements Serializable {
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
    REDSTONE_WIRE(2, Permission.Property.REDSTONE_WIRE, RedstoneWire.class, new RedstoneWireAction()),
    ANALOGUE_POWERABLE(10, Permission.Property.ANALOGUE_POWERABLE, AnaloguePowerable.class, new AnaloguePowerableAction()),
    POWERABLE(10, Permission.Property.POWERABLE, Powerable.class, new PowerableAction()),
    AGEABLE(20, Permission.Property.AGEABLE, Ageable.class, new AgeableAction()),
    STAGE(10, Permission.Property.STAGE, Sapling.class, new StageAction()),
    HONEY_LEVEL(5, Permission.Property.HONEY_LEVEL, Beehive.class, new HoneyLevelAction()),
    WALL(1, Permission.Property.WALL, Wall.class, new WallAction()),
    SNOWABLE(3, Permission.Property.SNOWABLE, Snowable.class, new SnowableAction()),
    DISTANCE(1, Permission.Property.DISTANCE, Leaves.class, new DistanceAction()),
    LAYERS(1, Permission.Property.LAYERS, Snow.class, new LayersAction()),
    BAMBOO_LEAVES(2, Permission.Property.BAMBOO_LEAVES, Bamboo.class, new BambooLeavesAction()),
    TILT(5, Permission.Property.TILT, BigDripleaf.class, new TiltAction()),
    EGGS(50, Permission.Property.EGGS, TurtleEgg.class, new EggsAction()),
    HATCH(20, Permission.Property.HATCH, TurtleEgg.class, new HatchAction()),
    SIGNAL_FIRE(5, Permission.Property.SIGNAL_FIRE, Campfire.class, new SignalFireAction()),
    ATTACHABLE(5, Permission.Property.ATTACHABLE, Attachable.class, new AttachableAction()),
    EXTENDABLE(10, Permission.Property.EXTENDABLE, Piston.class, new ExtendableAction()),
    HANGABLE(5, Permission.Property.HANGABLE, Hangable.class, new HangableAction()),
    CAKE(5, Permission.Property.CAKE, Cake.class, new CakeAction()),
    BERRY(10, Permission.Property.BERRY, CaveVinesPlant.class, new BerryAction()),
    CONDITIONAL(10, Permission.Property.CONDITIONAL, CommandBlock.class, new ConditionalAction()),
    OPENABLE(1, Permission.Property.OPENABLE, Openable.class, new OpenableAction()),
    END_PORTAL_FRAME(5, Permission.Property.END_PORTAL_FRAME, EndPortalFrame.class, new EndPortalFrameAction()),
    FACE_ATTACHABLE(3, Permission.Property.FACE_ATTACHABLE, FaceAttachable.class, new FaceAttachableAction()),
    MOISTURE(3, Permission.Property.MOISTURE, Farmland.class, new MoistureAction()),
    GATE(3, Permission.Property.GATE, Gate.class, new GateAction()),
    LEVELLED(10, Permission.Property.LEVELLED, Levelled.class, new LevelledAction()),
    THICKNESS(5, Permission.Property.THICNKESS, PointedDripstone.class, new ThicknessAction()),
    VERTICAL_DIRECTION(1, Permission.Property.VERTICAL_DIRECTION, PointedDripstone.class, new VerticalDirectionAction()),
    LOCKED(2, Permission.Property.LOCKED, Repeater.class, new LockedAction()),
    CHARGES(5, Permission.Property.CHARGES, RespawnAnchor.class, new ChargesAction()),
    BLOOM(10, Permission.Property.BLOOM, SculkCatalyst.class, new BloomAction()),
    PHASE(3, Permission.Property.PHASE, SculkSensor.class, new PhaseAction()),
    SUMMON(10, Permission.Property.SUMMON, SculkShrieker.class, new SummonAction()),
    SHRIEKING(1, Permission.Property.SHRIEKING, SculkShrieker.class, new ShriekingAction()),
    PICKLES(10, Permission.Property.PICKLES, SeaPickle.class, new PicklesAction()),
    DISARMED(3, Permission.Property.DISARMED, Tripwire.class, new DisarmedAction()),
    WATER_LOGGED(3, Permission.Property.WATER_LOGGED, Waterlogged.class, new WaterLoggedAction());
    private static final long serialVersionUID = 3L;


    private final int durability;
    @NotNull private final Permission.Property permission;
    @NotNull private final Class<? extends BlockData> dataClass;
    @NotNull private final PropertyAction action;


    Property(int durability, @NotNull Permission.Property permission, @NotNull Class<? extends BlockData> dataClass, @NotNull PropertyAction action) {
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

        String oldValue = this.action.getValue(block.getBlockData(), blockFace);
        if (block.getMetadata(DebugStick.METADATA_NAME_FORCE_VALUE).isEmpty())
            block.setMetadata(DebugStick.METADATA_NAME_FORCE_VALUE, new FixedMetadataValue(ProtectedDebugStick.getInstance(), true));
        this.action.modify(block.getBlockData(), block, blockFace);
        String value =  this.action.getValue(block.getBlockData(), blockFace);

        LogUtil.logEdit(player, this, value, block, oldValue);

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
