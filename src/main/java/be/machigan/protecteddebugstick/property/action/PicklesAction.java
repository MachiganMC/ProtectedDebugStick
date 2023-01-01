package be.machigan.protecteddebugstick.property.action;

import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.type.SeaPickle;
import org.jetbrains.annotations.NotNull;

public class PicklesAction implements PropertyAction {

    @Override
    public void modify(@NotNull BlockData data, @NotNull Block block, @NotNull BlockFace blockFace) throws ClassCastException {
        SeaPickle seaPickle = (SeaPickle) data;

        if (seaPickle.getPickles() == seaPickle.getMaximumPickles()) {
            seaPickle.setPickles(seaPickle.getMinimumPickles());
        } else {
            seaPickle.setPickles(seaPickle.getPickles() + 1);
        }

        block.setBlockData(seaPickle);
    }

    @Override
    public @NotNull String getValue(@NotNull BlockData data, @NotNull BlockFace blockFace) throws ClassCastException {
        SeaPickle seaPickle = (SeaPickle) data;
        return Integer.toString(seaPickle.getPickles());
    }
}
