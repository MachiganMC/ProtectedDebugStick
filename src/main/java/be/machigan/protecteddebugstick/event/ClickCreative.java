package be.machigan.protecteddebugstick.event;

import be.machigan.protecteddebugstick.property.Persistent;
import be.machigan.protecteddebugstick.property.Property;
import be.machigan.protecteddebugstick.utils.Message;
import org.bukkit.block.Block;
import org.bukkit.block.data.Ageable;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.Powerable;
import org.bukkit.block.data.Waterlogged;
import org.bukkit.block.data.type.Beehive;
import org.bukkit.block.data.type.Leaves;
import org.bukkit.block.data.type.Sapling;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;

public class ClickCreative {

    public static void onClick(PlayerInteractEvent e) {
        BlockData data = e.getClickedBlock().getBlockData();
        Player player = e.getPlayer();
        Block block = e.getClickedBlock();

        if (data instanceof Waterlogged) {
            Property.CORRESPONDANCE.get(Waterlogged.class).edit(player, block);
            return;
        }

        if (data instanceof Powerable) {
            Property.CORRESPONDANCE.get(Powerable.class).edit(player, block);
            return;
        }

        if (data instanceof Ageable) {
            Property.CORRESPONDANCE.get(Ageable.class).edit(player, block);
            return;
        }

        if (data instanceof Sapling) {
            Property.CORRESPONDANCE.get(Sapling.class).edit(player, block);
            return;
        }

        if (data instanceof Beehive) {
            Property.CORRESPONDANCE.get(Beehive.class).edit(player, block);
            return;
        }

        if (data instanceof Leaves) {
            Property.CORRESPONDANCE.get(Persistent.class).edit(player, block);
            return;
        }


        Message.getMessage("OnUse.NoPropertyType.Creative", player, false)
                .replace(block)
                .send(player);
    }
}
