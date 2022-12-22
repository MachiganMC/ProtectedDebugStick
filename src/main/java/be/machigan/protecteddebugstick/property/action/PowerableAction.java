package be.machigan.protecteddebugstick.property.action;

import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.Powerable;
import org.jetbrains.annotations.NotNull;

public class PowerableAction implements PropertyAction {
    @Override
    public @NotNull String modify(@NotNull BlockData data, @NotNull Block block) throws ClassCastException {
        Powerable powerableData = (Powerable) data;
        powerableData.setPowered(!powerableData.isPowered());
        block.setBlockData(powerableData);
        return Boolean.toString(((Powerable) data).isPowered()).toUpperCase();
    }
}
