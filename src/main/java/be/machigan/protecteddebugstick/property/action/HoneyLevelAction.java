package be.machigan.protecteddebugstick.property.action;

import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.type.Beehive;
import org.jetbrains.annotations.NotNull;

public class HoneyLevelAction implements PropertyAction {
    @Override
    public void modify(@NotNull BlockData data, @NotNull Block block, @NotNull BlockFace blockFace) throws ClassCastException {
        Beehive beehiveData = (Beehive) data;

        if (beehiveData.getHoneyLevel() == beehiveData.getMaximumHoneyLevel()) {
            beehiveData.setHoneyLevel(0);
        } else {
            beehiveData.setHoneyLevel(beehiveData.getHoneyLevel() + 1);
        }

        block.setBlockData(beehiveData);
    }

    @Override
    public @NotNull String getValue(@NotNull BlockData data, @NotNull BlockFace blockFace) throws ClassCastException {
        return Integer.toString(((Beehive) data).getHoneyLevel());
    }
}
