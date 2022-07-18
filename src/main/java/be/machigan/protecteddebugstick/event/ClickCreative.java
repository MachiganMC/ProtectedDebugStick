package be.machigan.protecteddebugstick.event;

import be.machigan.protecteddebugstick.ProtectedDebugStick;
import be.machigan.protecteddebugstick.def.DebugStick;
import be.machigan.protecteddebugstick.def.Durability;
import be.machigan.protecteddebugstick.utils.Utils;
import me.ryanhamshire.GriefPrevention.GriefPrevention;
import org.bukkit.block.data.Ageable;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.Powerable;
import org.bukkit.block.data.Waterlogged;
import org.bukkit.block.data.type.Beehive;
import org.bukkit.block.data.type.Leaves;
import org.bukkit.block.data.type.Sapling;
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
                        .replace("{player}", e.getPlayer().getName()).replace("{property}", "water log")
                        .replace("{perm}", "pds.properties.waterlogged"));
                return;
            }
            if (DebugStick.canNotUse(e.getPlayer().getInventory().getItemInMainHand(), Durability.WATERLOGGED)) {
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
            DebugStick.afterUse(e.getPlayer(), e.getClickedBlock(), "water log", value, Durability.WATERLOGGED);
            return;
        }

        if (data instanceof Powerable) {
            if (!e.getPlayer().hasPermission("pds.properties.powerable")) {
                e.getPlayer().sendMessage(Utils.configColor("messages.noPerm.noPermProperty").replace("{prefix}", ProtectedDebugStick.prefix)
                        .replace("{player}", e.getPlayer().getName()).replace("{property}", "powerable")
                        .replace("{perm}", "pds.properties.powerable"));
                return;
            }
            if (DebugStick.canNotUse(e.getPlayer().getInventory().getItemInMainHand(), Durability.POWERABLE)) {
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
            DebugStick.afterUse(e.getPlayer(), e.getClickedBlock(), "power", value, Durability.POWERABLE);
            return;
        }

        if (data instanceof Ageable) {
            if (!e.getPlayer().hasPermission("pds.properties.ageable")) {
                e.getPlayer().sendMessage(Utils.configColor("messages.noPerm.noPermProperty").replace("{prefix}", ProtectedDebugStick.prefix)
                        .replace("{player}", e.getPlayer().getName()).replace("{property}", "orientable")
                        .replace("{perm}", "pds.properties.ageable"));
                return;
            }
            if (DebugStick.canNotUse(e.getPlayer().getInventory().getItemInMainHand(), Durability.AGEABLE)) {
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
            DebugStick.afterUse(e.getPlayer(), e.getClickedBlock(), "age", value, Durability.AGEABLE);

            return;
        }

        if (data instanceof Sapling) {
            if (!e.getPlayer().hasPermission("pds.properties.sapling")) {
                e.getPlayer().sendMessage(Utils.configColor("messages.noPerm.noPermProperty").replace("{prefix}", ProtectedDebugStick.prefix)
                        .replace("{player}", e.getPlayer().getName())
                        .replace("{perm}", "pds.properties.sapling").replace("{property}", "stage"));
                return;
            }
            if (DebugStick.canNotUse(e.getPlayer().getInventory().getItemInMainHand(), Durability.SAPLING)) {
                e.getPlayer().sendMessage(Utils.configColor("messages.notEnoughDurability").replace("{prefix}", ProtectedDebugStick.prefix)
                        .replace("{player}", e.getPlayer().getName())
                        .replace("{durability}", Integer.toString(DebugStick.getDurability(e.getPlayer().getInventory().getItemInMainHand())))
                        .replace("{needed}", Integer.toString(Durability.SAPLING.value())));
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

            if (((Sapling) data).getStage() == ((Sapling) data).getMaximumStage()) {
                ((Sapling) data).setStage(0);
                value = "0";
            } else {
                ((Sapling) data).setStage(((Sapling) data).getStage() + 1);
                value = Integer.toString(((Sapling) data).getStage());
            }
            e.getClickedBlock().setBlockData(data);
            DebugStick.removeDurability(e.getPlayer(), Durability.SAPLING.value());
            DebugStick.afterUse(e.getPlayer(), e.getClickedBlock(), "stage", value, Durability.SAPLING);
            return;
        }

        if (data instanceof Beehive) {
            if (!e.getPlayer().hasPermission("pds.properties.beehive")) {
                e.getPlayer().sendMessage(Utils.configColor("messages.noPerm.noPermProperty").replace("{prefix}", ProtectedDebugStick.prefix)
                        .replace("{player}", e.getPlayer().getName())
                        .replace("{perm}", "pds.properties.beehive").replace("{property}", "honey level"));
                return;
            }
            if (DebugStick.canNotUse(e.getPlayer().getInventory().getItemInMainHand(), Durability.BEEHIVE)) {
                e.getPlayer().sendMessage(Utils.configColor("messages.notEnoughDurability").replace("{prefix}", ProtectedDebugStick.prefix)
                        .replace("{player}", e.getPlayer().getName())
                        .replace("{durability}", Integer.toString(DebugStick.getDurability(e.getPlayer().getInventory().getItemInMainHand())))
                        .replace("{needed}", Integer.toString(Durability.BEEHIVE.value())));
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

            if (((Beehive) data).getHoneyLevel() == ((Beehive) data).getMaximumHoneyLevel()) {
                ((Beehive) data).setHoneyLevel(0);
                value = "0";
            } else {
                ((Beehive) data).setHoneyLevel(((Beehive) data).getHoneyLevel() + 1);
                value = Integer.toString(((Beehive) data).getHoneyLevel());
            }
            e.getClickedBlock().setBlockData(data);
            DebugStick.afterUse(e.getPlayer(), e.getClickedBlock(), "honey level", value, Durability.BEEHIVE);
            return;
        }

        if (data instanceof Leaves) {
            if (!e.getPlayer().hasPermission("pds.properties.persistent")) {
                e.getPlayer().sendMessage(Utils.configColor("messages.noPerm.noPermProperty").replace("{prefix}", ProtectedDebugStick.prefix)
                        .replace("{player}", e.getPlayer().getName()).replace("{property}", "persistent")
                        .replace("{perm}", "pds.properties.persistent"));
                return;
            }
            if (DebugStick.canNotUse(e.getPlayer().getInventory().getItemInMainHand(), Durability.PERSISTENT)) {
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
            DebugStick.afterUse(e.getPlayer(), e.getClickedBlock(), "persistent", value, Durability.PERSISTENT);
            return;
        }


        e.getPlayer().sendMessage(Utils.configColor("messages.noCreative").replace("{prefix}", ProtectedDebugStick.prefix)
                .replace("{player}", e.getPlayer().getName()));
    }
}
