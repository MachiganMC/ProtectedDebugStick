package be.machigan.protecteddebugstick.event;

import be.machigan.protecteddebugstick.ProtectedDebugStick;
import be.machigan.protecteddebugstick.property.Distance;
import be.machigan.protecteddebugstick.property.Property;
import be.machigan.protecteddebugstick.property.action.MultiplefacingAction;
import be.machigan.protecteddebugstick.property.action.WallAction;
import be.machigan.protecteddebugstick.utils.Tools;
import org.bukkit.block.Block;
import org.bukkit.block.data.*;
import org.bukkit.block.data.type.*;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;

public class ClickSpecial {

    public static void onClick(PlayerInteractEvent e) {
        BlockData data = e.getClickedBlock().getBlockData();
        Player player = e.getPlayer();
        Block block = e.getClickedBlock();

        if (data instanceof Stairs) {
            Property.CORRESPONDANCE.get(Stairs.class).run(player, block);
            return;
        }

        if (data instanceof Rail) {
            Property.CORRESPONDANCE.get(Rail.class).run(player, block);
            return;
        }

        if (data instanceof MultipleFacing) {
            MultiplefacingAction action = (MultiplefacingAction) Property.CORRESPONDANCE.get(MultipleFacing.class).getAction();
            action.setClickedBlockFace(e.getBlockFace());
            Property.CORRESPONDANCE.get(MultipleFacing.class).run(player, block);
            return;
        }

        if (data instanceof Lightable) {
            Property.CORRESPONDANCE.get(Lightable.class).run(player, block);
            return;
        }

        if (data instanceof RedstoneWire) {
            Property.CORRESPONDANCE.get(RedstoneWire.class).run(player, block);
            return;
        }

        if (data instanceof Wall) {
            WallAction action = (WallAction) Property.CORRESPONDANCE.get(Wall.class).getAction();
            action.setClickedBlockFace(e.getBlockFace());
            Property.CORRESPONDANCE.get(Wall.class).run(player, block);
            return;
        }

        if (data instanceof Snowable) {
            Property.CORRESPONDANCE.get(Snowable.class).run(player, block);
            return;
        }

        if (data instanceof Leaves) {
            Property.CORRESPONDANCE.get(Distance.class).run(player, block);
            return;
        }

        if (data instanceof Snow) {
            Property.CORRESPONDANCE.get(Snow.class).run(player, block);
            return;
        }

        e.getPlayer().sendMessage(Tools.configColor("messages.noSpecial").replace("{prefix}", ProtectedDebugStick.prefix)
                .replace("{player}", e.getPlayer().getName()));
    }
}

