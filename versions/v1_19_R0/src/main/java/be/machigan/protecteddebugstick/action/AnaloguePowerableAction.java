package be.machigan.protecteddebugstick.action;

import be.machigan.protecteddebugstick.property.PropertyAction;
import org.bukkit.block.Block;
import org.bukkit.block.data.AnaloguePowerable;
import org.bukkit.block.data.BlockData;
import org.jetbrains.annotations.NotNull;

public class AnaloguePowerableAction implements PropertyAction {
    @Override
    public void modify(@NotNull BlockData data, @NotNull Block block) throws ClassCastException {
        AnaloguePowerable powerableData = (AnaloguePowerable) data;

        if (powerableData.getPower() == powerableData.getMaximumPower()) {
            powerableData.setPower(0);
        } else {
            powerableData.setPower(powerableData.getPower() + 1);
        }

        block.setBlockData(powerableData);
    }

    @Override
    public @NotNull String getValue(@NotNull BlockData data) throws ClassCastException {
        return Integer.toString(((AnaloguePowerable) data).getPower());
    }
}
