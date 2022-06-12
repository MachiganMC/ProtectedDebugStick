package be.machigan.protecteddebugstick.event;

import be.machigan.protecteddebugstick.ProtectedDebugStick;
import be.machigan.protecteddebugstick.def.DebugStick;
import be.machigan.protecteddebugstick.def.Durability;
import be.machigan.protecteddebugstick.utils.Utils;
import me.ryanhamshire.GriefPrevention.GriefPrevention;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.block.data.Ageable;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.Powerable;
import org.bukkit.block.data.Waterlogged;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;

public class ClickCreative implements Listener {

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
        if (!e.getAction().equals(Action.LEFT_CLICK_BLOCK)) {
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


        String value;
        BlockData data = e.getClickedBlock().getBlockData();
        if (data == null) {
            return;
        }

        if (data instanceof Waterlogged) {
            if (!e.getPlayer().hasPermission("pds.properties.waterlogged")) {
                e.getPlayer().sendMessage(Utils.configColor("messages.noPerm.noPermProperty").replace("{prefix}", ProtectedDebugStick.prefix)
                        .replace("{player}", e.getPlayer().getName().replace("{property}", "water log"))
                        .replace("{perm}", "pds.properties.waterlogged"));
                return;
            }
            if (!DebugStick.canUse(e.getPlayer().getInventory().getItemInMainHand(), Durability.WATERLOGGED.value())) {
                e.getPlayer().sendMessage(Utils.configColor("messages.notEnoughDurability").replace("{prefix}", ProtectedDebugStick.prefix)
                        .replace("{player}", e.getPlayer().getName())
                        .replace("{durability}", Integer.toString(DebugStick.getDurability(e.getPlayer().getInventory().getItemInMainHand())))
                        .replace("{needed}", Integer.toString(Durability.WATERLOGGED.value())));
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

            ((Waterlogged) data).setWaterlogged(!((Waterlogged) data).isWaterlogged());
            value = Boolean.toString(((Waterlogged) data).isWaterlogged()).toUpperCase();
            e.getClickedBlock().setBlockData(data);
            DebugStick.removeDurability(e.getPlayer(), Durability.WATERLOGGED.value());
            try {
                e.getPlayer().sendMessage(Utils.replaceColor(ProtectedDebugStick.config.getString("messages.successChat")
                        .replace("{prefix}", ProtectedDebugStick.prefix)
                        .replace("{block}", e.getClickedBlock().getBlockData().getMaterial().toString())
                        .replace("{property}", "water log").replace("{value}", value)
                        .replace("{durability}", Integer.toString(Durability.WATERLOGGED.value()))));
            } catch (NullPointerException ignored) {}
            try {

                e.getPlayer().spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(
                        Utils.replaceColor(ProtectedDebugStick.config.getString("messages.successHotbar"))
                                .replace("{prefix}", ProtectedDebugStick.prefix)
                                .replace("{block}", e.getClickedBlock().getBlockData().getMaterial().toString())
                                .replace("{property}", "water log").replace("{value}", value)
                                .replace("{durability}", Integer.toString(Durability.WATERLOGGED.value()))));
            } catch (NullPointerException ignored) {}

            if (DebugStick.willBreak(e.getPlayer().getInventory().getItemInMainHand())) {
                e.getPlayer().getInventory().setItemInMainHand(null);
                e.getPlayer().sendMessage(Utils.configColor("messages.onBreak").replace("{prefix}", ProtectedDebugStick.prefix)
                        .replace("{player}", e.getPlayer().getName()));
            }
            return;
        }

        if (data instanceof Powerable) {
            if (!e.getPlayer().hasPermission("pds.properties.powerable")) {
                e.getPlayer().sendMessage(Utils.configColor("messages.noPerm.noPermProperty").replace("{prefix}", ProtectedDebugStick.prefix)
                        .replace("{player}", e.getPlayer().getName().replace("{property}", "powerable"))
                        .replace("{perm}", "pds.properties.powerable"));
                return;
            }
            if (!DebugStick.canUse(e.getPlayer().getInventory().getItemInMainHand(), Durability.POWERABLE.value())) {
                e.getPlayer().sendMessage(Utils.configColor("messages.notEnoughDurability").replace("{prefix}", ProtectedDebugStick.prefix)
                        .replace("{player}", e.getPlayer().getName())
                        .replace("{durability}", Integer.toString(DebugStick.getDurability(e.getPlayer().getInventory().getItemInMainHand())))
                        .replace("{needed}", Integer.toString(Durability.POWERABLE.value())));
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

            ((Powerable) data).setPowered(!((Powerable) data).isPowered());
            value = Boolean.toString(((Powerable) data).isPowered()).toUpperCase();
            e.getClickedBlock().setBlockData(data);
            DebugStick.removeDurability(e.getPlayer(), Durability.POWERABLE.value());
            try {
                e.getPlayer().sendMessage(Utils.replaceColor(ProtectedDebugStick.config.getString("messages.successChat")
                        .replace("{prefix}", ProtectedDebugStick.prefix)
                        .replace("{block}", e.getClickedBlock().getBlockData().getMaterial().toString())
                        .replace("{property}", "power").replace("{value}", value)
                        .replace("{durability}", Integer.toString(Durability.POWERABLE.value()))));
            } catch (NullPointerException ignored) {}
            try {

                e.getPlayer().spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(
                        Utils.replaceColor(ProtectedDebugStick.config.getString("messages.successHotbar"))
                                .replace("{prefix}", ProtectedDebugStick.prefix)
                                .replace("{block}", e.getClickedBlock().getBlockData().getMaterial().toString())
                                .replace("{property}", "power").replace("{value}", value)
                                .replace("{durability}", Integer.toString(Durability.POWERABLE.value()))));
            } catch (NullPointerException ignored) {}

            if (DebugStick.willBreak(e.getPlayer().getInventory().getItemInMainHand())) {
                e.getPlayer().getInventory().setItemInMainHand(null);
                e.getPlayer().sendMessage(Utils.configColor("messages.onBreak").replace("{prefix}", ProtectedDebugStick.prefix)
                        .replace("{player}", e.getPlayer().getName()));
            }
            return;
        }

        if (data instanceof Ageable) {
            if (!e.getPlayer().hasPermission("pds.properties.ageable")) {
                e.getPlayer().sendMessage(Utils.configColor("messages.noPerm.noPermProperty").replace("{prefix}", ProtectedDebugStick.prefix)
                        .replace("{player}", e.getPlayer().getName().replace("{property}", "orientable"))
                        .replace("{perm}", "pds.properties.ageable"));
                return;
            }
            if (!DebugStick.canUse(e.getPlayer().getInventory().getItemInMainHand(), Durability.AGEABLE.value())) {
                e.getPlayer().sendMessage(Utils.configColor("messages.notEnoughDurability").replace("{prefix}", ProtectedDebugStick.prefix)
                        .replace("{player}", e.getPlayer().getName())
                        .replace("{durability}", Integer.toString(DebugStick.getDurability(e.getPlayer().getInventory().getItemInMainHand())))
                        .replace("{needed}", Integer.toString(Durability.AGEABLE.value())));
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

            if (((Ageable) data).getAge() == ((Ageable) data).getMaximumAge()) {
                ((Ageable) data).setAge(0);
                value = "0";
            } else {
                ((Ageable) data).setAge(((Ageable) data).getAge() + 1);
                value = Integer.toString(((Ageable) data).getAge());
            }

            e.getClickedBlock().setBlockData(data);
            DebugStick.removeDurability(e.getPlayer(), Durability.AGEABLE.value());
            try {
                e.getPlayer().sendMessage(Utils.replaceColor(ProtectedDebugStick.config.getString("messages.successChat")
                        .replace("{prefix}", ProtectedDebugStick.prefix)
                        .replace("{block}", e.getClickedBlock().getBlockData().getMaterial().toString())
                        .replace("{property}", "age").replace("{value}", value)
                        .replace("{durability}", Integer.toString(Durability.AGEABLE.value()))));
            } catch (NullPointerException ignored) {}
            try {

                e.getPlayer().spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(
                        Utils.replaceColor(ProtectedDebugStick.config.getString("messages.successHotbar"))
                                .replace("{prefix}", ProtectedDebugStick.prefix)
                                .replace("{block}", e.getClickedBlock().getBlockData().getMaterial().toString())
                                .replace("{property}", "age").replace("{value}", value)
                                .replace("{durability}", Integer.toString(Durability.AGEABLE.value()))));
            } catch (NullPointerException ignored) {}

            if (DebugStick.willBreak(e.getPlayer().getInventory().getItemInMainHand())) {
                e.getPlayer().getInventory().setItemInMainHand(null);
                e.getPlayer().sendMessage(Utils.configColor("messages.onBreak").replace("{prefix}", ProtectedDebugStick.prefix)
                        .replace("{player}", e.getPlayer().getName()));
            }
            return;
        }

        e.getPlayer().sendMessage(Utils.configColor("messages.noCreative").replace("{prefix}", ProtectedDebugStick.prefix)
                .replace("{player}", e.getPlayer().getName()));
    }
}
