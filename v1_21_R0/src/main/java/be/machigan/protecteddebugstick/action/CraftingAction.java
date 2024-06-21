package be.machigan.protecteddebugstick.action;

import be.machigan.protecteddebugstick.property.PropertyAction;
import org.bukkit.block.Block;
import org.bukkit.block.data.type.Crafter;
import org.bukkit.block.data.BlockData;
import org.jetbrains.annotations.NotNull;

public class CraftingAction implements PropertyAction {
    @Override
    public void modify(@NotNull BlockData data, @NotNull Block block) throws ClassCastException {
        Crafter crafter = (Crafter) data;
        crafter.setCrafting(!crafter.isCrafting());
        block.setBlockData(crafter);
    }

    @Override
    public @NotNull String getValue(@NotNull BlockData data) throws ClassCastException {
        return Boolean.toString(((Crafter) data).isCrafting());
    }
}
