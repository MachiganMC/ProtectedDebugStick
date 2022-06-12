package be.machigan.protecteddebugstick.event;

import be.machigan.protecteddebugstick.ProtectedDebugStick;
import be.machigan.protecteddebugstick.def.DebugStick;
import be.machigan.protecteddebugstick.def.Durability;
import be.machigan.protecteddebugstick.utils.Utils;
import me.ryanhamshire.GriefPrevention.GriefPrevention;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.Lightable;
import org.bukkit.block.data.MultipleFacing;
import org.bukkit.block.data.Rail;
import org.bukkit.block.data.type.Leaves;
import org.bukkit.block.data.type.RedstoneWire;
import org.bukkit.block.data.type.Stairs;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;

import java.util.Random;

public class ClickSpecial implements Listener {

    @EventHandler
    public static void onClick(PlayerInteractEvent e) {
        if (e.getHand() == null) {
            return;
        }
        if (!e.getHand().equals(EquipmentSlot.HAND)) {
            return;
        }
        if (!DebugStick.isPlayerHasDS(e.getPlayer())) {
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
                        .replace("{player}", e.getPlayer().getName().replace("{property}", "shape"))
                        .replace("{perm}", "pds.properties.shape.stairs"));
                return;
            }
            if (!DebugStick.canUse(e.getPlayer().getInventory().getItemInMainHand(), Durability.SHAPE.value())) {
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
            DebugStick.removeDurability(e.getPlayer(), Durability.SHAPE.value());
            try {
                e.getPlayer().sendMessage(Utils.replaceColor(ProtectedDebugStick.config.getString("messages.successChat")
                        .replace("{prefix}", ProtectedDebugStick.prefix)
                        .replace("{block}", e.getClickedBlock().getBlockData().getMaterial().toString())
                        .replace("{property}", "shape").replace("{value}", value)
                        .replace("{durability}", Integer.toString(Durability.SHAPE.value()))));
            } catch (NullPointerException ignored) {}
            try {

                e.getPlayer().spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(
                        Utils.replaceColor(ProtectedDebugStick.config.getString("messages.successHotbar"))
                                .replace("{prefix}", ProtectedDebugStick.prefix)
                                .replace("{block}", e.getClickedBlock().getBlockData().getMaterial().toString())
                                .replace("{property}", "shape").replace("{value}", value)
                                .replace("{durability}", Integer.toString(Durability.SHAPE.value()))));
            } catch (NullPointerException ignored) {}
            if (DebugStick.willBreak(e.getPlayer().getInventory().getItemInMainHand())) {
                e.getPlayer().getInventory().setItemInMainHand(null);
                e.getPlayer().sendMessage(Utils.configColor("messages.onBreak").replace("{prefix}", ProtectedDebugStick.prefix)
                        .replace("{player}", e.getPlayer().getName()));
            }
            return;
        }

        if (data instanceof Rail) {
            if (!e.getPlayer().hasPermission("pds.properties.shape.rails")) {
                e.getPlayer().sendMessage(Utils.configColor("messages.noPerm.noPermProperty").replace("{prefix}", ProtectedDebugStick.prefix)
                        .replace("{player}", e.getPlayer().getName().replace("{property}", "shape"))
                        .replace("{perm}", "pds.properties.shape.rails"));
                return;
            }
            if (!DebugStick.canUse(e.getPlayer().getInventory().getItemInMainHand(), Durability.SHAPE.value())) {
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
            e.getClickedBlock().setBlockData(data);
            DebugStick.removeDurability(e.getPlayer(), Durability.SHAPE.value());
            try {
                e.getPlayer().sendMessage(Utils.replaceColor(ProtectedDebugStick.config.getString("messages.successChat")
                        .replace("{prefix}", ProtectedDebugStick.prefix)
                        .replace("{block}", e.getClickedBlock().getBlockData().getMaterial().toString())
                        .replace("{property}", "shape").replace("{value}", value)
                        .replace("{durability}", Integer.toString(Durability.SHAPE.value()))));
            } catch (NullPointerException ignored) {}
            try {

                e.getPlayer().spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(
                        Utils.replaceColor(ProtectedDebugStick.config.getString("messages.successHotbar"))
                                .replace("{prefix}", ProtectedDebugStick.prefix)
                                .replace("{block}", e.getClickedBlock().getBlockData().getMaterial().toString())
                                .replace("{property}", "shape").replace("{value}", value)
                                .replace("{durability}", Integer.toString(Durability.SHAPE.value()))));
            } catch (NullPointerException ignored) {}

            if (DebugStick.willBreak(e.getPlayer().getInventory().getItemInMainHand())) {
                e.getPlayer().getInventory().setItemInMainHand(null);
                e.getPlayer().sendMessage(Utils.configColor("messages.onBreak").replace("{prefix}", ProtectedDebugStick.prefix)
                        .replace("{player}", e.getPlayer().getName()));
            }
            return;
        }

        if (data instanceof Leaves) {
            if (!e.getPlayer().hasPermission("pds.properties.persistent")) {
                e.getPlayer().sendMessage(Utils.configColor("messages.noPerm.noPermProperty").replace("{prefix}", ProtectedDebugStick.prefix)
                        .replace("{player}", e.getPlayer().getName().replace("{property}", "persistent"))
                        .replace("{perm}", "pds.properties.persistent"));
                return;
            }
            if (!DebugStick.canUse(e.getPlayer().getInventory().getItemInMainHand(), Durability.PERSISTENT.value())) {
                e.getPlayer().sendMessage(Utils.configColor("messages.notEnoughDurability").replace("{prefix}", ProtectedDebugStick.prefix)
                        .replace("{player}", e.getPlayer().getName())
                        .replace("{durability}", Integer.toString(DebugStick.getDurability(e.getPlayer().getInventory().getItemInMainHand())))
                        .replace("{needed}", Integer.toString(Durability.PERSISTENT.value())));
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
            ((Leaves) data).setPersistent(!((Leaves) data).isPersistent());
            value = Boolean.toString(((Leaves) data).isPersistent()).toUpperCase();

            e.getClickedBlock().setBlockData(data);
            DebugStick.removeDurability(e.getPlayer(), Durability.PERSISTENT.value());
            try {
                e.getPlayer().sendMessage(Utils.replaceColor(ProtectedDebugStick.config.getString("messages.successChat")
                        .replace("{prefix}", ProtectedDebugStick.prefix)
                        .replace("{block}", e.getClickedBlock().getBlockData().getMaterial().toString())
                        .replace("{property}", "persistent").replace("{value}", value)
                        .replace("{durability}", Integer.toString(Durability.PERSISTENT.value()))));
            } catch (NullPointerException ignored) {}
            try {

                e.getPlayer().spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(
                        Utils.replaceColor(ProtectedDebugStick.config.getString("messages.successHotbar"))
                                .replace("{prefix}", ProtectedDebugStick.prefix)
                                .replace("{block}", e.getClickedBlock().getBlockData().getMaterial().toString())
                                .replace("{property}", "persistent").replace("{value}", value)
                                .replace("{durability}", Integer.toString(Durability.PERSISTENT.value()))));
            } catch (NullPointerException ignored) {}

            if (DebugStick.willBreak(e.getPlayer().getInventory().getItemInMainHand())) {
                e.getPlayer().getInventory().setItemInMainHand(null);
                e.getPlayer().sendMessage(Utils.configColor("messages.onBreak").replace("{prefix}", ProtectedDebugStick.prefix)
                        .replace("{player}", e.getPlayer().getName()));
            }
            return;
        }

        if (data instanceof MultipleFacing) {
            if (!e.getPlayer().hasPermission("pds.properties.multipleFacing")) {
                e.getPlayer().sendMessage(Utils.configColor("messages.noPerm.noPermProperty").replace("{prefix}", ProtectedDebugStick.prefix)
                        .replace("{player}", e.getPlayer().getName().replace("{property}", "multiple facing"))
                        .replace("{perm}", "pds.properties.multipleFacing"));
                return;
            }
            if (!DebugStick.canUse(e.getPlayer().getInventory().getItemInMainHand(), Durability.MULTIPLEFACING.value())) {
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

            if (e.getBlockFace().equals(BlockFace.UP)) {
                return;
            }
            ((MultipleFacing) data).setFace(e.getBlockFace(), !((MultipleFacing) data).hasFace(e.getBlockFace()));
            value = Boolean.toString(((MultipleFacing) data).hasFace(e.getBlockFace())).toUpperCase();
            e.getClickedBlock().setBlockData(data);
            DebugStick.removeDurability(e.getPlayer(), Durability.MULTIPLEFACING.value());
            try {
                e.getPlayer().sendMessage(Utils.replaceColor(ProtectedDebugStick.config.getString("messages.successChat")
                        .replace("{prefix}", ProtectedDebugStick.prefix)
                        .replace("{block}", e.getClickedBlock().getBlockData().getMaterial().toString())
                        .replace("{property}", e.getBlockFace().toString().toLowerCase() + " facing").replace("{value}", value)
                        .replace("{durability}", Integer.toString(Durability.MULTIPLEFACING.value()))));
            } catch (NullPointerException ignored) {}
            try {

                e.getPlayer().spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(
                        Utils.replaceColor(ProtectedDebugStick.config.getString("messages.successHotbar"))
                                .replace("{prefix}", ProtectedDebugStick.prefix)
                                .replace("{block}", e.getClickedBlock().getBlockData().getMaterial().toString())
                                .replace("{property}", e.getBlockFace().toString().toLowerCase() + " facing").replace("{value}", value)
                                .replace("{durability}", Integer.toString(Durability.MULTIPLEFACING.value()))));
            } catch (NullPointerException ignored) {}

            if (DebugStick.willBreak(e.getPlayer().getInventory().getItemInMainHand())) {
                e.getPlayer().getInventory().setItemInMainHand(null);
                e.getPlayer().sendMessage(Utils.configColor("messages.onBreak").replace("{prefix}", ProtectedDebugStick.prefix)
                        .replace("{player}", e.getPlayer().getName()));
            }
            return;
        }

        if (data instanceof Lightable) {
            if (!e.getPlayer().hasPermission("pds.properties.lightable")) {
                e.getPlayer().sendMessage(Utils.configColor("messages.noPerm.noPermProperty").replace("{prefix}", ProtectedDebugStick.prefix)
                        .replace("{player}", e.getPlayer().getName().replace("{property}", "orientable"))
                        .replace("{perm}", "pds.properties.lightable"));
                return;
            }
            if (!DebugStick.canUse(e.getPlayer().getInventory().getItemInMainHand(), Durability.LIGHTABLE.value())) {
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
            DebugStick.removeDurability(e.getPlayer(), Durability.LIGHTABLE.value());
            try {
                e.getPlayer().sendMessage(Utils.replaceColor(ProtectedDebugStick.config.getString("messages.successChat")
                        .replace("{prefix}", ProtectedDebugStick.prefix)
                        .replace("{block}", e.getClickedBlock().getBlockData().getMaterial().toString())
                        .replace("{property}", "lightable").replace("{value}", value)
                        .replace("{durability}", Integer.toString(Durability.LIGHTABLE.value()))));
            } catch (NullPointerException ignored) {}
            try {

                e.getPlayer().spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(
                        Utils.replaceColor(ProtectedDebugStick.config.getString("messages.successHotbar"))
                                .replace("{prefix}", ProtectedDebugStick.prefix)
                                .replace("{block}", e.getClickedBlock().getBlockData().getMaterial().toString())
                                .replace("{property}", "lightable").replace("{value}", value)
                                .replace("{durability}", Integer.toString(Durability.LIGHTABLE.value()))));
            } catch (NullPointerException ignored) {}

            if (DebugStick.willBreak(e.getPlayer().getInventory().getItemInMainHand())) {
                e.getPlayer().getInventory().setItemInMainHand(null);
                e.getPlayer().sendMessage(Utils.configColor("messages.onBreak").replace("{prefix}", ProtectedDebugStick.prefix)
                        .replace("{player}", e.getPlayer().getName()));
            }
            return;
        }

        if (data instanceof RedstoneWire) {
            if (!e.getPlayer().hasPermission("pds.properties.redstonewire")) {
                e.getPlayer().sendMessage(Utils.configColor("messages.noPerm.noPermProperty").replace("{prefix}", ProtectedDebugStick.prefix)
                        .replace("{player}", e.getPlayer().getName().replace("{property}", "redstone wire"))
                        .replace("{perm}", "pds.properties.redstonewire"));
                return;
            }
            if (!DebugStick.canUse(e.getPlayer().getInventory().getItemInMainHand(), Durability.REDSTONEWIRE.value())) {
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
            DebugStick.removeDurability(e.getPlayer(), Durability.MULTIPLEFACING.value());
            try {
                e.getPlayer().sendMessage(Utils.replaceColor(ProtectedDebugStick.config.getString("messages.successChat")
                        .replace("{prefix}", ProtectedDebugStick.prefix)
                        .replace("{block}", e.getClickedBlock().getBlockData().getMaterial().toString())
                        .replace("{property}", blockFace.toString().toLowerCase() + " facing").replace("{value}", value)
                        .replace("{durability}", Integer.toString(Durability.MULTIPLEFACING.value()))));
            } catch (NullPointerException ignored) {}
            try {

                e.getPlayer().spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(
                        Utils.replaceColor(ProtectedDebugStick.config.getString("messages.successHotbar"))
                                .replace("{prefix}", ProtectedDebugStick.prefix)
                                .replace("{block}", e.getClickedBlock().getBlockData().getMaterial().toString())
                                .replace("{property}", blockFace.toString().toLowerCase() + " facing").replace("{value}", value)
                                .replace("{durability}", Integer.toString(Durability.PERSISTENT.value()))));
            } catch (NullPointerException ignored) {}

            if (DebugStick.willBreak(e.getPlayer().getInventory().getItemInMainHand())) {
                e.getPlayer().getInventory().setItemInMainHand(null);
                e.getPlayer().sendMessage(Utils.configColor("messages.onBreak").replace("{prefix}", ProtectedDebugStick.prefix)
                        .replace("{player}", e.getPlayer().getName()));
            }
            return;
        }

        e.getPlayer().sendMessage(Utils.configColor("messages.noSpecial").replace("{prefix}", ProtectedDebugStick.prefix)
                .replace("{player}", e.getPlayer().getName()));

    }
}

