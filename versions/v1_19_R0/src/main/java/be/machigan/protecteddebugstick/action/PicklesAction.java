package be.machigan.protecteddebugstick.action;

import be.machigan.protecteddebugstick.property.PropertyAction;
import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.type.SeaPickle;
import org.jetbrains.annotations.NotNull;

public class PicklesAction implements PropertyAction {

    @Override
    public void modify(@NotNull BlockData data, @NotNull Block block) throws ClassCastException {
        SeaPickle seaPickle = (SeaPickle) data;

        if (seaPickle.getPickles() == seaPickle.getMaximumPickles()) {
            seaPickle.setPickles(seaPickle.getMinimumPickles());
        } else {
            seaPickle.setPickles(seaPickle.getPickles() + 1);
        }

        block.setBlockData(seaPickle);
    }

    @Override
    public @NotNull String getValue(@NotNull BlockData data) throws ClassCastException {
        SeaPickle seaPickle = (SeaPickle) data;
        return Integer.toString(seaPickle.getPickles());
    }
}
