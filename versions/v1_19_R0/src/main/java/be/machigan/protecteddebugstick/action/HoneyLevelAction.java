package be.machigan.protecteddebugstick.action;

import be.machigan.protecteddebugstick.property.PropertyAction;
import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.type.Beehive;
import org.jetbrains.annotations.NotNull;

public class HoneyLevelAction implements PropertyAction {
    @Override
    public void modify(@NotNull BlockData data, @NotNull Block block) throws ClassCastException {
        Beehive beehiveData = (Beehive) data;

        if (beehiveData.getHoneyLevel() == beehiveData.getMaximumHoneyLevel()) {
            beehiveData.setHoneyLevel(0);
        } else {
            beehiveData.setHoneyLevel(beehiveData.getHoneyLevel() + 1);
        }

        block.setBlockData(beehiveData);
    }

    @Override
    public @NotNull String getValue(@NotNull BlockData data) throws ClassCastException {
        return Integer.toString(((Beehive) data).getHoneyLevel());
    }
}
