package be.machigan.protecteddebugstick.action;

import be.machigan.protecteddebugstick.property.PropertyAction;
import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.type.Sapling;
import org.jetbrains.annotations.NotNull;

public class StageAction implements PropertyAction {
    @Override
    public void modify(@NotNull BlockData data, @NotNull Block block) throws ClassCastException {
        Sapling saplingData = (Sapling) data;

        if (saplingData.getStage() == saplingData.getMaximumStage()) {
            saplingData.setStage(0);
        } else {
            saplingData.setStage(saplingData.getStage() + 1);
        }

        block.setBlockData(saplingData);
    }

    @Override
    public @NotNull String getValue(@NotNull BlockData data) throws ClassCastException {
        return Integer.toString(((Sapling) data).getStage());
    }
}
