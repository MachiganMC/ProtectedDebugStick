package be.machigan.protecteddebugstick.event;

import be.machigan.protecteddebugstick.property.Property;
import be.machigan.protecteddebugstick.utils.Message;
import org.bukkit.block.Block;
import org.bukkit.block.data.Bisected;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.type.Slab;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;

public class ClickReverse {

    public static void onClick(PlayerInteractEvent e) {
        BlockData data = e.getClickedBlock().getBlockData();
        Player player = e.getPlayer();
        Block block = e.getClickedBlock();

        if (data instanceof Slab) {
            Property.CORRESPONDANCE.get(Slab.class).edit(player, block);
            return;
        }

        if (data instanceof Bisected) {
            Property.CORRESPONDANCE.get(Bisected.class).edit(player, block);
            return;
        }

        Message.getMessage("OnUse.NoPropertyType.Reverse", player, false)
                .replace(block)
                .send(player);
    }
}
