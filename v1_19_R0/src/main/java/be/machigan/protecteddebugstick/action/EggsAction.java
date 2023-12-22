package be.machigan.protecteddebugstick.action;

import be.machigan.protecteddebugstick.property.PropertyAction;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.type.TurtleEgg;
import org.jetbrains.annotations.NotNull;

public class EggsAction implements PropertyAction {

    @Override
    public void modify(@NotNull BlockData data, @NotNull Block block, @NotNull BlockFace blockFace) throws ClassCastException {
        TurtleEgg eggData = (TurtleEgg) data;

        if (eggData.getEggs() == eggData.getMaximumEggs()) {
            eggData.setEggs(eggData.getMinimumEggs());
        } else {
            eggData.setEggs(eggData.getEggs() + 1);
        }

        block.setBlockData(eggData);
    }

    @Override
    public @NotNull String getValue(@NotNull BlockData data, @NotNull BlockFace blockFace) throws ClassCastException {
        TurtleEgg eggData = (TurtleEgg) data;
        return Integer.toString(eggData.getEggs());
    }
}
