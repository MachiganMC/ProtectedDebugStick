package be.machigan.protecteddebugstick.action;

import be.machigan.protecteddebugstick.property.PropertyAction;
import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.type.Piston;
import org.jetbrains.annotations.NotNull;

public class ExtendableAction implements PropertyAction {
    @Override
    public void modify(@NotNull BlockData data, @NotNull Block block) throws ClassCastException {
        Piston pistonData = (Piston) data;

        pistonData.setExtended(!pistonData.isExtended());

        block.setBlockData(pistonData);
    }

    @Override
    public @NotNull String getValue(@NotNull BlockData data) throws ClassCastException {
        Piston pistonData = (Piston) data;
        return Boolean.toString(pistonData.isExtended());
    }
}
