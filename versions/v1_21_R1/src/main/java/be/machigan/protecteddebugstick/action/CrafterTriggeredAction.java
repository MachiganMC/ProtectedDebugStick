package be.machigan.protecteddebugstick.action;

import be.machigan.protecteddebugstick.property.PropertyAction;
import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.type.Crafter;
import org.jetbrains.annotations.NotNull;

public class CrafterTriggeredAction implements PropertyAction {
    @Override
    public void modify(@NotNull BlockData data, @NotNull Block block) throws ClassCastException {
        Crafter crafter = (Crafter) data;
        crafter.setTriggered(!crafter.isTriggered());
        block.setBlockData(crafter);
    }

    @Override
    public @NotNull String getValue(@NotNull BlockData data) throws ClassCastException {
        return Boolean.toString(((Crafter) data).isTriggered());
    }
}
