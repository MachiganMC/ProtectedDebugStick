package be.machigan.protecteddebugstick.event;

import be.machigan.protecteddebugstick.def.DebugStick;
import be.machigan.protecteddebugstick.property.Property;
import be.machigan.protecteddebugstick.utils.Config;
import be.machigan.protecteddebugstick.utils.Message;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;
import java.util.List;

public class OnUse implements Listener {
    @EventHandler
    public static void onUse(PlayerInteractEvent e) {
        if (e.getHand() == null)
            return;

        if (!e.getHand().equals(EquipmentSlot.HAND))
           return;

        ItemStack item = e.getPlayer().getInventory().getItemInMainHand();
        if (item == null)
            return;
        if (!DebugStick.isDebugStick(item))
            return;

        e.setCancelled(true);
        if (!e.getPlayer().hasPermission("pds.debugstick.use"))
            return;

        if (e.getClickedBlock() == null)
            return;

        if (e.getClickedBlock().getBlockData() == null)
            return;

        if (Config.BlackList.getWorlds().contains(e.getClickedBlock().getLocation().getWorld())) {
            Message.getMessage("OnUse.BlackList.World", e.getPlayer(), false)
                    .replace(e.getClickedBlock())
                    .send(e.getPlayer());
            return;
        }

        if (Config.BlackList.getMaterials().contains(e.getClickedBlock().getType())) {
            Message.getMessage("OnUse.BlackList.Material", e.getPlayer(), false)
                    .replace(e.getClickedBlock())
                    .send(e.getPlayer());
            return;
        }

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

        Player player = e.getPlayer();
        Block block = e.getClickedBlock();
        BlockData data = e.getClickedBlock().getBlockData();
        List<Property> properties = new ArrayList<>();
        for (Property property : Property.values()) {
            try {
                property.getDataClass().cast(data);
            } catch (ClassCastException exception) {
                continue;
            }
            properties.add(property);
        }

        if (properties.isEmpty()) {
            Message.getMessage("OnUse.NoPropertyType", player, false)
                    .replace(block)
                    .send(player);
            return;
        }


        String propertyStr = item.getItemMeta().getPersistentDataContainer().get(DebugStick.CURRENT_PROPERTY, PersistentDataType.STRING);
        Property current;
        int index;
        try {
            current = Property.valueOf(propertyStr);
            if (current == null)
                throw new NullPointerException();
            index = properties.indexOf(current);
            if (index == -1)
                throw new NullPointerException();
        } catch (NullPointerException | IllegalArgumentException exception) {
            index = 0;
            current = properties.get(0);
        }


        if (e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
            current.edit(player, block, e.getBlockFace());
            return;
        }

        if (e.getAction().equals(Action.LEFT_CLICK_BLOCK)) {
            if (player.isSneaking()) {
                Message.getMessage("OnUse.ListProperties.Before", player, false)
                        .replace(block)
                        .send(player);
                for (Property property : properties) {
                    if (property.equals(current)) {
                        Message.getMessage("OnUse.ListProperties.Current", player, true)
                                .replace(block)
                                .replace(property)
                                .replace("{value}", property.getAction().getValue(data, e.getBlockFace()))
                                .send(player);
                    } else {
                        Message.getMessage("OnUse.ListProperties.Property", player, true)
                                .replace(block)
                                .replace(property)
                                .replace("{value}", property.getAction().getValue(data, e.getBlockFace()))
                                .send(player);
                    }
                }
                Message.getMessage("OnUse.ListProperties.After", player, false)
                        .replace(block)
                        .send(player);
                return;
            }

            if (index == properties.size() - 1) {
                current = properties.get(0);
            } else {
                current = properties.get(index + 1);
            }
            ItemMeta meta = item.getItemMeta();
            meta.getPersistentDataContainer().set(DebugStick.CURRENT_PROPERTY, PersistentDataType.STRING, current.name());
            item.setItemMeta(meta);
            Message.getMessage("OnUse.ChangeProperty", player, false)
                    .replace(block)
                    .replace(current)
                    .send(player);
        }
    }
}
