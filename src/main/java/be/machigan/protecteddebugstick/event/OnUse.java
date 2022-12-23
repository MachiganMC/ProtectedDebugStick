package be.machigan.protecteddebugstick.event;

import be.machigan.protecteddebugstick.def.DebugStick;
import be.machigan.protecteddebugstick.utils.Message;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

public class OnUse implements Listener {
    @EventHandler
    public static void onUse(PlayerInteractEvent e) {
        if (e.getHand() == null)
            return;
        if (!e.getHand().equals(EquipmentSlot.HAND))
           return;
        if (DebugStick.playerHasNotDS(e.getPlayer()))
            return;
        e.setCancelled(true);
        if (!e.getPlayer().hasPermission("pds.debugstick.use"))
            return;
        if (e.getClickedBlock() == null) {
            return;
        }
        if (e.getClickedBlock().getBlockData() == null)
            return;

        BlockPlaceEvent event = new BlockPlaceEvent(
                e.getClickedBlock(),
                e.getClickedBlock().getState(),
                e.getClickedBlock(),
                new ItemStack(Material.AIR),
                e.getPlayer(),
                true,
                EquipmentSlot.HAND);
        Bukkit.getServer().getPluginManager().callEvent(event);
        if (event.isCancelled()) {
            Message.getMessage("OnUse.PluginPrevent", e.getPlayer(), false)
                    .replace(e.getClickedBlock())
                    .send(e.getPlayer());
            return;
        }

        if (e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
            if (e.getPlayer().isSneaking()) {
                ClickSpecial.onClick(e);
                return;
            }
            ClickRotation.onClick(e);
            return;
        }

        if (e.getAction().equals(Action.LEFT_CLICK_BLOCK)) {
            if (e.getPlayer().isSneaking()) {
                ClickCreative.onClick(e);
                return;
            }
            ClickReverse.onClick(e);
        }
    }
}
