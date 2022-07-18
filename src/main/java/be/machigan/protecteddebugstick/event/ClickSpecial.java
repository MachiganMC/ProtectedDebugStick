package be.machigan.protecteddebugstick.event;

import be.machigan.protecteddebugstick.ProtectedDebugStick;
import be.machigan.protecteddebugstick.def.DebugStick;
import be.machigan.protecteddebugstick.def.Durability;
import be.machigan.protecteddebugstick.utils.Utils;
import me.ryanhamshire.GriefPrevention.GriefPrevention;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.*;
import org.bukkit.block.data.type.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class ClickSpecial implements Listener {
    final private static int MAX_DISTANCE = 7; //currently, the max value for the 'distance' property

    @EventHandler
    public static void onClick(PlayerInteractEvent e) {
        if (e.getHand() == null) {
            return;
        }
        if (!e.getHand().equals(EquipmentSlot.HAND)) {
            e.setCancelled(true);
            return;
        }
        if (DebugStick.playerHasNotDS(e.getPlayer())) {
            return;
        }
        e.setCancelled(true);
        if (!e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
            return;
        }
        if (!e.getPlayer().hasPermission("pds.debugstick.use")) {
            return;
        }
        if (!e.getPlayer().isSneaking()) {
            return;
        }
        if (e.getClickedBlock() == null) {
            return;
        }
        if (GriefPrevention.instance.allowBuild(e.getPlayer(), e.getClickedBlock().getLocation()) != null) {
            e.getPlayer().sendMessage(Utils.configColor("messages.noPlayerClaim").replace("{prefix}", ProtectedDebugStick.prefix)
                    .replace("{player}", e.getPlayer().getName()));
            return;
        }


        BlockData data = e.getClickedBlock().getBlockData();
        String value = "";
        if (data == null) {
            return;
        }
        if (data instanceof Stairs) {
            if (!e.getPlayer().hasPermission("pds.properties.shape.stairs")) {
                e.getPlayer().sendMessage(Utils.configColor("messages.noPerm.noPermProperty").replace("{prefix}", ProtectedDebugStick.prefix)
                        .replace("{player}", e.getPlayer().getName()).replace("{property}", "shape")
                        .replace("{perm}", "pds.properties.shape.stairs"));
                return;
            }
            if (DebugStick.canNotUse(e.getPlayer().getInventory().getItemInMainHand(), Durability.SHAPE)) {
                e.getPlayer().sendMessage(Utils.configColor("messages.notEnoughDurability").replace("{prefix}", ProtectedDebugStick.prefix)
                        .replace("{player}", e.getPlayer().getName())
                        .replace("{durability}", Integer.toString(DebugStick.getDurability(e.getPlayer().getInventory().getItemInMainHand())))
                        .replace("{needed}", Integer.toString(Durability.SHAPE.value())));
                return;
            }
            if (DebugStick.blacklist.contains(e.getClickedBlock().getBlockData().getMaterial().toString())) {
                try {
                    e.getPlayer().sendMessage(Utils.replaceColor(ProtectedDebugStick.config.getString("messages.blacklisted"))
                            .replace("{prefix}", ProtectedDebugStick.prefix)
                            .replace("{player}", e.getPlayer().getName()).replace("{block}", e.getClickedBlock().getBlockData().getMaterial().toString()));
                } catch (NullPointerException ignored){}
                return;
            }
            switch (((Stairs) data).getShape()) {
                case STRAIGHT:
                    ((Stairs) data).setShape(Stairs.Shape.INNER_RIGHT);
                    value = "INNER_RIGHT";
                    break;
                case INNER_RIGHT:
                    ((Stairs) data).setShape(Stairs.Shape.OUTER_LEFT);
                    value = "OUTER_LEFT";
                    break;
                case OUTER_LEFT:
                    ((Stairs) data).setShape(Stairs.Shape.INNER_LEFT);
                    value = "INNER_LEFT";
                    break;
                case INNER_LEFT:
                    ((Stairs) data).setShape(Stairs.Shape.OUTER_RIGHT);
                    value = "OUTER_RIGHT";
                    break;
                case OUTER_RIGHT:
                    ((Stairs) data).setShape(Stairs.Shape.STRAIGHT);
                    value = "STRAIGHT";
                    break;
            }
            e.getClickedBlock().setBlockData(data);
            DebugStick.afterUse(e.getPlayer(), e.getClickedBlock(), "shape", value, Durability.SHAPE);
            return;
        }

        if (data instanceof Rail) {
            if (!e.getPlayer().hasPermission("pds.properties.shape.rails")) {
                e.getPlayer().sendMessage(Utils.configColor("messages.noPerm.noPermProperty").replace("{prefix}", ProtectedDebugStick.prefix)
                        .replace("{player}", e.getPlayer().getName()).replace("{property}", "shape")
                        .replace("{perm}", "pds.properties.shape.rails"));
                return;
            }
            if (DebugStick.canNotUse(e.getPlayer().getInventory().getItemInMainHand(), Durability.SHAPE)) {
                e.getPlayer().sendMessage(Utils.configColor("messages.notEnoughDurability").replace("{prefix}", ProtectedDebugStick.prefix)
                        .replace("{player}", e.getPlayer().getName())
                        .replace("{durability}", Integer.toString(DebugStick.getDurability(e.getPlayer().getInventory().getItemInMainHand())))
                        .replace("{needed}", Integer.toString(Durability.SHAPE.value())));
                return;
            }
            if (DebugStick.blacklist.contains(e.getClickedBlock().getBlockData().getMaterial().toString())) {
                try {
                    e.getPlayer().sendMessage(Utils.replaceColor(ProtectedDebugStick.config.getString("messages.blacklisted"))
                            .replace("{prefix}", ProtectedDebugStick.prefix)
                            .replace("{player}", e.getPlayer().getName()).replace("{block}", e.getClickedBlock().getBlockData().getMaterial().toString()));
                } catch (NullPointerException ignored){}
                return;
            }
            if (((Rail) data).getShapes().size() == 10) {
                switch (((Rail) data).getShape()) {
                    case ASCENDING_NORTH:
                        ((Rail) data).setShape(Rail.Shape.ASCENDING_EAST);
                        value = "ASCENDING_EAST";
                        break;
                    case ASCENDING_EAST:
                        ((Rail) data).setShape(Rail.Shape.ASCENDING_SOUTH);
                        value = "ASCENDING_SOUTH";
                        break;
                    case ASCENDING_SOUTH:
                        ((Rail) data).setShape(Rail.Shape.ASCENDING_WEST);
                        value = "ASCENDING_WEST";
                        break;
                    case ASCENDING_WEST:
                        ((Rail) data).setShape(Rail.Shape.NORTH_EAST);
                        value = "NORTH_EAST";
                        break;
                    case NORTH_EAST:
                        ((Rail) data).setShape(Rail.Shape.NORTH_SOUTH);
                        value = "NORTH_SOUTH";
                        break;
                    case NORTH_SOUTH:
                        ((Rail) data).setShape(Rail.Shape.NORTH_WEST);
                        value = "NORTH_WEST";
                        break;
                    case NORTH_WEST:
                        ((Rail) data).setShape(Rail.Shape.EAST_WEST);
                        value = "EAST_WEST";
                        break;
                    case EAST_WEST:
                        ((Rail) data).setShape(Rail.Shape.SOUTH_EAST);
                        value = "SOUTH_EST";
                        break;
                    case SOUTH_EAST:
                        ((Rail) data).setShape(Rail.Shape.SOUTH_WEST);
                        value = "SOUTH_WEST";
                        break;
                    case SOUTH_WEST:
                        ((Rail) data).setShape(Rail.Shape.ASCENDING_NORTH);
                        value = "ASCENDING_NORTH";
                        break;
                }
            } else if (((Rail) data).getShapes().size() == 6) {
                switch (((Rail) data).getShape()) {
                    case NORTH_SOUTH:
                        ((Rail) data).setShape(Rail.Shape.EAST_WEST);
                        value = "EAST_WEST";
                        break;
                    case EAST_WEST:
                        ((Rail) data).setShape(Rail.Shape.ASCENDING_EAST);
                        value = "ASCENDING_EAST";
                        break;
                    case ASCENDING_EAST:
                        ((Rail) data).setShape(Rail.Shape.ASCENDING_WEST);
                        value = "ASCENDING_WEST";
                        break;
                    case ASCENDING_WEST:
                        ((Rail) data).setShape(Rail.Shape.ASCENDING_NORTH);
                        value = "ASCENDING_NORTH";
                        break;
                    case ASCENDING_NORTH:
                        ((Rail) data).setShape(Rail.Shape.ASCENDING_SOUTH);
                        value = "ASCENDING_SOUTH";
                        break;
                    case ASCENDING_SOUTH:
                        ((Rail) data).setShape(Rail.Shape.NORTH_SOUTH);
                        value = "NORTH_SOUTH";
                        break;
                }
            }
            e.getClickedBlock().setBlockData(data);
            DebugStick.afterUse(e.getPlayer(), e.getClickedBlock(), "shape", value, Durability.SHAPE);
            return;
        }

        if (data instanceof MultipleFacing) {
            if (!e.getPlayer().hasPermission("pds.properties.multipleFacing")) {
                e.getPlayer().sendMessage(Utils.configColor("messages.noPerm.noPermProperty").replace("{prefix}", ProtectedDebugStick.prefix)
                        .replace("{player}", e.getPlayer().getName()).replace("{property}", "multiple facing")
                        .replace("{perm}", "pds.properties.multipleFacing"));
                return;
            }
            if (DebugStick.canNotUse(e.getPlayer().getInventory().getItemInMainHand(), Durability.MULTIPLEFACING)) {
                e.getPlayer().sendMessage(Utils.configColor("messages.notEnoughDurability").replace("{prefix}", ProtectedDebugStick.prefix)
                        .replace("{player}", e.getPlayer().getName())
                        .replace("{durability}", Integer.toString(DebugStick.getDurability(e.getPlayer().getInventory().getItemInMainHand())))
                        .replace("{needed}", Integer.toString(Durability.MULTIPLEFACING.value())));
                return;
            }
            if (DebugStick.blacklist.contains(e.getClickedBlock().getBlockData().getMaterial().toString())) {
                try {
                    e.getPlayer().sendMessage(Utils.replaceColor(ProtectedDebugStick.config.getString("messages.blacklisted"))
                            .replace("{prefix}", ProtectedDebugStick.prefix)
                            .replace("{player}", e.getPlayer().getName()).replace("{block}", e.getClickedBlock().getBlockData().getMaterial().toString()));
                } catch (NullPointerException ignored){}
                return;
            }

            BlockFace blockFace = e.getBlockFace();
            if (!((MultipleFacing) data).getAllowedFaces().contains(BlockFace.UP) && e.getBlockFace().equals(BlockFace.UP)) {
                blockFace = (BlockFace) ((MultipleFacing) data).getAllowedFaces().toArray()[new Random().nextInt(((MultipleFacing) data).getAllowedFaces().size())];
            }
            if (!((MultipleFacing) data).getAllowedFaces().contains(BlockFace.DOWN) && e.getBlockFace().equals(BlockFace.DOWN)) {
                blockFace = (BlockFace) ((MultipleFacing) data).getAllowedFaces().toArray()[new Random().nextInt(((MultipleFacing) data).getAllowedFaces().size())];
            }

            ((MultipleFacing) data).setFace(blockFace, !((MultipleFacing) data).hasFace(blockFace));
            value = Boolean.toString(((MultipleFacing) data).hasFace(blockFace)).toUpperCase();

            e.getClickedBlock().setBlockData(data);
            DebugStick.afterUse(e.getPlayer(), e.getClickedBlock(), blockFace.toString().toLowerCase() + " facing", value, Durability.MULTIPLEFACING);
            return;
        }

        if (data instanceof Lightable) {
            if (!e.getPlayer().hasPermission("pds.properties.lightable")) {
                e.getPlayer().sendMessage(Utils.configColor("messages.noPerm.noPermProperty").replace("{prefix}", ProtectedDebugStick.prefix)
                        .replace("{player}", e.getPlayer().getName()).replace("{property}", "orientable")
                        .replace("{perm}", "pds.properties.lightable"));
                return;
            }
            if (DebugStick.canNotUse(e.getPlayer().getInventory().getItemInMainHand(), Durability.LIGHTABLE)) {
                e.getPlayer().sendMessage(Utils.configColor("messages.notEnoughDurability").replace("{prefix}", ProtectedDebugStick.prefix)
                        .replace("{player}", e.getPlayer().getName())
                        .replace("{durability}", Integer.toString(DebugStick.getDurability(e.getPlayer().getInventory().getItemInMainHand())))
                        .replace("{needed}", Integer.toString(Durability.LIGHTABLE.value())));
                return;
            }
            if (DebugStick.blacklist.contains(e.getClickedBlock().getBlockData().getMaterial().toString())) {
                try {
                    e.getPlayer().sendMessage(Utils.replaceColor(ProtectedDebugStick.config.getString("messages.blacklisted"))
                            .replace("{prefix}", ProtectedDebugStick.prefix)
                            .replace("{player}", e.getPlayer().getName()).replace("{block}", e.getClickedBlock().getBlockData().getMaterial().toString()));
                } catch (NullPointerException ignored){}
                return;
            }

            ((Lightable) data).setLit(!((Lightable) data).isLit());
            value = Boolean.toString(((Lightable) data).isLit()).toUpperCase();
            e.getClickedBlock().setBlockData(data);
            DebugStick.afterUse(e.getPlayer(), e.getClickedBlock(), "lightable", value, Durability.LIGHTABLE);
            return;
        }

        if (data instanceof RedstoneWire) {
            if (!e.getPlayer().hasPermission("pds.properties.redstonewire")) {
                e.getPlayer().sendMessage(Utils.configColor("messages.noPerm.noPermProperty").replace("{prefix}", ProtectedDebugStick.prefix)
                        .replace("{player}", e.getPlayer().getName()).replace("{property}", "redstone wire")
                        .replace("{perm}", "pds.properties.redstonewire"));
                return;
            }
            if (DebugStick.canNotUse(e.getPlayer().getInventory().getItemInMainHand(), Durability.REDSTONEWIRE)) {
                e.getPlayer().sendMessage(Utils.configColor("messages.notEnoughDurability").replace("{prefix}", ProtectedDebugStick.prefix)
                        .replace("{player}", e.getPlayer().getName())
                        .replace("{durability}", Integer.toString(DebugStick.getDurability(e.getPlayer().getInventory().getItemInMainHand())))
                        .replace("{needed}", Integer.toString(Durability.REDSTONEWIRE.value())));
                return;
            }
            if (DebugStick.blacklist.contains(e.getClickedBlock().getBlockData().getMaterial().toString())) {
                try {
                    e.getPlayer().sendMessage(Utils.replaceColor(ProtectedDebugStick.config.getString("messages.blacklisted"))
                            .replace("{prefix}", ProtectedDebugStick.prefix)
                            .replace("{player}", e.getPlayer().getName()).replace("{block}", e.getClickedBlock().getBlockData().getMaterial().toString()));
                } catch (NullPointerException ignored){}
                return;
            }

            BlockFace blockFace;

            try {
                ((RedstoneWire) data).getFace(e.getBlockFace());
                blockFace = e.getBlockFace();

            } catch (IllegalArgumentException ignored) {
                blockFace = (BlockFace) ((RedstoneWire) data).getAllowedFaces().toArray()[new Random().nextInt(((RedstoneWire) data).getAllowedFaces().size())];
            }

            switch (((RedstoneWire) data).getFace(blockFace)) {
                case NONE:
                    ((RedstoneWire) data).setFace(blockFace, RedstoneWire.Connection.SIDE);
                    value = "SIDE";
                    break;
                case SIDE:
                    ((RedstoneWire) data).setFace(blockFace, RedstoneWire.Connection.UP);
                    value = "UP";
                    break;
                case UP:
                    ((RedstoneWire) data).setFace(blockFace, RedstoneWire.Connection.NONE);
                    value = "NONE";
                    break;
            }

            e.getClickedBlock().setBlockData(data);
            DebugStick.afterUse(e.getPlayer(), e.getClickedBlock(), blockFace.toString().toLowerCase() + " facing", value, Durability.MULTIPLEFACING);
            return;
        }

        if (data instanceof Wall) {
            if (!e.getPlayer().hasPermission("pds.properties.wall")) {
                e.getPlayer().sendMessage(Utils.configColor("messages.noPerm.noPermProperty").replace("{prefix}", ProtectedDebugStick.prefix)
                        .replace("{player}", e.getPlayer().getName()).replace("{property}", "wall")
                        .replace("{perm}", "pds.properties.wall"));
                return;
            }
            if (DebugStick.canNotUse(e.getPlayer().getInventory().getItemInMainHand(), Durability.WALL)) {
                e.getPlayer().sendMessage(Utils.configColor("messages.notEnoughDurability").replace("{prefix}", ProtectedDebugStick.prefix)
                        .replace("{player}", e.getPlayer().getName())
                        .replace("{durability}", Integer.toString(DebugStick.getDurability(e.getPlayer().getInventory().getItemInMainHand())))
                        .replace("{needed}", Integer.toString(Durability.WALL.value())));
                return;
            }
            if (DebugStick.blacklist.contains(e.getClickedBlock().getBlockData().getMaterial().toString())) {
                try {
                    e.getPlayer().sendMessage(Utils.replaceColor(ProtectedDebugStick.config.getString("messages.blacklisted"))
                            .replace("{prefix}", ProtectedDebugStick.prefix)
                            .replace("{player}", e.getPlayer().getName()).replace("{block}", e.getClickedBlock().getBlockData().getMaterial().toString()));
                } catch (NullPointerException ignored){}
                return;
            }

            BlockFace blockFace;
            try {
                ((Wall) data).getHeight(e.getBlockFace());
                blockFace = e.getBlockFace();
            } catch (ArrayIndexOutOfBoundsException ignored){
                blockFace = new ArrayList<>(Arrays.asList(BlockFace.NORTH, BlockFace.EAST, BlockFace.SOUTH, BlockFace.WEST))
                        .get(new Random().nextInt(4));
            }

            switch (((Wall) data).getHeight(blockFace)) {
                case NONE:
                    ((Wall) data).setHeight(blockFace, Wall.Height.LOW);
                    value = "LOW";
                    break;
                case LOW:
                    ((Wall) data).setHeight(blockFace, Wall.Height.TALL);
                    value = "TALL";
                    break;
                case TALL:
                    ((Wall) data).setHeight(blockFace, Wall.Height.NONE);
                    value = "NONE";
                    break;
            }
            e.getClickedBlock().setBlockData(data);
            DebugStick.afterUse(e.getPlayer(), e.getClickedBlock(), blockFace + " wall", value, Durability.WALL);
            return;
        }

        if (data instanceof Snowable) {
            if (!e.getPlayer().hasPermission("pds.properties.snowable")) {
                e.getPlayer().sendMessage(Utils.configColor("messages.noPerm.noPermProperty").replace("{prefix}", ProtectedDebugStick.prefix)
                        .replace("{player}", e.getPlayer().getName()).replace("{property}", "snowable")
                        .replace("{perm}", "pds.properties.snowable"));
                return;
            }
            if (DebugStick.canNotUse(e.getPlayer().getInventory().getItemInMainHand(), Durability.SNOWABLE)) {
                e.getPlayer().sendMessage(Utils.configColor("messages.notEnoughDurability").replace("{prefix}", ProtectedDebugStick.prefix)
                        .replace("{player}", e.getPlayer().getName())
                        .replace("{durability}", Integer.toString(DebugStick.getDurability(e.getPlayer().getInventory().getItemInMainHand())))
                        .replace("{needed}", Integer.toString(Durability.SNOWABLE.value())));
                return;
            }
            if (DebugStick.blacklist.contains(e.getClickedBlock().getBlockData().getMaterial().toString())) {
                try {
                    e.getPlayer().sendMessage(Utils.replaceColor(ProtectedDebugStick.config.getString("messages.blacklisted"))
                            .replace("{prefix}", ProtectedDebugStick.prefix)
                            .replace("{player}", e.getPlayer().getName())
                            .replace("{block}", e.getClickedBlock().getBlockData().getMaterial().toString()));
                } catch (NullPointerException ignored){}
                return;
            }

            ((Snowable) data).setSnowy(!((Snowable) data).isSnowy());
            e.getClickedBlock().setBlockData(data);
            DebugStick.afterUse(e.getPlayer(), e.getClickedBlock(), "snowable", Boolean.toString(((Snowable) data).isSnowy()).toUpperCase(),
                    Durability.SNOWABLE);
            return;
        }

        if (data instanceof Leaves) {
            if (!e.getPlayer().hasPermission("pds.properties.distance")) {
                e.getPlayer().sendMessage(Utils.configColor("messages.noPerm.noPermProperty").replace("{prefix}", ProtectedDebugStick.prefix)
                        .replace("{player}", e.getPlayer().getName()).replace("{property}", "distance")
                        .replace("{perm}", "pds.properties.distance"));
                return;
            }
            if (DebugStick.canNotUse(e.getPlayer().getInventory().getItemInMainHand(), Durability.DISTANCE)) {
                e.getPlayer().sendMessage(Utils.configColor("messages.notEnoughDurability").replace("{prefix}", ProtectedDebugStick.prefix)
                        .replace("{player}", e.getPlayer().getName())
                        .replace("{durability}", Integer.toString(DebugStick.getDurability(e.getPlayer().getInventory().getItemInMainHand())))
                        .replace("{needed}", Integer.toString(Durability.DISTANCE.value())));
                return;
            }
            if (DebugStick.blacklist.contains(e.getClickedBlock().getBlockData().getMaterial().toString())) {
                try {
                    e.getPlayer().sendMessage(Utils.replaceColor(ProtectedDebugStick.config.getString("messages.blacklisted"))
                            .replace("{prefix}", ProtectedDebugStick.prefix)
                            .replace("{player}", e.getPlayer().getName())
                            .replace("{block}", e.getClickedBlock().getBlockData().getMaterial().toString()));
                } catch (NullPointerException ignored){}
                return;
            }

            if (((Leaves) data).getDistance() == MAX_DISTANCE) {
                ((Leaves) data).setDistance(1);
            } else {
                ((Leaves) data).setDistance(((Leaves) data).getDistance() + 1);
            }
            e.getClickedBlock().setBlockData(data);
            DebugStick.afterUse(e.getPlayer(), e.getClickedBlock(), "distance", Integer.toString(((Leaves) data).getDistance()), Durability.DISTANCE);
            return;
        }

        if (data instanceof Snow) {
            if (!e.getPlayer().hasPermission("pds.properties.layers")) {
                e.getPlayer().sendMessage(Utils.configColor("messages.noPerm.noPermProperty").replace("{prefix}", ProtectedDebugStick.prefix)
                        .replace("{player}", e.getPlayer().getName()).replace("{property}", "layers")
                        .replace("{perm}", "pds.properties.layers"));
                return;
            }
            if (DebugStick.canNotUse(e.getPlayer().getInventory().getItemInMainHand(), Durability.LAYERS)) {
                e.getPlayer().sendMessage(Utils.configColor("messages.notEnoughDurability").replace("{prefix}", ProtectedDebugStick.prefix)
                        .replace("{player}", e.getPlayer().getName())
                        .replace("{durability}", Integer.toString(DebugStick.getDurability(e.getPlayer().getInventory().getItemInMainHand())))
                        .replace("{needed}", Integer.toString(Durability.LAYERS.value())));
                return;
            }
            if (DebugStick.blacklist.contains(e.getClickedBlock().getBlockData().getMaterial().toString())) {
                try {
                    e.getPlayer().sendMessage(Utils.replaceColor(ProtectedDebugStick.config.getString("messages.blacklisted"))
                            .replace("{prefix}", ProtectedDebugStick.prefix)
                            .replace("{player}", e.getPlayer().getName())
                            .replace("{block}", e.getClickedBlock().getBlockData().getMaterial().toString()));
                } catch (NullPointerException ignored){}
                return;
            }

            if (((Snow) data).getLayers() == ((Snow) data).getMaximumLayers()) {
                ((Snow) data).setLayers(((Snow) data).getMinimumLayers());
            } else {
                ((Snow) data).setLayers(((Snow) data).getLayers() + 1);
            }
            e.getClickedBlock().setBlockData(data);
            DebugStick.afterUse(e.getPlayer(), e.getClickedBlock(), "layers", Integer.toString(((Snow) data).getLayers()), Durability.LAYERS);
            return;
        }

        e.getPlayer().sendMessage(Utils.configColor("messages.noSpecial").replace("{prefix}", ProtectedDebugStick.prefix)
                .replace("{player}", e.getPlayer().getName()));

    }
}

