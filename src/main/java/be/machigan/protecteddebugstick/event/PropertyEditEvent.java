package be.machigan.protecteddebugstick.event;

import be.machigan.protecteddebugstick.def.DebugStick;
import be.machigan.protecteddebugstick.persistent.LocationListDataType;
import be.machigan.protecteddebugstick.property.Property;
import be.machigan.protecteddebugstick.utils.Config;
import be.machigan.protecteddebugstick.utils.Message;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class PropertyEditEvent {
    private final Property property;
    private final Player player;
    private final Block block;
    private final BlockFace face;
    private final ItemStack itemInHand;
    private final String blockDataOldValue;

    @Nullable String blockDataNewValue;

    public PropertyEditEvent(@NotNull Property property, @NotNull Player player, @NotNull Block block, @NotNull BlockFace face) {
        this.property = property;
        this.player = player;
        this.block = block;
        this.face = face;
        this.itemInHand = player.getInventory().getItemInMainHand();
        this.blockDataOldValue = this.property.getAction().getValue(block.getBlockData(), face);
    }

    public void sendNoPermPropertyMessage() {
        Message.getMessage("OnUse.NoPerm.Property", this.player, false)
                .replace(this.property)
                .replace("{perm}", this.property.getPermission().toString())
                .send(this.player);
    }

    public boolean hasNotEnoughDurability() {
        return DebugStick.isDebugStick(this.itemInHand)
                && DebugStick.hasNotEnoughDurability(this.itemInHand, this.property);
    }

    public void sendNotEnoughDurabilityMessage() {
        Message.getMessage("OnUse.NotEnoughDurability", this.player, false)
                .replace(this.property)
                .replace("{need}", Integer.toString(this.property.getDurability()))
                .send(this.player);
    }

    public void registerEditedBlock() {
        LocationListDataType.addNewBlock(this.block);
    }

    public void editBlockData() {
        this.property.getAction().modify(block.getBlockData(), this.block, this.face);
        this.blockDataNewValue = this.property.getAction().getValue(block.getBlockData(), this.face);
    }

    public void sendSuccessMessage() {
        Message.getMessage("OnUse.Success", player, false)
                .replace(block)
                .replace(this.property)
                .replace("{value}", this.blockDataNewValue)
                .send(player);
    }

    public void removeDurability() {
        if (DebugStick.isInfinityDebugStick(this.itemInHand))
            return;

        DebugStick.removeDurability(this.player, this.property.getDurability());
        if (DebugStick.willBreak(this.itemInHand)) {
            this.player.getInventory().setItemInMainHand(null);
            this.sendDebugStickBrokeMessage();
        } else if (Config.PreventPlayerWhenBreaking.isEnable()) {
            this.sendAlertDurabilityMessage();
        }
    }

    public void sendDebugStickBrokeMessage() {
        Message.getMessage("OnUse.Break", this.player, false)
                .replace(this.block)
                .replace(this.property)
                .replace("{value}", this.blockDataNewValue)
                .send(this.player);
    }

    public void sendAlertDurabilityMessage() {
        if (DebugStick.isBasicDebugStick(this.itemInHand) && this.mustSendAlertMessage()) {
            Message.getMessage("OnUse.WarnBreakMessage", this.player, false)
                    .replace(this.block)
                    .replace(this.property)
                    .replace("{value}", this.getBlockDataNewValue())
                    .replace("{durability}", Integer.toString(DebugStick.getDurability(this.itemInHand)))
                    .send(this.player);
        }
    }

    public boolean mustSendAlertMessage() {
        int currentDurability = DebugStick.getDurability(this.itemInHand);
        int durabilityPrevention = Config.PreventPlayerWhenBreaking.getDurability();
        boolean mustSendOnce = Config.PreventPlayerWhenBreaking.mustSendOnce();
        return
                (
                        !mustSendOnce
                        && currentDurability <= durabilityPrevention
                )
                || (
                        mustSendOnce
                        && (currentDurability + this.property.getDurability()) > durabilityPrevention
                        && currentDurability <= durabilityPrevention
                );
    }

    @Nullable
    public String getBlockDataNewValue() {
        return this.blockDataNewValue;
    }

}
