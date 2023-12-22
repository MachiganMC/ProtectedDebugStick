package be.machigan.protecteddebugstick.action;

import be.machigan.protecteddebugstick.property.PropertyAction;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.type.Farmland;
import org.jetbrains.annotations.NotNull;

public class MoistureAction implements PropertyAction {

    @Override
    public void modify(@NotNull BlockData data, @NotNull Block block, @NotNull BlockFace blockFace) throws ClassCastException {
        Farmland farmland = (Farmland) data;

        if (farmland.getMoisture() == farmland.getMaximumMoisture()) {
            farmland.setMoisture(0);
        } else {
            farmland.setMoisture(farmland.getMoisture() + 1);
        }

        block.setBlockData(farmland);
    }

    @Override
    public @NotNull String getValue(@NotNull BlockData data, @NotNull BlockFace blockFace) throws ClassCastException {
        Farmland farmland = (Farmland) data;
        return Integer.toString(farmland.getMoisture());
    }
}
