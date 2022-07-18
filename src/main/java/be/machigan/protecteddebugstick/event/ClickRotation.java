package be.machigan.protecteddebugstick.event;

import be.machigan.protecteddebugstick.ProtectedDebugStick;
import be.machigan.protecteddebugstick.def.DebugStick;
import be.machigan.protecteddebugstick.def.Durability;
import be.machigan.protecteddebugstick.utils.Utils;
import me.ryanhamshire.GriefPrevention.GriefPrevention;
import org.bukkit.Axis;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.Directional;
import org.bukkit.block.data.Orientable;
import org.bukkit.block.data.Rotatable;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;

public class ClickRotation implements Listener {

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
        if (e.getPlayer().isSneaking()) {
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
        if (data == null) {
            return;
        }
        if (data instanceof Orientable) {
            if (!e.getPlayer().hasPermission("pds.properties.orientable")) {
                e.getPlayer().sendMessage(Utils.configColor("messages.noPerm.noPermProperty").replace("{prefix}", ProtectedDebugStick.prefix)
                        .replace("{player}", e.getPlayer().getName()).replace("{property}", "orientable")
                        .replace("{perm}", "pds.properties.orientable"));
                return;
            }
            if (DebugStick.canNotUse(e.getPlayer().getInventory().getItemInMainHand(), Durability.ORIENTABLE)) {
                e.getPlayer().sendMessage(Utils.configColor("messages.notEnoughDurability").replace("{prefix}", ProtectedDebugStick.prefix)
                        .replace("{player}", e.getPlayer().getName())
                        .replace("{durability}", Integer.toString(DebugStick.getDurability(e.getPlayer().getInventory().getItemInMainHand())))
                        .replace("{needed}", Integer.toString(Durability.ORIENTABLE.value())));
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
            String value = "";
            switch (((Orientable) data).getAxis()) {
                case X:
                    ((Orientable) data).setAxis(Axis.Y);
                    value = "Y";
                    break;
                case Y:
                    ((Orientable) data).setAxis(Axis.Z);
                    value = "Z";
                    break;
                case Z:
                    ((Orientable) data).setAxis(Axis.X);
                    value = "X";
                    break;
            }
            e.getClickedBlock().setBlockData(data);
            DebugStick.afterUse(e.getPlayer(), e.getClickedBlock(), "axis", value, Durability.ORIENTABLE);
            return;
        }
        if (data instanceof Directional) {
            if (!e.getPlayer().hasPermission("pds.properties.orientable")) {
                e.getPlayer().sendMessage(Utils.configColor("messages.noPerm.noPermProperty").replace("{prefix}", ProtectedDebugStick.prefix)
                        .replace("{player}", e.getPlayer().getName())
                        .replace("{perm}", "pds.properties.orientable").replace("{property}", "directional"));
                return;
            }
            if (DebugStick.canNotUse(e.getPlayer().getInventory().getItemInMainHand(), Durability.DIRECTIONAL)) {
                e.getPlayer().sendMessage(Utils.configColor("messages.notEnoughDurability").replace("{prefix}", ProtectedDebugStick.prefix)
                        .replace("{player}", e.getPlayer().getName())
                        .replace("{durability}", Integer.toString(DebugStick.getDurability(e.getPlayer().getInventory().getItemInMainHand())))
                        .replace("{needed}", Integer.toString(Durability.DIRECTIONAL.value())));
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
            String value = "";
            if (((Directional) data).getFaces().size() == 6) {
                switch (((Directional) data).getFacing()) {
                    case NORTH:
                        ((Directional) data).setFacing(BlockFace.WEST);
                        value = "WEST";
                        break;
                    case WEST:
                        ((Directional) data).setFacing(BlockFace.SOUTH);
                        value = "SOUTH";
                        break;
                    case SOUTH:
                        ((Directional) data).setFacing(BlockFace.EAST);
                        value = "EAST";
                        break;
                    case EAST:
                        ((Directional) data).setFacing(BlockFace.UP);
                        value = "UP";
                        break;
                    case UP:
                        ((Directional) data).setFacing(BlockFace.DOWN);
                        value = "DOWN";
                        break;
                    case DOWN:
                        ((Directional) data).setFacing(BlockFace.NORTH);
                        value = "NORTH";
                        break;
                }
            } else if (((Directional) data).getFaces().size() == 4) {
                switch (((Directional) data).getFacing()) {
                    case NORTH:
                        ((Directional) data).setFacing(BlockFace.WEST);
                        value = "WEST";
                        break;
                    case WEST:
                        ((Directional) data).setFacing(BlockFace.SOUTH);
                        value = "SOUTH";
                        break;
                    case SOUTH:
                        ((Directional) data).setFacing(BlockFace.EAST);
                        value = "EAST";
                        break;
                    case EAST:
                        ((Directional) data).setFacing(BlockFace.NORTH);
                        value = "NORTH";
                        break;
                }
            }
            e.getClickedBlock().setBlockData(data);
            DebugStick.afterUse(e.getPlayer(), e.getClickedBlock(), "facing", value, Durability.DIRECTIONAL);
            return;
        }

        if (data instanceof Rotatable) {
            if (!e.getPlayer().hasPermission("pds.properties.rotatable")) {
                e.getPlayer().sendMessage(Utils.configColor("messages.noPerm.noPermProperty").replace("{prefix}", ProtectedDebugStick.prefix)
                        .replace("{player}", e.getPlayer().getName())
                        .replace("{perm}", "pds.properties.rotatable").replace("{property}", "rotatable"));
                return;
            }
            if (DebugStick.canNotUse(e.getPlayer().getInventory().getItemInMainHand(), Durability.ROTATABLE)) {
                e.getPlayer().sendMessage(Utils.configColor("messages.notEnoughDurability").replace("{prefix}", ProtectedDebugStick.prefix)
                        .replace("{player}", e.getPlayer().getName())
                        .replace("{durability}", Integer.toString(DebugStick.getDurability(e.getPlayer().getInventory().getItemInMainHand())))
                        .replace("{needed}", Integer.toString(Durability.ROTATABLE.value())));
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

            String value = "";
            switch (((Rotatable) data).getRotation()) {
                case NORTH:
                    ((Rotatable) data).setRotation(BlockFace.NORTH_NORTH_EAST);
                    value = "NORTH_NORTH_EAST";
                    break;
                case NORTH_NORTH_EAST:
                    ((Rotatable) data).setRotation(BlockFace.NORTH_EAST);
                    value = "NORTH_EAST";
                    break;
                case NORTH_EAST:
                    ((Rotatable) data).setRotation(BlockFace.EAST_NORTH_EAST);
                    value = "EAST_NORTH_EAST";
                    break;
                case EAST_NORTH_EAST:
                    ((Rotatable) data).setRotation(BlockFace.EAST);
                    value = "EAST";
                    break;
                case EAST:
                    ((Rotatable) data).setRotation(BlockFace.EAST_SOUTH_EAST);
                    value = "EAST_SOUTH_EAST";
                    break;
                case EAST_SOUTH_EAST:
                    ((Rotatable) data).setRotation(BlockFace.SOUTH_EAST);
                    value = "EAST_SOUTH_EAST";
                    break;
                case SOUTH_EAST:
                    ((Rotatable) data).setRotation(BlockFace.SOUTH_SOUTH_EAST);
                    value = "SOUTH_SOUTH_EAST";
                    break;
                case SOUTH_SOUTH_EAST:
                    ((Rotatable) data).setRotation(BlockFace.SOUTH);
                    value = "SOUTH";
                    break;
                case SOUTH:
                    ((Rotatable) data).setRotation(BlockFace.SOUTH_SOUTH_WEST);
                    value = "SOUTH_SOUTH_WEST";
                    break;
                case SOUTH_SOUTH_WEST:
                    ((Rotatable) data).setRotation(BlockFace.SOUTH_WEST);
                    value = "SOUTH_WEST";
                    break;
                case SOUTH_WEST:
                    ((Rotatable) data).setRotation(BlockFace.WEST_SOUTH_WEST);
                    value = "WEST_SOUTH_WEST";
                    break;
                case WEST_SOUTH_WEST:
                    ((Rotatable) data).setRotation(BlockFace.WEST);
                    value = "WEST";
                    break;
                case WEST:
                    ((Rotatable) data).setRotation(BlockFace.WEST_NORTH_WEST);
                    value = "WEST_NORTH_WEST";
                    break;
                case WEST_NORTH_WEST:
                    ((Rotatable) data).setRotation(BlockFace.NORTH_WEST);
                    value = "NORTH_WEST";
                    break;
                case NORTH_WEST:
                    ((Rotatable) data).setRotation(BlockFace.NORTH_NORTH_WEST);
                    value = "NORTH_NORTH_WEST";
                    break;
                case NORTH_NORTH_WEST:
                    ((Rotatable) data).setRotation(BlockFace.NORTH);
                    value = "NORTH";
                    break;

            }
            e.getClickedBlock().setBlockData(data);
            DebugStick.afterUse(e.getPlayer(), e.getClickedBlock(), "rotatable", value, Durability.ROTATABLE);
            return;
        }

        e.getPlayer().sendMessage(Utils.configColor("messages.noTurnable").replace("{prefix}", ProtectedDebugStick.prefix)
                .replace("{player}", e.getPlayer().getName()));
    }
}
