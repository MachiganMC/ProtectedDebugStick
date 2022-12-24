package be.machigan.protecteddebugstick.event;

import be.machigan.protecteddebugstick.property.Property;
import be.machigan.protecteddebugstick.utils.Message;
import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.Directional;
import org.bukkit.block.data.Orientable;
import org.bukkit.block.data.Rotatable;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;

public class ClickRotation {
    public static void onClick(PlayerInteractEvent e) {
        BlockData data = e.getClickedBlock().getBlockData();
        Player player = e.getPlayer();
        Block block = e.getClickedBlock();

        if (data instanceof Orientable) {
            Property.CORRESPONDANCE.get(Orientable.class).edit(player, block);
            return;
        }

        if (data instanceof Directional) {
            Property.CORRESPONDANCE.get(Directional.class).edit(player, block);
            return;
        }

        if (data instanceof Rotatable) {
            Property.CORRESPONDANCE.get(Rotatable.class).edit(player, block);
            return;
        }

        Message.getMessage("OnUse.NoPropertyType.Rotation", player, false)
                .replace(block)
                .send(player);
    }
}
