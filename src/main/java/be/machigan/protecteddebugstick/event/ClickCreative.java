package be.machigan.protecteddebugstick.event;

import be.machigan.protecteddebugstick.ProtectedDebugStick;
import be.machigan.protecteddebugstick.property.Persistent;
import be.machigan.protecteddebugstick.property.Property;
import be.machigan.protecteddebugstick.utils.Tools;
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
            Property.CORRESPONDANCE.get(Waterlogged.class).run(player, block);
            return;
        }

        if (data instanceof Powerable) {
            Property.CORRESPONDANCE.get(Powerable.class).run(player, block);
            return;
        }

        if (data instanceof Ageable) {
            Property.CORRESPONDANCE.get(Ageable.class).run(player, block);
            return;
        }

        if (data instanceof Sapling) {
            Property.CORRESPONDANCE.get(Sapling.class).run(player, block);
            return;
        }

        if (data instanceof Beehive) {
            Property.CORRESPONDANCE.get(Beehive.class).run(player, block);
            return;
        }

        if (data instanceof Leaves) {
            Property.CORRESPONDANCE.get(Persistent.class).run(player, block);
            return;
        }


        e.getPlayer().sendMessage(Tools.configColor("messages.noCreative").replace("{prefix}", ProtectedDebugStick.prefix)
                .replace("{player}", e.getPlayer().getName()));
    }
}
