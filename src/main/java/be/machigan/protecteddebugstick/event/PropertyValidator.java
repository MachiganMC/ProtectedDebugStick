package be.machigan.protecteddebugstick.event;

import be.machigan.protecteddebugstick.def.DebugStick;
import be.machigan.protecteddebugstick.def.DebugStickDataType;
import be.machigan.protecteddebugstick.persistent.LocationListDataType;
import be.machigan.protecteddebugstick.property.Property;
import be.machigan.protecteddebugstick.utils.Config;
import be.machigan.protecteddebugstick.utils.Message;
import be.machigan.protecteddebugstick.utils.Permission;
import org.apache.commons.lang3.SerializationException;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class PropertyValidator {
    private final Player player;
    private final Block clickedBlock;
    private final BlockFace face;
    private final BlockData data;

    @Nullable List<Property> validProperties;

    public PropertyValidator(@NotNull Player player, @NotNull Block block, @NotNull BlockFace face) {
        this.player = player;
        this.clickedBlock = block;
        this.face = face;
        this.data = this.clickedBlock.getBlockData();
    }

    public boolean isWorldBlackListed() {
        return Config.BlackList.getWorlds().contains(this.clickedBlock.getWorld());
    }

    public void sendWorldBlackListedMessage() {
        Message.getMessage("OnUse.BlackList.World", this.player, false)
                .replace(this.clickedBlock)
                .send(this.player);
    }

    public boolean isMaterialBlackListed() {
        return Config.BlackList.getMaterials().contains(this.clickedBlock.getType());
    }

    public void sendMaterialBlackListedMessage() {
        Message.getMessage("OnUse.BlackList.Material", this.player, false)
                .replace(this.clickedBlock)
                .send(this.player);
    }

    @NotNull
    public List<Property> getValidProperties() {
        if (this.validProperties != null)
            return this.validProperties;

        List<Property> properties = new ArrayList<>();
        for (Property property : Property.values()) {
            try {
                property.getDataClass().cast(this.data);
                if (Config.Settings.hideNoPermProperty() && !property.getPermission().has(this.player))
                    throw new ClassCastException();
            } catch (ClassCastException exception) {
                continue;
            }
            properties.add(property);
        }
        this.validProperties = properties;
        return properties;
    }

    public void sendNoValidPropertyMessage() {
        Message.getMessage("OnUse.NoPropertyType", this.player, false)
                .replace(this.clickedBlock)
                .send(this.player);
    }

    public boolean isDebugStickContainsCorruptedData() {
        try {
            ItemMeta itemMeta = this.player.getInventory().getItemInMainHand().getItemMeta();
            if (itemMeta == null)return true;
            itemMeta.getPersistentDataContainer().get(DebugStick.DEBUG_STICK_KEY, DebugStickDataType.INSTANCE);
            return false;
        } catch (SerializationException e) {
            return true;
        }
    }

    public void sendDebugStickContainsCorruptedDataMessage() {
        Message.getMessage("OnUse.InvalidDebugStick", this.player, true).send(this.player);
    }

    @NotNull
    public Property getCurrentPropertyOnDebugStick() {
        Property property = this.player.getInventory().getItemInMainHand().getItemMeta().getPersistentDataContainer()
                .get(DebugStick.DEBUG_STICK_KEY, DebugStickDataType.INSTANCE)
                .getDebugStick().getCurrentProperty();
        List<Property> properties = this.getValidProperties();
        if (property == null)
            return properties.get(0);
        return properties.contains(property) ? property : properties.get(0);
    }

    public boolean playerCannotEditWithCurrentDebugStick() {
        ItemStack item = this.player.getInventory().getItemInMainHand();
        return !((DebugStick.isBasicDebugStick(item) && Permission.Item.BASIC_EDIT.has(this.player))
                || (DebugStick.isInfinityDebugStick(item) && Permission.Item.INFINITE_EDIT.has(this.player)));
    }

    public void sendPlayerCannotEditWithCurrentDebugStickMessage() {
        boolean isBasicDebugStick = DebugStick.isBasicDebugStick(this.player.getInventory().getItemInMainHand());
        Message message;
        if (isBasicDebugStick) {
            message = Message.getMessage("OnUse.NoPerm.Basic.Edit", this.player, false).replace(Permission.Item.BASIC_EDIT);
        }  else {
            message = Message.getMessage("OnUse.NoPerm.Infinite.Edit", this.player, false).replace(Permission.Item.INFINITE_EDIT);
        }
        message.replace(this.clickedBlock).send(this.player);
    }


    public static boolean isPlayerHoldADebugStick(@NotNull PlayerInteractEvent e) {
        if (e.getHand() == null)
            return false;

        if (!e.getHand().equals(EquipmentSlot.HAND))
            return false;

        return DebugStick.isDebugStick(e.getPlayer().getInventory().getItemInMainHand());
    }

    public void removeFromEditedBlocks() {
        LocationListDataType.removeBlock(this.clickedBlock);
        Message.getMessage("OnUse.RemovePermanentValue", this.player, false)
                .replace(this.clickedBlock)
                .send(this.player);
    }

    public boolean playerCanEditAtLocation() {
        if (Permission.Bypass.PLUGIN_BLOCK.has(this.player)){
            return true;
        }
        BlockPlaceEvent event = new BlockPlaceEvent(
                this.clickedBlock,
                this.clickedBlock.getState(),
                this.clickedBlock,
                new ItemStack(Material.AIR),
                this.player,
                true,
                EquipmentSlot.HAND);
        Bukkit.getServer().getPluginManager().callEvent(event);
        return !event.isCancelled();
    }

    public void sendCannotEditAtLocationMessage() {
        Message.getMessage("OnUse.PluginPrevent", this.player, false)
                .replace(this.clickedBlock)
                .send(this.player);
    }

    public boolean playerCanSeePropertiesWithCurrentDebutStick() {
        ItemStack item = this.player.getInventory().getItemInMainHand();
        return (DebugStick.isBasicDebugStick(item) && Permission.Item.BASIC_SEE.has(this.player))
                || (DebugStick.isInfinityDebugStick(item) && Permission.Item.INFINITE_SEE.has(this.player));
    }

    public void sendPlayerCannotSeePropertiesWithCurrentDebugStick() {
        boolean isBasicDebugStick = DebugStick.isBasicDebugStick(this.player.getInventory().getItemInMainHand());
        Message message;
        if (isBasicDebugStick) {
            message = Message.getMessage("OnUse.NoPerm.Basic.See", this.player, false).replace(Permission.Item.BASIC_SEE);
        }  else {
            message = Message.getMessage("OnUse.NoPerm.Infinite.See", this.player, false).replace(Permission.Item.INFINITE_SEE);
        }
        message.replace(this.clickedBlock).send(this.player);
    }

    public void sendListEditablePropertiesMessage() {
        Message.getMessage("OnUse.ListProperties.Before", player, false)
                .replace(this.clickedBlock)
                .send(player);
        for (Property property : this.getValidProperties()) {
            Message messages;
            if (property.equals(this.getCurrentPropertyOnDebugStick())) {
                messages = Message.getMessage("OnUse.ListProperties.Current", this.player, true);
            } else {
                messages = Message.getMessage("OnUse.ListProperties.Property", this.player, true);
            }
            messages.replace(this.clickedBlock)
                    .replace(property)
                    .replace("{value}", property.getAction().getValue(this.data, this.face))
                    .send(this.player);
        }
        Message.getMessage("OnUse.ListProperties.After", this.player, false)
                .replace(this.clickedBlock)
                .send(this.player);
    }

    public void changeCurrentPropertyOnDebugStick() {
        List<Property> properties = this.getValidProperties();
        int index = properties.indexOf(this.getCurrentPropertyOnDebugStick());
        DebugStick.changeCurrentProperty(
                index == -1 || index == properties.size() - 1 ?
                properties.get(0) :
                properties.get(index + 1),
                this.player.getInventory().getItemInMainHand()
        );
    }

    public void sendPropertyOnDebugStickChangedMessage() {
        Message.getMessage("OnUse.ChangeProperty", this.player, false)
                .replace(this.clickedBlock)
                .replace(this.getCurrentPropertyOnDebugStick())
                .send(this.player);
    }

    public static boolean isPlayerClickOnABlock(@Nullable Block block) {
        return (block != null && block.getBlockData() != null);
    }

    @NotNull
    public Player getPlayer() {
        return player;
    }

    @NotNull
    public Block getClickedBlock() {
        return clickedBlock;
    }

    @NotNull
    public BlockData getData() {
        return data;
    }

    @NotNull
    public BlockFace getFace() {
        return face;
    }
}
