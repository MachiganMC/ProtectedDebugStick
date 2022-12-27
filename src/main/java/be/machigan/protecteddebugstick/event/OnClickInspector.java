package be.machigan.protecteddebugstick.event;

import be.machigan.protecteddebugstick.def.DebugStick;
import be.machigan.protecteddebugstick.utils.Message;
import be.machigan.protecteddebugstick.utils.Permission;
import be.machigan.protecteddebugstick.utils.Tools;
import org.apache.commons.lang3.StringUtils;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;

public class OnClickInspector implements Listener {

    @EventHandler
    public static void onClick(PlayerInteractEvent e) {
        Player player = e.getPlayer();
        if (e.getClickedBlock() == null) {
            return;
        }
        if (e.getHand() == null) {
            return;
        }
        if (!e.getHand().equals(EquipmentSlot.HAND)) {
            return;
        }
        if (e.getPlayer().getInventory().getItemInMainHand() == null) {
            return;
        }
        if (e.getPlayer().getInventory().getItemInMainHand().getItemMeta() == null) {
            return;
        }
        if (!DebugStick.isInspector(e.getPlayer().getInventory().getItemInMainHand()))
            return;

        e.setCancelled(true);
        String start = "<s:FF0000>&m-------------------<e:FFFF19><s:FF8C19>&lINSPECTOR<e:FF8C19><s:FFFF19>&m------------------------<e:FF0000>&r\n\n";
        String end = "<s:FF0000>&m------------------------<e:FFFF19><s:FFFF19>&m-----------------------------<e:FF0000>";
        if (e.getAction().equals(Action.LEFT_CLICK_BLOCK)) {
            if (!Permission.Item.INSPECTOR_PROPERTIES.has(player)) {
                Message.getMessage("OnUse.NoPerm.Inspector.Implementation", player, false)
                        .replace(e.getClickedBlock())
                        .replace(Permission.Item.INSPECTOR_PROPERTIES)
                        .send(player);
                return;
            }

            String message = e.getClickedBlock().getBlockData().getAsString();
            message = message.replace(",", "\n");
            String blocType = StringUtils.substringBetween(message, "minecraft:", "[");
            if (blocType == null) {
                blocType = message.replace("minecraft:", "");
                message = message.replace("minecraft:" + blocType, "&a&l&n" + blocType.toUpperCase() + " :&r\n\n" +
                        "&8&o   Nothing to see here ...&r");

                e.getPlayer().sendMessage(Tools.replaceColor(start + message + "&r\n" + end));
                return;
            }
            message = message.replace("minecraft:" + blocType, "&a&l&n" + blocType.toUpperCase() + " :&r\n\n")
                    .replace("[", "").replace("]", "");


            for (String line : message.split("\n")) {
                String[] lineCut = line.split("=");
                if (lineCut.length <= 1) {
                    continue;
                }

                String color;
                try {
                    Integer.parseInt(lineCut[1]);
                    color = "&9";
                } catch (NumberFormatException ignored) {
                    if (lineCut[1].equalsIgnoreCase("true") || lineCut[1].equalsIgnoreCase("false")) {
                        color = "&2";
                    } else {
                        color = "&c";
                    }
                }

                String finalLine = "   &8» " + color + lineCut[0] + " &7= &e" + lineCut[1];
                message = message.replace(line, finalLine);
            }

            e.getPlayer().sendMessage(Tools.replaceColor(start + message + "&r\n" + end));
            return;
        }

        if (e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
            if (!Permission.Item.INSPECTOR_IMPLEMENTATION.has(player)) {
                Message.getMessage("OnUse.NoPerm.Inspector.Implementation", player, false)
                        .replace(e.getClickedBlock())
                        .replace(Permission.Item.INSPECTOR_IMPLEMENTATION)
                        .send(player);
                return;
            }
            StringBuilder message = new StringBuilder("&a&l&n" + e.getClickedBlock().getBlockData().getMaterial() + " :&r\n\n");
            for (Class<?> c : e.getClickedBlock().getBlockData().getClass().getInterfaces()) {
                message.append("   &8» &9").append(c.getName()).append("\n");
            }
            e.getPlayer().sendMessage(Tools.replaceColor(start + message + end));
        }
    }
}
