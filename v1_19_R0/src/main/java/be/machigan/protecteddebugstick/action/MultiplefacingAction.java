package be.machigan.protecteddebugstick.action;

import be.machigan.protecteddebugstick.property.PropertyAction;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.MultipleFacing;
import org.jetbrains.annotations.NotNull;

public class MultiplefacingAction implements PropertyAction {
    private final BlockFace blockFace;

    public MultiplefacingAction(BlockFace blockFace) {
        this.blockFace = blockFace;
    }

    @Override
    public void modify(@NotNull BlockData data, @NotNull Block block) throws ClassCastException {
        MultipleFacing multipleFacingData = (MultipleFacing) data;

        multipleFacingData.setFace(this.blockFace, !multipleFacingData.hasFace(this.blockFace));
        block.setBlockData(multipleFacingData);
    }

    @Override
    public @NotNull String getValue(@NotNull BlockData data) throws ClassCastException {
        MultipleFacing multipleFacingData = (MultipleFacing) data;
        return Boolean.toString(multipleFacingData.hasFace(this.blockFace));
    }
}
