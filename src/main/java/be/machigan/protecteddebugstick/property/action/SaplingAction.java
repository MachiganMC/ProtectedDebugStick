package be.machigan.protecteddebugstick.property.action;

import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.type.Sapling;
import org.jetbrains.annotations.NotNull;

public class SaplingAction implements PropertyAction {
    @Override
    public void modify(@NotNull BlockData data, @NotNull Block block, @NotNull BlockFace blockFace) throws ClassCastException {
        Sapling saplingData = (Sapling) data;

        if (saplingData.getStage() == saplingData.getMaximumStage()) {
            saplingData.setStage(0);
        } else {
            saplingData.setStage(saplingData.getStage() + 1);
        }

        block.setBlockData(saplingData);
    }

    @Override
    public @NotNull String getValue(@NotNull BlockData data, @NotNull BlockFace blockFace) throws ClassCastException {
        return Integer.toString(((Sapling) data).getStage());
    }
}
