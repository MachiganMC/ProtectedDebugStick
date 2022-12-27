package be.machigan.protecteddebugstick.property.action;

import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.AnaloguePowerable;
import org.bukkit.block.data.BlockData;
import org.jetbrains.annotations.NotNull;

public class PowerableAction implements PropertyAction {
    @Override
    public @NotNull void modify(@NotNull BlockData data, @NotNull Block block, @NotNull BlockFace blockFace) throws ClassCastException {
        AnaloguePowerable powerableData = (AnaloguePowerable) data;

        if (powerableData.getPower() == powerableData.getMaximumPower()) {
            powerableData.setPower(0);
        } else {
            powerableData.setPower(powerableData.getPower() + 1);
        }

        block.setBlockData(powerableData);
    }

    @Override
    public @NotNull String getValue(@NotNull BlockData data, @NotNull BlockFace blockFace) throws ClassCastException {
        return Integer.toString(((AnaloguePowerable) data).getPower());
    }
}
