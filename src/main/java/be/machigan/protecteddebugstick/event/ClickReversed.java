package be.machigan.protecteddebugstick.event;

import be.machigan.protecteddebugstick.ProtectedDebugStick;
import be.machigan.protecteddebugstick.property.Property;
import be.machigan.protecteddebugstick.utils.Tools;
import org.bukkit.block.Block;
import org.bukkit.block.data.Bisected;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.type.Slab;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;

public class ClickReversed {

    public static void onClick(PlayerInteractEvent e) {
        BlockData data = e.getClickedBlock().getBlockData();
        Player player = e.getPlayer();
        Block block = e.getClickedBlock();

        if (data instanceof Slab) {
            Property.CORRESPONDANCE.get(Slab.class).run(player, block);
            return;
        }

        if (data instanceof Bisected) {
            Property.CORRESPONDANCE.get(Bisected.class).run(player, block);
            return;
        }

        e.getPlayer().sendMessage(Tools.configColor("messages.noReversed").replace("{prefix}", ProtectedDebugStick.prefix)
                .replace("{player}", e.getPlayer().getName()));
    }
}
