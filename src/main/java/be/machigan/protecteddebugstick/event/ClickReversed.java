package be.machigan.protecteddebugstick.event;

import be.machigan.protecteddebugstick.ProtectedDebugStick;
import be.machigan.protecteddebugstick.def.DebugStick;
import be.machigan.protecteddebugstick.def.Durability;
import be.machigan.protecteddebugstick.utils.Utils;
import me.ryanhamshire.GriefPrevention.GriefPrevention;
import org.bukkit.block.data.Bisected;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.type.Slab;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;

public class ClickReversed implements Listener {

    @EventHandler
    public static void onClick(PlayerInteractEvent e) {
        if (e.getHand() == null) {
            return;
        }
        if (!e.getHand().equals(EquipmentSlot.HAND)) {
            return;
        }
        if (DebugStick.playerHasNotDS(e.getPlayer())) {
            return;
        }
        e.setCancelled(true);
        if (!e.getAction().equals(Action.LEFT_CLICK_BLOCK)) {
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
        if (data instanceof Slab) {
            if (!e.getPlayer().hasPermission("pds.properties.type")) {
                e.getPlayer().sendMessage(Utils.configColor("messages.noPerm.noPermProperty").replace("{prefix}", ProtectedDebugStick.prefix)
                        .replace("{player}", e.getPlayer().getName()).replace("{property}", "type")
                        .replace("{perm}", "pds.properties.type"));
                return;
            }
            if (DebugStick.canNotUse(e.getPlayer().getInventory().getItemInMainHand(), Durability.TYPE)) {
                e.getPlayer().sendMessage(Utils.configColor("messages.notEnoughDurability").replace("{prefix}", ProtectedDebugStick.prefix)
                        .replace("{player}", e.getPlayer().getName())
                        .replace("{durability}", Integer.toString(DebugStick.getDurability(e.getPlayer().getInventory().getItemInMainHand())))
                        .replace("{needed}", Integer.toString(Durability.TYPE.value())));
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
            switch (((Slab) data).getType()) {
                case TOP:
                    ((Slab) data).setType(Slab.Type.BOTTOM);
                    value = "BOTTOM";
                    break;
                case BOTTOM:
                    ((Slab) data).setType(Slab.Type.TOP);
                    value = "TOP";
                    break;
            }
            e.getClickedBlock().setBlockData(data);
            DebugStick.afterUse(e.getPlayer(), e.getClickedBlock(), "type", value, Durability.TYPE);
            return;
        }

        if (data instanceof Bisected) {
            if (!e.getPlayer().hasPermission("pds.properties.bisected")) {
                e.getPlayer().sendMessage(Utils.configColor("messages.noPerm.noPermProperty").replace("{prefix}", ProtectedDebugStick.prefix)
                        .replace("{player}", e.getPlayer().getName())
                        .replace("{perm}", "pds.properties.bisected").replace("{property}", "bisected"));
                return;
            }
            if (DebugStick.canNotUse(e.getPlayer().getInventory().getItemInMainHand(), Durability.BISECTED)) {
                e.getPlayer().sendMessage(Utils.configColor("messages.notEnoughDurability").replace("{prefix}", ProtectedDebugStick.prefix)
                        .replace("{player}", e.getPlayer().getName())
                        .replace("{durability}", Integer.toString(DebugStick.getDurability(e.getPlayer().getInventory().getItemInMainHand())))
                        .replace("{needed}", Integer.toString(Durability.BISECTED.value())));
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
            switch (((Bisected) data).getHalf()) {
                case TOP:
                    ((Bisected) data).setHalf(Bisected.Half.BOTTOM);
                    value = "BOTTOM";
                    break;
                case BOTTOM:
                    ((Bisected) data).setHalf(Bisected.Half.TOP);
                    value = "TOP";
            }
            e.getClickedBlock().setBlockData(data);
            DebugStick.afterUse(e.getPlayer(), e.getClickedBlock(), "half", value, Durability.BISECTED);
            return;
        }

        e.getPlayer().sendMessage(Utils.configColor("messages.noReversed").replace("{prefix}", ProtectedDebugStick.prefix)
                .replace("{player}", e.getPlayer().getName()));
    }
}
