package be.machigan.protecteddebugstick.action;

import be.machigan.protecteddebugstick.property.PropertyAction;
import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.Hatchable;
import org.jetbrains.annotations.NotNull;

public class HatchAction implements PropertyAction {

    @Override
    public void modify(@NotNull BlockData data, @NotNull Block block) throws ClassCastException {
        Hatchable eggData = (Hatchable) data;

        if (eggData.getHatch() == eggData.getMaximumHatch()) {
            eggData.setHatch(0);
        } else {
            eggData.setHatch(eggData.getHatch() + 1);
        }

        block.setBlockData(eggData);
    }

    @Override
    public @NotNull String getValue(@NotNull BlockData data) throws ClassCastException {
        Hatchable eggData = (Hatchable) data;
        return Integer.toString(eggData.getHatch());
    }
}
