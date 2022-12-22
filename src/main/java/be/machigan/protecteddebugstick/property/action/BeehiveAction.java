package be.machigan.protecteddebugstick.property.action;

import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.type.Beehive;
import org.jetbrains.annotations.NotNull;

public class BeehiveAction implements PropertyAction {
    @Override
    public @NotNull String modify(@NotNull BlockData data, @NotNull Block block) throws ClassCastException {
        Beehive beehiveData = (Beehive) data;

        if (beehiveData.getHoneyLevel() == beehiveData.getMaximumHoneyLevel()) {
            beehiveData.setHoneyLevel(0);
        } else {
            beehiveData.setHoneyLevel(beehiveData.getHoneyLevel() + 1);
        }

        block.setBlockData(beehiveData);
        return Integer.toString(beehiveData.getHoneyLevel());
    }
}
