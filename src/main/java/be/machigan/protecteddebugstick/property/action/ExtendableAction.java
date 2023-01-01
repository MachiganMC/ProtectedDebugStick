package be.machigan.protecteddebugstick.property.action;

import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.type.Piston;
import org.jetbrains.annotations.NotNull;

public class ExtendableAction implements PropertyAction {
    @Override
    public void modify(@NotNull BlockData data, @NotNull Block block, @NotNull BlockFace blockFace) throws ClassCastException {
        Piston pistonData = (Piston) data;

        pistonData.setExtended(!pistonData.isExtended());

        block.setBlockData(pistonData);
    }

    @Override
    public @NotNull String getValue(@NotNull BlockData data, @NotNull BlockFace blockFace) throws ClassCastException {
        Piston pistonData = (Piston) data;
        return Boolean.toString(pistonData.isExtended());
    }
}
